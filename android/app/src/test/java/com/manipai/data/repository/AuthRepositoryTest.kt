package com.manipai.data.repository

import com.manipai.data.TokenManager
import com.manipai.data.api.AuthService
import com.manipai.data.api.LoginRequest
import com.manipai.data.models.LoginResponse
import com.manipai.data.models.User
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.Response

class AuthRepositoryTest {

    private lateinit var authService: AuthService
    private lateinit var tokenManager: TokenManager
    private lateinit var authRepository: AuthRepository

    @Before
    fun setup() {
        authService = mock()
        tokenManager = mock()
        authRepository = AuthRepository(authService, tokenManager)
    }

    @Test
    fun `login successful should save token and return success`() = runTest {
        // Arrange
        val user = User("1", "testuser", "test@example.com")
        val loginResponse = LoginResponse("token123", user)
        whenever(authService.login(any())).thenReturn(Response.success(loginResponse))

        // Act
        val result = authRepository.login("test@example.com", "password")

        // Assert
        assertTrue(result.isSuccess)
        assertEquals(loginResponse, result.getOrNull())
        verify(tokenManager).saveToken("token123")
    }

    @Test
    fun `login failure should return error`() = runTest {
        // Arrange
        whenever(authService.login(any())).thenReturn(Response.error(401, "".toResponseBody()))

        // Act
        val result = authRepository.login("test@example.com", "wrong")

        // Assert
        assertTrue(result.isFailure)
    }
}
