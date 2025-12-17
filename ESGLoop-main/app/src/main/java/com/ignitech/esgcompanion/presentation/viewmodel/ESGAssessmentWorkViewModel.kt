package com.ignitech.esgcompanion.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignitech.esgcompanion.data.local.dao.ESGQuestionDao
import com.ignitech.esgcompanion.data.local.entity.ESGQuestionEntity
import com.ignitech.esgcompanion.domain.entity.ESGPillar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ESGAssessmentWorkViewModel @Inject constructor(
    private val esgQuestionDao: ESGQuestionDao
) : ViewModel() {
    
    private val _questions = MutableStateFlow<List<ESGQuestionEntity>>(emptyList())
    val questions: StateFlow<List<ESGQuestionEntity>> = _questions.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _isSaving = MutableStateFlow(false)
    val isSaving: StateFlow<Boolean> = _isSaving.asStateFlow()
    
    private val _saveResult = MutableStateFlow<String?>(null)
    val saveResult: StateFlow<String?> = _saveResult.asStateFlow()
    
    private val _savedAnswers = MutableStateFlow<Map<String, String>>(emptyMap())
    val savedAnswers: StateFlow<Map<String, String>> = _savedAnswers.asStateFlow()
    
    fun loadQuestionsByPillar(pillar: ESGPillar) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                esgQuestionDao.getQuestionsByPillar(pillar).collect { questionsList ->
                    _questions.value = questionsList
                    if (_isLoading.value) {
                        _isLoading.value = false
                    }
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _saveResult.value = "Lỗi khi tải câu hỏi: ${e.message}"
            }
        }
    }
    
    fun saveAssessment(answers: Map<String, String>) {
        viewModelScope.launch {
            _isSaving.value = true
            try {
                // TODO: Implement save to database
                // For now, just simulate saving
                kotlinx.coroutines.delay(1000)
                _savedAnswers.value = answers
                _saveResult.value = "Đã lưu đánh giá thành công"
            } catch (e: Exception) {
                _saveResult.value = "Lỗi khi lưu đánh giá: ${e.message}"
            } finally {
                _isSaving.value = false
            }
        }
    }
    
    fun clearSaveResult() {
        _saveResult.value = null
    }
    
    fun clearSavedAnswers() {
        _savedAnswers.value = emptyMap()
    }
}
