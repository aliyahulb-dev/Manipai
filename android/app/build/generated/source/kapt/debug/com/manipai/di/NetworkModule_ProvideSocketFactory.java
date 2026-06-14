package com.manipai.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import io.socket.client.Socket;
import javax.annotation.processing.Generated;

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
public final class NetworkModule_ProvideSocketFactory implements Factory<Socket> {
  @Override
  public Socket get() {
    return provideSocket();
  }

  public static NetworkModule_ProvideSocketFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static Socket provideSocket() {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideSocket());
  }

  private static final class InstanceHolder {
    private static final NetworkModule_ProvideSocketFactory INSTANCE = new NetworkModule_ProvideSocketFactory();
  }
}
