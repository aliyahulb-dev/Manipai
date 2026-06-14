package com.manipai.data.api

import com.manipai.data.models.*
import retrofit2.Response
import retrofit2.http.*

interface AuthService {
    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("api/auth/verify")
    suspend fun verifyToken(): Response<VerifyResponse>
}

data class RegisterRequest(val username: String, val email: String, val password: String)
data class LoginRequest(val email: String, val password: String)
data class VerifyResponse(val valid: Boolean, val user: User)
