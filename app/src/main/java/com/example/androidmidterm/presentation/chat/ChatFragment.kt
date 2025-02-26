package com.example.androidmidterm.presentation.chat

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidmidterm.BuildConfig
import com.example.androidmidterm.R
import com.example.androidmidterm.adapter.MessageAdapter
import com.example.androidmidterm.common.Resource
import com.example.androidmidterm.databinding.FragmentChatBinding
import com.example.androidmidterm.presentation.base_fragment.BaseFragment
import com.example.androidmidterm.presentation.model.Message
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.UUID


@AndroidEntryPoint
class ChatFragment : BaseFragment<FragmentChatBinding>(FragmentChatBinding::inflate) {
    private val chatViewModel: ChatViewModel by viewModels()

    private val messageAdapter by lazy {
        MessageAdapter()
    }

    override fun setUp() {
        setUpMessagesRecyclerView()
        messageState()
    }

    override fun listeners() {
        askAI()
    }


    private fun messageState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                chatViewModel.geminiMessageState.collectLatest { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            loading()
                        }
                        is Resource.Success -> {
                            loaded()
                            val newMessages = resource.data
                            messageAdapter.submitList(newMessages)
                        }
                        is Resource.Error -> {
                            loaded()
                            Snackbar.make(binding.root, resource.errorMessage, Snackbar.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        }
    }


    private fun askAI() {
        binding.ivSend.setOnClickListener {
            val inputText = binding.etInputMessage.text.toString().trim()

            if (inputText.isEmpty()) {
                binding.etInputMessage.error = getString(R.string.message_input_should_not_be_empty)
                return@setOnClickListener
            }

            val userMessage = Message(
                id = UUID.randomUUID(),
                text = inputText,
            )
            chatViewModel.addMessageToList(userMessage)

            binding.etInputMessage.text?.clear()

            chatViewModel.fetchGeminiMessage(inputText)

            binding.RecyclerViewContainer.scrollToPosition(chatViewModel.messages.size - 1)
        }
    }


    private fun setUpMessagesRecyclerView() {
        binding.RecyclerViewContainer.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = messageAdapter
        }
    }

    private fun loading() {
        binding.loading.visibility = View.VISIBLE
    }

    private fun loaded() {
        binding.loading.visibility = View.GONE
    }
}