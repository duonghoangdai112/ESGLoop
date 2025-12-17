package com.ignitech.esgcompanion.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.ignitech.esgcompanion.data.local.dao.AssessmentDao
import com.ignitech.esgcompanion.data.local.entity.QuizAttemptEntity
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class StudentQuizTakingUiState(
    val isLoading: Boolean = false,
    val quiz: StudentQuiz? = null,
    val currentQuestionIndex: Int = 0,
    val selectedAnswers: List<Int?> = emptyList(),
    val timeRemaining: Int = 0, // in seconds
    val isSubmitted: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class StudentQuizTakingViewModel @Inject constructor(
    private val assessmentDao: AssessmentDao
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(StudentQuizTakingUiState())
    val uiState: StateFlow<StudentQuizTakingUiState> = _uiState.asStateFlow()
    
    private var navController: NavController? = null
    private val gson = Gson()
    
    fun setNavController(navController: NavController) {
        this.navController = navController
    }
    
    fun loadQuiz(quiz: StudentQuiz) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                quiz = quiz,
                selectedAnswers = List(quiz.questions.size) { null },
                timeRemaining = quiz.timeLimit * 60, // Convert minutes to seconds
                isSubmitted = false,
                error = null
            )
            
            // Start timer
            startTimer()
        }
    }
    
    fun setError(errorMessage: String) {
        _uiState.value = _uiState.value.copy(
            isLoading = false,
            error = errorMessage
        )
    }
    
    fun selectAnswer(questionIndex: Int, answerIndex: Int) {
        val currentAnswers = _uiState.value.selectedAnswers.toMutableList()
        currentAnswers[questionIndex] = answerIndex
        _uiState.value = _uiState.value.copy(selectedAnswers = currentAnswers)
    }
    
    fun nextQuestion() {
        val currentIndex = _uiState.value.currentQuestionIndex
        val totalQuestions = _uiState.value.quiz?.questions?.size ?: 0
        if (currentIndex < totalQuestions - 1) {
            _uiState.value = _uiState.value.copy(currentQuestionIndex = currentIndex + 1)
        }
    }
    
    fun previousQuestion() {
        val currentIndex = _uiState.value.currentQuestionIndex
        if (currentIndex > 0) {
            _uiState.value = _uiState.value.copy(currentQuestionIndex = currentIndex - 1)
        }
    }
    
    fun submitQuiz(userId: String = "academic_001") {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSubmitted = true)
            
            val quiz = _uiState.value.quiz ?: return@launch
            val selectedAnswers = _uiState.value.selectedAnswers
            
            // Calculate score
            var correctAnswers = 0
            quiz.questions.forEachIndexed { index, question ->
                if (selectedAnswers[index] == question.correctAnswerIndex) {
                    correctAnswers++
                }
            }
            
            val totalQuestions = quiz.questions.size
            val score = if (totalQuestions > 0) {
                (correctAnswers.toFloat() / totalQuestions * 100).toInt()
            } else {
                0
            }
            val passed = score >= 70 // Passing score threshold
            
            // Save quiz attempt to database
            try {
                val attemptId = "attempt_${System.currentTimeMillis()}"
                val answersJson = gson.toJson(selectedAnswers.mapIndexed { index, answer ->
                    mapOf(
                        "questionId" to quiz.questions[index].id,
                        "selectedAnswer" to (answer ?: -1),
                        "isCorrect" to (answer == quiz.questions[index].correctAnswerIndex)
                    )
                })
                
                val attempt = QuizAttemptEntity(
                    id = attemptId,
                    userId = userId,
                    quizTitle = quiz.title,
                    totalQuestions = totalQuestions,
                    correctAnswers = correctAnswers,
                    score = score,
                    passed = passed,
                    completedAt = System.currentTimeMillis(),
                    answersJson = answersJson
                )
                
                assessmentDao.insertQuizAttempt(attempt)
                
                // Navigate to results screen
                navController?.navigate("student_quiz_results/${quiz.id}/$score/$correctAnswers/$totalQuestions")
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to save quiz result"
                )
            }
        }
    }
    
    private fun startTimer() {
        viewModelScope.launch {
            while (_uiState.value.timeRemaining > 0 && !_uiState.value.isSubmitted) {
                delay(1000)
                _uiState.value = _uiState.value.copy(
                    timeRemaining = _uiState.value.timeRemaining - 1
                )
            }
            
            // Auto submit when time runs out
            if (_uiState.value.timeRemaining == 0 && !_uiState.value.isSubmitted) {
                submitQuiz()
            }
        }
    }
}

