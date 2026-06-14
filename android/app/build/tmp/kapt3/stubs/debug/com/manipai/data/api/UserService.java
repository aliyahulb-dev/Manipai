package com.manipai.data.api;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0005J\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0003H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0005J\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0003H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0005J!\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00032\b\b\u0001\u0010\f\u001a\u00020\u0007H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\r\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u000e"}, d2 = {"Lcom/manipai/data/api/UserService;", "", "getProfile", "Lretrofit2/Response;", "Lcom/manipai/data/models/User;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getSettings", "Lcom/manipai/data/models/Settings;", "getStats", "Lcom/manipai/data/models/UserStats;", "updateSettings", "Lcom/manipai/data/api/UpdateSettingsResponse;", "settings", "(Lcom/manipai/data/models/Settings;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface UserService {
    
    @retrofit2.http.GET(value = "api/user/profile")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getProfile(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.manipai.data.models.User>> $completion);
    
    @retrofit2.http.GET(value = "api/user/settings")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getSettings(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.manipai.data.models.Settings>> $completion);
    
    @retrofit2.http.PUT(value = "api/user/settings")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateSettings(@retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    com.manipai.data.models.Settings settings, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.manipai.data.api.UpdateSettingsResponse>> $completion);
    
    @retrofit2.http.GET(value = "api/user/stats")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getStats(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.manipai.data.models.UserStats>> $completion);
}