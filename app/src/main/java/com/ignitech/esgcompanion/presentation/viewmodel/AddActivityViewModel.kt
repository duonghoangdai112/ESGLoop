package com.ignitech.esgcompanion.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignitech.esgcompanion.data.entity.*
import com.ignitech.esgcompanion.domain.entity.ESGPillar
import com.ignitech.esgcompanion.data.repository.ESGAssessmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

data class AddActivityUiState(
    val isLoading: Boolean = false,
    val selectedActivityType: ESGTrackerType? = null,
    val title: String = "",
    val description: String = "",
    val selectedPillar: ESGPillar? = null,
    val selectedPriority: TrackerPriority = TrackerPriority.MEDIUM,
    val plannedDate: Long = System.currentTimeMillis(),
    val department: String = "",
    val location: String = "",
    val budget: String = "",
    val notes: String = "",
    val titleError: String = "",
    val descriptionError: String = "",
    val budgetError: String = "",
    val isFormValid: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class AddActivityViewModel @Inject constructor(
    private val repository: ESGAssessmentRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(AddActivityUiState())
    val uiState: StateFlow<AddActivityUiState> = _uiState.asStateFlow()
    
    private val userId = "enterprise_user_1" // TODO: Get from auth
    
    init {
        validateForm()
    }
    
    fun selectActivityType(type: ESGTrackerType) {
        _uiState.update { 
            it.copy(selectedActivityType = type)
        }
        validateForm()
    }
    
    fun updateTitle(title: String) {
        _uiState.update { 
            it.copy(
                title = title,
                titleError = if (title.isBlank()) "Title cannot be empty" else ""
            )
        }
        validateForm()
    }
    
    fun updateDescription(description: String) {
        _uiState.update { 
            it.copy(
                description = description,
                descriptionError = if (description.isBlank()) "Description cannot be empty" else ""
            )
        }
        validateForm()
    }
    
    fun selectPillar(pillar: ESGPillar) {
        _uiState.update { 
            it.copy(selectedPillar = pillar)
        }
        validateForm()
    }
    
    fun selectPriority(priority: TrackerPriority) {
        _uiState.update { 
            it.copy(selectedPriority = priority)
        }
        validateForm()
    }
    
    fun selectDate(date: Long) {
        _uiState.update { 
            it.copy(plannedDate = date)
        }
        validateForm()
    }
    
    fun updateDepartment(department: String) {
        _uiState.update { 
            it.copy(department = department)
        }
        validateForm()
    }
    
    fun updateLocation(location: String) {
        _uiState.update { 
            it.copy(location = location)
        }
        validateForm()
    }
    
    fun updateBudget(budget: String) {
        val budgetError = if (budget.isNotEmpty() && !budget.matches(Regex("\\d+"))) {
            "Budget must be a number"
        } else ""
        
        _uiState.update { 
            it.copy(
                budget = budget,
                budgetError = budgetError
            )
        }
        validateForm()
    }
    
    fun updateNotes(notes: String) {
        _uiState.update { 
            it.copy(notes = notes)
        }
        validateForm()
    }
    
    fun saveActivity() {
        val currentState = _uiState.value
        
        if (!currentState.isFormValid) return
        
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            try {
                val activity = ESGTrackerEntity(
                    id = UUID.randomUUID().toString(),
                    userId = userId,
                    activityType = currentState.selectedActivityType ?: ESGTrackerType.ENERGY_EFFICIENCY,
                    pillar = currentState.selectedPillar ?: ESGPillar.ENVIRONMENTAL,
                    title = currentState.title,
                    description = currentState.description,
                    status = TrackerStatus.PLANNED,
                    priority = currentState.selectedPriority,
                    plannedDate = currentState.plannedDate,
                    createdAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis(),
                    createdBy = userId,
                    department = currentState.department.takeIf { it.isNotEmpty() },
                    location = currentState.location.takeIf { it.isNotEmpty() },
                    budget = currentState.budget.toDoubleOrNull(),
                    notes = currentState.notes,
                    isRecurring = false,
                    isPublic = true,
                    isVerified = false
                )
                
                repository.insertTrackerActivity(activity)
                
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "An error occurred while saving the activity"
                    )
                }
            }
        }
    }
    
    private fun validateForm() {
        val currentState = _uiState.value
        
        val isFormValid = currentState.selectedActivityType != null &&
                currentState.title.isNotBlank() &&
                currentState.description.isNotBlank() &&
                currentState.selectedPillar != null &&
                currentState.titleError.isEmpty() &&
                currentState.descriptionError.isEmpty() &&
                currentState.budgetError.isEmpty()
        
        _uiState.update { 
            it.copy(isFormValid = isFormValid)
        }
    }
}
