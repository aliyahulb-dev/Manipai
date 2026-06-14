package com.manipai.data.repository;

import com.manipai.data.api.UserService;
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
public final class UserRepository_Factory implements Factory<UserRepository> {
  private final Provider<UserService> userServiceProvider;

  public UserRepository_Factory(Provider<UserService> userServiceProvider) {
    this.userServiceProvider = userServiceProvider;
  }

  @Override
  public UserRepository get() {
    return newInstance(userServiceProvider.get());
  }

  public static UserRepository_Factory create(Provider<UserService> userServiceProvider) {
    return new UserRepository_Factory(userServiceProvider);
  }

  public static UserRepository newInstance(UserService userService) {
    return new UserRepository(userService);
  }
}
