package com.example.androidmidterm.data.repository.register

import com.example.androidmidterm.common.Resource


interface RegisterRepository {
    suspend fun registerUser(email: String, password: String, weight: Int, age: Int, height: Int): Resource<Unit>
//    suspend fun registerUser(email: String, password: String, weight: Int, age: Int, height: Int)
}
