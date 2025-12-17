package com.ignitech.esgcompanion.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignitech.esgcompanion.data.entity.*
import com.ignitech.esgcompanion.data.repository.ESGAssessmentRepository
import com.ignitech.esgcompanion.domain.entity.UserRole
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LearningHubCategoryViewModel @Inject constructor(
    private val repository: ESGAssessmentRepository,
    private val authRepository: com.ignitech.esgcompanion.domain.repository.AuthRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(LearningHubCategoryUiState())
    val uiState: StateFlow<LearningHubCategoryUiState> = _uiState.asStateFlow()
    
    fun loadCategoryResources(categoryId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            try {
                // Get current user role
                val userRole = try {
                    val result = authRepository.getCurrentUser().first()
                    when (result) {
                        is com.ignitech.esgcompanion.common.Result.Success -> {
                            result.data?.role ?: UserRole.ENTERPRISE
                        }
                        else -> UserRole.ENTERPRISE
                    }
                } catch (e: Exception) {
                    UserRole.ENTERPRISE
                }
                
                // Load category
                val categories = repository.getLearningCategoriesByUserRole(userRole)
                val category = categories.find { it.id == categoryId }
                
                if (category == null) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Category not found"
                    )
                    return@launch
                }
                
                // Load resources by category name (resources use category.name)
                val allResources = repository.getLearningResourcesByUserRole(userRole)
                val categoryResources = allResources.filter { resource ->
                    resource.category.equals(category.name, ignoreCase = true) &&
                    resource.userRole == category.userRole
                }
                
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    category = category,
                    resources = categoryResources,
                    filteredResources = categoryResources,
                    selectedRole = userRole,
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Error loading category resources"
                )
            }
        }
    }
    
    fun filterByLevel(level: LearningLevel?) {
        _uiState.value = _uiState.value.copy(selectedLevel = level)
        applyFilters()
    }
    
    fun filterByType(type: LearningResourceType?) {
        _uiState.value = _uiState.value.copy(selectedType = type)
        applyFilters()
    }
    
    fun clearFilters() {
        _uiState.value = _uiState.value.copy(
            selectedLevel = null,
            selectedType = null
        )
        applyFilters()
    }
    
    private fun applyFilters() {
        val allResources = _uiState.value.resources
        
        val filtered = allResources.filter { resource ->
            val levelMatch = _uiState.value.selectedLevel?.let { 
                resource.level == it 
            } ?: true
            
            val typeMatch = _uiState.value.selectedType?.let { 
                resource.type == it 
            } ?: true
            
            levelMatch && typeMatch
        }
        
        _uiState.value = _uiState.value.copy(filteredResources = filtered)
    }
    
    fun refresh() {
        _uiState.value.category?.let { category ->
            loadCategoryResources(category.id)
        }
    }
}

data class LearningHubCategoryUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val category: LearningCategoryEntity? = null,
    val resources: List<LearningResourceEntity> = emptyList(),
    val filteredResources: List<LearningResourceEntity> = emptyList(),
    val selectedRole: UserRole = UserRole.ENTERPRISE,
    val selectedLevel: LearningLevel? = null,
    val selectedType: LearningResourceType? = null
)

