package com.ignitech.esgcompanion.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignitech.esgcompanion.data.local.entity.ESGQuestionEntity
import com.ignitech.esgcompanion.data.local.entity.QuizAttemptEntity
import com.ignitech.esgcompanion.data.local.dao.AssessmentDao
import com.ignitech.esgcompanion.data.dao.ESGAssessmentDao
import com.ignitech.esgcompanion.domain.entity.ESGPillar
import com.ignitech.esgcompanion.domain.entity.UserRole
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

data class StudentQuiz(
    val id: String,
    val title: String,
    val description: String,
    val category: StudentQuizCategory,
    val questionCount: Int,
    val timeLimit: Int, // in minutes
    val bestScore: Int? = null,
    val questions: List<StudentQuizQuestion>
)

data class StudentQuizQuestion(
    val id: String,
    val question: String,
    val options: List<String>,
    val correctAnswerIndex: Int,
    val explanation: String?
)

enum class StudentQuizCategory(
    val displayName: String,
    val icon: String,
    val pillar: ESGPillar?
) {
    ENVIRONMENTAL("Environmental", "🌱", ESGPillar.ENVIRONMENTAL),
    SOCIAL("Social", "👥", ESGPillar.SOCIAL),
    GOVERNANCE("Governance", "🏛️", ESGPillar.GOVERNANCE),
    SUSTAINABILITY("Sustainability", "♻️", null)
}

data class StudentQuizAttempt(
    val id: String,
    val userId: String,
    val quizTitle: String,
    val totalQuestions: Int,
    val correctAnswers: Int,
    val score: Int,
    val passed: Boolean,
    val completedAt: Long
)

data class StudentAssessmentUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val quizzes: List<StudentQuiz> = emptyList(),
    val recentAttempts: List<StudentQuizAttempt> = emptyList(),
    val achievements: StudentAchievements = StudentAchievements()
)

data class StudentAchievements(
    val completedLessons: Int = 0,
    val averageScore: Int = 0,
    val certificates: Int = 0
)

@HiltViewModel
class StudentAssessmentViewModel @Inject constructor(
    private val esgAssessmentDao: ESGAssessmentDao,
    private val assessmentDao: AssessmentDao
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(StudentAssessmentUiState())
    val uiState: StateFlow<StudentAssessmentUiState> = _uiState.asStateFlow()
    
    private val gson = Gson()
    
    fun loadQuizzesAndAttempts(userId: String = "academic_001") {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            try {
                // Load questions for students (ACADEMIC role)
                val allQuestions = esgAssessmentDao.getQuestionsByRole(UserRole.ACADEMIC).first()
                
                // Load quiz attempts first (needed for best scores)
                val attempts = assessmentDao.getQuizAttemptsByUser(userId).first()
                
                // Group questions by category/pillar to create quizzes
                val quizzes = createQuizzesFromQuestions(allQuestions, userId, attempts)
                
                val recentAttempts = attempts.take(5).map { mapToStudentQuizAttempt(it) }
                
                // Calculate achievements
                val achievements = calculateAchievements(attempts)
                
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    quizzes = quizzes,
                    recentAttempts = recentAttempts,
                    achievements = achievements,
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load quizzes"
                )
            }
        }
    }
    
    private fun createQuizzesFromQuestions(
        questions: List<ESGQuestionEntity>,
        userId: String,
        attempts: List<QuizAttemptEntity>
    ): List<StudentQuiz> {
        val quizzes = mutableListOf<StudentQuiz>()
        
        // Group by pillar/category
        val questionsByCategory = questions.groupBy { question ->
            when (question.pillar) {
                ESGPillar.ENVIRONMENTAL -> StudentQuizCategory.ENVIRONMENTAL
                ESGPillar.SOCIAL -> StudentQuizCategory.SOCIAL
                ESGPillar.GOVERNANCE -> StudentQuizCategory.GOVERNANCE
                else -> StudentQuizCategory.SUSTAINABILITY
            }
        }
        
        // Create quizzes from grouped questions
        questionsByCategory.forEach { (category, categoryQuestions) ->
            if (categoryQuestions.isNotEmpty()) {
                // Group by sub-category or create single quiz per category
                val questionsBySubCategory = categoryQuestions.groupBy { it.category }
                
                questionsBySubCategory.forEach { (subCategory, subCategoryQuestions) ->
                    val quizId = "${category.name.lowercase()}_quiz_${subCategory.lowercase().replace(" ", "_")}"
                    val quizTitle = when (category) {
                        StudentQuizCategory.ENVIRONMENTAL -> "$subCategory Quiz"
                        StudentQuizCategory.SOCIAL -> "$subCategory Quiz"
                        StudentQuizCategory.GOVERNANCE -> "$subCategory Quiz"
                        StudentQuizCategory.SUSTAINABILITY -> "$subCategory Quiz"
                    }
                    
                    val quizQuestions = subCategoryQuestions.take(20).map { question ->
                        parseQuestionToQuizQuestion(question)
                    }
                    
                    // Get best score for this quiz from attempts list
                    val bestScore = attempts
                        .filter { it.quizTitle == quizTitle }
                        .maxOfOrNull { it.score }
                    
                    // Calculate time limit (1 minute per question, minimum 15 minutes)
                    val timeLimit = maxOf(15, quizQuestions.size)
                    
                    quizzes.add(
                        StudentQuiz(
                            id = quizId,
                            title = quizTitle,
                            description = "Test your knowledge about $subCategory",
                            category = category,
                            questionCount = quizQuestions.size,
                            timeLimit = timeLimit,
                            bestScore = bestScore,
                            questions = quizQuestions
                        )
                    )
                }
            }
        }
        
        return quizzes
    }
    
    private fun parseQuestionToQuizQuestion(question: ESGQuestionEntity): StudentQuizQuestion {
        val optionsList = try {
            val type = object : TypeToken<List<Map<String, Any>>>() {}.type
            val options: List<Map<String, Any>> = gson.fromJson(question.optionsJson, type)
            
            val optionTexts = options.map { it["text"] as? String ?: "" }
            val correctAnswerText = optionTexts.firstOrNull { option ->
                val optionMap = options.find { it["text"] == option }
                (optionMap?.get("score") as? Double ?: 0.0).toInt() >= 3
            } ?: optionTexts.firstOrNull() ?: ""
            
            val correctIndex = optionTexts.indexOf(correctAnswerText)
            
            Pair(optionTexts, maxOf(0, correctIndex))
        } catch (e: Exception) {
            // Fallback if JSON parsing fails
            val fallbackOptions = listOf("Option 1", "Option 2", "Option 3", "Option 4")
            Pair(fallbackOptions, 0)
        }
        
        return StudentQuizQuestion(
            id = question.id,
            question = question.question,
            options = optionsList.first,
            correctAnswerIndex = optionsList.second,
            explanation = question.description
        )
    }
    
    
    private fun mapToStudentQuizAttempt(attempt: QuizAttemptEntity): StudentQuizAttempt {
        return StudentQuizAttempt(
            id = attempt.id,
            userId = attempt.userId,
            quizTitle = attempt.quizTitle,
            totalQuestions = attempt.totalQuestions,
            correctAnswers = attempt.correctAnswers,
            score = attempt.score,
            passed = attempt.passed,
            completedAt = attempt.completedAt
        )
    }
    
    private fun calculateAchievements(attempts: List<QuizAttemptEntity>): StudentAchievements {
        val completedCount = attempts.size
        val averageScore = if (attempts.isNotEmpty()) {
            attempts.map { it.score }.average().toInt()
        } else {
            0
        }
        val certificates = attempts.count { it.passed && it.score >= 80 }
        
        return StudentAchievements(
            completedLessons = completedCount,
            averageScore = averageScore,
            certificates = certificates
        )
    }
    
    fun refresh(userId: String = "academic_001") {
        loadQuizzesAndAttempts(userId)
    }
    
    fun getQuizzesForCategory(category: StudentQuizCategory): List<StudentQuiz> {
        return _uiState.value.quizzes.filter { it.category == category }
    }
    
    fun getQuizById(quizId: String): StudentQuiz? {
        return _uiState.value.quizzes.find { it.id == quizId }
    }
}

