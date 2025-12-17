package com.ignitech.esgcompanion.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignitech.esgcompanion.data.entity.AssignmentEntity
import com.ignitech.esgcompanion.data.local.AssignmentDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssignmentDetailViewModel @Inject constructor(
    private val assignmentDao: AssignmentDao
) : ViewModel() {
    
    private val _assignment = MutableStateFlow<AssignmentEntity?>(null)
    val assignment: StateFlow<AssignmentEntity?> = _assignment.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    fun loadAssignment(assignmentId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val assignment = assignmentDao.getAssignmentById(assignmentId)
                _assignment.value = assignment
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
