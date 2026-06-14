package com.manipai.data.repository

import com.manipai.data.api.ChatService
import com.manipai.data.models.Conversation
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response
import java.util.Date

class ChatRepositoryTest {

    private lateinit var chatService: ChatService
    private lateinit var chatRepository: ChatRepository

    @Before
    fun setup() {
        chatService = mock()
        chatRepository = ChatRepository(chatService)
    }

    @Test
    fun `getConversations should return list of conversations`() = runTest {
        // Arrange
        val conversations = listOf(
            Conversation("1", "user1", "Title 1", Date()),
            Conversation("2", "user1", "Title 2", Date())
        )
        whenever(chatService.getConversations()).thenReturn(Response.success(conversations))

        // Act
        val result = chatRepository.getConversations()

        // Assert
        assertTrue(result.isSuccess)
        assertEquals(conversations, result.getOrNull())
    }
}
