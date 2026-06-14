package com.manipai.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manipai.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthState>(AuthState.Idle)
    val uiState: StateFlow<AuthState> = _uiState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthState.Loading
            val result = authRepository.login(email, password)
            result.onSuccess {
                _uiState.value = AuthState.Authenticated
            }.onFailure {
                _uiState.value = AuthState.Error(it.message ?: "Login failed")
            }
        }
    }

    fun register(username: String, email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthState.Loading
            val result = authRepository.register(username, email, password)
            result.onSuccess {
                _uiState.value = AuthState.RegistrationSuccess
            }.onFailure {
                _uiState.value = AuthState.Error(it.message ?: "Registration failed")
            }
        }
    }

    fun resetState() {
        _uiState.value = AuthState.Idle
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Authenticated : AuthState()
    object RegistrationSuccess : AuthState()
    data class Error(val message: String) : AuthState()
}
