package com.example.androidmidterm.presentation.screens.home

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.androidmidterm.databinding.FragmentHomeBinding
import com.example.androidmidterm.presentation.base_fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val homeViewModel: HomeViewModel by viewModels()

    override fun setUp() {
    }

    override fun listeners() {
        navigateToProfileListener()
    }


    private fun navigateToProfileListener() {
        binding.goToProfile.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProfileFragment())
        }
    }

}