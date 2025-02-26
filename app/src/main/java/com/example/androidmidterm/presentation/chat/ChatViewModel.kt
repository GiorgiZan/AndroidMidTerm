package com.example.androidmidterm.presentation.chat

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

    private val _geminiMessageState = MutableStateFlow<Resource<List<Message>>>(Resource.Success(emptyList()))
    val geminiMessageState: StateFlow<Resource<List<Message>>> = _geminiMessageState

    private val _messages = mutableListOf<Message>()
    val messages = listOf<Message>()

    fun fetchGeminiMessage(prompt: String) {
        viewModelScope.launch {
            val history = _messages.joinToString("\n") { "User: ${it.text}" }
            val fullPrompt = "$history\nUser: $prompt\nAI:"

            val result = geminiRepository.fetchGeminiResponse(fullPrompt)
            when (result) {
                is Resource.Success -> {
                    val newMessages = result.data.candidates?.map { candidate ->
                        Message(
                            id = UUID.randomUUID(),
                            text = candidate.content?.parts?.firstOrNull()?.text ?: "No content",
                        )
                    } ?: emptyList()

                    // Avoid duplicates before adding
                    newMessages.forEach { message ->
                        if (!_messages.contains(message)) {
                            _messages.add(message)
                        }
                    }

                    _geminiMessageState.value = Resource.Success(_messages.toList()) // Ensure a new list instance
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


    fun addMessageToList(message: Message) {
        _messages.add(message)
        _geminiMessageState.value = Resource.Success(_messages.toList()) // Ensure UI updates
    }
}

