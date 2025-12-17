package com.ignitech.esgcompanion.presentation.screen.expert

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ignitech.esgcompanion.presentation.viewmodel.ExpertQuizResultsViewModel
import com.ignitech.esgcompanion.utils.AppColors
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpertQuizResultsScreen(
    navController: NavController,
    quizId: String,
    score: Int,
    viewModel: ExpertQuizResultsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val colors = AppColors()

    LaunchedEffect(quizId) {
        viewModel.loadQuizResults(quizId, score)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Quiz Results",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.backgroundSurface)
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Score Summary Card
            item {
                ScoreSummaryCard(
                    score = score,
                    totalQuestions = uiState.quiz?.questions?.size ?: 0,
                    timeSpent = uiState.timeSpent,
                    completedAt = uiState.completedAt
                )
            }

            // Performance Analysis
            item {
                PerformanceAnalysisCard(
                    score = score,
                    category = uiState.quiz?.category ?: "",
                    difficulty = uiState.quiz?.difficulty ?: QuizDifficulty.MEDIUM
                )
            }

            // Question Review
            uiState.quiz?.let { quiz ->
                item {
                    Text(
                        text = "Question Details",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = colors.textPrimary
                    )
                }

                items(quiz.questions) { question ->
                    QuestionReviewCard(
                        question = question,
                        userAnswer = uiState.userAnswers[question.id] ?: -1,
                        isCorrect = uiState.userAnswers[question.id] == question.correctAnswer
                    )
                }
            }

            // Action Buttons
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = { navController.navigate("expert_quiz_taking/$quizId") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colors.primary
                        )
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Retake Quiz")
                    }

                    OutlinedButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Home, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Home")
                    }
                }
            }
        }
    }
}

@Composable
fun ScoreSummaryCard(
    score: Int,
    totalQuestions: Int,
    timeSpent: Int,
    completedAt: Long
) {
    val colors = AppColors()
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = when {
                score >= 80 -> Color(0xFF4CAF50).copy(alpha = 0.1f)
                score >= 60 -> Color(0xFFFF9800).copy(alpha = 0.1f)
                else -> Color(0xFFFF5722).copy(alpha = 0.1f)
            }
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Score Circle
            Box(
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    progress = score / 100f,
                    modifier = Modifier.size(120.dp),
                    strokeWidth = 8.dp,
                    color = when {
                        score >= 80 -> Color(0xFF4CAF50)
                        score >= 60 -> Color(0xFFFF9800)
                        else -> Color(0xFFFF5722)
                    },
                    trackColor = colors.textSecondary.copy(alpha = 0.3f)
                )
                
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "$score%",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = colors.textPrimary
                    )
                    Text(
                        text = "Score",
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.textSecondary
                    )
                }
            }

            // Score Details
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ScoreDetailItem(
                    label = "Correct",
                    value = "${(score * totalQuestions / 100)}/$totalQuestions",
                    icon = Icons.Default.CheckCircle,
                    color = Color(0xFF4CAF50)
                )
                
                ScoreDetailItem(
                    label = "Time",
                    value = formatTime(timeSpent),
                    icon = Icons.Default.Timer,
                    color = colors.primary
                )
            }

            Divider()

            Text(
                text = "Completed at: ${dateFormat.format(Date(completedAt))}",
                style = MaterialTheme.typography.bodySmall,
                color = colors.textSecondary
            )
        }
    }
}

@Composable
fun ScoreDetailItem(
    label: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = color.copy(alpha = 0.7f)
        )
    }
}

@Composable
fun PerformanceAnalysisCard(
    score: Int,
    category: String,
    difficulty: QuizDifficulty
) {
    val colors = AppColors()
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = colors.backgroundSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Results Analysis",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = colors.textPrimary
            )

            // Performance Level
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Level:",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.textSecondary
                )
                
                Surface(
                    color = when {
                        score >= 90 -> Color(0xFF4CAF50).copy(alpha = 0.1f)
                        score >= 80 -> Color(0xFF8BC34A).copy(alpha = 0.1f)
                        score >= 70 -> Color(0xFFFF9800).copy(alpha = 0.1f)
                        score >= 60 -> Color(0xFFFFC107).copy(alpha = 0.1f)
                        else -> Color(0xFFFF5722).copy(alpha = 0.1f)
                    },
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = when {
                            score >= 90 -> "Excellent"
                            score >= 80 -> "Very Good"
                            score >= 70 -> "Good"
                            score >= 60 -> "Fair"
                            else -> "Needs Improvement"
                        },
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = when {
                            score >= 90 -> Color(0xFF4CAF50)
                            score >= 80 -> Color(0xFF8BC34A)
                            score >= 70 -> Color(0xFFFF9800)
                            score >= 60 -> Color(0xFFFFC107)
                            else -> Color(0xFFFF5722)
                        }
                    )
                }
            }

            // Category and Difficulty
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Category: $category",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.textSecondary
                )
                
                DifficultyChip(difficulty = difficulty)
            }
        }
    }
}

@Composable
fun QuestionReviewCard(
    question: QuizQuestion,
    userAnswer: Int,
    isCorrect: Boolean
) {
    val colors = AppColors()
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isCorrect) 
                Color(0xFF4CAF50).copy(alpha = 0.05f) 
            else 
                Color(0xFFFF5722).copy(alpha = 0.05f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = question.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = colors.textPrimary,
                    modifier = Modifier.weight(1f)
                )
                
                Icon(
                    imageVector = if (isCorrect) Icons.Default.CheckCircle else Icons.Default.Cancel,
                    contentDescription = null,
                    tint = if (isCorrect) Color(0xFF4CAF50) else Color(0xFFFF5722)
                )
            }

            // Answer options
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                question.options.forEachIndexed { index, option ->
                    val optionLabels = listOf("A", "B", "C", "D", "E")
                    val isUserAnswer = userAnswer == index
                    val isCorrectAnswer = question.correctAnswer == index
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Surface(
                            color = when {
                                isCorrectAnswer -> Color(0xFF4CAF50).copy(alpha = 0.2f)
                                isUserAnswer -> Color(0xFFFF5722).copy(alpha = 0.2f)
                                else -> colors.textSecondary.copy(alpha = 0.1f)
                            },
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text(
                                text = optionLabels[index],
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold,
                                color = when {
                                    isCorrectAnswer -> Color(0xFF4CAF50)
                                    isUserAnswer -> Color(0xFFFF5722)
                                    else -> colors.textSecondary
                                }
                            )
                        }
                        
                        Text(
                            text = option,
                            style = MaterialTheme.typography.bodyMedium,
                            color = when {
                                isCorrectAnswer -> Color(0xFF4CAF50)
                                isUserAnswer -> Color(0xFFFF5722)
                                else -> colors.textSecondary
                            },
                            modifier = Modifier.weight(1f)
                        )
                        
                        if (isCorrectAnswer) {
                            Icon(
                                Icons.Default.Check,
                                contentDescription = "Correct",
                                tint = Color(0xFF4CAF50),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        
                        if (isUserAnswer && !isCorrectAnswer) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Wrong",
                                tint = Color(0xFFFF5722),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }

            // Explanation
            if (question.explanation.isNotEmpty()) {
                Surface(
                    color = colors.primary.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Explanation: ${question.explanation}",
                        modifier = Modifier.padding(12.dp),
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.textPrimary
                    )
                }
            }
        }
    }
}

private fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format("%02d:%02d", minutes, remainingSeconds)
}

// Data classes
data class QuizResultsUiState(
    val isLoading: Boolean = false,
    val quiz: ExpertQuiz? = null,
    val userAnswers: Map<String, Int> = emptyMap(),
    val timeSpent: Int = 0,
    val completedAt: Long = System.currentTimeMillis()
)
