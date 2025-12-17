package com.ignitech.esgcompanion.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignitech.esgcompanion.data.entity.*
import com.ignitech.esgcompanion.domain.entity.ESGPillar
import com.ignitech.esgcompanion.data.repository.ESGAssessmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EnterpriseTrackerUiState(
    val isLoading: Boolean = false,
    val activities: List<ESGTrackerEntity> = emptyList(),
    val kpis: List<ESGTrackerKPIEntity> = emptyList(),
    val selectedPillar: ESGPillar? = null,
    val selectedStatus: TrackerStatus? = null,
    val isFilterVisible: Boolean = false,
    val totalActivities: Int = 0,
    val completedActivities: Int = 0,
    val overdueActivities: Int = 0,
    val kpiCount: Int = 0,
    val error: String? = null
)

@HiltViewModel
class EnterpriseTrackerViewModel @Inject constructor(
    private val repository: ESGAssessmentRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(EnterpriseTrackerUiState())
    val uiState: StateFlow<EnterpriseTrackerUiState> = _uiState.asStateFlow()
    
    private val userId = "user_3" // TODO: Get from auth
    
    init {
        loadTrackerData()
    }
    
    fun loadTrackerData() {
        viewModelScope.launch {
            println("DEBUG: EnterpriseTrackerViewModel - Loading tracker data for userId: $userId")
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            try {
                // Initialize tracker data if needed
                println("DEBUG: EnterpriseTrackerViewModel - Initializing tracker data")
                repository.initializeTrackerData()
                
                // Load activities and KPIs using Flow
                println("DEBUG: EnterpriseTrackerViewModel - Starting to collect activities")
                repository.getTrackerActivitiesByUser(userId).collect { activities ->
                    println("DEBUG: EnterpriseTrackerViewModel - Collected ${activities.size} activities")
                    repository.getTrackerKPIsByUser(userId).collect { kpis ->
                        println("DEBUG: EnterpriseTrackerViewModel - Collected ${kpis.size} KPIs")
                        // Calculate statistics
                        val totalActivities = activities.size
                        val completedActivities = activities.count { it.status == TrackerStatus.COMPLETED }
                        val overdueActivities = activities.count { it.status == TrackerStatus.OVERDUE }
                        println("DEBUG: EnterpriseTrackerViewModel - Stats: total=$totalActivities, completed=$completedActivities, overdue=$overdueActivities")
                        val kpiCount = kpis.size
                        
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                activities = activities,
                                kpis = kpis,
                                totalActivities = totalActivities,
                                completedActivities = completedActivities,
                                overdueActivities = overdueActivities,
                                kpiCount = kpiCount
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Có lỗi xảy ra"
                    )
                }
            }
        }
    }
    
    fun selectPillar(pillar: ESGPillar?) {
        _uiState.update { it.copy(selectedPillar = pillar) }
        filterActivities()
    }
    
    fun selectStatus(status: TrackerStatus?) {
        _uiState.update { it.copy(selectedStatus = status) }
        filterActivities()
    }
    
    fun toggleFilter() {
        _uiState.update { it.copy(isFilterVisible = !it.isFilterVisible) }
    }
    
    private val _navigateToAddActivity = MutableStateFlow(false)
    val navigateToAddActivity: StateFlow<Boolean> = _navigateToAddActivity.asStateFlow()
    
    fun showAddActivity() {
        _navigateToAddActivity.value = true
    }
    
    fun onAddActivityNavigated() {
        _navigateToAddActivity.value = false
    }
    
    fun openActivity(activity: ESGTrackerEntity) {
        // TODO: Navigate to activity detail screen
    }
    
    fun openKPI(kpi: ESGTrackerKPIEntity) {
        // TODO: Navigate to KPI detail screen
    }
    
    fun updateActivityStatus(activityId: String, status: TrackerStatus) {
        viewModelScope.launch {
            try {
                repository.updateTrackerActivityStatus(activityId, status)
                loadTrackerData() // Refresh data
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message ?: "Có lỗi khi cập nhật trạng thái") }
            }
        }
    }
    
    fun updateKPIValue(kpiId: String, value: Double) {
        viewModelScope.launch {
            try {
                repository.updateTrackerKPIValue(kpiId, value)
                loadTrackerData() // Refresh data
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message ?: "Có lỗi khi cập nhật KPI") }
            }
        }
    }
    
    private fun filterActivities() {
        viewModelScope.launch {
            val currentState = _uiState.value
            
            repository.getTrackerActivitiesByUser(userId).collect { allActivities ->
                val filteredActivities = allActivities.filter { activity ->
                    val pillarMatch = currentState.selectedPillar?.let { activity.pillar == it } ?: true
                    val statusMatch = currentState.selectedStatus?.let { activity.status == it } ?: true
                    pillarMatch && statusMatch
                }
                
                _uiState.update { it.copy(activities = filteredActivities) }
            }
        }
    }
}
