package com.example.androidmidterm.presentation.screens.difficulty

import androidx.lifecycle.ViewModel
import com.example.androidmidterm.data.repository.difficulty.DifficultyRepository
import com.example.androidmidterm.presentation.model.WorkoutDifficulty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class DifficultyViewModel @Inject constructor(
    private val difficultyRepository: DifficultyRepository
) :
    ViewModel() {
    private val _difficultyLevels = MutableStateFlow<List<WorkoutDifficulty>>(emptyList())
    val difficultyLevels: StateFlow<List<WorkoutDifficulty>> get() = _difficultyLevels

    fun loadDifficultyLevels() {
        _difficultyLevels.value = difficultyRepository.getDifficultyLevels()
    }
}