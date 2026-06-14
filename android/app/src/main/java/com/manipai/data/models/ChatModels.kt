package com.manipai.data.models

import java.util.Date

data class Conversation(
    val id: String,
    val userId: String,
    val title: String,
    val createdAt: Date
)

data class Message(
    val id: String,
    val conversationId: String,
    val role: String, // "user" or "ai"
    val content: String,
    val createdAt: Date
)
