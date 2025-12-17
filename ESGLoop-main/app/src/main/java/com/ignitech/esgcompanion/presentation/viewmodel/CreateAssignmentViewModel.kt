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
class CreateAssignmentViewModel @Inject constructor(
    private val learningHubRepository: LearningHubRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(CreateAssignmentUiState())
    val uiState: StateFlow<CreateAssignmentUiState> = _uiState.asStateFlow()
    
    fun createAssignment(
        title: String,
        description: String,
        instructions: String,
        type: String,
        classId: String,
        className: String,
        difficulty: String,
        maxScore: Int,
        dueDate: Long,
        authorId: String,
        authorName: String,
        requirements: List<String>,
        attachments: List<String>,
        tags: List<String>,
        timeLimit: Int?,
        attempts: Int
    ) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                
                val assignmentId = learningHubRepository.createAssignment(
                    title = title,
                    description = description,
                    instructions = instructions,
                    type = type,
                    classId = classId,
                    className = className,
                    difficulty = difficulty,
                    maxScore = maxScore,
                    dueDate = dueDate,
                    authorId = authorId,
                    authorName = authorName,
                    requirements = requirements,
                    attachments = attachments,
                    tags = tags,
                    timeLimit = timeLimit,
                    attempts = attempts
                )
                
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isSuccess = true,
                    createdAssignmentId = assignmentId
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Có lỗi xảy ra khi tạo bài tập"
                )
            }
        }
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
    
    fun resetSuccess() {
        _uiState.value = _uiState.value.copy(isSuccess = false, createdAssignmentId = null)
    }
}

data class CreateAssignmentUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val createdAssignmentId: String? = null,
    val error: String? = null
)
