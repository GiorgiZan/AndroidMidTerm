package com.example.androidmidterm.presentation.screens.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidmidterm.common.Resource
import com.example.androidmidterm.data.repository.gemini.GeminiRepository
import com.example.androidmidterm.presentation.model.Message
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
@HiltViewModel
class ChatViewModel @Inject constructor(
    private val geminiRepository: GeminiRepository
) : ViewModel() {

    private val _geminiMessageState = MutableStateFlow<Resource<List<Message>>>(Resource.Loading)
    val geminiMessageState: StateFlow<Resource<List<Message>>> = _geminiMessageState

    private val _messages = mutableListOf<Message>()
    val messages = listOf<Message>()



    fun fetchGeminiMessage(prompt: String) {
        val userMessage = Message(id = UUID.randomUUID(), text = prompt)

        viewModelScope.launch {
            _geminiMessageState.value = Resource.Loading

            val history = _messages.joinToString("\n") { "User: ${it.text}" }
            val fullPrompt = "$history\nUser: $prompt\nAI:"

            val result = geminiRepository.fetchGeminiResponse(fullPrompt)
            when (result) {
                is Resource.Success -> {
                    _messages.add(userMessage) // add user's message only on success

                    val aiMessages = result.data.candidates?.map { candidate ->
                        Message(id = UUID.randomUUID(), text = candidate.content?.parts?.firstOrNull()?.text ?: "No content")
                    } ?: emptyList()

                    _messages.addAll(aiMessages) // add AI response messages
                    _geminiMessageState.value = Resource.Success(_messages.toList())
                }
                is Resource.Error -> {
                    _geminiMessageState.value = Resource.Error(result.errorMessage)
                }
                is Resource.Loading -> {
                    _geminiMessageState.value = Resource.Loading
                }
            }
        }
    }

}

