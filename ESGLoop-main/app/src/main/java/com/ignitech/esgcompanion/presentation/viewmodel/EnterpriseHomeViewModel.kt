package com.ignitech.esgcompanion.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignitech.esgcompanion.data.local.dao.ESGScoresDao
import com.ignitech.esgcompanion.data.entity.ESGScoreResult
import com.ignitech.esgcompanion.data.entity.PillarScore
import com.ignitech.esgcompanion.data.local.dao.UserDao
import com.ignitech.esgcompanion.domain.entity.ESGPillar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ESGScoreData(
    val overallScore: Int = 0,
    val maxScore: Int = 100,
    val environmentalScore: Int = 0,
    val socialScore: Int = 0,
    val governanceScore: Int = 0,
    val lastUpdated: String = "No data available",
    val assessmentCount: Int = 0
)

data class EnterpriseHomeUiState(
    val isLoading: Boolean = false,
    val esgScoreData: ESGScoreData = ESGScoreData(),
    val error: String? = null
)

@HiltViewModel
class EnterpriseHomeViewModel @Inject constructor(
    private val esgScoresDao: ESGScoresDao,
    private val userDao: UserDao
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(EnterpriseHomeUiState())
    val uiState: StateFlow<EnterpriseHomeUiState> = _uiState.asStateFlow()
    
    private var userId: String? = null
    
    fun loadESGData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            try {
                android.util.Log.d("EnterpriseHomeViewModel", "Loading ESG data...")
                // Get current user ID
                val currentUser = userDao.getCurrentUser()
                android.util.Log.d("EnterpriseHomeViewModel", "Current user: $currentUser")
                
                if (currentUser == null) {
                    android.util.Log.d("EnterpriseHomeViewModel", "No current user found, showing error")
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            error = "Not logged in. Please login again."
                        )
                    }
                    return@launch
                }
                
                userId = currentUser.id
                android.util.Log.d("EnterpriseHomeViewModel", "User ID set to: $userId")
                
                // Load ESG scores
                loadESGScores()
                
            } catch (e: Exception) {
                android.util.Log.e("EnterpriseHomeViewModel", "Error loading ESG data", e)
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "An error occurred while loading ESG data"
                    )
                }
            }
        }
    }
    
    private suspend fun loadESGScores() {
        if (userId == null) return
        
        try {
            // Get overall ESG score
            val overallScoreResult = esgScoresDao.getOverallESGScore(userId!!)
            
            // Get scores by pillar
            val pillarScoresRaw = esgScoresDao.getESGScoresByPillar(userId!!)
            
            // Convert raw scores to PillarScore objects
            val pillarScores = pillarScoresRaw.map { raw ->
                PillarScore(
                    pillar = ESGPillar.valueOf(raw.pillar),
                    averageScore = raw.averageScore
                )
            }
            
            // Convert pillar scores to map for easy access
            val pillarScoreMap = pillarScores.associate { pillarScore ->
                pillarScore.pillar to pillarScore.averageScore.toInt() 
            }
            
            val esgScoreData = ESGScoreData(
                overallScore = overallScoreResult?.averageScore?.toInt() ?: 0,
                maxScore = 100,
                environmentalScore = pillarScoreMap[ESGPillar.ENVIRONMENTAL] ?: 0,
                socialScore = pillarScoreMap[ESGPillar.SOCIAL] ?: 0,
                governanceScore = pillarScoreMap[ESGPillar.GOVERNANCE] ?: 0,
                lastUpdated = if (overallScoreResult?.assessmentCount ?: 0 > 0) {
                    "Last updated: Today"
                } else {
                    "No data available"
                },
                assessmentCount = overallScoreResult?.assessmentCount ?: 0
            )
            
            _uiState.update { 
                it.copy(
                    isLoading = false,
                    esgScoreData = esgScoreData
                )
            }
            
        } catch (e: Exception) {
            _uiState.update { 
                it.copy(
                    isLoading = false,
                    error = e.message ?: "An error occurred while loading ESG scores"
                )
            }
        }
    }
    
    fun refreshData() {
        loadESGData()
    }
}
