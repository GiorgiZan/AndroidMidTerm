package com.example.androidmidterm.data.repository.gemini

import com.example.androidmidterm.BuildConfig
import com.example.androidmidterm.common.ApiHelper
import com.example.androidmidterm.common.Resource
import com.example.androidmidterm.data.remote.gemini.Content
import com.example.androidmidterm.data.remote.gemini.GeminiRequest
import com.example.androidmidterm.data.remote.gemini.GeminiResponse
import com.example.androidmidterm.data.remote.gemini.Part
import com.example.androidmidterm.data.remote.retrofit.RetrofitGeminiService
import javax.inject.Inject

class GeminiRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val geminiService: RetrofitGeminiService
) : GeminiRepository {

    override suspend fun fetchGeminiResponse(prompt: String): Resource<GeminiResponse> {
        val request = GeminiRequest(listOf(Content(listOf(Part(prompt)))))
        return apiHelper.handleHttpRequest(apiCall = {
            geminiService.getGeminiResponse(BuildConfig.API_KEY, request)
        })
    }
}

//suspend fun main() = runBlocking {
//    val json = Json {
//        ignoreUnknownKeys = true // Ignores unknown fields in JSON response
//    }
//    val geminiService = Retrofit.Builder()
//        .baseUrl("https://generativelanguage.googleapis.com/v1beta/") // Replace with the actual base URL
//        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
//        .build()
//        .create(RetrofitGeminiService::class.java)
//
//    val geminiRepository = GeminiRepository(geminiService)
//
//    val response = geminiRepository.fetchGeminiResponse("i am 50 kg 160cm and 15 years old give me home exrcile check list in vert short", "AIzaSyBQ8P4ZcbULmqM5HxjELrswH6Calp35IX8")
//    println(response)
//}