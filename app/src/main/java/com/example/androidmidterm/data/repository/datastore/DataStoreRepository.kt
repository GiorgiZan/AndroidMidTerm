package com.example.androidmidterm.data.repository.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
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
        val ID_TOKEN_KEY = stringPreferencesKey("id_token")
    }

    val token: Flow<String?> = dataStore.data.map { preferences ->
        preferences[ID_TOKEN_KEY]
    }

    private val rememberMe: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[REMEMBER_ME] ?: false
    }

    suspend fun saveLoginInfo(idToken: String, rememberMe: Boolean) {
        dataStore.edit { preferences ->
            preferences[ID_TOKEN_KEY] = idToken
            preferences[REMEMBER_ME] = rememberMe
        }
    }

    suspend fun clearLoginInfo() {
        dataStore.edit { preferences ->
            preferences.remove(ID_TOKEN_KEY)
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
