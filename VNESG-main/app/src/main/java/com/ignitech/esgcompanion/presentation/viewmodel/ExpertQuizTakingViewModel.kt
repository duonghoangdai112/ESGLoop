package com.ignitech.esgcompanion.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.ignitech.esgcompanion.presentation.screen.expert.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpertQuizTakingViewModel @Inject constructor() : ViewModel() {
    
    private val _uiState = MutableStateFlow(QuizTakingUiState())
    val uiState: StateFlow<QuizTakingUiState> = _uiState.asStateFlow()
    
    private var navController: NavController? = null
    
    fun setNavController(navController: NavController) {
        this.navController = navController
    }
    
    fun loadQuiz(quizId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            // Simulate loading delay
            delay(1000)
            
            // Load mock quiz data
            val quiz = getMockQuiz(quizId)
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                quiz = quiz,
                selectedAnswers = List(quiz.questions.size) { null },
                timeRemaining = quiz.duration * 60 // Convert minutes to seconds
            )
            
            // Start timer
            startTimer()
        }
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
    
    fun submitQuiz() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSubmitted = true)
            
            // Calculate score
            val quiz = _uiState.value.quiz ?: return@launch
            val selectedAnswers = _uiState.value.selectedAnswers
            var correctAnswers = 0
            
            quiz.questions.forEachIndexed { index, question ->
                if (selectedAnswers[index] == question.correctAnswer) {
                    correctAnswers++
                }
            }
            
            val score = (correctAnswers.toFloat() / quiz.questions.size * 100).toInt()
            
            // TODO: Save quiz result to database
            // Navigate to results screen with score
            navController?.navigate("expert_quiz_results/${quiz.id}/$score")
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
}
