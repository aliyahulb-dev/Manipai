package com.manipai.data.socket;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import io.socket.client.Socket;
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
public final class SocketManager_Factory implements Factory<SocketManager> {
  private final Provider<Socket> socketProvider;

  public SocketManager_Factory(Provider<Socket> socketProvider) {
    this.socketProvider = socketProvider;
  }

  @Override
  public SocketManager get() {
    return newInstance(socketProvider.get());
  }

  public static SocketManager_Factory create(Provider<Socket> socketProvider) {
    return new SocketManager_Factory(socketProvider);
  }

  public static SocketManager newInstance(Socket socket) {
    return new SocketManager(socket);
  }
}
