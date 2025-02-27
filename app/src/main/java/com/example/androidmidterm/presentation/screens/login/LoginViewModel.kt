package com.example.androidmidterm.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidmidterm.common.Resource
import com.example.androidmidterm.data.repository.datastore.DataStoreRepository
import com.example.androidmidterm.data.repository.login.LoginRepository
import com.example.androidmidterm.presentation.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val dataStoreRepository: DataStoreRepository,
    private val firebaseFireStore: FirebaseFirestore
) : ViewModel() {

    private val _loginState = MutableStateFlow<Resource<Unit>>(Resource.Loading)
    val loginState: StateFlow<Resource<Unit>> = _loginState



    suspend fun checkSavedUser(): Boolean {
        val token = dataStoreRepository.userId.first()
        return !token.isNullOrEmpty()
    }


    fun login(email: String, password: String, rememberMe: Boolean) {
        viewModelScope.launch {
            _loginState.value = Resource.Loading

            val result = loginRepository.loginUser(email, password)

            if (result is Resource.Success) {
                val user = FirebaseAuth.getInstance().currentUser
                val idToken = user?.getIdToken(true)?.await()?.token

                user?.uid?.let { uid ->
                    val snapshot = firebaseFireStore.collection("users").document(uid).get().await()
                    val userData = snapshot.toObject(User::class.java)

                    userData?.let { data ->
                        dataStoreRepository.saveUserInfo(
                            uid,
                            data.username,
                            data.age,
                            data.weight,
                            data.height,
                            rememberMe
                        )
                    }
                }
            }

            _loginState.value = result
        }
    }
//    fun login(email: String, password: String, rememberMe: Boolean) {
//        viewModelScope.launch {
//            val result = loginRepository.loginUser(email, password)
//            when (result) {
//                is Resource.Success -> {
//                    val user = FirebaseAuth.getInstance().currentUser
//                    val idToken = user?.getIdToken(true)?.await()?.token
//
//                    idToken?.let { token ->
//                        dataStoreRepository.saveLoginInfo(token, rememberMe)
//                    }
//                    _loginState.value = Resource.Success(Unit)
//                }
//
//                is Resource.Error -> {
//                    _loginState.value = Resource.Error(result.errorMessage)
//                }
//
//                is Resource.Loading -> {
//                }
//            }
//        }
//    }

}