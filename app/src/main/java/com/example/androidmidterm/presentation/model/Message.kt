package com.example.androidmidterm.presentation.model

import com.example.androidmidterm.util.DateUtils
import java.util.UUID

data class Message(
    val id: UUID,
    val text:String,
    val date: String = DateUtils.todayDate()
)