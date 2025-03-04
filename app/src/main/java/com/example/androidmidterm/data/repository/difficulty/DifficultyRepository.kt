package com.example.androidmidterm.data.repository.difficulty

import com.example.androidmidterm.presentation.model.WorkoutDifficulty

interface DifficultyRepository {
    fun getDifficultyLevels(): List<WorkoutDifficulty>
}