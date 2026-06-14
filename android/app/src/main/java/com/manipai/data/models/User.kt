package com.manipai.data.models

data class User(
    val id: String,
    val username: String,
    val email: String
)

data class LoginResponse(
    val token: String,
    val user: User
)

data class RegisterResponse(
    val message: String
)
