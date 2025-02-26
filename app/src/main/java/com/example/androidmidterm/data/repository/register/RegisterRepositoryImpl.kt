package com.example.androidmidterm.data.repository.register


import com.example.androidmidterm.common.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : RegisterRepository {

    override suspend fun registerUser(
        email: String,
        password: String,
        weight: Int,
        age: Int,
        height: Int
    ): Resource<Unit> {
        return try {
            val user = auth.createUserWithEmailAndPassword(email, password).await().user
            user?.let {
                val userData = mapOf("weight" to weight, "age" to age, "height" to height)
                db.collection("users").document(user.uid).set(userData).await()
            }
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Registration failed")
        }
    }
}
//class RegisterRepositoryImpl @Inject constructor(
//    private val auth: FirebaseAuth,
//    private val db: FirebaseFirestore
//) : RegisterRepository {
//
//    override suspend fun registerUser(
//        email: String,
//        password: String,
//        weight: Int,
//        age: Int,
//        height: Int
//    ) {
//        val user = auth.createUserWithEmailAndPassword(email, password).await().user
//        user?.let {
//            val userData = mapOf("weight" to weight, "age" to age, "height" to height)
//            db.collection("users").document(user.uid).set(userData).await()
//        }
//    }
//}