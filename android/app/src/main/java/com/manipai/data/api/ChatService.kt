package com.manipai.data.api

import com.manipai.data.models.Conversation
import com.manipai.data.models.Message
import retrofit2.Response
import retrofit2.http.*

interface ChatService {
    @GET("api/chat/conversations")
    suspend fun getConversations(): Response<List<Conversation>>

    @POST("api/chat/conversations")
    suspend fun createConversation(@Body request: CreateConversationRequest): Response<Conversation>

    @GET("api/chat/conversations/{id}/messages")
    suspend fun getMessages(@Path("id") conversationId: String): Response<List<Message>>

    @POST("api/chat/conversations/{id}/messages")
    suspend fun sendMessage(
        @Path("id") conversationId: String,
        @Body request: SendMessageRequest
    ): Response<SendMessageResponse>

    @DELETE("api/chat/conversations/{id}")
    suspend fun deleteConversation(@Path("id") conversationId: String): Response<DeleteResponse>
}

data class CreateConversationRequest(val title: String)
data class SendMessageRequest(val content: String)
data class SendMessageResponse(val userMessage: Message, val streaming: Boolean, val aiMessageId: String)
data class DeleteResponse(val message: String)
