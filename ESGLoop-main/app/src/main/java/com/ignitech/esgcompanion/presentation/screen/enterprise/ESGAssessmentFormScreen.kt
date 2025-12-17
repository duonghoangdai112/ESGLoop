package com.ignitech.esgcompanion.presentation.screen.enterprise

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ignitech.esgcompanion.data.entity.AssessmentAnswer
import com.ignitech.esgcompanion.data.local.entity.ESGQuestionEntity
import com.ignitech.esgcompanion.domain.entity.ESGPillar
import com.ignitech.esgcompanion.domain.entity.UserRole
import com.ignitech.esgcompanion.presentation.viewmodel.ESGAssessmentViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ESGAssessmentFormScreen(
    navController: NavController,
    pillar: ESGPillar? = null,
    assessmentId: String? = null,
    viewModel: ESGAssessmentViewModel = hiltViewModel()
) {
    var selectedTab by remember { mutableStateOf(0) }
    var showResultDialog by remember { mutableStateOf(false) }
    
    val tabs = listOf(
        TabData("Environmental", "🌱", ESGPillar.ENVIRONMENTAL),
        TabData("Social", "👥", ESGPillar.SOCIAL),
        TabData("Governance", "🏛️", ESGPillar.GOVERNANCE)
    )
    
    val uiState by viewModel.uiState.collectAsState()
    
    // Determine if we're in single pillar mode (pillar is pre-selected)
    val isSinglePillarMode = pillar != null
    
    // Nếu có pillar được chọn, set tab tương ứng và load questions
    LaunchedEffect(pillar) {
        pillar?.let {
            selectedTab = tabs.indexOfFirst { tab -> tab.pillar == it }
            viewModel.loadQuestions(it, UserRole.ENTERPRISE)
        }
    }
    
    // Load assessment if provided
    LaunchedEffect(assessmentId) {
        assessmentId?.let {
            viewModel.loadAnswers(it)
            viewModel.loadAssessmentProgress(it)
        }
    }
    
    // Load questions for current tab (only in multi-pillar mode)
    LaunchedEffect(selectedTab) {
        if (!isSinglePillarMode) {
            viewModel.loadQuestions(tabs[selectedTab].pillar, UserRole.ENTERPRISE)
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column(
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        Text(
                            text = if (isSinglePillarMode) {
                                "${pillar?.let { getPillarName(it) }} Assessment"
                            } else {
                                "ESG Assessment"
                            },
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = if (isSinglePillarMode) {
                                "Assess your enterprise's ${pillar?.let { getPillarName(it) }} readiness"
                            } else {
                                "Assess your enterprise's ESG readiness"
                            },
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 20.sp
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { 
                        navController.navigate("enterprise_home") {
                            popUpTo("enterprise_home") { inclusive = true }
                            launchSingleTop = true
                        }
                    }) {
                        Icon(
                            Icons.Default.ArrowBack, 
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                actions = {
                    // Progress indicator in top bar
                    if (uiState.questions.isNotEmpty()) {
                        val progress = uiState.answers.size.toFloat() / uiState.questions.size
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .size(40.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                progress = progress,
                                modifier = Modifier.size(32.dp),
                                color = MaterialTheme.colorScheme.primary,
                                strokeWidth = 3.dp
                            )
                            Text(
                                text = "${(progress * 100).toInt()}%",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        bottomBar = {
            AssessmentBottomBar(
                onSaveDraft = { /* Save draft logic */ },
                onSubmitAssessment = { showResultDialog = true },
                canSubmit = uiState.answers.size >= uiState.questions.size,
                progress = if (uiState.questions.isNotEmpty()) uiState.answers.size.toFloat() / uiState.questions.size else 0f
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Progress Bar
            ProgressSection(
                currentTab = if (isSinglePillarMode) 0 else selectedTab,
                totalTabs = if (isSinglePillarMode) 1 else tabs.size,
                currentQuestion = 0,
                totalQuestions = uiState.questions.size,
                progress = uiState.assessmentProgress,
                isSinglePillarMode = isSinglePillarMode
            )
            
            // Tabs Navigation (only show in multi-pillar mode)
            if (!isSinglePillarMode) {
                TabRow(
                    selectedTabIndex = selectedTab,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    tabs.forEachIndexed { index, tab ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { 
                                selectedTab = index
                                // Reset to first question
                            },
                            text = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(tab.icon, fontSize = 18.sp)
                                    Text(
                                        text = tab.name,
                                        fontSize = 14.sp,
                                        fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Medium
                                    )
                                }
                            }
                        )
                    }
                }
            }
            
            // Questions Content
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Loading questions...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else if (uiState.questions.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            Icons.Default.Quiz,
                            contentDescription = "Không có câu hỏi",
                            modifier = Modifier.size(48.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "Không có câu hỏi nào",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "Vui lòng thử lại sau",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.questions) { question ->
                        val existingAnswer = uiState.answers.find { it.questionId == question.id }
                        QuestionCard(
                            question = question,
                            answer = existingAnswer?.answer,
                            onAnswerSelected = { answer ->
                                assessmentId?.let { id ->
                                    viewModel.saveAnswer(id, question.id, answer)
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
    
    // Result Dialog
    if (showResultDialog) {
        AssessmentResultDialog(
            onDismiss = { showResultDialog = false },
            onSubmit = { 
                // Submit assessment logic
                navController.popBackStack()
            },
            answers = uiState.answers.associate { it.questionId to it.answer },
            score = uiState.assessmentScore
        )
    }
}

@Composable
fun ProgressSection(
    currentTab: Int,
    totalTabs: Int,
    currentQuestion: Int,
    totalQuestions: Int,
    progress: Float,
    isSinglePillarMode: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Tiến độ đánh giá",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                    Text(
                        text = if (isSinglePillarMode) {
                            "Câu hỏi ${currentQuestion + 1}/${totalQuestions}"
                        } else {
                            "Câu hỏi ${currentQuestion + 1}/${totalQuestions} - Trụ cột ${currentTab + 1}/${totalTabs}"
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
            }
            Text(
                text = "${(progress * 100).toInt()}%",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
        )
    }
}

@Composable
fun QuestionCard(
    question: ESGQuestionEntity,
    answer: AssessmentAnswer?,
    onAnswerSelected: (AssessmentAnswer) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Question Header with Category and Weight
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Category Badge
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = question.category,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
                
                // Weight Badge
                if (question.weight > 1) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        ),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "x${question.weight}",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Question Title
            Text(
                text = question.question,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 22.sp,
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Question Description
            if (!question.description.isNullOrEmpty()) {
                Text(
                    text = question.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 20.sp,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
            
            // Answer Options
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                AssessmentAnswer.values().forEach { option ->
                    AnswerOption(
                        option = option,
                        isSelected = answer == option,
                        onClick = { onAnswerSelected(option) }
                    )
                }
            }
            
            // Required indicator
            if (question.isRequired) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = "Bắt buộc",
                        modifier = Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.error
                    )
                    Text(
                        text = "Câu hỏi bắt buộc",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun AnswerOption(
    option: AssessmentAnswer,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val (text, description) = when (option) {
        AssessmentAnswer.FULLY_IMPLEMENTED -> Pair(
            "Có đầy đủ",
            "Tổ chức đã triển khai đầy đủ các biện pháp"
        )
        AssessmentAnswer.IN_PROGRESS -> Pair(
            "Đang triển khai",
            "Tổ chức đang trong quá trình triển khai"
        )
        AssessmentAnswer.NOT_IMPLEMENTED -> Pair(
            "Chưa có",
            "Tổ chức chưa triển khai biện pháp này"
        )
        AssessmentAnswer.NOT_APPLICABLE -> Pair(
            "Không áp dụng",
            "Biện pháp này không phù hợp với tổ chức"
        )
    }
    
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true)
            ) { onClick() },
        color = if (isSelected) 
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f)
        else 
            Color.Transparent,
        shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Radio Button
            RadioButton(
                selected = isSelected,
                onClick = onClick,
                colors = RadioButtonDefaults.colors(
                    selectedColor = MaterialTheme.colorScheme.primary,
                    unselectedColor = MaterialTheme.colorScheme.outline
                ),
                modifier = Modifier.size(20.dp)
            )
            
            // Text Content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                    color = if (isSelected) 
                        MaterialTheme.colorScheme.primary 
                    else 
                        MaterialTheme.colorScheme.onSurface
                )
                
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isSelected) 
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                    else 
                        MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 16.sp
                )
            }
        }
    }
}

@Composable
fun AssessmentBottomBar(
    onSaveDraft: () -> Unit,
    onSubmitAssessment: () -> Unit,
    canSubmit: Boolean,
    progress: Float
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = onSaveDraft,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF9800),
                    contentColor = Color.White
                )
            ) {
                Icon(Icons.Default.Save, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Lưu nháp")
            }
            
            Button(
                onClick = onSubmitAssessment,
                enabled = canSubmit,
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Hoàn thành")
            }
        }
    }
}

@Composable
fun AssessmentResultDialog(
    onDismiss: () -> Unit,
    onSubmit: () -> Unit,
    answers: Map<String, AssessmentAnswer>,
    score: Pair<Int, Int> = Pair(0, 100)
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        title = {
            Text(
                text = "Xác nhận hoàn thành đánh giá",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                Text(
                    text = "Bạn đã chắc chắn muốn nộp kết quả này chưa?",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Điểm ESG dự kiến: ${score.first}/${score.second}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        confirmButton = {
            Button(onClick = onSubmit) {
                Text("Xác nhận")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Hủy")
            }
        }
    )
}

// Helper Functions
private fun getPillarName(pillar: ESGPillar): String {
    return when (pillar) {
        ESGPillar.ENVIRONMENTAL -> "Môi trường"
        ESGPillar.SOCIAL -> "Xã hội"
        ESGPillar.GOVERNANCE -> "Quản trị"
    }
}

// Data Classes
data class TabData(
    val name: String,
    val icon: String,
    val pillar: ESGPillar
)
