package com.manipai.ui.chat;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u000f\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u000e\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u0012J\u000e\u0010\u001f\u001a\u00020\u001d2\u0006\u0010 \u001a\u00020\u000eJ\u000e\u0010!\u001a\u00020\u001d2\u0006\u0010\"\u001a\u00020\u000eJ\u0006\u0010#\u001a\u00020\u001dJ\u000e\u0010$\u001a\u00020\u001d2\u0006\u0010\"\u001a\u00020\u000eJ\b\u0010%\u001a\u00020\u001dH\u0014J\u0016\u0010&\u001a\u00020\u001d2\u0006\u0010\"\u001a\u00020\u000e2\u0006\u0010\'\u001a\u00020\u000eJ\b\u0010(\u001a\u00020\u001dH\u0002J\u0016\u0010)\u001a\u00020\u001d2\u0006\u0010*\u001a\u00020\u000e2\u0006\u0010+\u001a\u00020\u000eR\u001a\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\r\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0019\u0010\u0017\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0016R\u0017\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00100\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0016R\u001d\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u000b0\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006,"}, d2 = {"Lcom/manipai/ui/chat/ChatViewModel;", "Landroidx/lifecycle/ViewModel;", "chatRepository", "Lcom/manipai/data/repository/ChatRepository;", "authRepository", "Lcom/manipai/data/repository/AuthRepository;", "socketManager", "Lcom/manipai/data/socket/SocketManager;", "(Lcom/manipai/data/repository/ChatRepository;Lcom/manipai/data/repository/AuthRepository;Lcom/manipai/data/socket/SocketManager;)V", "_conversations", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "Lcom/manipai/data/models/Conversation;", "_error", "", "_isLoading", "", "_messages", "Lcom/manipai/data/models/Message;", "conversations", "Lkotlinx/coroutines/flow/StateFlow;", "getConversations", "()Lkotlinx/coroutines/flow/StateFlow;", "error", "getError", "isLoading", "messages", "getMessages", "addMessage", "", "message", "createConversation", "title", "deleteConversation", "conversationId", "loadConversations", "loadMessages", "onCleared", "sendMessage", "content", "setupSocket", "updateLastMessageContent", "messageId", "text", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel
public final class ChatViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.manipai.data.repository.ChatRepository chatRepository = null;
    @org.jetbrains.annotations.NotNull
    private final com.manipai.data.repository.AuthRepository authRepository = null;
    @org.jetbrains.annotations.NotNull
    private final com.manipai.data.socket.SocketManager socketManager = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.manipai.data.models.Conversation>> _conversations = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.manipai.data.models.Conversation>> conversations = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.manipai.data.models.Message>> _messages = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.manipai.data.models.Message>> messages = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isLoading = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isLoading = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _error = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> error = null;
    
    @javax.inject.Inject
    public ChatViewModel(@org.jetbrains.annotations.NotNull
    com.manipai.data.repository.ChatRepository chatRepository, @org.jetbrains.annotations.NotNull
    com.manipai.data.repository.AuthRepository authRepository, @org.jetbrains.annotations.NotNull
    com.manipai.data.socket.SocketManager socketManager) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.manipai.data.models.Conversation>> getConversations() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.manipai.data.models.Message>> getMessages() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isLoading() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getError() {
        return null;
    }
    
    private final void setupSocket() {
    }
    
    @java.lang.Override
    protected void onCleared() {
    }
    
    public final void loadConversations() {
    }
    
    public final void createConversation(@org.jetbrains.annotations.NotNull
    java.lang.String title) {
    }
    
    public final void loadMessages(@org.jetbrains.annotations.NotNull
    java.lang.String conversationId) {
    }
    
    public final void sendMessage(@org.jetbrains.annotations.NotNull
    java.lang.String conversationId, @org.jetbrains.annotations.NotNull
    java.lang.String content) {
    }
    
    public final void deleteConversation(@org.jetbrains.annotations.NotNull
    java.lang.String conversationId) {
    }
    
    public final void addMessage(@org.jetbrains.annotations.NotNull
    com.manipai.data.models.Message message) {
    }
    
    public final void updateLastMessageContent(@org.jetbrains.annotations.NotNull
    java.lang.String messageId, @org.jetbrains.annotations.NotNull
    java.lang.String text) {
    }
}