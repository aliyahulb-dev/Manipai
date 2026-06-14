package com.manipai.data.repository;

import com.manipai.data.TokenManager;
import com.manipai.data.api.AuthService;
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
public final class AuthRepository_Factory implements Factory<AuthRepository> {
  private final Provider<AuthService> authServiceProvider;

  private final Provider<TokenManager> tokenManagerProvider;

  public AuthRepository_Factory(Provider<AuthService> authServiceProvider,
      Provider<TokenManager> tokenManagerProvider) {
    this.authServiceProvider = authServiceProvider;
    this.tokenManagerProvider = tokenManagerProvider;
  }

  @Override
  public AuthRepository get() {
    return newInstance(authServiceProvider.get(), tokenManagerProvider.get());
  }

  public static AuthRepository_Factory create(Provider<AuthService> authServiceProvider,
      Provider<TokenManager> tokenManagerProvider) {
    return new AuthRepository_Factory(authServiceProvider, tokenManagerProvider);
  }

  public static AuthRepository newInstance(AuthService authService, TokenManager tokenManager) {
    return new AuthRepository(authService, tokenManager);
  }
}
