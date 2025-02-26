package com.example.androidmidterm.data.repository.login

import com.example.androidmidterm.common.Resource
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : LoginRepository {
    override suspend fun loginUser(email: String, password: String): Resource<Unit> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Login failed")
        }
    }

}