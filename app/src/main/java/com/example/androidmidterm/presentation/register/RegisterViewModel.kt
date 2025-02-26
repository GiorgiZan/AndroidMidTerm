package com.example.androidmidterm.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidmidterm.common.Resource
import com.example.androidmidterm.data.repository.register.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerRepository: RegisterRepository
) : ViewModel() {

    private val _registerState = MutableStateFlow<Resource<Unit>>(Resource.Success(Unit))
    val registerState: StateFlow<Resource<Unit>> = _registerState


//    fun register(email: String, password: String, weight: Int, age: Int, height: Int) {
//        viewModelScope.launch {
//            _registerState.value = Resource.Loading
//            _registerState.value = try {
//                registerRepository.registerUser(email, password, weight, age, height)
//                Resource.Success(Unit)
//            } catch (e: Exception) {
//                Resource.Error(e.message ?: "Registration failed")
//            }
//        }
//    }


    fun register(email: String, password: String, weight: Int, age: Int, height: Int) {
        viewModelScope.launch {
            val result = registerRepository.registerUser(email, password, weight, age, height)

            when (result) {
                is Resource.Success -> {
                    _registerState.value = Resource.Success(Unit)
                }

                is Resource.Error -> {
                    _registerState.value = Resource.Error(result.errorMessage)
                }

                is Resource.Loading -> {}
            }
        }
    }
}