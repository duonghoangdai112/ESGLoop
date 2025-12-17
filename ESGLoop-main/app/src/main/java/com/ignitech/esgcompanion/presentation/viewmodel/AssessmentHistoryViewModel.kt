package com.ignitech.esgcompanion.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignitech.esgcompanion.data.local.entity.ESGAssessmentEntity
import com.ignitech.esgcompanion.domain.entity.ESGPillar
import com.ignitech.esgcompanion.data.repository.ESGAssessmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AssessmentHistoryUiState(
    val historicalAssessments: List<ESGAssessmentEntity> = emptyList(),
    val currentAssessments: List<ESGAssessmentEntity> = emptyList(),
    val assessmentYears: List<Int> = emptyList(),
    val selectedYear: Int? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class AssessmentHistoryViewModel @Inject constructor(
    private val repository: ESGAssessmentRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(AssessmentHistoryUiState())
    val uiState: StateFlow<AssessmentHistoryUiState> = _uiState.asStateFlow()
    
    fun loadAssessmentHistory(userId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            try {
                // Initialize assessment history if needed
                repository.initializeAssessmentHistory(userId)
                
                // Load historical assessments
                repository.getHistoricalAssessments(userId).collect { historical ->
                    repository.getCurrentAssessments(userId).collect { current ->
                        repository.getAssessmentYears(userId).collect { years ->
                            _uiState.value = _uiState.value.copy(
                                historicalAssessments = historical,
                                currentAssessments = current,
                                assessmentYears = years,
                                isLoading = false
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error occurred"
                )
            }
        }
    }
    
    fun loadAssessmentsByYear(userId: String, year: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(selectedYear = year, isLoading = true)
            
            try {
                repository.getAssessmentsByYear(userId, year).collect { assessments ->
                    val historical = assessments.filter { it.isHistorical }
                    val current = assessments.filter { !it.isHistorical }
                    
                    _uiState.value = _uiState.value.copy(
                        historicalAssessments = historical,
                        currentAssessments = current,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error occurred"
                )
            }
        }
    }
    
    fun loadAssessmentsByPillar(userId: String, pillar: ESGPillar) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            try {
                repository.getHistoricalAssessmentsByPillar(userId, pillar).collect { assessments ->
                    _uiState.value = _uiState.value.copy(
                        historicalAssessments = assessments,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error occurred"
                )
            }
        }
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
    
    // Helper functions for UI
    fun getAssessmentsByPeriod(period: String): List<ESGAssessmentEntity> {
        return _uiState.value.historicalAssessments.filter { it.assessmentPeriod == period } +
                _uiState.value.currentAssessments.filter { it.assessmentPeriod == period }
    }
    
    fun getAverageScoreByPeriod(period: String): Float {
        val assessments = getAssessmentsByPeriod(period)
        if (assessments.isEmpty()) return 0f
        
        val totalScore = assessments.sumOf { it.totalScore }
        val maxScore = assessments.sumOf { it.maxScore }
        
        return if (maxScore > 0) totalScore.toFloat() / maxScore else 0f
    }
    
    fun getTrendByPillar(pillar: ESGPillar): String {
        val pillarAssessments = _uiState.value.historicalAssessments
            .filter { it.pillar == pillar }
            .sortedBy { it.assessmentYear * 10 + it.assessmentQuarter }
        
        if (pillarAssessments.size < 2) return "Không đủ dữ liệu"
        
        val latest = pillarAssessments.last()
        val previous = pillarAssessments[pillarAssessments.size - 2]
        
        val latestScore = if (latest.maxScore > 0) latest.totalScore.toFloat() / latest.maxScore else 0f
        val previousScore = if (previous.maxScore > 0) previous.totalScore.toFloat() / previous.maxScore else 0f
        
        val difference = latestScore - previousScore
        
        return when {
            difference > 0.05f -> "Tăng trưởng"
            difference < -0.05f -> "Giảm sút"
            else -> "Ổn định"
        }
    }
}
