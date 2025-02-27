package com.example.androidmidterm.data.repository.profile

import com.example.androidmidterm.common.Resource

interface ProfileRepository {
    suspend fun updateUserProfile(username: String, age: Int, weight: Int, height: Int): Resource<Unit>
}
