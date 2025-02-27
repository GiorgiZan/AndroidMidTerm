package com.example.androidmidterm.presentation.screens.profile

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.androidmidterm.databinding.FragmentProfileBinding
import com.example.androidmidterm.presentation.base_fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun setUp() {
        displayUserDetails()
    }

    override fun listeners() {
    }

    private fun displayUserDetails() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileViewModel.userState.collectLatest { user ->
                    user?.let {
                        binding.tvUsername.text = it.username
                        binding.etAge.setText(it.age.toString())
                        binding.etWeight.setText(it.weight.toString())
                        binding.etHeight.setText(it.height.toString())
                    }
                }
            }
        }
    }
}