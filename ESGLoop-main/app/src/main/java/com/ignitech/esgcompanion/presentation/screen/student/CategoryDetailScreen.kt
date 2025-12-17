package com.ignitech.esgcompanion.presentation.screen.student

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ignitech.esgcompanion.R
import com.ignitech.esgcompanion.utils.AppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDetailScreen(
    navController: NavController,
    categoryName: String,
    modifier: Modifier = Modifier
) {
    val colors = AppColors()
    
    // Get category from name
    val category = when (categoryName) {
        "ENVIRONMENTAL" -> CategoryQuizCategory.ENVIRONMENTAL
        "SOCIAL" -> CategoryQuizCategory.SOCIAL
        "GOVERNANCE" -> CategoryQuizCategory.GOVERNANCE
        "SUSTAINABILITY" -> CategoryQuizCategory.SUSTAINABILITY
        else -> CategoryQuizCategory.ENVIRONMENTAL
    }
    
    val quizzes = getQuizzesForCategory(category)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = category.displayName,
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
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Category Info Card
            item {
                CategoryInfoCard(category = category, quizCount = quizzes.size)
            }
            
            // Quiz List
            items(quizzes) { quiz ->
                CategoryQuizCard(
                    quiz = quiz,
                    onClick = {
                        // TODO: Navigate to quiz screen
                        // navController.navigate("quiz/${quiz.id}")
                    }
                )
            }
        }
    }
}

@Composable
fun CategoryInfoCard(
    category: CategoryQuizCategory,
    quizCount: Int
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
            Text(
                text = category.displayName,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.interactive_primary)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = when (category) {
                    CategoryQuizCategory.ENVIRONMENTAL -> "Assess knowledge of environmental protection and sustainable development"
                    CategoryQuizCategory.SOCIAL -> "Assess understanding of social rights and community responsibility"
                    CategoryQuizCategory.GOVERNANCE -> "Assess knowledge of corporate governance and transparency"
                    CategoryQuizCategory.SUSTAINABILITY -> "Comprehensive assessment of sustainable development"
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = "Total tests: $quizCount",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = colorResource(id = R.color.interactive_primary)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryQuizCard(
    quiz: CategoryQuiz,
    onClick: () -> Unit
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
        ),
        onClick = onClick
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
                        text = quiz.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.interactive_primary)
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Text(
                        text = quiz.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                // Category Chip
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = colorResource(id = quiz.category.colorRes).copy(alpha = 0.1f)
                    )
                ) {
                    Text(
                        text = quiz.category.displayName,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = colorResource(id = quiz.category.colorRes),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Quiz Info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CategoryInfoItem(
                        icon = Icons.Default.Quiz,
                        text = "${quiz.questionCount} questions"
                    )
                    CategoryInfoItem(
                        icon = Icons.Default.Schedule,
                        text = "${quiz.timeLimit} min"
                    )
                }
                
                // Best Score or Start Button
                if (quiz.bestScore != null) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = colorResource(id = R.color.esg_success).copy(alpha = 0.1f)
                        )
                    ) {
                        Text(
                            text = "Best Score: ${quiz.bestScore}",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = colorResource(id = R.color.esg_success),
                            fontWeight = FontWeight.Medium
                        )
                    }
                } else {
                    Button(
                        onClick = onClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.interactive_primary)
                        )
                    ) {
                        Text("Start")
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryInfoItem(
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
            modifier = Modifier.size(16.dp),
            tint = colorResource(id = R.color.interactive_primary)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

// Data classes for CategoryDetailScreen
enum class CategoryQuizCategory(
    val displayName: String,
    val icon: String,
    val colorRes: Int
) {
    ENVIRONMENTAL("Environmental", "🌱", R.color.esg_success),
    SOCIAL("Social", "👥", R.color.esg_info),
    GOVERNANCE("Governance", "🏛️", R.color.esg_warning),
    SUSTAINABILITY("Sustainability", "♻️", R.color.esg_success)
}

data class CategoryQuiz(
    val id: String,
    val title: String,
    val description: String,
    val category: CategoryQuizCategory,
    val questionCount: Int,
    val timeLimit: Int, // in minutes
    val bestScore: Int? = null
)

// Mock data functions
private fun getQuizzesForCategory(category: CategoryQuizCategory): List<CategoryQuiz> {
    return when (category) {
        CategoryQuizCategory.ENVIRONMENTAL -> listOf(
            CategoryQuiz(
                id = "env_quiz_001",
                title = "Basic Environmental Principles",
                description = "Test knowledge of environmental protection and sustainable development",
                category = category,
                questionCount = 20,
                timeLimit = 30,
                bestScore = 85
            ),
            CategoryQuiz(
                id = "env_quiz_002",
                title = "Waste Management",
                description = "Understanding waste classification and treatment",
                category = category,
                questionCount = 15,
                timeLimit = 25
            ),
            CategoryQuiz(
                id = "env_quiz_003",
                title = "Renewable Energy",
                description = "Knowledge of clean and sustainable energy sources",
                category = category,
                questionCount = 18,
                timeLimit = 28
            )
        )
        CategoryQuizCategory.SOCIAL -> listOf(
            CategoryQuiz(
                id = "social_quiz_001",
                title = "Quyền lao động",
                description = "Kiến thức về quyền và phúc lợi người lao động",
                category = category,
                questionCount = 18,
                timeLimit = 25,
                bestScore = 92
            ),
            CategoryQuiz(
                id = "social_quiz_002",
                title = "Đa dạng và hòa nhập",
                description = "Hiểu biết về tạo môi trường làm việc đa dạng",
                category = category,
                questionCount = 16,
                timeLimit = 22
            )
        )
        CategoryQuizCategory.GOVERNANCE -> listOf(
            CategoryQuiz(
                id = "gov_quiz_001",
                title = "Quản trị doanh nghiệp",
                description = "Hiểu biết về cơ cấu quản trị và minh bạch",
                category = category,
                questionCount = 22,
                timeLimit = 35,
                bestScore = 78
            ),
            CategoryQuiz(
                id = "gov_quiz_002",
                title = "Đạo đức kinh doanh",
                description = "Kiến thức về chuẩn mực đạo đức trong kinh doanh",
                category = category,
                questionCount = 20,
                timeLimit = 30
            )
        )
        CategoryQuizCategory.SUSTAINABILITY -> listOf(
            CategoryQuiz(
                id = "sustain_quiz_001",
                title = "Phát triển bền vững tổng quan",
                description = "Kiến thức tổng hợp về phát triển bền vững",
                category = category,
                questionCount = 25,
                timeLimit = 40
            ),
            CategoryQuiz(
                id = "sustain_quiz_002",
                title = "Mục tiêu phát triển bền vững",
                description = "Hiểu biết về 17 SDGs của Liên Hợp Quốc",
                category = category,
                questionCount = 30,
                timeLimit = 45
            )
        )
    }
}
