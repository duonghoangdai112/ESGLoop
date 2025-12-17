package com.ignitech.esgcompanion.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignitech.esgcompanion.data.repository.ESGAssessmentRepository
import com.ignitech.esgcompanion.domain.entity.AssessmentStatus
import com.ignitech.esgcompanion.domain.entity.ESGPillar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

data class EnterpriseAssessmentUiState(
    val isLoading: Boolean = false,
    val currentPeriod: String = "",
    val currentYear: Int = 0,
    val currentQuarter: Int = 0,
    val assessments: List<com.ignitech.esgcompanion.data.local.entity.ESGAssessmentEntity> = emptyList(),
    val overallProgress: Float = 0f,
    val completedAssessments: Int = 0,
    val inProgressAssessments: Int = 0,
    val averageScore: Int = 0,
    val environmentalProgress: Float = 0f,
    val environmentalScore: Int = 0,
    val socialProgress: Float = 0f,
    val socialScore: Int = 0,
    val governanceProgress: Float = 0f,
    val governanceScore: Int = 0,
    val error: String? = null
)

@HiltViewModel
class EnterpriseAssessmentViewModel @Inject constructor(
    private val repository: ESGAssessmentRepository
) : ViewModel() {
    
    private val userId = "enterprise_001" // Default enterprise user ID
    
    private val _uiState = MutableStateFlow(EnterpriseAssessmentUiState())
    val uiState: StateFlow<EnterpriseAssessmentUiState> = _uiState.asStateFlow()
    
    init {
        loadAssessmentData()
    }
    
    fun loadAssessmentData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            try {
                // Get current period
                val calendar = Calendar.getInstance()
                val currentYear = calendar.get(Calendar.YEAR)
                val currentMonth = calendar.get(Calendar.MONTH) + 1
                val currentQuarter = (currentMonth - 1) / 3 + 1
                val currentPeriod = "Q$currentQuarter-$currentYear"
                
                // Load current assessments (not historical)
                repository.getCurrentAssessments(userId).collect { allCurrentAssessments ->
                    // Filter by current period
                    val periodAssessments = allCurrentAssessments.filter { 
                        it.assessmentPeriod == currentPeriod 
                    }
                    
                    // Calculate statistics
                    val completed = periodAssessments.count { 
                        it.status == AssessmentStatus.COMPLETED 
                    }
                    val inProgress = periodAssessments.count { 
                        it.status == AssessmentStatus.IN_PROGRESS 
                    }
                    
                    // Calculate average score
                    val completedAssessments = periodAssessments.filter { 
                        it.status == AssessmentStatus.COMPLETED 
                    }
                    val averageScore = if (completedAssessments.isNotEmpty()) {
                        completedAssessments.map { assessment ->
                            if (assessment.maxScore > 0) {
                                (assessment.totalScore.toFloat() / assessment.maxScore * 100).toInt()
                            } else {
                                0
                            }
                        }.average().toInt()
                    } else {
                        0
                    }
                    
                    // Calculate overall progress (percentage of completed assessments)
                    val totalPillars = ESGPillar.values().size
                    val overallProgress = if (totalPillars > 0) {
                        completed.toFloat() / totalPillars
                    } else {
                        0f
                    }
                    
                    // Calculate progress by pillar
                    val environmentalAssessment = periodAssessments.find { 
                        it.pillar == ESGPillar.ENVIRONMENTAL 
                    }
                    val socialAssessment = periodAssessments.find { 
                        it.pillar == ESGPillar.SOCIAL 
                    }
                    val governanceAssessment = periodAssessments.find { 
                        it.pillar == ESGPillar.GOVERNANCE 
                    }
                    
                    val environmentalProgress = if (environmentalAssessment != null) {
                        if (environmentalAssessment.maxScore > 0) {
                            environmentalAssessment.totalScore.toFloat() / environmentalAssessment.maxScore
                        } else {
                            0f
                        }
                    } else {
                        0f
                    }
                    
                    val environmentalScore = if (environmentalAssessment != null && environmentalAssessment.maxScore > 0) {
                        (environmentalAssessment.totalScore.toFloat() / environmentalAssessment.maxScore * 100).toInt()
                    } else {
                        0
                    }
                    
                    val socialProgress = if (socialAssessment != null) {
                        if (socialAssessment.maxScore > 0) {
                            socialAssessment.totalScore.toFloat() / socialAssessment.maxScore
                        } else {
                            0f
                        }
                    } else {
                        0f
                    }
                    
                    val socialScore = if (socialAssessment != null && socialAssessment.maxScore > 0) {
                        (socialAssessment.totalScore.toFloat() / socialAssessment.maxScore * 100).toInt()
                    } else {
                        0
                    }
                    
                    val governanceProgress = if (governanceAssessment != null) {
                        if (governanceAssessment.maxScore > 0) {
                            governanceAssessment.totalScore.toFloat() / governanceAssessment.maxScore
                        } else {
                            0f
                        }
                    } else {
                        0f
                    }
                    
                    val governanceScore = if (governanceAssessment != null && governanceAssessment.maxScore > 0) {
                        (governanceAssessment.totalScore.toFloat() / governanceAssessment.maxScore * 100).toInt()
                    } else {
                        0
                    }
                    
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        currentPeriod = currentPeriod,
                        currentYear = currentYear,
                        currentQuarter = currentQuarter,
                        assessments = periodAssessments,
                        overallProgress = overallProgress,
                        completedAssessments = completed,
                        inProgressAssessments = inProgress,
                        averageScore = averageScore,
                        environmentalProgress = environmentalProgress,
                        environmentalScore = environmentalScore,
                        socialProgress = socialProgress,
                        socialScore = socialScore,
                        governanceProgress = governanceProgress,
                        governanceScore = governanceScore,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load assessment data"
                )
            }
        }
    }
    
    fun refresh() {
        loadAssessmentData()
    }
}

