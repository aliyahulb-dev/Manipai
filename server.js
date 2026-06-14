import express from 'express';
import http from 'http';
import { Server } from 'socket.io';
import cors from 'cors';
import helmet from 'helmet';
import morgan from 'morgan';
import dotenv from 'dotenv';
import bcrypt from 'bcryptjs';
import jwt from 'jsonwebtoken';
import { Anthropic } from '@anthropic-ai/sdk';
import https from 'https';

dotenv.config();

const app = express();
const server = http.createServer(app);
const io = new Server(server, {
    cors: {
        origin: process.env.CORS_ORIGIN || '*',
        methods: ['GET', 'POST']
    }
});

const PORT = process.env.PORT || 3000;
const JWT_SECRET = process.env.SESSION_SECRET || 'fallback_secret_change_me';

// Anthropic SDK
const anthropic = new Anthropic({
    apiKey: process.env.ANTHROPIC_API_KEY,
});

// Middleware
app.use(helmet({
    contentSecurityPolicy: false, // For development and Socket.IO
}));
app.use(cors());
app.use(morgan('dev'));
app.use(express.json());
app.use(express.static('public'));

// In-memory Storage
const db = {
    users: [],
    conversations: [],
    messages: []
};

// Helper: Authentication Middleware
const authenticateToken = (req, res, next) => {
    const authHeader = req.headers['authorization'];
    const token = authHeader && authHeader.split(' ')[1];

    if (!token) return res.status(401).json({ error: 'Access denied' });

    jwt.verify(token, JWT_SECRET, (err, user) => {
        if (err) return res.status(403).json({ error: 'Invalid token' });
        req.user = user;
        next();
    });
};

// --- API Endpoints ---

// Authentication
app.post('/api/auth/register', async (req, res) => {
    const { username, email, password } = req.body;
    
    if (db.users.find(u => u.email === email)) {
        return res.status(400).json({ error: 'User already exists' });
    }

    const hashedPassword = await bcrypt.hash(password, 10);
    const user = { id: Date.now().toString(), username, email, password: hashedPassword };
    db.users.push(user);
    
    res.status(201).json({ message: 'User registered successfully' });
});

app.post('/api/auth/login', async (req, res) => {
    const { email, password } = req.body;
    const user = db.users.find(u => u.email === email);

    if (!user || !(await bcrypt.compare(password, user.password))) {
        return res.status(401).json({ error: 'Invalid email or password' });
    }

    const token = jwt.sign({ id: user.id, username: user.username }, JWT_SECRET, { expiresIn: '24h' });
    res.json({ token, user: { id: user.id, username: user.username, email: user.email } });
});

app.get('/api/auth/verify', authenticateToken, (req, res) => {
    res.json({ valid: true, user: req.user });
});

// Chat Conversations
app.get('/api/chat/conversations', authenticateToken, (req, res) => {
    const userConversations = db.conversations.filter(c => c.userId === req.user.id);
    res.json(userConversations);
});

app.post('/api/chat/conversations', authenticateToken, (req, res) => {
    const { title } = req.body;
    const conversation = {
        id: Date.now().toString(),
        userId: req.user.id,
        title: title || 'New Conversation',
        createdAt: new Date()
    };
    db.conversations.push(conversation);
    res.status(201).json(conversation);
});

app.get('/api/chat/conversations/:id/messages', authenticateToken, (req, res) => {
    const messages = db.messages.filter(m => m.conversationId === req.params.id);
    res.json(messages);
});

app.post('/api/chat/conversations/:id/messages', authenticateToken, async (req, res) => {
    const { content } = req.body;
    const conversationId = req.params.id;

    // Save user message
    const userMessage = {
        id: Date.now().toString(),
        conversationId,
        role: 'user',
        content,
        createdAt: new Date()
    };
    db.messages.push(userMessage);

    // Get conversation history
    const history = db.messages
        .filter(m => m.conversationId === conversationId)
        .slice(-10) // Limit to last 10 messages for context
        .map(m => ({
            role: m.role === 'ai' ? 'assistant' : 'user',
            content: m.content
        }));

    const user = db.users.find(u => u.id === req.user.id);
    const aiSettings = user?.settings?.ai || { provider: 'anthropic' };
    const aiMessageId = (Date.now() + 1).toString();

    try {
        let aiContent = '';

        if (aiSettings.provider === 'anthropic' || !aiSettings.provider) {
            // Use streaming (syntax for ^0.12.0)
            const stream = await anthropic.messages.create({
                model: process.env.AI_MODEL || 'claude-3-sonnet-20240229',
                max_tokens: parseInt(process.env.AI_MAX_TOKENS) || 1024,
                messages: history,
                stream: true,
            });

            // Notify client that AI is typing/starting
            io.to(req.user.id).emit('ai_response_start', { conversationId, messageId: aiMessageId });

            for await (const chunk of stream) {
                if (chunk.type === 'content_block_delta' && chunk.delta.text) {
                    const text = chunk.delta.text;
                    aiContent += text;
                    io.to(req.user.id).emit('ai_response_chunk', { 
                        conversationId, 
                        messageId: aiMessageId,
                        text 
                    });
                }
            }
        } else {
            // OpenAI-compatible (OpenAI, NVIDIA, Custom)
            const { baseUrl, apiKey, model } = aiSettings;
            const url = new URL(baseUrl.endsWith('/') ? baseUrl + 'chat/completions' : baseUrl + '/chat/completions');
            
            const body = JSON.stringify({
                model: model || 'gpt-3.5-turbo',
                messages: history,
                stream: true
            });

            const options = {
                hostname: url.hostname,
                port: url.port || (url.protocol === 'https:' ? 443 : 80),
                path: url.pathname + url.search,
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${apiKey}`,
                    'Content-Length': Buffer.byteLength(body)
                }
            };

            aiContent = await new Promise((resolve, reject) => {
                const aiReq = https.request(options, (aiRes) => {
                    let fullAiContent = '';
                    let buffer = '';
                    io.to(req.user.id).emit('ai_response_start', { conversationId, messageId: aiMessageId });

                    aiRes.on('data', (chunk) => {
                        buffer += chunk.toString();
                        const lines = buffer.split('\n');
                        buffer = lines.pop(); // Keep partial line in buffer

                        for (const line of lines) {
                            const trimmedLine = line.trim();
                            if (!trimmedLine || !trimmedLine.startsWith('data: ')) continue;
                            
                            const dataStr = trimmedLine.slice(6).trim();
                            if (dataStr === '[DONE]') continue;
                            
                            try {
                                const data = JSON.parse(dataStr);
                                const text = data.choices[0]?.delta?.content;
                                if (text) {
                                    fullAiContent += text;
                                    io.to(req.user.id).emit('ai_response_chunk', { 
                                        conversationId, 
                                        messageId: aiMessageId,
                                        text 
                                    });
                                }
                            } catch (e) {
                                // Ignore parse errors for incomplete JSON
                            }
                        }
                    });

                    aiRes.on('end', () => {
                        resolve(fullAiContent);
                    });

                    aiRes.on('error', (e) => reject(e));
                });

                aiReq.on('error', (e) => reject(e));
                aiReq.write(body);
                aiReq.end();
            });
        }

        const finalAiMessage = {
            id: aiMessageId,
            conversationId,
            role: 'ai',
            content: aiContent,
            createdAt: new Date()
        };
        db.messages.push(finalAiMessage);
        io.to(req.user.id).emit('ai_response_end', { 
            conversationId, 
            messageId: aiMessageId, 
            fullContent: aiContent 
        });

        res.json({ userMessage, streaming: true, aiMessageId });
    } catch (err) {
        console.error('AI API Error:', err);
        res.status(500).json({ error: 'AI response failed' });
    }
});

app.delete('/api/chat/conversations/:id', authenticateToken, (req, res) => {
    db.conversations = db.conversations.filter(c => c.id !== req.params.id);
    db.messages = db.messages.filter(m => m.conversationId !== req.params.id);
    res.json({ message: 'Conversation deleted' });
});

// User Profile & Settings
app.get('/api/user/profile', authenticateToken, (req, res) => {
    const user = db.users.find(u => u.id === req.user.id);
    if (!user) return res.status(404).json({ error: 'User not found' });
    res.json({ id: user.id, username: user.username, email: user.email });
});

app.get('/api/user/settings', authenticateToken, (req, res) => {
    // In-memory: settings could be stored on user object
    const user = db.users.find(u => u.id === req.user.id);
    res.json(user.settings || { theme: 'dark', language: 'en', notifications: true });
});

app.put('/api/user/settings', authenticateToken, (req, res) => {
    const user = db.users.find(u => u.id === req.user.id);
    user.settings = req.body;
    res.json({ message: 'Settings updated' });
});

app.get('/api/user/stats', authenticateToken, (req, res) => {
    const userConvs = db.conversations.filter(c => c.userId === req.user.id);
    const userMsgs = db.messages.filter(m => userConvs.find(c => c.id === m.conversationId));
    res.json({
        conversationsCount: userConvs.length,
        messagesCount: userMsgs.length
    });
});

// --- Socket.IO ---
io.on('connection', (socket) => {
    console.log('User connected:', socket.id);
    
    // Simple join room for the user
    socket.on('join', (userId) => {
        socket.join(userId);
        console.log(`User ${userId} joined room`);
    });

    socket.on('disconnect', () => {
        console.log('User disconnected:', socket.id);
    });
});

// Start Server
server.listen(PORT, () => {
    console.log(`Server running on port ${PORT}`);
});
