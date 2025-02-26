package com.example.androidmidterm.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidmidterm.common.Resource
import com.example.androidmidterm.data.repository.datastore.DataStoreRepository
import com.example.androidmidterm.data.repository.login.LoginRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val dataStoreRepository: DataStoreRepository
): ViewModel() {

    private val _loginState = MutableStateFlow<Resource<Unit>>(Resource.Loading)
    val loginState: StateFlow<Resource<Unit>> = _loginState


    fun login(email: String, password: String, rememberMe: Boolean) {
        viewModelScope.launch {
            val result = loginRepository.loginUser(email, password)
            when (result) {
                is Resource.Success -> {
                    val user = FirebaseAuth.getInstance().currentUser
                    val idToken = user?.getIdToken(true)?.await()?.token

                    idToken?.let { token ->
                        dataStoreRepository.saveLoginInfo(token, rememberMe)
                    }
                    _loginState.value = Resource.Success(Unit)
                }

                is Resource.Error -> {
                    _loginState.value = Resource.Error(result.errorMessage)
                }

                is Resource.Loading -> {
                }
            }
        }
    }
}