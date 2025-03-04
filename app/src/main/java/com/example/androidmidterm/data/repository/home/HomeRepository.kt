package com.example.androidmidterm.data.repository.home

import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getUserWorkerCountFromDataStore(): Flow<Int?>
}