package com.ignitech.esgcompanion.data.remote

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface OpenAIService {
    
    @Headers("Content-Type: application/json")
    @POST("v1/chat/completions")
    suspend fun chat(@Body request: ChatCompletionRequest): ChatCompletionResponse
}

// Request
data class ChatCompletionRequest(
    val model: String = "gpt-3.5-turbo",
    val messages: List<Message>,
    val temperature: Double = 0.7,
    val max_tokens: Int = 1000
)

data class Message(
    val role: String, // "system", "user", or "assistant"
    val content: String
)

// Response
data class ChatCompletionResponse(
    val id: String,
    val `object`: String,
    val created: Long,
    val model: String,
    val choices: List<Choice>,
    val usage: Usage?
)

data class Choice(
    val index: Int,
    val message: Message,
    val finish_reason: String?
)

data class Usage(
    val prompt_tokens: Int,
    val completion_tokens: Int,
    val total_tokens: Int
)

