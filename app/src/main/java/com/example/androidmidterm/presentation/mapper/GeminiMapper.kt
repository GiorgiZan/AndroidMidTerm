package com.example.androidmidterm.presentation.mapper

import com.example.androidmidterm.data.remote.gemini.GeminiResponse
import com.example.androidmidterm.presentation.model.Message
import com.example.androidmidterm.util.DateUtils
import java.util.UUID

fun GeminiResponse.toPresenter(): Message {
    val text = candidates?.firstOrNull()?.content?.parts?.joinToString("\n") { it.text } ?: "No response"

    return Message(
        id = UUID.randomUUID(),
        text = text,
        date = DateUtils.todayDate()
    )
}