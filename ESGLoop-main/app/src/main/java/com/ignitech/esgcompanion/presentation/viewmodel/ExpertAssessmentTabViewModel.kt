package com.ignitech.esgcompanion.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.ignitech.esgcompanion.presentation.screen.expert.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpertAssessmentTabViewModel @Inject constructor() : ViewModel() {
    
    private val _uiState = MutableStateFlow(ExpertAssessmentTabUiState())
    val uiState: StateFlow<ExpertAssessmentTabUiState> = _uiState.asStateFlow()
    
    private var navController: NavController? = null
    
    fun setNavController(navController: NavController) {
        this.navController = navController
    }
    
    fun loadAssessmentData() {
        viewModelScope.launch {
            // Load mock data for now
            _uiState.value = _uiState.value.copy(
                totalQuizzes = 15,
                completedQuizzes = 8,
                averageScore = 87.5f,
                currentStreak = 5,
                featuredQuiz = ExpertQuiz(
                    id = "featured_001",
                    title = "Kiến thức ESG nâng cao",
                    description = "Bài thi tổng hợp kiến thức ESG chuyên sâu dành cho chuyên gia",
                    questionCount = 50,
                    duration = 60,
                    difficulty = QuizDifficulty.EXPERT,
                    category = "Tổng hợp"
                ),
                quizCategories = listOf(
                    QuizCategory("env", "Môi trường", "🌱", 5),
                    QuizCategory("social", "Xã hội", "👥", 4),
                    QuizCategory("governance", "Quản trị", "🏛️", 3),
                    QuizCategory("compliance", "Tuân thủ", "📋", 2),
                    QuizCategory("reporting", "Báo cáo", "📊", 1)
                ),
                recentQuizzes = listOf(
                    ExpertQuiz(
                        id = "quiz_001",
                        title = "Quản lý rủi ro môi trường",
                        description = "Đánh giá kiến thức về quản lý rủi ro môi trường",
                        questionCount = 25,
                        duration = 30,
                        difficulty = QuizDifficulty.HARD,
                        category = "Môi trường",
                        isCompleted = true,
                        lastAttemptedAt = System.currentTimeMillis() - 86400000,
                        bestScore = 92
                    ),
                    ExpertQuiz(
                        id = "quiz_002",
                        title = "Đa dạng và hòa nhập",
                        description = "Kiến thức về đa dạng và hòa nhập trong doanh nghiệp",
                        questionCount = 20,
                        duration = 25,
                        difficulty = QuizDifficulty.MEDIUM,
                        category = "Xã hội",
                        isCompleted = false
                    ),
                    ExpertQuiz(
                        id = "quiz_003",
                        title = "Quản trị doanh nghiệp",
                        description = "Nguyên tắc quản trị doanh nghiệp bền vững",
                        questionCount = 30,
                        duration = 35,
                        difficulty = QuizDifficulty.HARD,
                        category = "Quản trị",
                        isCompleted = true,
                        lastAttemptedAt = System.currentTimeMillis() - 172800000,
                        bestScore = 88
                    )
                ),
                achievements = listOf(
                    ExpertAchievement(
                        id = "ach_001",
                        title = "Chuyên gia ESG",
                        icon = "⭐",
                        color = androidx.compose.ui.graphics.Color(0xFFFFD700),
                        isUnlocked = true
                    ),
                    ExpertAchievement(
                        id = "ach_002",
                        title = "Người học chăm",
                        icon = "🎓",
                        color = androidx.compose.ui.graphics.Color(0xFF4CAF50),
                        isUnlocked = true
                    ),
                    ExpertAchievement(
                        id = "ach_003",
                        title = "Thi thử liên tục",
                        icon = "🔥",
                        color = androidx.compose.ui.graphics.Color(0xFFFF5722),
                        isUnlocked = true
                    ),
                    ExpertAchievement(
                        id = "ach_004",
                        title = "Điểm cao",
                        icon = "🏆",
                        color = androidx.compose.ui.graphics.Color(0xFF9C27B0),
                        isUnlocked = false
                    )
                )
            )
        }
    }
    
    fun startQuiz(quizId: String) {
        navController?.navigate("expert_quiz_taking/$quizId")
    }
    
    fun viewQuizResults(quizId: String) {
        // Get the best score for this quiz from the recent quizzes
        val quiz = _uiState.value.recentQuizzes.find { it.id == quizId }
        val score = quiz?.bestScore ?: 0
        navController?.navigate("expert_quiz_results/$quizId/$score")
    }
    
    fun selectCategory(categoryId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(selectedCategory = categoryId)
        }
    }
    
    fun viewAllQuizzes() {
        viewModelScope.launch {
            // TODO: Navigate to all quizzes screen
        }
    }
}

data class ExpertAssessmentTabUiState(
    val totalQuizzes: Int = 0,
    val completedQuizzes: Int = 0,
    val averageScore: Float = 0f,
    val currentStreak: Int = 0,
    val featuredQuiz: ExpertQuiz? = null,
    val quizCategories: List<QuizCategory> = emptyList(),
    val recentQuizzes: List<ExpertQuiz> = emptyList(),
    val achievements: List<ExpertAchievement> = emptyList(),
    val selectedCategory: String? = null
)

