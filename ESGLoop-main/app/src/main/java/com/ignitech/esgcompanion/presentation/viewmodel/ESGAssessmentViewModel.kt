package com.ignitech.esgcompanion.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignitech.esgcompanion.data.entity.*
import com.ignitech.esgcompanion.data.local.entity.ESGAssessmentEntity
import com.ignitech.esgcompanion.data.local.entity.ESGAnswerEntity
import com.ignitech.esgcompanion.data.local.entity.ESGQuestionEntity
import com.ignitech.esgcompanion.data.repository.ESGAssessmentRepository
import com.ignitech.esgcompanion.domain.entity.ESGPillar
import com.ignitech.esgcompanion.domain.entity.UserRole
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ESGAssessmentViewModel @Inject constructor(
    private val repository: ESGAssessmentRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ESGAssessmentUiState())
    val uiState: StateFlow<ESGAssessmentUiState> = _uiState.asStateFlow()
    
    init {
        initializeDatabase()
    }
    
    private fun initializeDatabase() {
        viewModelScope.launch {
            try {
                repository.initializeDatabase()
                _uiState.value = _uiState.value.copy(isLoading = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error"
                )
            }
        }
    }
    
    fun loadQuestions(pillar: ESGPillar, userRole: UserRole = UserRole.ENTERPRISE) {
        viewModelScope.launch {
            repository.getQuestionsByPillarAndRole(pillar, userRole)
                .catch { e ->
                    _uiState.value = _uiState.value.copy(
                        error = e.message ?: "Failed to load questions"
                    )
                }
                .collect { questions ->
                    _uiState.value = _uiState.value.copy(
                        questions = questions,
                        currentPillar = pillar,
                        currentUserRole = userRole
                    )
                }
        }
    }
    
    fun loadAssessments(userId: String) {
        viewModelScope.launch {
            repository.getAssessmentsByUser(userId)
                .catch { e ->
                    _uiState.value = _uiState.value.copy(
                        error = e.message ?: "Failed to load assessments"
                    )
                }
                .collect { assessments ->
                    _uiState.value = _uiState.value.copy(assessments = assessments)
                }
        }
    }
    
    fun createAssessment(
        userId: String,
        pillar: ESGPillar,
        title: String,
        description: String
    ) {
        viewModelScope.launch {
            try {
                val assessment = repository.createAssessment(userId, pillar, title, description)
                _uiState.value = _uiState.value.copy(
                    currentAssessment = assessment,
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to create assessment"
                )
            }
        }
    }
    
    fun loadAnswers(assessmentId: String) {
        viewModelScope.launch {
            repository.getAnswersByAssessment(assessmentId)
                .catch { e ->
                    _uiState.value = _uiState.value.copy(
                        error = e.message ?: "Failed to load answers"
                    )
                }
                .collect { answers ->
                    _uiState.value = _uiState.value.copy(answers = answers)
                }
        }
    }
    
    fun saveAnswer(
        assessmentId: String,
        questionId: String,
        answer: AssessmentAnswer,
        notes: String = ""
    ) {
        viewModelScope.launch {
            try {
                repository.saveAnswer(assessmentId, questionId, answer, notes)
                _uiState.value = _uiState.value.copy(error = null)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to save answer"
                )
            }
        }
    }
    
    fun updateAnswer(answer: ESGAnswerEntity) {
        viewModelScope.launch {
            try {
                repository.updateAnswer(answer)
                _uiState.value = _uiState.value.copy(error = null)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to update answer"
                )
            }
        }
    }
    
    fun loadAssessmentProgress(assessmentId: String) {
        viewModelScope.launch {
            try {
                val progress = repository.getAssessmentProgress(assessmentId)
                val (totalScore, maxScore) = repository.getAssessmentScore(assessmentId)
                _uiState.value = _uiState.value.copy(
                    assessmentProgress = progress,
                    assessmentScore = Pair(totalScore, maxScore)
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to load progress"
                )
            }
        }
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
    
    fun setCurrentAssessment(assessment: ESGAssessmentEntity?) {
        _uiState.value = _uiState.value.copy(currentAssessment = assessment)
    }
}

data class ESGAssessmentUiState(
    val isLoading: Boolean = true,
    val questions: List<ESGQuestionEntity> = emptyList(),
    val assessments: List<ESGAssessmentEntity> = emptyList(),
    val answers: List<ESGAnswerEntity> = emptyList(),
    val currentAssessment: ESGAssessmentEntity? = null,
    val currentPillar: ESGPillar? = null,
    val currentUserRole: UserRole = UserRole.ENTERPRISE,
    val assessmentProgress: Float = 0f,
    val assessmentScore: Pair<Int, Int> = Pair(0, 0),
    val error: String? = null
)
