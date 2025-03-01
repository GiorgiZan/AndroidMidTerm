package com.example.androidmidterm.data.repository.gemini

import com.example.androidmidterm.common.Resource
import com.example.androidmidterm.data.remote.gemini.GeminiResponse

interface GeminiRepository {
    suspend fun fetchGeminiResponse(prompt: String): Resource<GeminiResponse>
}