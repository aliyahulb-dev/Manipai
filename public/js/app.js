const socket = io();

// State
let state = {
    token: localStorage.getItem('token'),
    user: JSON.parse(localStorage.getItem('user')),
    conversations: [],
    currentConversationId: null,
    messages: [],
    settings: JSON.parse(localStorage.getItem('settings')) || {
        theme: 'dark',
        language: 'en',
        notifications: true,
        ai: {
            provider: 'anthropic',
            baseUrl: '',
            apiKey: '',
            model: ''
        }
    }
};

// DOM Elements
const authView = document.getElementById('authView');
const chatView = document.getElementById('chatView');
const loginForm = document.getElementById('loginForm');
const registerForm = document.getElementById('registerForm');
const messagesContainer = document.getElementById('messagesContainer');
const messageInput = document.getElementById('messageInput');
const conversationsList = document.getElementById('conversationsList');
const settingsModal = document.getElementById('settingsModal');

// Initialization
function init() {
    applySettings();
    if (state.token) {
        verifyToken();
    } else {
        showAuth();
    }
}

// Auth Functions
function showAuth() {
    authView.classList.remove('hidden');
    chatView.classList.add('hidden');
}

function showChat() {
    authView.classList.add('hidden');
    chatView.classList.remove('hidden');
    if (state.user) {
        socket.emit('join', state.user.id);
    }
    loadConversations();
}

function switchTab(tab) {
    const tabs = document.querySelectorAll('.tab-btn');
    tabs.forEach(t => t.classList.remove('active'));
    
    if (tab === 'login') {
        tabs[0].classList.add('active');
        loginForm.classList.add('active');
        registerForm.classList.remove('active');
    } else {
        tabs[1].classList.add('active');
        loginForm.classList.remove('active');
        registerForm.classList.add('active');
    }
}

async function handleLogin(e) {
    e.preventDefault();
    const email = e.target[0].value;
    const password = e.target[1].value;

    try {
        const response = await fetch('/api/auth/login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ email, password })
        });
        const data = await response.json();
        
        if (response.ok) {
            state.token = data.token;
            state.user = data.user;
            localStorage.setItem('token', data.token);
            localStorage.setItem('user', JSON.stringify(data.user));
            showChat();
        } else {
            alert(data.error || 'Login failed');
        }
    } catch (err) {
        console.error('Login error:', err);
        alert('An error occurred during login');
    }
}

async function handleRegister(e) {
    e.preventDefault();
    const username = e.target[0].value;
    const email = e.target[1].value;
    const password = e.target[2].value;
    const confirmPassword = e.target[3].value;

    if (password !== confirmPassword) {
        return alert('Passwords do not match');
    }

    try {
        const response = await fetch('/api/auth/register', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username, email, password })
        });
        const data = await response.json();

        if (response.ok) {
            alert('Registration successful! Please login.');
            switchTab('login');
        } else {
            alert(data.error || 'Registration failed');
        }
    } catch (err) {
        console.error('Registration error:', err);
        alert('An error occurred during registration');
    }
}

async function verifyToken() {
    try {
        const response = await fetch('/api/auth/verify', {
            headers: { 'Authorization': `Bearer ${state.token}` }
        });
        if (response.ok) {
            showChat();
        } else {
            logout();
        }
    } catch (err) {
        console.error('Verify error:', err);
        logout();
    }
}

function logout() {
    state.token = null;
    state.user = null;
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    showAuth();
}

// Chat Functions
async function loadConversations() {
    try {
        const response = await fetch('/api/chat/conversations', {
            headers: { 'Authorization': `Bearer ${state.token}` }
        });
        const data = await response.json();
        state.conversations = data;
        renderConversations();
    } catch (err) {
        console.error('Load conversations error:', err);
    }
}

function renderConversations() {
    conversationsList.innerHTML = '';
    state.conversations.forEach(conv => {
        const container = document.createElement('div');
        container.className = `conversation-item-container ${state.currentConversationId === conv.id ? 'active' : ''}`;
        
        const item = document.createElement('div');
        item.className = 'conversation-item';
        item.textContent = conv.title || 'New Conversation';
        item.onclick = () => selectConversation(conv.id);
        
        const deleteBtn = document.createElement('button');
        deleteBtn.className = 'delete-conv-btn';
        deleteBtn.innerHTML = '×';
        deleteBtn.title = 'Delete conversation';
        deleteBtn.onclick = (e) => deleteConversation(e, conv.id);
        
        container.appendChild(item);
        container.appendChild(deleteBtn);
        conversationsList.appendChild(container);
    });
}

async function deleteConversation(e, id) {
    e.stopPropagation();
    if (!confirm('Are you sure you want to delete this conversation?')) return;

    try {
        const response = await fetch(`/api/chat/conversations/${id}`, {
            method: 'DELETE',
            headers: { 'Authorization': `Bearer ${state.token}` }
        });
        if (response.ok) {
            if (state.currentConversationId === id) {
                state.currentConversationId = null;
                state.messages = [];
                renderMessages();
            }
            loadConversations();
        }
    } catch (err) {
        console.error('Delete error:', err);
    }
}

async function selectConversation(id) {
    state.currentConversationId = id;
    renderConversations();
    try {
        const response = await fetch(`/api/chat/conversations/${id}/messages`, {
            headers: { 'Authorization': `Bearer ${state.token}` }
        });
        const data = await response.json();
        state.messages = data;
        renderMessages();
    } catch (err) {
        console.error('Load messages error:', err);
    }
}

async function startNewChat() {
    try {
        const response = await fetch('/api/chat/conversations', {
            method: 'POST',
            headers: { 
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${state.token}` 
            },
            body: JSON.stringify({ title: 'New Chat' })
        });
        const data = await response.json();
        state.currentConversationId = data.id;
        loadConversations();
        state.messages = [];
        renderMessages();
    } catch (err) {
        console.error('New chat error:', err);
    }
}

function renderMessages() {
    messagesContainer.innerHTML = '';
    state.messages.forEach(msg => {
        const msgDiv = document.createElement('div');
        msgDiv.className = `message ${msg.role}`;
        msgDiv.textContent = msg.content;
        messagesContainer.appendChild(msgDiv);
    });
    messagesContainer.scrollTop = messagesContainer.scrollHeight;
}

async function sendMessage(e) {
    e.preventDefault();
    const content = messageInput.value.trim();
    if (!content || !state.currentConversationId) return;

    // Add user message to UI immediately
    const userMsg = { role: 'user', content };
    state.messages.push(userMsg);
    renderMessages();
    messageInput.value = '';

    try {
        const response = await fetch(`/api/chat/conversations/${state.currentConversationId}/messages`, {
            method: 'POST',
            headers: { 
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${state.token}` 
            },
            body: JSON.stringify({ content })
        });
        const data = await response.json();
        if (!response.ok) {
            alert(data.error || 'Failed to send message');
        }
        // AI response will be handled via Socket.IO streaming events
    } catch (err) {
        console.error('Send message error:', err);
    }
}

async function loadStats() {
    try {
        const response = await fetch('/api/user/stats', {
            headers: { 'Authorization': `Bearer ${state.token}` }
        });
        const data = await response.json();
        const convStat = document.getElementById('convCount');
        const msgStat = document.getElementById('msgCount');
        if (convStat) convStat.textContent = data.conversationsCount;
        if (msgStat) msgStat.textContent = data.messagesCount;
    } catch (err) {
        console.error('Load stats error:', err);
    }
}

// Settings Functions
function openSettings() {
    document.getElementById('themeSelect').value = state.settings.theme;
    document.getElementById('languageSelect').value = state.settings.language;
    document.getElementById('notificationsToggle').checked = state.settings.notifications;
    
    // AI Settings
    const ai = state.settings.ai || { provider: 'anthropic', baseUrl: '', apiKey: '', model: '' };
    document.getElementById('aiProviderSelect').value = ai.provider;
    document.getElementById('aiBaseUrl').value = ai.baseUrl || '';
    document.getElementById('aiApiKey').value = ai.apiKey || '';
    document.getElementById('aiModelName').value = ai.model || '';
    
    toggleCustomAISettings();
    loadStats();
    settingsModal.classList.remove('hidden');
}

function toggleCustomAISettings() {
    const provider = document.getElementById('aiProviderSelect').value;
    const customSettings = document.getElementById('customAISettings');
    const baseUrlInput = document.getElementById('aiBaseUrl');
    
    if (provider === 'anthropic') {
        customSettings.classList.add('hidden');
    } else {
        customSettings.classList.remove('hidden');
        if (provider === 'nvidia' && !baseUrlInput.value) {
            baseUrlInput.value = 'https://integrate.api.nvidia.com/v1';
        } else if (provider === 'openai' && !baseUrlInput.value) {
            baseUrlInput.value = 'https://api.openai.com/v1';
        }
    }
}

function closeSettings() {
    settingsModal.classList.add('hidden');
}

async function saveSettings(e) {
    e.preventDefault();
    state.settings = {
        theme: document.getElementById('themeSelect').value,
        language: document.getElementById('languageSelect').value,
        notifications: document.getElementById('notificationsToggle').checked,
        ai: {
            provider: document.getElementById('aiProviderSelect').value,
            baseUrl: document.getElementById('aiBaseUrl').value,
            apiKey: document.getElementById('aiApiKey').value,
            model: document.getElementById('aiModelName').value
        }
    };
    
    localStorage.setItem('settings', JSON.stringify(state.settings));
    applySettings();
    
    try {
        await fetch('/api/user/settings', {
            method: 'PUT',
            headers: { 
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${state.token}` 
            },
            body: JSON.stringify(state.settings)
        });
    } catch (err) {
        console.error('Save settings error:', err);
    }
    
    closeSettings();
}

function applySettings() {
    document.body.setAttribute('data-theme', state.settings.theme);
}

// Socket.IO
socket.on('connect', () => {
    console.log('Connected to server');
    if (state.token && state.user) {
        socket.emit('join', state.user.id);
    }
});

socket.on('ai_response_start', (data) => {
    if (data.conversationId !== state.currentConversationId) return;
    
    const msgDiv = document.createElement('div');
    msgDiv.className = 'message ai streaming';
    msgDiv.id = `msg-${data.messageId}`;
    messagesContainer.appendChild(msgDiv);
    messagesContainer.scrollTop = messagesContainer.scrollHeight;
});

socket.on('ai_response_chunk', (data) => {
    if (data.conversationId !== state.currentConversationId) return;
    
    const msgDiv = document.getElementById(`msg-${data.messageId}`);
    if (msgDiv) {
        msgDiv.textContent += data.text;
        messagesContainer.scrollTop = messagesContainer.scrollHeight;
    }
});

socket.on('ai_response_end', (data) => {
    if (data.conversationId !== state.currentConversationId) return;
    
    const msgDiv = document.getElementById(`msg-${data.messageId}`);
    if (msgDiv) {
        msgDiv.classList.remove('streaming');
        // Add to state if not already there
        if (!state.messages.find(m => m.id === data.messageId)) {
            state.messages.push({
                id: data.messageId,
                role: 'ai',
                content: data.fullContent,
                createdAt: new Date()
            });
        }
    }
});

socket.on('ai_typing', (data) => {
    // Legacy event, could show a small indicator if needed
});

init();
