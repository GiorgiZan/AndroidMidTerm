package com.example.androidmidterm.data.repository.profile

import com.example.androidmidterm.common.FirebaseHelper
import com.example.androidmidterm.common.Resource
import com.example.androidmidterm.data.repository.datastore.DataStoreRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val firebaseHelper: FirebaseHelper,
    private val dataStoreRepository: DataStoreRepository
) : ProfileRepository {

    override suspend fun updateUserProfile(username: String, age: Int, weight: Int, height: Int): Resource<Unit> {
        return firebaseHelper.handleFirebaseRequest {
            val uid = dataStoreRepository.userId.firstOrNull()

            if (!uid.isNullOrEmpty()) {
                val userData = mapOf("username" to username, "age" to age, "weight" to weight, "height" to height)

                db.collection("users").document(uid).set(userData).await()

                dataStoreRepository.updateUserDetails(username, age, weight, height)
            }
        }
    }
}