package com.example.androidmidterm.data.repository.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class DataStoreRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    companion object {
        val REMEMBER_ME = booleanPreferencesKey("remember_me")
        val USER_ID = stringPreferencesKey("user_id")
        val USERNAME = stringPreferencesKey("username")
        val USER_AGE = intPreferencesKey("user_age")
        val USER_WEIGHT = intPreferencesKey("user_weight")
        val USER_HEIGHT = intPreferencesKey("user_height")

    }

    val userId: Flow<String?> = dataStore.data.map { it[USER_ID] }
    val username: Flow<String?> = dataStore.data.map { it[USERNAME] }
    val userAge: Flow<Int?> = dataStore.data.map { it[USER_AGE] }
    val userWeight: Flow<Int?> = dataStore.data.map { it[USER_WEIGHT] }
    val userHeight: Flow<Int?> = dataStore.data.map { it[USER_HEIGHT] }
    val rememberMe: Flow<Boolean> = dataStore.data.map { it[REMEMBER_ME] ?: false }

    suspend fun saveUserInfo(
        uid: String,
        username: String,
        age: Int,
        weight: Int,
        height: Int,
        rememberMe: Boolean
    ) {
        dataStore.edit { preferences ->
            preferences[USER_ID] = uid
            preferences[USER_AGE] = age
            preferences[USERNAME] = username
            preferences[USER_WEIGHT] = weight
            preferences[USER_HEIGHT] = height
            preferences[REMEMBER_ME] = rememberMe
        }
    }

    suspend fun updateUserDetails(username: String, age: Int, weight: Int, height: Int) {
        dataStore.edit { preferences ->
            preferences[USERNAME] = username
            preferences[USER_AGE] = age
            preferences[USER_WEIGHT] = weight
            preferences[USER_HEIGHT] = height
        }
    }


    suspend fun clearLoginInfo() {
        dataStore.edit { preferences ->
            preferences.remove(USER_ID)
            preferences.remove(USERNAME)
            preferences.remove(USER_AGE)
            preferences.remove(USER_WEIGHT)
            preferences.remove(USER_HEIGHT)
            preferences.remove(REMEMBER_ME)
        }
    }

    suspend fun clearEmailIfNotRemembered() {
        val rememberMeValue = rememberMe.first()
        if (!rememberMeValue) {
            clearLoginInfo()
        }
    }
}
