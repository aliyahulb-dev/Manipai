package com.manipai.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manipai.data.models.Conversation
import com.manipai.data.models.Message
import com.manipai.data.repository.AuthRepository
import com.manipai.data.repository.ChatRepository
import com.manipai.data.socket.SocketManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val authRepository: AuthRepository,
    private val socketManager: SocketManager
) : ViewModel() {

    private val _conversations = MutableStateFlow<List<Conversation>>(emptyList())
    val conversations: StateFlow<List<Conversation>> = _conversations

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        setupSocket()
    }

    private fun setupSocket() {
        viewModelScope.launch {
            val userId = authRepository.getUserId()
            if (userId != null) {
                socketManager.connect()
                socketManager.join(userId)
            }
        }

        socketManager.onResponseStart { conversationId, messageId ->
            val aiMsg = Message(
                id = messageId,
                conversationId = conversationId,
                role = "ai",
                content = "",
                createdAt = java.util.Date()
            )
            addMessage(aiMsg)
        }

        socketManager.onResponseChunk { conversationId, messageId, text ->
            updateLastMessageContent(messageId, text)
        }

        socketManager.onResponseEnd { conversationId, messageId, fullContent ->
            // Optionally sync with full content to ensure accuracy
        }
    }

    override fun onCleared() {
        super.onCleared()
        socketManager.disconnect()
    }

    fun loadConversations() {
        viewModelScope.launch {
            _isLoading.value = true
            chatRepository.getConversations()
                .onSuccess { _conversations.value = it }
                .onFailure { _error.value = it.message }
            _isLoading.value = false
        }
    }

    fun createConversation(title: String) {
        viewModelScope.launch {
            chatRepository.createConversation(title)
                .onSuccess { 
                    loadConversations()
                }
                .onFailure { _error.value = it.message }
        }
    }

    fun loadMessages(conversationId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            chatRepository.getMessages(conversationId)
                .onSuccess { _messages.value = it }
                .onFailure { _error.value = it.message }
            _isLoading.value = false
        }
    }

    fun sendMessage(conversationId: String, content: String) {
        viewModelScope.launch {
            // Optimistic update
            val userMsg = Message(
                id = "temp-${System.currentTimeMillis()}",
                conversationId = conversationId,
                role = "user",
                content = content,
                createdAt = java.util.Date()
            )
            _messages.value = _messages.value + userMsg
            
            chatRepository.sendMessage(conversationId, content)
                .onFailure { 
                    _error.value = it.message
                    // Rollback if needed
                }
        }
    }

    fun deleteConversation(conversationId: String) {
        viewModelScope.launch {
            chatRepository.deleteConversation(conversationId)
                .onSuccess { loadConversations() }
                .onFailure { _error.value = it.message }
        }
    }
    
    fun addMessage(message: Message) {
        _messages.update { it + message }
    }

    fun updateLastMessageContent(messageId: String, text: String) {
        _messages.update { messages ->
            messages.map {
                if (it.id == messageId) it.copy(content = it.content + text) else it
            }
        }
    }
}
