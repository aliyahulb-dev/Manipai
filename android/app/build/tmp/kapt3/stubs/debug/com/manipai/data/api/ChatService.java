package com.manipai.data.api;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J!\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0007J!\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u00032\b\b\u0001\u0010\n\u001a\u00020\u000bH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\fJ\u001d\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u000e0\u0003H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000fJ\'\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u000e0\u00032\b\b\u0001\u0010\n\u001a\u00020\u000bH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\fJ+\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u00032\b\b\u0001\u0010\n\u001a\u00020\u000b2\b\b\u0001\u0010\u0005\u001a\u00020\u0014H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0015\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0016"}, d2 = {"Lcom/manipai/data/api/ChatService;", "", "createConversation", "Lretrofit2/Response;", "Lcom/manipai/data/models/Conversation;", "request", "Lcom/manipai/data/api/CreateConversationRequest;", "(Lcom/manipai/data/api/CreateConversationRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteConversation", "Lcom/manipai/data/api/DeleteResponse;", "conversationId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getConversations", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMessages", "Lcom/manipai/data/models/Message;", "sendMessage", "Lcom/manipai/data/api/SendMessageResponse;", "Lcom/manipai/data/api/SendMessageRequest;", "(Ljava/lang/String;Lcom/manipai/data/api/SendMessageRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface ChatService {
    
    @retrofit2.http.GET(value = "api/chat/conversations")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getConversations(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<java.util.List<com.manipai.data.models.Conversation>>> $completion);
    
    @retrofit2.http.POST(value = "api/chat/conversations")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object createConversation(@retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    com.manipai.data.api.CreateConversationRequest request, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.manipai.data.models.Conversation>> $completion);
    
    @retrofit2.http.GET(value = "api/chat/conversations/{id}/messages")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getMessages(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull
    java.lang.String conversationId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<java.util.List<com.manipai.data.models.Message>>> $completion);
    
    @retrofit2.http.POST(value = "api/chat/conversations/{id}/messages")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object sendMessage(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull
    java.lang.String conversationId, @retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    com.manipai.data.api.SendMessageRequest request, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.manipai.data.api.SendMessageResponse>> $completion);
    
    @retrofit2.http.DELETE(value = "api/chat/conversations/{id}")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteConversation(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull
    java.lang.String conversationId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.manipai.data.api.DeleteResponse>> $completion);
}