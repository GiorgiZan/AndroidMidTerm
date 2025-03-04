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
