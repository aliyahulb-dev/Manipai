package com.manipai.di;

import com.manipai.data.api.ChatService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class NetworkModule_ProvideChatServiceFactory implements Factory<ChatService> {
  private final Provider<Retrofit> retrofitProvider;

  public NetworkModule_ProvideChatServiceFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public ChatService get() {
    return provideChatService(retrofitProvider.get());
  }

  public static NetworkModule_ProvideChatServiceFactory create(
      Provider<Retrofit> retrofitProvider) {
    return new NetworkModule_ProvideChatServiceFactory(retrofitProvider);
  }

  public static ChatService provideChatService(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideChatService(retrofit));
  }
}
