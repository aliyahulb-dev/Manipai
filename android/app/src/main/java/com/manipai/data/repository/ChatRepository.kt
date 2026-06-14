package com.manipai.data.repository

import com.manipai.data.api.ChatService
import com.manipai.data.api.CreateConversationRequest
import com.manipai.data.api.SendMessageRequest
import com.manipai.data.api.SendMessageResponse
import com.manipai.data.models.Conversation
import com.manipai.data.models.Message
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepository @Inject constructor(
    private val chatService: ChatService
) {
    suspend fun getConversations(): Result<List<Conversation>> {
        return try {
            val response = chatService.getConversations()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to fetch conversations"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createConversation(title: String): Result<Conversation> {
        return try {
            val response = chatService.createConversation(CreateConversationRequest(title))
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to create conversation"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getMessages(conversationId: String): Result<List<Message>> {
        return try {
            val response = chatService.getMessages(conversationId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to fetch messages"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun sendMessage(conversationId: String, content: String): Result<SendMessageResponse> {
        return try {
            val response = chatService.sendMessage(conversationId, SendMessageRequest(content))
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to send message"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteConversation(conversationId: String): Result<String> {
        return try {
            val response = chatService.deleteConversation(conversationId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.message)
            } else {
                Result.failure(Exception("Failed to delete conversation"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
