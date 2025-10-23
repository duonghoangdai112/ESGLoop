package com.ignitech.esgcompanion.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignitech.esgcompanion.data.dao.ESGAssessmentDao
import com.ignitech.esgcompanion.data.dao.ESGTrackerDao
import com.ignitech.esgcompanion.data.local.entity.ESGAnswerEntity
import com.ignitech.esgcompanion.data.entity.ESGTrackerEntity
import com.ignitech.esgcompanion.domain.entity.ESGPillar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EnterpriseESGDataViewModel @Inject constructor(
    private val assessmentDao: ESGAssessmentDao,
    private val trackerDao: ESGTrackerDao
) : ViewModel() {

    private val _assessmentData = MutableStateFlow<AssessmentData?>(null)
    val assessmentData: StateFlow<AssessmentData?> = _assessmentData.asStateFlow()
    
    private val _activities = MutableStateFlow<List<ESGTrackerEntity>>(emptyList())
    val activities: StateFlow<List<ESGTrackerEntity>> = _activities.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun loadEnterpriseData(enterpriseId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            println("DEBUG: Loading ESG data for enterprise: $enterpriseId")
            
            try {
                // Load assessment data
                loadAssessmentData(enterpriseId)
                
                // Load activity tracker data
                loadActivityData(enterpriseId)
                
            } catch (e: Exception) {
                println("DEBUG: Error loading ESG data: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    private suspend fun loadAssessmentData(enterpriseId: String) {
        try {
            // Get all assessments for this enterprise
            assessmentDao.getAssessmentsByUser(enterpriseId).collect { assessments ->
                if (assessments.isNotEmpty()) {
                    // Get the latest assessment
                    val latestAssessment = assessments.firstOrNull()
                    if (latestAssessment != null) {
                        val data = AssessmentData(
                            overallScore = latestAssessment.totalScore,
                            environmentalScore = latestAssessment.totalScore, // Mock for now
                            socialScore = latestAssessment.totalScore,
                            governanceScore = latestAssessment.totalScore,
                            totalAnswers = 1
                        )
                        _assessmentData.value = data
                        println("DEBUG: Loaded assessment data")
                    }
                } else {
                    println("DEBUG: No assessment data found for enterprise: $enterpriseId")
                }
            }
        } catch (e: Exception) {
            println("DEBUG: Error loading assessment data: ${e.message}")
        }
    }
    
    private suspend fun loadActivityData(enterpriseId: String) {
        try {
            trackerDao.getActivitiesByUser(enterpriseId).collect { activities ->
                _activities.value = activities
                println("DEBUG: Loaded ${activities.size} activities")
            }
        } catch (e: Exception) {
            println("DEBUG: Error loading activity data: ${e.message}")
        }
    }
    
    
    fun getActivityCountByPillar(pillar: ESGPillar): Int {
        return _activities.value.count { it.pillar == pillar }
    }
    
    fun getActivitiesByPillar(pillar: ESGPillar): List<ESGTrackerEntity> {
        return _activities.value.filter { it.pillar == pillar }
    }
}

data class AssessmentData(
    val overallScore: Int,
    val environmentalScore: Int,
    val socialScore: Int,
    val governanceScore: Int,
    val totalAnswers: Int
)
