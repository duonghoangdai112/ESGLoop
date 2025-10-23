package com.ignitech.esgcompanion.presentation.screen.expert

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ignitech.esgcompanion.domain.entity.ESGPillar
import com.ignitech.esgcompanion.presentation.viewmodel.ExpertAssessmentTabViewModel
import com.ignitech.esgcompanion.utils.AppColors
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpertAssessmentTabScreen(
    modifier: Modifier = Modifier,
    navController: androidx.navigation.NavController? = null,
    viewModel: ExpertAssessmentTabViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val colors = AppColors()

    LaunchedEffect(Unit) {
        navController?.let { viewModel.setNavController(it) }
        viewModel.loadAssessmentData()
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(colors.backgroundSurface),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header Section
        item {
            Column {
                Text(
                    text = "ESG Knowledge Assessment",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = colors.textPrimary
                )
                Text(
                    text = "Test and enhance professional knowledge",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.textSecondary
                )
            }
        }

        // Quick Stats
        item {
            ExpertAssessmentStatsSection(
                totalQuizzes = uiState.totalQuizzes,
                completedQuizzes = uiState.completedQuizzes,
                averageScore = uiState.averageScore,
                currentStreak = uiState.currentStreak
            )
        }

        // Featured Quiz
        if (uiState.featuredQuiz != null) {
            item {
                Text(
                    text = "Featured Quizzes",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = colors.textPrimary
                )
            }
            
            item {
                FeaturedQuizCard(
                    quiz = uiState.featuredQuiz!!,
                    onClick = { viewModel.startQuiz(uiState.featuredQuiz!!.id) }
                )
            }
        }

        // Quiz Categories
        item {
            Text(
                text = "Quiz Categories",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = colors.textPrimary
            )
        }

        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 4.dp)
            ) {
                items(uiState.quizCategories) { category ->
                    QuizCategoryCard(
                        category = category,
                        onClick = { viewModel.selectCategory(category.id) }
                    )
                }
            }
        }

        // Recent Quizzes
        if (uiState.recentQuizzes.isNotEmpty()) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Recent Quizzes",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = colors.textPrimary
                    )
                    
                    TextButton(onClick = { viewModel.viewAllQuizzes() }) {
                        Text("View All")
                        Icon(Icons.Default.ArrowForward, contentDescription = null)
                    }
                }
            }
            
            items(uiState.recentQuizzes) { quiz ->
                QuizCard(
                    quiz = quiz,
                    onClick = { viewModel.startQuiz(quiz.id) },
                    onViewResults = { viewModel.viewQuizResults(quiz.id) }
                )
            }
        }

        // Achievements
        if (uiState.achievements.isNotEmpty()) {
            item {
                Text(
                    text = "Achievements",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = colors.textPrimary
                )
            }
            
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 4.dp)
                ) {
                    items(uiState.achievements) { achievement ->
                        AchievementCard(achievement = achievement)
                    }
                }
            }
        }
    }
}

@Composable
fun ExpertAssessmentStatsSection(
    totalQuizzes: Int,
    completedQuizzes: Int,
    averageScore: Float,
    currentStreak: Int
) {
    val colors = AppColors()
    
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Row 1
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ExpertStatCard(
                title = "Total Quizzes",
                value = totalQuizzes.toString(),
                color = colors.primary,
                modifier = Modifier.weight(1f)
            )
            
            ExpertStatCard(
                title = "Completed",
                value = completedQuizzes.toString(),
                color = Color(0xFF4CAF50),
                modifier = Modifier.weight(1f)
            )
        }
        
        // Row 2
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ExpertStatCard(
                title = "Avg Score",
                value = String.format("%.1f", averageScore),
                color = Color(0xFFFF9800),
                modifier = Modifier.weight(1f)
            )
            
            ExpertStatCard(
                title = "Day Streak",
                value = currentStreak.toString(),
                color = Color(0xFFFF5722),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun FeaturedQuizCard(
    quiz: ExpertQuiz,
    onClick: () -> Unit
) {
    val colors = AppColors()
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = colors.primary.copy(alpha = 0.1f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "⭐",
                    style = MaterialTheme.typography.headlineLarge
                )
                
                Surface(
                    color = colors.primary,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Featured",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White
                    )
                }
            }
            
            Text(
                text = quiz.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = colors.textPrimary
            )
            
            Text(
                text = quiz.description,
                style = MaterialTheme.typography.bodyMedium,
                color = colors.textSecondary
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "${quiz.questionCount} questions",
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.textSecondary
                    )
                    
                    Text(
                        text = "${quiz.duration} phút",
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.textSecondary
                    )
                    
                    Text(
                        text = when (quiz.difficulty) {
                            QuizDifficulty.EASY -> "Easy"
                            QuizDifficulty.MEDIUM -> "Medium"
                            QuizDifficulty.HARD -> "Hard"
                            QuizDifficulty.EXPERT -> "Expert"
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.textSecondary
                    )
                }
                
                Button(
                    onClick = onClick,
                    colors = ButtonDefaults.buttonColors(containerColor = colors.primary)
                ) {
                    Text("Bắt đầu")
                }
            }
        }
    }
}

@Composable
fun QuizCategoryCard(
    category: QuizCategory,
    onClick: () -> Unit
) {
    val colors = AppColors()
    
    Card(
        modifier = Modifier
            .width(120.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() },
        colors = CardDefaults.cardColors(containerColor = colors.backgroundSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = category.icon,
                style = MaterialTheme.typography.headlineMedium
            )
            
            Text(
                text = category.name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = colors.textPrimary,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            
            Text(
                text = "${category.quizCount} bài thi",
                style = MaterialTheme.typography.bodySmall,
                color = colors.textSecondary,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun QuizCard(
    quiz: ExpertQuiz,
    onClick: () -> Unit,
    onViewResults: () -> Unit
) {
    val colors = AppColors()
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() },
        colors = CardDefaults.cardColors(containerColor = colors.backgroundSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = quiz.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = colors.textPrimary,
                    modifier = Modifier.weight(1f)
                )
                
                DifficultyChip(difficulty = quiz.difficulty)
            }
            
            Text(
                text = quiz.description,
                style = MaterialTheme.typography.bodyMedium,
                color = colors.textSecondary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${quiz.questionCount} câu",
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.textSecondary
                    )
                    
                    Text(
                        text = "${quiz.duration} phút",
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.textSecondary
                    )
                    
                    if (quiz.lastAttemptedAt > 0) {
                        Text(
                            text = "Lần cuối: ${dateFormat.format(Date(quiz.lastAttemptedAt))}",
                            style = MaterialTheme.typography.bodySmall,
                            color = colors.textSecondary
                        )
                    }
                }
                
                if (quiz.isCompleted) {
                    Button(
                        onClick = onViewResults,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                    ) {
                        Text("Xem kết quả")
                    }
                } else {
                    Button(
                        onClick = onClick,
                        colors = ButtonDefaults.buttonColors(containerColor = colors.primary)
                    ) {
                        Text("Bắt đầu")
                    }
                }
            }
        }
    }
}

@Composable
fun AchievementCard(
    achievement: ExpertAchievement
) {
    val colors = AppColors()
    
    Card(
        modifier = Modifier.width(100.dp),
        colors = CardDefaults.cardColors(
            containerColor = achievement.color.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = achievement.icon,
                style = MaterialTheme.typography.headlineSmall
            )
            
            Text(
                text = achievement.title,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                color = colors.textPrimary,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

@Composable
fun DifficultyChip(difficulty: QuizDifficulty) {
    val (text, color) = when (difficulty) {
        QuizDifficulty.EASY -> "Dễ" to Color(0xFF4CAF50)
        QuizDifficulty.MEDIUM -> "Trung bình" to Color(0xFFFF9800)
        QuizDifficulty.HARD -> "Khó" to Color(0xFFFF5722)
        QuizDifficulty.EXPERT -> "Chuyên gia" to Color(0xFF9C27B0)
    }
    
    Surface(
        color = color.copy(alpha = 0.1f),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.bodySmall,
            color = color,
            fontWeight = FontWeight.Medium
        )
    }
}


// Data classes
data class ExpertQuiz(
    val id: String,
    val title: String,
    val description: String,
    val questionCount: Int,
    val duration: Int, // in minutes
    val difficulty: QuizDifficulty,
    val category: String,
    val isCompleted: Boolean = false,
    val lastAttemptedAt: Long = 0,
    val bestScore: Int = 0,
    val questions: List<QuizQuestion> = emptyList()
)

data class QuizCategory(
    val id: String,
    val name: String,
    val icon: String,
    val quizCount: Int
)

data class ExpertAchievement(
    val id: String,
    val title: String,
    val icon: String,
    val color: Color,
    val isUnlocked: Boolean = false
)

enum class QuizDifficulty {
    EASY, MEDIUM, HARD, EXPERT
}
