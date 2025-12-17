package com.ignitech.esgcompanion.presentation.screen.student

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ignitech.esgcompanion.presentation.viewmodel.StudentQuizTakingViewModel
import com.ignitech.esgcompanion.presentation.viewmodel.StudentQuiz
import com.ignitech.esgcompanion.presentation.viewmodel.StudentQuizQuestion
import com.ignitech.esgcompanion.utils.AppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentQuizTakingScreen(
    navController: NavController,
    quizId: String,
    viewModel: StudentQuizTakingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val colors = AppColors()

    LaunchedEffect(quizId) {
        viewModel.setNavController(navController)
        val quiz = com.ignitech.esgcompanion.presentation.viewmodel.StudentQuizCache.getQuiz(quizId)
        if (quiz != null) {
            viewModel.loadQuiz(quiz)
        } else {
            viewModel.setError("Quiz not found. Please try again.")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Quiz: ${uiState.quiz?.title ?: "Loading..."}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    // Timer
                    if (uiState.timeRemaining > 0) {
                        Text(
                            text = formatTime(uiState.timeRemaining),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = if (uiState.timeRemaining < 300) Color(0xFFFF5722) else colors.textPrimary,
                            modifier = Modifier.padding(end = 16.dp)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.backgroundSurface)
                .padding(paddingValues)
        ) {
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (uiState.quiz != null) {
                val currentQuiz = uiState.quiz!!
                
                // Progress bar
                LinearProgressIndicator(
                    progress = uiState.currentQuestionIndex.toFloat() / currentQuiz.questions.size,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    color = colors.primary
                )

                // Question counter
                Text(
                    text = "Question ${uiState.currentQuestionIndex + 1}/${currentQuiz.questions.size}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.textSecondary,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                // Question content
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        StudentQuestionCard(
                            question = currentQuiz.questions[uiState.currentQuestionIndex],
                            selectedAnswer = uiState.selectedAnswers[uiState.currentQuestionIndex],
                            onAnswerSelected = { answerIndex ->
                                viewModel.selectAnswer(uiState.currentQuestionIndex, answerIndex)
                            }
                        )
                    }
                }

                // Navigation buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Previous button
                    Button(
                        onClick = { viewModel.previousQuestion() },
                        enabled = uiState.currentQuestionIndex > 0,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (uiState.currentQuestionIndex > 0) colors.primary else colors.textSecondary.copy(alpha = 0.3f)
                        )
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Previous")
                    }

                    // Question counter in center
                    Text(
                        text = "${uiState.currentQuestionIndex + 1}/${currentQuiz.questions.size}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = colors.textPrimary,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    // Next/Submit button
                    Button(
                        onClick = {
                            if (uiState.currentQuestionIndex == currentQuiz.questions.size - 1) {
                                viewModel.submitQuiz()
                            } else {
                                viewModel.nextQuestion()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (uiState.currentQuestionIndex == currentQuiz.questions.size - 1) 
                                Color(0xFF4CAF50) 
                            else 
                                colors.primary
                        )
                    ) {
                        Text(
                            text = if (uiState.currentQuestionIndex == currentQuiz.questions.size - 1) "Submit" else "Next"
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = if (uiState.currentQuestionIndex == currentQuiz.questions.size - 1) 
                                Icons.Default.Check 
                            else 
                                Icons.Default.ArrowForward,
                            contentDescription = null
                        )
                    }
                }
            } else if (uiState.error != null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = uiState.error ?: "Error",
                            color = MaterialTheme.colorScheme.error
                        )
                        Button(
                            onClick = { navController.popBackStack() }
                        ) {
                            Text("Go Back")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StudentQuestionCard(
    question: StudentQuizQuestion,
    selectedAnswer: Int?,
    onAnswerSelected: (Int) -> Unit
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
            // Question text
            Text(
                text = question.question,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = colors.textPrimary
            )
            
            // Question explanation (if any)
            if (!question.explanation.isNullOrEmpty()) {
                Text(
                    text = question.explanation ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.textSecondary
                )
            }
            
            // Answer options
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                question.options.forEachIndexed { index, option ->
                    StudentAnswerOption(
                        option = option,
                        index = index,
                        isSelected = selectedAnswer == index,
                        onClick = { onAnswerSelected(index) }
                    )
                }
            }
        }
    }
}

@Composable
fun StudentAnswerOption(
    option: String,
    index: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val colors = AppColors()
    val optionLabels = listOf("A", "B", "C", "D", "E")
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) 
                colors.primary.copy(alpha = 0.1f) 
            else 
                colors.backgroundSurface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Option label (A, B, C, D)
            Surface(
                color = if (isSelected) colors.primary else colors.textSecondary.copy(alpha = 0.3f),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(
                    text = optionLabels.getOrElse(index) { "?" },
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (isSelected) Color.White else colors.textSecondary
                )
            }
            
            // Option text
            Text(
                text = option,
                style = MaterialTheme.typography.bodyMedium,
                color = colors.textPrimary,
                modifier = Modifier.weight(1f)
            )
            
            // Selection indicator
            if (isSelected) {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = "Selected",
                    tint = colors.primary
                )
            }
        }
    }
}

private fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format("%02d:%02d", minutes, remainingSeconds)
}

