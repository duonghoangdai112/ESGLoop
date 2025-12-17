package com.ignitech.esgcompanion.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignitech.esgcompanion.data.local.dao.ClassDao
import com.ignitech.esgcompanion.data.local.dao.StudentDao
import com.ignitech.esgcompanion.data.local.entity.ClassEntity
import com.ignitech.esgcompanion.data.local.entity.StudentEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClassDetailViewModel @Inject constructor(
    private val classDao: ClassDao,
    private val studentDao: StudentDao
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ClassDetailUiState())
    val uiState: StateFlow<ClassDetailUiState> = _uiState.asStateFlow()
    
    fun loadClassData(classId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            try {
                // Load class info
                val classEntity = classDao.getClassById(classId)
                
                // Load students for this class
                studentDao.getStudentsByClass(classId).collect { students ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            classEntity = classEntity,
                            students = students,
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

data class ClassDetailUiState(
    val isLoading: Boolean = false,
    val classEntity: ClassEntity? = null,
    val students: List<StudentEntity> = emptyList(),
    val error: String? = null
)
