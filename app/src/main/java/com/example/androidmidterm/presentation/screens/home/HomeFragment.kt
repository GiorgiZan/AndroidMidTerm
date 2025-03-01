package com.example.androidmidterm.presentation.screens.home

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.androidmidterm.databinding.FragmentHomeBinding
import com.example.androidmidterm.presentation.base_fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val homeViewModel: HomeViewModel by viewModels()

    override fun setUp() {

    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            homeViewModel.username.collectLatest { username ->
//                binding.goToProfile.text = username // Set it to your EditText
            }
        }
    }
    override fun listeners() {
        navigateToProfileListener()
        navigateToChatListener()
    }


    private fun navigateToProfileListener() {
        binding.tvProfileContainer.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProfileFragment())
        }
    }

    private fun navigateToChatListener() {
        binding.tvChatContainer.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToChatFragment())
        }
    }

    private fun navigateToWorkoutListener() {
        binding.tvWorkoutContainer.setOnClickListener {
//            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToChatFragment())
        }
    }

}