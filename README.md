# Manip AI - Advanced Chatbot Application

Manip AI is a full-featured conversational AI application powered by Anthropic's Claude API. It provides a modern web interface with real-time chat capabilities, conversation management, and user authentication.

## Features

✨ **Core Features:**
- 🤖 AI-powered conversations using Anthropic Claude
- 💬 Real-time chat with WebSocket support
- 👤 User authentication and profiles
- 💾 In-memory conversation history
- ⚙️ Customizable user settings
- 📊 Usage statistics tracking
- 🎨 Modern dark/light theme support
- 📱 Responsive design for mobile and desktop
- 📦 Mobile-optimized (no native dependencies)

## Prerequisites

- Node.js (v16 or higher)
- npm or yarn
- Anthropic API key

## Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/aliyahulb-dev/Manipai.git
   cd Manipai
   ```

2. **Install dependencies:**
   ```bash
   npm install
   ```

3. **Create environment file:**
   ```bash
   cp .env.example .env
   ```

4. **Configure environment variables:**
   Edit `.env` and add your Anthropic API key:
   ```env
   ANTHROPIC_API_KEY=your_key_here
   PORT=3000
   NODE_ENV=development
   ```

## Running the Application

### Development Mode
```bash
npm run dev
```

### Production Mode
```bash
npm start
```

## Mobile Applications

### Android App
The project includes a native Android application built with Jetpack Compose and Hilt.

#### Development
1. Open the `android` folder in Android Studio.
2. Update `BASE_URL` in `NetworkModule.kt` to point to your backend.
3. Build and run on an emulator or physical device.

#### Publishing
1. Change `BASE_URL` to your production cloud URL (e.g., `https://manipai.onrender.com/`).
2. Go to **Build > Generate Signed Bundle / APK**.
3. Follow the wizard to create a release key and generate an `.aab` file for the Google Play Store.

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login user
- `POST /api/auth/verify` - Verify token

### Chat
- `GET /api/chat/conversations` - List user conversations
- `POST /api/chat/conversations` - Create new conversation
- `GET /api/chat/conversations/:id/messages` - Get messages from conversation
- `POST /api/chat/conversations/:id/messages` - Send message and get AI response
- `DELETE /api/chat/conversations/:id` - Delete conversation

### User
- `GET /api/user/profile` - Get user profile
- `GET /api/user/settings` - Get user settings
- `PUT /api/user/settings` - Update user settings
- `GET /api/user/stats` - Get user statistics

## Architecture

- **Backend**: Express.js with Node.js
- **Database**: In-memory storage (no external DB required)
- **Real-time**: Socket.IO for WebSocket communication
- **AI**: Anthropic Claude API
- **Frontend**: Vanilla JavaScript with modern CSS
- **Authentication**: JWT tokens + bcryptjs

## Security Features

- ✅ JWT-based authentication
- ✅ Password hashing with bcryptjs
- ✅ CORS protection
- ✅ Helmet.js for HTTP headers
- ✅ Session management
- ✅ Input validation
- ✅ Authorization middleware

## Mobile Build

This application is optimized for mobile deployment with:
- No native C++ dependencies
- Pure JavaScript implementation
- In-memory storage
- Minimal footprint

## Dependencies

- **express** - Web framework
- **@anthropic-ai/sdk** - Anthropic API client
- **socket.io** - Real-time communication
- **bcryptjs** - Password hashing
- **jsonwebtoken** - JWT authentication
- **cors** - CORS middleware
- **helmet** - Security headers
- **morgan** - HTTP logging

## License

This project is licensed under the MIT License.

---

**Manip AI v1.0.0** - Advanced AI Chatbot Application