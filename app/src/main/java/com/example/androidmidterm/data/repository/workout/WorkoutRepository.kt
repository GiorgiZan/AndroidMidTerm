package com.example.androidmidterm.data.repository.workout

import com.example.androidmidterm.common.Resource
import com.example.androidmidterm.presentation.model.Workout

interface WorkoutRepository {
    fun getEasyWorkouts(): List<Workout>
    fun getMediumWorkouts(): List<Workout>
    fun getHardWorkouts(): List<Workout>
    suspend fun updateUserWorkoutCount(): Resource<Unit>
}