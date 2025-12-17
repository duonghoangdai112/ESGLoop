package com.ignitech.esgcompanion.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignitech.esgcompanion.data.entity.AssignmentEntity
import com.ignitech.esgcompanion.data.entity.QuestionEntity
import com.ignitech.esgcompanion.data.entity.AssignmentSubmissionEntity
import com.ignitech.esgcompanion.data.entity.QuestionAttemptEntity
import com.ignitech.esgcompanion.data.local.AssignmentDao
import com.ignitech.esgcompanion.data.local.QuestionDao
import com.ignitech.esgcompanion.data.local.AssignmentSubmissionDao
import com.ignitech.esgcompanion.data.local.QuestionAttemptDao
import java.util.UUID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssignmentWorkViewModel @Inject constructor(
    private val assignmentDao: AssignmentDao,
    private val questionDao: QuestionDao,
    private val assignmentSubmissionDao: AssignmentSubmissionDao,
    private val questionAttemptDao: QuestionAttemptDao
) : ViewModel() {
    
    private val _assignment = MutableStateFlow<AssignmentEntity?>(null)
    val assignment: StateFlow<AssignmentEntity?> = _assignment.asStateFlow()
    
    private val _questions = MutableStateFlow<List<QuestionEntity>>(emptyList())
    val questions: StateFlow<List<QuestionEntity>> = _questions.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _isSaving = MutableStateFlow(false)
    val isSaving: StateFlow<Boolean> = _isSaving.asStateFlow()
    
    private val _saveResult = MutableStateFlow<String?>(null)
    val saveResult: StateFlow<String?> = _saveResult.asStateFlow()
    
    private val _savedAnswers = MutableStateFlow<Map<String, String>>(emptyMap())
    val savedAnswers: StateFlow<Map<String, String>> = _savedAnswers.asStateFlow()
    
    fun loadAssignmentAndQuestions(assignmentId: String, studentId: String = "student_001") {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Load assignment
                val assignment = assignmentDao.getAssignmentById(assignmentId)
                _assignment.value = assignment
                
                // Load questions for this assignment
                questionDao.getQuestionsByAssignmentId(assignmentId).collect { questionList ->
                    _questions.value = questionList
                    
                    // Load saved answers for this assignment
                    loadSavedAnswers(assignmentId, studentId)
                    
                    _isLoading.value = false // Reset loading after first emission
                }
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()
                _isLoading.value = false
            }
        }
    }
    
    private fun loadSavedAnswers(assignmentId: String, studentId: String) {
        viewModelScope.launch {
            try {
                // Get the latest submission for this assignment and student
                val latestSubmission = assignmentSubmissionDao.getLatestSubmission(assignmentId, studentId)
                
                if (latestSubmission != null) {
                    // Get all question attempts for this submission
                    val questionAttempts = questionDao.getQuestionsByAssignmentId(assignmentId)
                        .collect { questions ->
                            val savedAnswersMap = mutableMapOf<String, String>()
                            
                            questions.forEach { question ->
                                // Get the latest attempt for this question
                                val latestAttempt = questionAttemptDao.getLatestAttempt(question.id, studentId)
                                if (latestAttempt != null) {
                                    savedAnswersMap[question.id] = latestAttempt.studentAnswer
                                }
                            }
                            
                            _savedAnswers.value = savedAnswersMap
                        }
                } else {
                    _savedAnswers.value = emptyMap()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _savedAnswers.value = emptyMap()
            }
        }
    }
    
    fun saveAssignment(userAnswers: Map<String, String>, studentId: String = "student_001", studentName: String = "Học sinh") {
        viewModelScope.launch {
            _isSaving.value = true
            _saveResult.value = null
            
            try {
                val assignment = _assignment.value
                if (assignment == null) {
                    _saveResult.value = "Assignment information not found"
                    return@launch
                }
                
                // Create assignment submission
                val submissionId = UUID.randomUUID().toString()
                val submission = AssignmentSubmissionEntity(
                    id = submissionId,
                    assignmentId = assignment.id,
                    studentId = studentId,
                    studentName = studentName,
                    content = userAnswers.toString(), // Simple serialization for now
                    submittedAt = System.currentTimeMillis(),
                    attemptNumber = 1 // TODO: Calculate based on previous attempts
                )
                
                // Create question attempts
                val questionAttempts = userAnswers.map { (questionId, answer) ->
                    QuestionAttemptEntity(
                        id = UUID.randomUUID().toString(),
                        questionId = questionId,
                        studentId = studentId,
                        studentAnswer = answer,
                        isCorrect = false, // TODO: Implement answer checking logic
                        pointsEarned = 0, // TODO: Calculate points
                        attemptedAt = System.currentTimeMillis(),
                        timeSpent = 0 // TODO: Track time spent per question
                    )
                }
                
                // Save to database
                assignmentSubmissionDao.insertSubmission(submission)
                questionAttemptDao.insertAttempts(questionAttempts)
                
                _saveResult.value = "Assignment saved successfully!"
                
            } catch (e: Exception) {
                e.printStackTrace()
                _saveResult.value = "Lỗi khi lưu bài tập: ${e.message}"
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
