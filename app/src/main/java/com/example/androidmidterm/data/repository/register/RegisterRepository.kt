package com.example.androidmidterm.data.repository.register

import com.example.androidmidterm.common.Resource


interface RegisterRepository {
    suspend fun registerUser(email: String, password: String, username: String, weight: Int, age: Int, height: Int): Resource<Unit>
}
