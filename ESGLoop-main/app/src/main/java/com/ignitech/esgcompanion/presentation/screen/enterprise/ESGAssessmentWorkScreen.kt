package com.ignitech.esgcompanion.presentation.screen.enterprise

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ignitech.esgcompanion.R
import com.ignitech.esgcompanion.data.local.entity.ESGQuestionEntity
import com.ignitech.esgcompanion.domain.entity.ESGPillar
import com.ignitech.esgcompanion.presentation.viewmodel.ESGAssessmentWorkViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ESGAssessmentWorkScreen(
    navController: NavController,
    pillar: ESGPillar,
    modifier: Modifier = Modifier,
    viewModel: ESGAssessmentWorkViewModel = hiltViewModel()
) {
    val questions by viewModel.questions.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val isSaving by viewModel.isSaving.collectAsStateWithLifecycle()
    val saveResult by viewModel.saveResult.collectAsStateWithLifecycle()
    val savedAnswers by viewModel.savedAnswers.collectAsStateWithLifecycle()
    
    // User answers state - initialize with saved answers
    var userAnswers by remember { mutableStateOf(savedAnswers) }
    
    LaunchedEffect(pillar) {
        viewModel.loadQuestionsByPillar(pillar)
    }
    
    // Update userAnswers when savedAnswers change
    LaunchedEffect(savedAnswers) {
        userAnswers = savedAnswers
    }
    
    // Current question index
    var currentQuestionIndex by remember { mutableStateOf(0) }
    
    if (isLoading || questions.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = colorResource(id = R.color.interactive_primary)
            )
        }
        return
    }
    
    val pillarName = getPillarName(pillar)
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text("$pillarName Assessment")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    Text(
                        text = "${questions.size} questions",
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
                            viewModel.saveAssessment(userAnswers.toMap())
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
                            Text("Save Assessment")
                        }
                    }
                    
                    Button(
                        onClick = { /* TODO: Submit assessment */ },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.interactive_primary)
                        )
                    ) {
                        Text("Complete")
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
            
            // Question counter and category
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
                    CategoryChip(category = questions[currentQuestionIndex].category)
                }
            }
            
            // Current Question
            if (currentQuestionIndex < questions.size) {
                ESGQuestionCard(
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
fun ESGQuestionCard(
    question: ESGQuestionEntity,
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
        
        // Description if available
        question.description?.let { description ->
            if (description.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Answer section - ESG questions use multiple choice with scores
        ESGMultipleChoiceAnswer(
            optionsJson = question.optionsJson,
            selectedAnswer = userAnswer,
            onAnswerSelected = onAnswerChange
        )
    }
}

@Composable
fun CategoryChip(category: String) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.esg_info).copy(alpha = 0.1f)
        )
    ) {
        Text(
            text = category,
            style = MaterialTheme.typography.labelSmall,
            color = colorResource(id = R.color.esg_info),
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun ESGMultipleChoiceAnswer(
    optionsJson: String,
    selectedAnswer: String,
    onAnswerSelected: (String) -> Unit
) {
    val gson = Gson()
    val type = object : TypeToken<List<Map<String, Any>>>() {}.type
    val options = try {
        gson.fromJson<List<Map<String, Any>>>(optionsJson, type) ?: emptyList()
    } catch (e: Exception) {
        emptyList()
    }
    
    Column {
        options.forEach { option ->
            val text = option["text"] as? String ?: ""
            val score = option["score"] as? Double ?: 0.0
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedAnswer == text,
                    onClick = { onAnswerSelected(text) }
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "${score.toInt()} points",
                        style = MaterialTheme.typography.bodySmall,
                        color = colorResource(id = R.color.interactive_primary)
                    )
                }
            }
        }
    }
}

private fun getPillarName(pillar: ESGPillar): String {
    return when (pillar) {
        ESGPillar.ENVIRONMENTAL -> "Environmental"
        ESGPillar.SOCIAL -> "Social"
        ESGPillar.GOVERNANCE -> "Governance"
    }
}
