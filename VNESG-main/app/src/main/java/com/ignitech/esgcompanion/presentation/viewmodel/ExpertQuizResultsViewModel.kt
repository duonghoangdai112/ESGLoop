package com.ignitech.esgcompanion.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignitech.esgcompanion.presentation.screen.expert.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpertQuizResultsViewModel @Inject constructor() : ViewModel() {
    
    private val _uiState = MutableStateFlow(QuizResultsUiState())
    val uiState: StateFlow<QuizResultsUiState> = _uiState.asStateFlow()
    
    fun loadQuizResults(quizId: String, score: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            // Simulate loading delay
            delay(500)
            
            // Load mock quiz data and results
            val quiz = getMockQuiz(quizId)
            val userAnswers = generateMockUserAnswers(quiz, score)
            
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                quiz = quiz,
                userAnswers = userAnswers,
                timeSpent = quiz.duration * 60 - (quiz.duration * 60 * score / 100), // Mock time calculation
                completedAt = System.currentTimeMillis()
            )
        }
    }
    
    private fun getMockQuiz(quizId: String): ExpertQuiz {
        return ExpertQuiz(
            id = quizId,
            title = "Kiến thức ESG nâng cao",
            description = "Bài thi tổng hợp kiến thức ESG chuyên sâu dành cho chuyên gia",
            questionCount = 5,
            duration = 30,
            difficulty = QuizDifficulty.EXPERT,
            category = "Tổng hợp",
            questions = listOf(
                QuizQuestion(
                    id = "q1",
                    title = "ESG là viết tắt của gì?",
                    description = "Chọn đáp án đúng nhất",
                    options = listOf(
                        "Environmental, Social, Governance",
                        "Economic, Social, Governance",
                        "Environmental, Sustainability, Governance",
                        "Energy, Social, Governance"
                    ),
                    correctAnswer = 0,
                    difficulty = QuizDifficulty.EASY,
                    explanation = "ESG là viết tắt của Environmental (Môi trường), Social (Xã hội), và Governance (Quản trị)."
                ),
                QuizQuestion(
                    id = "q2",
                    title = "Theo tiêu chuẩn GRI, báo cáo bền vững cần bao gồm những yếu tố nào?",
                    options = listOf(
                        "Chỉ các chỉ số tài chính",
                        "Chỉ các tác động môi trường",
                        "Các chỉ số kinh tế, môi trường và xã hội",
                        "Chỉ các hoạt động từ thiện"
                    ),
                    correctAnswer = 2,
                    difficulty = QuizDifficulty.MEDIUM,
                    explanation = "GRI yêu cầu báo cáo bền vững phải bao gồm đầy đủ các chỉ số kinh tế, môi trường và xã hội."
                ),
                QuizQuestion(
                    id = "q3",
                    title = "Carbon footprint là gì?",
                    options = listOf(
                        "Lượng carbon dioxide được thải ra trực tiếp và gián tiếp",
                        "Dấu chân carbon trên đất",
                        "Lượng carbon được hấp thụ bởi cây cối",
                        "Phương pháp đo lường carbon trong không khí"
                    ),
                    correctAnswer = 0,
                    difficulty = QuizDifficulty.EASY,
                    explanation = "Carbon footprint là tổng lượng khí nhà kính (chủ yếu CO2) được thải ra trực tiếp và gián tiếp."
                ),
                QuizQuestion(
                    id = "q4",
                    title = "TCFD (Task Force on Climate-related Financial Disclosures) khuyến nghị các công ty nên tiết lộ thông tin về:",
                    options = listOf(
                        "Chỉ các rủi ro tài chính",
                        "Chỉ các cơ hội kinh doanh",
                        "Cả rủi ro và cơ hội liên quan đến khí hậu",
                        "Chỉ các hoạt động CSR"
                    ),
                    correctAnswer = 2,
                    difficulty = QuizDifficulty.HARD,
                    explanation = "TCFD khuyến nghị tiết lộ cả rủi ro và cơ hội liên quan đến khí hậu ảnh hưởng đến hoạt động kinh doanh."
                ),
                QuizQuestion(
                    id = "q5",
                    title = "UN SDGs (Sustainable Development Goals) có bao nhiều mục tiêu?",
                    options = listOf(
                        "15 mục tiêu",
                        "16 mục tiêu",
                        "17 mục tiêu",
                        "18 mục tiêu"
                    ),
                    correctAnswer = 2,
                    difficulty = QuizDifficulty.MEDIUM,
                    explanation = "UN SDGs có 17 mục tiêu phát triển bền vững được thông qua vào năm 2015."
                )
            )
        )
    }
    
    private fun generateMockUserAnswers(quiz: ExpertQuiz, score: Int): Map<String, Int> {
        val userAnswers = mutableMapOf<String, Int>()
        val totalQuestions = quiz.questions.size
        val correctAnswers = (totalQuestions * score / 100)
        
        // Generate answers based on score
        quiz.questions.forEachIndexed { index, question ->
            userAnswers[question.id] = if (index < correctAnswers) {
                question.correctAnswer
            } else {
                // Generate wrong answer
                val wrongAnswers = question.options.indices.filter { it != question.correctAnswer }
                wrongAnswers.random()
            }
        }
        
        return userAnswers
    }
}
