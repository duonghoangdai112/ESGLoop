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
class CreateLessonViewModel @Inject constructor(
    private val learningHubRepository: LearningHubRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(CreateLessonUiState())
    val uiState: StateFlow<CreateLessonUiState> = _uiState.asStateFlow()
    
    fun createLesson(
        title: String,
        description: String,
        content: String,
        classId: String,
        className: String,
        duration: Int,
        difficulty: String,
        authorId: String,
        authorName: String,
        objectives: List<String>,
        materials: List<String>,
        tags: List<String>
    ) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                
                val difficultyLevel = when (difficulty) {
                    "Dễ" -> com.ignitech.esgcompanion.data.entity.LearningLevel.BEGINNER
                    "Trung bình" -> com.ignitech.esgcompanion.data.entity.LearningLevel.INTERMEDIATE
                    "Khó" -> com.ignitech.esgcompanion.data.entity.LearningLevel.ADVANCED
                    "Rất khó" -> com.ignitech.esgcompanion.data.entity.LearningLevel.EXPERT
                    else -> com.ignitech.esgcompanion.data.entity.LearningLevel.INTERMEDIATE
                }
                
                val lessonId = learningHubRepository.createLesson(
                    title = title,
                    description = description,
                    content = content,
                    classId = classId,
                    className = className,
                    duration = duration,
                    difficulty = difficultyLevel,
                    authorId = authorId,
                    authorName = authorName,
                    objectives = objectives,
                    materials = materials,
                    tags = tags
                )
                
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isSuccess = true,
                    createdLessonId = lessonId
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Có lỗi xảy ra khi tạo bài giảng"
                )
            }
        }
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
    
    fun resetSuccess() {
        _uiState.value = _uiState.value.copy(isSuccess = false, createdLessonId = null)
    }
}

data class CreateLessonUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val createdLessonId: String? = null,
    val error: String? = null
)
