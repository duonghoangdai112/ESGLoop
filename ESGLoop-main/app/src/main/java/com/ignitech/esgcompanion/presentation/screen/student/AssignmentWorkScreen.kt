package com.ignitech.esgcompanion.presentation.screen.student

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ignitech.esgcompanion.R
import com.ignitech.esgcompanion.data.entity.QuestionEntity
import com.ignitech.esgcompanion.data.entity.QuestionType
import com.ignitech.esgcompanion.presentation.viewmodel.AssignmentWorkViewModel
import com.ignitech.esgcompanion.utils.AppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignmentWorkScreen(
    navController: NavController,
    assignmentId: String,
    modifier: Modifier = Modifier,
    viewModel: AssignmentWorkViewModel = hiltViewModel()
) {
    val assignment by viewModel.assignment.collectAsStateWithLifecycle()
    val questions by viewModel.questions.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val isSaving by viewModel.isSaving.collectAsStateWithLifecycle()
    val saveResult by viewModel.saveResult.collectAsStateWithLifecycle()
    val savedAnswers by viewModel.savedAnswers.collectAsStateWithLifecycle()
    val colors = AppColors()
    
    // User answers state - initialize with saved answers
    var userAnswers by remember { mutableStateOf(savedAnswers) }
    
    LaunchedEffect(assignmentId) {
        viewModel.loadAssignmentAndQuestions(assignmentId)
    }
    
    // Update userAnswers when savedAnswers change
    LaunchedEffect(savedAnswers) {
        userAnswers = savedAnswers
    }
    
    // Current question index
    var currentQuestionIndex by remember { mutableStateOf(0) }
    
    if (isLoading || assignment == null) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }
    
    val assignmentData = assignment!!
    
    if (questions.isEmpty()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Work on Assignment") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("Assignment not found")
            }
        }
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(assignmentData.title)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    Text(
                        text = "${assignmentData.estimatedTime} min",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                }
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Action buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { 
                            viewModel.saveAssignment(userAnswers.toMap())
                        },
                        enabled = !isSaving && userAnswers.isNotEmpty(),
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.esg_warning)
                        )
                    ) {
                        if (isSaving) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        } else {
                            Text("Save Assignment")
                        }
                    }
                    
                    Button(
                        onClick = { /* TODO: Submit assignment */ },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.interactive_primary)
                        )
                    ) {
                        Text("Submit")
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // Progress bar
            LinearProgressIndicator(
                progress = (currentQuestionIndex + 1).toFloat() / questions.size.toFloat(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp),
                color = colorResource(id = R.color.interactive_primary),
                trackColor = colorResource(id = R.color.border_light)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Question counter and type
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Question ${currentQuestionIndex + 1}/${questions.size}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.interactive_primary)
                )
                
                if (currentQuestionIndex < questions.size) {
                    QuestionTypeChip(type = questions[currentQuestionIndex].type)
                }
            }
            
            // Current Question
            if (currentQuestionIndex < questions.size) {
                QuestionCard(
                    question = questions[currentQuestionIndex],
                    userAnswer = userAnswers[questions[currentQuestionIndex].id] ?: "",
                    onAnswerChange = { answer ->
                        userAnswers = userAnswers + (questions[currentQuestionIndex].id to answer)
                    }
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Navigation buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Previous button
                    Button(
                        onClick = { 
                            if (currentQuestionIndex > 0) {
                                currentQuestionIndex--
                            }
                        },
                        enabled = currentQuestionIndex > 0,
                        modifier = Modifier.size(48.dp),
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.interactive_primary)
                        )
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, modifier = Modifier.size(20.dp))
                    }
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    // Next button
                    Button(
                        onClick = { 
                            if (currentQuestionIndex < questions.size - 1) {
                                currentQuestionIndex++
                            }
                        },
                        enabled = currentQuestionIndex < questions.size - 1,
                        modifier = Modifier.size(48.dp),
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.interactive_primary)
                        )
                    ) {
                        Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(20.dp))
                    }
                }
            }
            
            // Bottom spacing for bottom bar
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
    
    // Show save result dialog
    saveResult?.let { result ->
        AlertDialog(
            onDismissRequest = { viewModel.clearSaveResult() },
            containerColor = Color.White,
            title = { Text("Results") },
            text = { Text(result) },
            confirmButton = {
                TextButton(onClick = { viewModel.clearSaveResult() }) {
                    Text("OK")
                }
            }
        )
    }
}

@Composable
fun QuestionCard(
    question: QuestionEntity,
    userAnswer: String,
    onAnswerChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        
        // Question text
        Text(
            text = question.question,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        
        Spacer(modifier = Modifier.height(16.dp))
            
        // Answer section based on question type
        when (question.type) {
            QuestionType.MULTIPLE_CHOICE -> {
                MultipleChoiceAnswer(
                    options = question.options ?: emptyList(),
                    selectedAnswer = userAnswer,
                    onAnswerSelected = onAnswerChange
                )
            }
            QuestionType.TRUE_FALSE -> {
                TrueFalseAnswer(
                    selectedAnswer = userAnswer,
                    onAnswerSelected = onAnswerChange
                )
            }
            QuestionType.ESSAY -> {
                EssayAnswer(
                    answer = userAnswer,
                    onAnswerChange = onAnswerChange
                )
            }
        }
    }
}

@Composable
fun QuestionTypeChip(type: QuestionType) {
    val (text, color) = when (type) {
        QuestionType.MULTIPLE_CHOICE -> "Multiple Choice" to colorResource(id = R.color.esg_info)
        QuestionType.TRUE_FALSE -> "True/False" to colorResource(id = R.color.esg_warning)
        QuestionType.ESSAY -> "Essay" to colorResource(id = R.color.esg_success)
    }
    
    Card(
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.1f)
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = color,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun MultipleChoiceAnswer(
    options: List<String>,
    selectedAnswer: String,
    onAnswerSelected: (String) -> Unit
) {
    Column {
        options.forEach { option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedAnswer == option,
                    onClick = { onAnswerSelected(option) }
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = option,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun TrueFalseAnswer(
    selectedAnswer: String,
    onAnswerSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = selectedAnswer == "True",
                onClick = { onAnswerSelected("True") }
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "True",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = selectedAnswer == "False",
                onClick = { onAnswerSelected("False") }
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Sai",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun EssayAnswer(
    answer: String,
    onAnswerChange: (String) -> Unit
) {
    OutlinedTextField(
        value = answer,
        onValueChange = onAnswerChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        placeholder = {
            Text(
                "Enter your answer...",
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = colorResource(id = R.color.interactive_primary),
            unfocusedBorderColor = colorResource(id = R.color.border_card)
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        maxLines = 8
    )
}

// Data classes
data class QuestionEntity(
    val id: String,
    val type: QuestionType,
    val question: String,
    val options: List<String>? = null,
    val correctAnswer: String? = null
)

enum class QuestionType {
    MULTIPLE_CHOICE,
    TRUE_FALSE,
    ESSAY
}
