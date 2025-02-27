package com.example.androidmidterm.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidmidterm.common.Resource
import com.example.androidmidterm.data.repository.datastore.DataStoreRepository
import com.example.androidmidterm.data.repository.login.LoginRepository
import com.example.androidmidterm.presentation.model.User
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val dataStoreRepository: DataStoreRepository,
    private val firebaseFireStore: FirebaseFirestore,
) : ViewModel() {
    private val _userState = MutableStateFlow<Resource<User>>(Resource.Loading)
    val userState: StateFlow<Resource<User>> = _userState


    fun getUserDataFromFireStore() {
        viewModelScope.launch {
            val uid = dataStoreRepository.userId.firstOrNull()
            if (!uid.isNullOrEmpty()) {
                val snapshot = firebaseFireStore.collection("users").document(uid).get().await()
                val user = snapshot.toObject(User::class.java)

                if (user != null) {
                    _userState.value = Resource.Success(user)
                } else {
                    _userState.value = Resource.Error("Failed to retrieve user data")
                }
            } else {
                _userState.value = Resource.Error("No saved user ID found")
            }
        }
    }




}