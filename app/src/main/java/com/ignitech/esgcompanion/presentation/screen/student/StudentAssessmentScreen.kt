package com.ignitech.esgcompanion.presentation.screen.student

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ignitech.esgcompanion.R
import com.ignitech.esgcompanion.domain.entity.*
import com.ignitech.esgcompanion.ui.theme.*
import com.ignitech.esgcompanion.utils.AppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentAssessmentScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var selectedCategory by remember { mutableStateOf<QuizCategory?>(null) }
    var showCategoryDialog by remember { mutableStateOf(false) }
    
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "ESG Knowledge Test",
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
                    IconButton(onClick = { showCategoryDialog = true }) {
                        Icon(Icons.Default.FilterList, contentDescription = "Select Category")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Welcome Card
            item {
                Column {
                    Text(
                        text = "ESG Knowledge Test",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Challenge yourself with ESG and sustainable development tests",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            
            // Quiz List
            if (selectedCategory != null) {
                item {
                    Text(
                        text = "${selectedCategory?.displayName} Tests",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                items(getQuizzesForCategory(selectedCategory!!)) { quiz ->
                    QuizCard(
                        quiz = quiz,
                        onClick = { 
                            // TODO: Navigate to quiz screen
                            // navController.navigate("quiz/${quiz.id}")
                        }
                    )
                }
            } else {
                // Show all quizzes grouped by category
                QuizCategory.values().forEach { category ->
                    item {
                        CategorySection(
                            category = category,
                            quizzes = getQuizzesForCategory(category),
                            navController = navController
                        )
                    }
                }
            }
            
            // Recent Attempts
            item {
                Text(
                    text = "Recent Attempts",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            
            items(getRecentAttempts()) { attempt ->
                AttemptCard(
                    attempt = attempt,
                    onRetake = { 
                        // TODO: Navigate to quiz screen
                        // navController.navigate("quiz/${attempt.id}")
                    }
                )
            }
            
            // Achievement Card
            item {
                AchievementCard()
            }
        }
    }
    
           // Category Selection Dialog
           if (showCategoryDialog) {
               CategoryFilterDialog(
                   selectedCategory = selectedCategory,
                   onCategorySelected = { category ->
                       selectedCategory = category
                       showCategoryDialog = false
                   },
                   onDismiss = { showCategoryDialog = false }
               )
           }
}

@Composable
fun CategoryFilterDialog(
    selectedCategory: QuizCategory?,
    onCategorySelected: (QuizCategory?) -> Unit,
    onDismiss: () -> Unit
) {
    val colors = AppColors()
    
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        title = {
            Text(
                text = "Select Category",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = colors.textPrimary
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Category Filter
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Category",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium,
                        color = colors.textPrimary
                    )
                    
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(listOf(null) + QuizCategory.values().toList()) { category ->
                            CategoryChip(
                                category = category,
                                isSelected = category == selectedCategory,
                                onClick = { onCategorySelected(category) }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    "Apply",
                    color = colorResource(id = R.color.interactive_primary)
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    "Cancel",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    )
}

@Composable
fun CategoryChip(
    category: QuizCategory?,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val colors = AppColors()
    
    Surface(
        modifier = Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberRipple()
        ) { onClick() },
        color = if (isSelected) colors.primary else Color.Transparent,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = 1.dp,
            color = if (isSelected) colors.primary else colors.borderLight
        )
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if (category != null) {
                Text(
                    text = category.icon,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Text(
                text = when (category) {
                    QuizCategory.ENVIRONMENTAL -> "Environmental"
                    QuizCategory.SOCIAL -> "Social"
                    QuizCategory.GOVERNANCE -> "Governance"
                    QuizCategory.SUSTAINABILITY -> "Sustainability"
                    null -> "All"
                },
                style = MaterialTheme.typography.bodySmall,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) Color.White else colors.textPrimary
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizCard(
    quiz: Quiz,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = quiz.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = quiz.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = quiz.category.color.copy(alpha = 0.1f)
                    )
                ) {
                    Text(
                        text = quiz.category.displayName,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = quiz.category.color
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    InfoItem(
                        icon = Icons.Default.Quiz,
                        text = "${quiz.questionCount} questions"
                    )
                    InfoItem(
                        icon = Icons.Default.Schedule,
                        text = "${quiz.timeLimit} min"
                    )
                }
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (quiz.bestScore != null) {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = ESGSuccess.copy(alpha = 0.1f)
                            )
                        ) {
                            Text(
                                text = "Best Score: ${quiz.bestScore}",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelSmall,
                                color = ESGSuccess
                            )
                        }
                    }
                    
                    Button(onClick = onClick) {
                        Text("Start")
                    }
                }
            }
        }
    }
}

@Composable
fun InfoItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun CategorySection(
    category: QuizCategory,
    quizzes: List<Quiz>,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = colorResource(id = R.color.border_card),
                shape = MaterialTheme.shapes.medium
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = category.displayName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.interactive_primary)
                )
                
                Text(
                    text = "${quizzes.size} tests",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.interactive_primary)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Description
            Text(
                text = when (category) {
                    QuizCategory.ENVIRONMENTAL -> "Assess knowledge of environmental protection and sustainable development"
                    QuizCategory.SOCIAL -> "Assess understanding of social rights and community responsibility"
                    QuizCategory.GOVERNANCE -> "Assess knowledge of corporate governance and transparency"
                    QuizCategory.SUSTAINABILITY -> "Comprehensive assessment of sustainable development"
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // View Details Button
            Button(
                onClick = { 
                    navController.navigate("category_detail/${category.name}")
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.interactive_primary)
                )
            ) {
                Text("View Details")
            }
        }
    }
}

@Composable
fun AttemptCard(
    attempt: QuizAttempt,
    onRetake: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = colorResource(id = R.color.border_card),
                shape = MaterialTheme.shapes.medium
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = attempt.quizTitle,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.interactive_primary)
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Text(
                        text = "${attempt.correctAnswers}/${attempt.totalQuestions} correct",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                // Status Chip
                AttemptStatusChip(
                    passed = attempt.passed,
                    score = attempt.score
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Action Button
            Button(
                onClick = onRetake,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.esg_warning),
                    contentColor = Color.White
                )
            ) {
                Text("Retry")
            }
        }
    }
}

@Composable
fun AttemptStatusChip(
    passed: Boolean,
    score: Int
) {
    val (text, color) = if (passed) {
        "Pass ($score%)" to colorResource(id = R.color.esg_success)
    } else {
        "Fail ($score%)" to colorResource(id = R.color.esg_error)
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
fun AchievementCard() {
    Column {
        Text(
            text = "Learning Achievements",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AchievementItemCard(
                title = "Completed Lessons",
                value = "12",
                modifier = Modifier.weight(1f)
            )
            AchievementItemCard(
                title = "Average Score",
                value = "85%",
                modifier = Modifier.weight(1f)
            )
            AchievementItemCard(
                title = "Certificates",
                value = "3",
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun AchievementItemCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .border(
                width = 1.dp,
                color = colorResource(id = R.color.border_card),
                shape = MaterialTheme.shapes.medium
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.interactive_primary),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

// Data classes
enum class QuizCategory(
    val displayName: String,
    val icon: String,
    val color: Color
) {
    ENVIRONMENTAL("Environmental", "🌱", ESGSuccess),
    SOCIAL("Social", "👥", ESGInfo),
    GOVERNANCE("Governance", "🏛️", ESGWarning),
    SUSTAINABILITY("Sustainability", "♻️", ESGSuccess)
}

data class Quiz(
    val id: String,
    val title: String,
    val description: String,
    val category: QuizCategory,
    val questionCount: Int,
    val timeLimit: Int, // in minutes
    val bestScore: Int? = null
)

// Mock data functions
private fun getQuizzesForCategory(category: QuizCategory): List<Quiz> {
    return when (category) {
        QuizCategory.ENVIRONMENTAL -> listOf(
            Quiz(
                id = "env_quiz_001",
                title = "Basic Environmental Principles",
                description = "Test your knowledge about environmental protection and sustainable development",
                category = category,
                questionCount = 20,
                timeLimit = 30,
                bestScore = 85
            ),
            Quiz(
                id = "env_quiz_002",
                title = "Waste Management",
                description = "Understanding waste classification and treatment",
                category = category,
                questionCount = 15,
                timeLimit = 25
            )
        )
        QuizCategory.SOCIAL -> listOf(
            Quiz(
                id = "social_quiz_001",
                title = "Labor Rights",
                description = "Knowledge about workers' rights and benefits",
                category = category,
                questionCount = 18,
                timeLimit = 25,
                bestScore = 92
            )
        )
        QuizCategory.GOVERNANCE -> listOf(
            Quiz(
                id = "gov_quiz_001",
                title = "Corporate Governance",
                description = "Understanding governance structure and transparency",
                category = category,
                questionCount = 22,
                timeLimit = 35,
                bestScore = 78
            )
        )
        QuizCategory.SUSTAINABILITY -> listOf(
            Quiz(
                id = "sustain_quiz_001",
                title = "Sustainable Development Overview",
                description = "Comprehensive knowledge about sustainable development",
                category = category,
                questionCount = 25,
                timeLimit = 40
            )
        )
    }
}

private fun getRecentAttempts(): List<QuizAttempt> {
    return listOf(
        QuizAttempt(
            id = "attempt_001",
            userId = "academic_001",
            quizTitle = "Basic Environmental Principles",
            totalQuestions = 20,
            correctAnswers = 17,
            score = 85,
            passed = true,
            completedAt = System.currentTimeMillis() - 86400000 * 2,
            answers = emptyList()
        ),
        QuizAttempt(
            id = "attempt_002",
            userId = "academic_001",
            quizTitle = "Corporate Governance",
            totalQuestions = 22,
            correctAnswers = 15,
            score = 68,
            passed = false,
            completedAt = System.currentTimeMillis() - 86400000 * 5,
            answers = emptyList()
        )
    )
}
