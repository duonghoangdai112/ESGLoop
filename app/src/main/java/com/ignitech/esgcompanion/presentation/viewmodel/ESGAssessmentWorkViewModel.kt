package com.ignitech.esgcompanion.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ignitech.esgcompanion.data.entity.AssessmentAnswer
import com.ignitech.esgcompanion.data.local.dao.ESGQuestionDao
import com.ignitech.esgcompanion.data.local.entity.ESGQuestionEntity
import com.ignitech.esgcompanion.data.local.entity.ESGAnswerEntity
import com.ignitech.esgcompanion.data.repository.ESGAssessmentRepository
import com.ignitech.esgcompanion.domain.entity.AssessmentStatus
import com.ignitech.esgcompanion.domain.entity.ESGPillar
import kotlinx.coroutines.flow.first
import java.util.Calendar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ESGAssessmentWorkViewModel @Inject constructor(
    private val esgQuestionDao: ESGQuestionDao,
    private val repository: ESGAssessmentRepository
) : ViewModel() {
    
    private val userId = "enterprise_001" // Default enterprise user ID
    
    private val _questions = MutableStateFlow<List<ESGQuestionEntity>>(emptyList())
    val questions: StateFlow<List<ESGQuestionEntity>> = _questions.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _isSaving = MutableStateFlow(false)
    val isSaving: StateFlow<Boolean> = _isSaving.asStateFlow()
    
    private val _isSubmitting = MutableStateFlow(false)
    val isSubmitting: StateFlow<Boolean> = _isSubmitting.asStateFlow()
    
    private val _saveResult = MutableStateFlow<String?>(null)
    val saveResult: StateFlow<String?> = _saveResult.asStateFlow()
    
    private val _savedAnswers = MutableStateFlow<Map<String, String>>(emptyMap())
    val savedAnswers: StateFlow<Map<String, String>> = _savedAnswers.asStateFlow()
    
    private var currentPillar: ESGPillar? = null
    private var currentAssessmentId: String? = null
    
    private fun getCurrentPeriod(): String {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH) + 1 // 1-12
        val currentQuarter = (currentMonth - 1) / 3 + 1 // 1-4
        return "Q$currentQuarter-$currentYear"
    }
    
    fun loadQuestionsByPillar(pillar: ESGPillar) {
        viewModelScope.launch {
            _isLoading.value = true
            currentPillar = pillar
            currentAssessmentId = null
            
            try {
                // Load questions
                esgQuestionDao.getQuestionsByPillar(pillar).collect { questionsList ->
                    _questions.value = questionsList
                    
                    // Check if there's an existing assessment for current period
                    val currentPeriod = getCurrentPeriod()
                    val existingAssessment = repository.getCurrentAssessmentByPillar(
                        userId = userId,
                        pillar = pillar,
                        period = currentPeriod
                    )
                    
                    if (existingAssessment != null) {
                        // Load existing assessment
                        currentAssessmentId = existingAssessment.id
                        
                        // Load existing answers
                        val answersFlow = repository.getAnswersByAssessment(existingAssessment.id)
                        val answers = answersFlow.first()
                        
                        // Convert answers to Map<String, String> format
                        val answersMap = mutableMapOf<String, String>()
                        answers.forEach { answer ->
                            // Convert AssessmentAnswer enum to text (we need to get from question options)
                            val question = questionsList.find { it.id == answer.questionId }
                            if (question != null) {
                                val answerText = convertAssessmentAnswerToText(
                                    answer.answer,
                                    question.optionsJson
                                )
                                answersMap[answer.questionId] = answerText
                            }
                        }
                        
                        _savedAnswers.value = answersMap
                    } else {
                        // New assessment, clear saved answers
                        _savedAnswers.value = emptyMap()
                    }
                    
                    if (_isLoading.value) {
                        _isLoading.value = false
                    }
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _saveResult.value = "Error loading questions: ${e.message}"
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
                _saveResult.value = "Assessment saved successfully"
            } catch (e: Exception) {
                _saveResult.value = "Error saving assessment: ${e.message}"
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
    
    fun submitAssessment(answers: Map<String, String>): Boolean {
        if (currentPillar == null) {
            _saveResult.value = "Error: Assessment pillar not identified"
            return false
        }
        
        if (answers.isEmpty()) {
            _saveResult.value = "Please answer at least one question before completing"
            return false
        }
        
        viewModelScope.launch {
            _isSubmitting.value = true
            try {
                val pillar = currentPillar!!
                val currentPeriod = getCurrentPeriod()
                
                // Check if assessment exists for current period
                var assessment = currentAssessmentId?.let { 
                    repository.getAssessmentById(it) 
                } ?: repository.getCurrentAssessmentByPillar(
                    userId = userId,
                    pillar = pillar,
                    period = currentPeriod
                )
                
                val isNewAssessment = assessment == null
                
                // Create new assessment if doesn't exist
                if (assessment == null) {
                    assessment = repository.createAssessment(
                        userId = userId,
                        pillar = pillar,
                        title = "${getPillarName(pillar)} Assessment $currentPeriod",
                        description = "Manual assessment for ${getPillarName(pillar)} pillar - $currentPeriod"
                    )
                    
                    // Update assessment with period info
                    val calendar = Calendar.getInstance()
                    val currentYear = calendar.get(Calendar.YEAR)
                    val currentMonth = calendar.get(Calendar.MONTH) + 1
                    val currentQuarter = (currentMonth - 1) / 3 + 1
                    
                    val assessmentWithPeriod = assessment.copy(
                        assessmentPeriod = currentPeriod,
                        assessmentYear = currentYear,
                        assessmentQuarter = currentQuarter,
                        isHistorical = false
                    )
                    repository.updateAssessment(assessmentWithPeriod)
                    assessment = assessmentWithPeriod
                    
                    currentAssessmentId = assessment.id
                }
                
                // Save/update all answers
                for ((questionId, answerText) in answers) {
                    val question = _questions.value.find { it.id == questionId }
                    if (question != null) {
                        val assessmentAnswer = convertTextToAssessmentAnswer(
                            answerText, 
                            question.optionsJson
                        )
                        
                        // Check if answer already exists
                        val existingAnswer = repository.getAnswerByAssessmentAndQuestion(
                            assessment.id,
                            questionId
                        )
                        
                        if (existingAnswer != null) {
                            // Update existing answer
                            val updatedAnswer = existingAnswer.copy(
                                answer = assessmentAnswer,
                                answeredAt = System.currentTimeMillis()
                            )
                            repository.updateAnswer(updatedAnswer)
                        } else {
                            // Create new answer
                            repository.saveAnswer(
                                assessmentId = assessment.id,
                                questionId = questionId,
                                answer = assessmentAnswer
                            )
                        }
                    }
                }
                
                // Update assessment to completed
                val updatedAssessment = assessment.copy(
                    status = AssessmentStatus.COMPLETED,
                    completedAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis()
                )
                repository.updateAssessment(updatedAssessment)
                
                // Update current assessment ID
                currentAssessmentId = assessment.id
                
                _saveResult.value = if (isNewAssessment) 
                    "Assessment completed successfully!" 
                else 
                    "Assessment updated successfully!"
                _isSubmitting.value = false
            } catch (e: Exception) {
                _saveResult.value = "Error completing assessment: ${e.message}"
                _isSubmitting.value = false
            }
        }
        
        return true
    }
    
    private fun convertAssessmentAnswerToText(answer: AssessmentAnswer, optionsJson: String): String {
        // Convert AssessmentAnswer enum back to text from options
        val gson = Gson()
        val type = object : TypeToken<List<Map<String, Any>>>() {}.type
        val options = try {
            gson.fromJson<List<Map<String, Any>>>(optionsJson, type) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
        
        // Find option with matching score
        val targetScore = when (answer) {
            AssessmentAnswer.FULLY_IMPLEMENTED -> 4
            AssessmentAnswer.IN_PROGRESS -> 3
            AssessmentAnswer.NOT_APPLICABLE -> 2
            AssessmentAnswer.NOT_IMPLEMENTED -> 1
        }
        
        val matchingOption = options.find { 
            val score = (it["score"] as? Double)?.toInt() ?: 0
            score == targetScore
        }
        
        return matchingOption?.get("text") as? String ?: ""
    }
    
    private fun convertTextToAssessmentAnswer(answerText: String, optionsJson: String): AssessmentAnswer {
        // Parse options JSON to find the score for this answer text
        val gson = Gson()
        val type = object : TypeToken<List<Map<String, Any>>>() {}.type
        val options = try {
            gson.fromJson<List<Map<String, Any>>>(optionsJson, type) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
        
        // Find the option that matches the answer text and get its score
        val option = options.find { it["text"] == answerText }
        val score = (option?.get("score") as? Double)?.toInt() ?: 0
        
        // Convert score to AssessmentAnswer enum
        // Based on calculateScore in repository:
        // FULLY_IMPLEMENTED -> 4, IN_PROGRESS -> 3, NOT_APPLICABLE -> 2, NOT_IMPLEMENTED -> 1
        return when {
            score >= 4 -> AssessmentAnswer.FULLY_IMPLEMENTED
            score >= 3 -> AssessmentAnswer.IN_PROGRESS
            score >= 2 -> AssessmentAnswer.NOT_APPLICABLE
            else -> AssessmentAnswer.NOT_IMPLEMENTED
        }
    }
    
    private fun getPillarName(pillar: ESGPillar): String {
        return when (pillar) {
            ESGPillar.ENVIRONMENTAL -> "Environmental"
            ESGPillar.SOCIAL -> "Social"
            ESGPillar.GOVERNANCE -> "Governance"
        }
    }
}
