package com.example.androidmidterm.data.repository.login

import com.example.androidmidterm.common.Resource

interface LoginRepository {
    suspend fun loginUser(email: String, password: String): Resource<Unit>
}