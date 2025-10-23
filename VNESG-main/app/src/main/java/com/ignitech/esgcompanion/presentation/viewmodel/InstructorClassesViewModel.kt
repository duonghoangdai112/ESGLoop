package com.ignitech.esgcompanion.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignitech.esgcompanion.data.local.dao.ClassDao
import com.ignitech.esgcompanion.data.local.entity.ClassEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InstructorClassesViewModel @Inject constructor(
    private val classDao: ClassDao
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(InstructorClassesUiState())
    val uiState: StateFlow<InstructorClassesUiState> = _uiState.asStateFlow()
    
    private val instructorId = "instructor_001" // Demo instructor ID
    
    init {
        loadClasses()
    }
    
    fun loadClasses() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            try {
                classDao.getClassesByInstructor(instructorId).collect { classes ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            classes = classes,
                            error = null
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
            }
        }
    }
}

data class InstructorClassesUiState(
    val isLoading: Boolean = false,
    val classes: List<ClassEntity> = emptyList(),
    val error: String? = null
)
