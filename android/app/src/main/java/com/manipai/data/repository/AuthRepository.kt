package com.manipai.data.repository

import com.manipai.data.TokenManager
import com.manipai.data.api.AuthService
import com.manipai.data.api.LoginRequest
import com.manipai.data.api.RegisterRequest
import com.manipai.data.models.LoginResponse
import com.manipai.data.models.RegisterResponse
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val authService: AuthService,
    private val tokenManager: TokenManager
) {
    private var currentUserId: String? = null

    suspend fun login(email: String, password: String): Result<LoginResponse> {
        return try {
            val response = authService.login(LoginRequest(email, password))
            if (response.isSuccessful && response.body() != null) {
                val loginResponse = response.body()!!
                tokenManager.saveToken(loginResponse.token)
                currentUserId = loginResponse.user.id
                Result.success(loginResponse)
            } else {
                Result.failure(Exception(response.errorBody()?.string() ?: "Login failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUserId(): String? {
        if (currentUserId != null) return currentUserId
        
        return try {
            val response = authService.verifyToken()
            if (response.isSuccessful && response.body() != null) {
                currentUserId = response.body()!!.user.id
                currentUserId
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun register(username: String, email: String, password: String): Result<RegisterResponse> {
        return try {
            val response = authService.register(RegisterRequest(username, email, password))
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.errorBody()?.string() ?: "Registration failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getToken(): String? = tokenManager.getToken()

    fun logout() {
        tokenManager.deleteToken()
    }
}
