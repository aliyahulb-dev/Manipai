package com.manipai.data.socket

import io.socket.client.Socket
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SocketManager @Inject constructor(
    private val socket: Socket
) {
    fun connect() {
        if (!socket.connected()) {
            socket.connect()
        }
    }

    fun disconnect() {
        socket.disconnect()
    }

    fun join(userId: String) {
        socket.emit("join", userId)
    }

    fun onResponseStart(callback: (String, String) -> Unit) {
        socket.on("ai_response_start") { args ->
            val data = args[0] as JSONObject
            val conversationId = data.getString("conversationId")
            val messageId = data.getString("messageId")
            callback(conversationId, messageId)
        }
    }

    fun onResponseChunk(callback: (String, String, String) -> Unit) {
        socket.on("ai_response_chunk") { args ->
            val data = args[0] as JSONObject
            val conversationId = data.getString("conversationId")
            val messageId = data.getString("messageId")
            val text = data.getString("text")
            callback(conversationId, messageId, text)
        }
    }

    fun onResponseEnd(callback: (String, String, String) -> Unit) {
        socket.on("ai_response_end") { args ->
            val data = args[0] as JSONObject
            val conversationId = data.getString("conversationId")
            val messageId = data.getString("messageId")
            val fullContent = data.getString("fullContent")
            callback(conversationId, messageId, fullContent)
        }
    }
}
