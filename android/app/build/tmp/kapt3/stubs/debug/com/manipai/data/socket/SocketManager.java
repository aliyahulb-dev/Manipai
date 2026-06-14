package com.manipai.data.socket;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0005\u001a\u00020\u0006J\u0006\u0010\u0007\u001a\u00020\u0006J\u000e\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\nJ&\u0010\u000b\u001a\u00020\u00062\u001e\u0010\f\u001a\u001a\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00060\rJ&\u0010\u000e\u001a\u00020\u00062\u001e\u0010\f\u001a\u001a\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00060\rJ \u0010\u000f\u001a\u00020\u00062\u0018\u0010\f\u001a\u0014\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00060\u0010R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lcom/manipai/data/socket/SocketManager;", "", "socket", "Lio/socket/client/Socket;", "(Lio/socket/client/Socket;)V", "connect", "", "disconnect", "join", "userId", "", "onResponseChunk", "callback", "Lkotlin/Function3;", "onResponseEnd", "onResponseStart", "Lkotlin/Function2;", "app_debug"})
public final class SocketManager {
    @org.jetbrains.annotations.NotNull
    private final io.socket.client.Socket socket = null;
    
    @javax.inject.Inject
    public SocketManager(@org.jetbrains.annotations.NotNull
    io.socket.client.Socket socket) {
        super();
    }
    
    public final void connect() {
    }
    
    public final void disconnect() {
    }
    
    public final void join(@org.jetbrains.annotations.NotNull
    java.lang.String userId) {
    }
    
    public final void onResponseStart(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.String, kotlin.Unit> callback) {
    }
    
    public final void onResponseChunk(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function3<? super java.lang.String, ? super java.lang.String, ? super java.lang.String, kotlin.Unit> callback) {
    }
    
    public final void onResponseEnd(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function3<? super java.lang.String, ? super java.lang.String, ? super java.lang.String, kotlin.Unit> callback) {
    }
}