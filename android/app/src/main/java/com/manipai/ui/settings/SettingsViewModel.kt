package com.manipai.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manipai.data.models.Settings
import com.manipai.data.models.UserStats
import com.manipai.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _settings = MutableStateFlow(Settings())
    val settings: StateFlow<Settings> = _settings

    private val _stats = MutableStateFlow<UserStats?>(null)
    val stats: StateFlow<UserStats?> = _stats

    init {
        loadSettings()
        loadStats()
    }

    fun loadSettings() {
        viewModelScope.launch {
            userRepository.getSettings().onSuccess {
                _settings.value = it
            }
        }
    }

    fun loadStats() {
        viewModelScope.launch {
            userRepository.getStats().onSuccess {
                _stats.value = it
            }
        }
    }

    fun updateTheme(theme: String) {
        val newSettings = _settings.value.copy(theme = theme)
        _settings.value = newSettings
        viewModelScope.launch {
            userRepository.updateSettings(newSettings)
        }
    }

    fun updateLanguage(language: String) {
        val newSettings = _settings.value.copy(language = language)
        _settings.value = newSettings
        viewModelScope.launch {
            userRepository.updateSettings(newSettings)
        }
    }

    fun updateAiSettings(
        provider: String,
        baseUrl: String,
        apiKey: String,
        model: String
    ) {
        val newAiSettings = com.manipai.data.models.AiSettings(
            provider = provider,
            baseUrl = baseUrl,
            apiKey = apiKey,
            model = model
        )
        val newSettings = _settings.value.copy(ai = newAiSettings)
        _settings.value = newSettings
        viewModelScope.launch {
            userRepository.updateSettings(newSettings)
        }
    }
}
