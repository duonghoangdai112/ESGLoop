package com.ignitech.esgcompanion.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignitech.esgcompanion.data.repository.LearningHubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateQuestionViewModel @Inject constructor(
    private val learningHubRepository: LearningHubRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(CreateQuestionUiState())
    val uiState: StateFlow<CreateQuestionUiState> = _uiState.asStateFlow()
    
    fun createQuestion(
        questionText: String,
        type: String,
        difficulty: String,
        category: String,
        points: Int,
        explanation: String,
        authorId: String,
        authorName: String,
        options: List<String>,
        correctAnswer: String,
        tags: List<String>,
        hint: String
    ) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                
                val questionId = learningHubRepository.createQuestion(
                    questionText = questionText,
                    type = type,
                    difficulty = difficulty,
                    category = category,
                    points = points,
                    explanation = explanation,
                    authorId = authorId,
                    authorName = authorName,
                    options = options,
                    correctAnswer = correctAnswer,
                    tags = tags,
                    hint = hint
                )
                
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isSuccess = true,
                    createdQuestionId = questionId
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Error creating question"
                )
            }
        }
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
    
    fun resetSuccess() {
        _uiState.value = _uiState.value.copy(isSuccess = false, createdQuestionId = null)
    }
}

data class CreateQuestionUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val createdQuestionId: String? = null,
    val error: String? = null
)
