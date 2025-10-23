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
class StudentAssignmentsViewModel @Inject constructor(
    private val assignmentDao: AssignmentDao
) : ViewModel() {
    
    private val _assignments = MutableStateFlow<List<AssignmentEntity>>(emptyList())
    val assignments: StateFlow<List<AssignmentEntity>> = _assignments.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    init {
        loadAssignments()
    }
    
    private fun loadAssignments() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                assignmentDao.getAllAssignments().collect { assignmentList ->
                    _assignments.value = assignmentList
                    _isLoading.value = false // Reset loading after first emission
                }
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()
                _isLoading.value = false
            }
        }
    }
    
    fun refreshAssignments() {
        loadAssignments()
    }
}
