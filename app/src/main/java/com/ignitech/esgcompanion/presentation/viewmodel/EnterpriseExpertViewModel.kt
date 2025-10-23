package com.ignitech.esgcompanion.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignitech.esgcompanion.domain.entity.ExpertData
import com.ignitech.esgcompanion.data.local.dao.ExpertDao
import com.ignitech.esgcompanion.data.local.dao.EnterpriseExpertConnectionDao
import com.ignitech.esgcompanion.data.local.entity.ExpertEntity
import com.ignitech.esgcompanion.data.local.entity.EnterpriseExpertConnectionEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ExpertWithConnection(
    val expert: ExpertData,
    val connection: EnterpriseExpertConnectionEntity?,
    val isConnected: Boolean = false,
    val isPending: Boolean = false
)

data class EnterpriseExpertUiState(
    val isLoading: Boolean = false,
    val connectedExperts: List<ExpertWithConnection> = emptyList(),
    val allExperts: List<ExpertWithConnection> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class EnterpriseExpertViewModel @Inject constructor(
    private val expertDao: ExpertDao,
    private val enterpriseExpertConnectionDao: EnterpriseExpertConnectionDao
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(EnterpriseExpertUiState())
    val uiState: StateFlow<EnterpriseExpertUiState> = _uiState.asStateFlow()
    
    fun loadExpertData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            try {
                val enterpriseId = "user_3" // Demo enterprise user ID
                
                // Load all experts first
                val allExpertEntities = expertDao.getAllExperts().first()
                val connections = enterpriseExpertConnectionDao.getConnectedExperts(enterpriseId).first()
                
                println("DEBUG: Loaded ${allExpertEntities.size} experts")
                println("DEBUG: Loaded ${connections.size} connections")
                
                val allExpertsWithConnection = allExpertEntities.map { expertEntity ->
                    val connection = connections.find { it.expertId == expertEntity.id }
                    ExpertWithConnection(
                        expert = expertEntity.toExpertData(),
                        connection = connection,
                        isConnected = connection?.connectionStatus == "connected",
                        isPending = connection?.connectionStatus == "pending"
                    )
                }
                
                val connectedExperts = allExpertsWithConnection.filter { it.isConnected }
                
                println("DEBUG: Connected experts: ${connectedExperts.size}")
                println("DEBUG: All experts: ${allExpertsWithConnection.size}")
                
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        connectedExperts = connectedExperts,
                        allExperts = allExpertsWithConnection
                    )
                }
            } catch (e: Exception) {
                println("DEBUG: Error loading expert data: ${e.message}")
                e.printStackTrace()
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Có lỗi xảy ra khi tải dữ liệu chuyên gia"
                    )
                }
            }
        }
    }
    
    fun selectExpert(expertId: String) {
        // TODO: Navigate to expert detail screen
    }
    
    fun showAskQuestionDialog() {
        // TODO: Show ask question dialog
    }
    
    fun showBookConsultationDialog() {
        // TODO: Show book consultation dialog
    }
    
    fun showConsultationHistory() {
        // TODO: Show consultation history
    }
    
    fun bookConsultation(expertId: String) {
        // TODO: Book consultation with expert
    }

    fun initiatePhoneCall(expertId: String) {
        // TODO: Initiate phone call with expert
        println("DEBUG: Initiating phone call with expert: $expertId")
    }

    fun scheduleMeeting(expertId: String) {
        // TODO: Schedule meeting with expert
        println("DEBUG: Scheduling meeting with expert: $expertId")
    }

    fun sendMessage(expertId: String) {
        // TODO: Send message to expert
        println("DEBUG: Sending message to expert: $expertId")
    }

    fun sendConnectionRequest(expertId: String) {
        // TODO: Send connection request to expert
        println("DEBUG: Sending connection request to expert: $expertId")
        // In a real app, this would trigger a success message
        // For now, just log the action
    }
    
    private fun ExpertEntity.toExpertData(): ExpertData {
        return ExpertData(
            id = this.id,
            name = this.name,
            specialization = this.specialization,
            rating = this.rating,
            hourlyRate = this.hourlyRate.toInt(),
            experience = this.experience.toString(),
            isAvailable = this.isAvailable
        )
    }
}
