package com.example.androidmidterm.data.remote.gemini

import kotlinx.serialization.Serializable

@Serializable
data class GeminiResponse(
    val candidates: List<Candidate>?
)

@Serializable
data class Candidate(
    val content: Content?
)
