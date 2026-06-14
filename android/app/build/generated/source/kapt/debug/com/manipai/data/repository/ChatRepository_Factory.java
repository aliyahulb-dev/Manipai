package com.manipai.data.repository;

import com.manipai.data.api.ChatService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class ChatRepository_Factory implements Factory<ChatRepository> {
  private final Provider<ChatService> chatServiceProvider;

  public ChatRepository_Factory(Provider<ChatService> chatServiceProvider) {
    this.chatServiceProvider = chatServiceProvider;
  }

  @Override
  public ChatRepository get() {
    return newInstance(chatServiceProvider.get());
  }

  public static ChatRepository_Factory create(Provider<ChatService> chatServiceProvider) {
    return new ChatRepository_Factory(chatServiceProvider);
  }

  public static ChatRepository newInstance(ChatService chatService) {
    return new ChatRepository(chatService);
  }
}
