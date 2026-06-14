package com.manipai.data.models

data class AiSettings(
    val provider: String = "anthropic",
    val baseUrl: String = "",
    val apiKey: String = "",
    val model: String = ""
)

data class Settings(
    val theme: String = "dark",
    val language: String = "en",
    val notifications: Boolean = true,
    val ai: AiSettings = AiSettings()
)

data class UserStats(
    val conversationsCount: Int,
    val messagesCount: Int
)
