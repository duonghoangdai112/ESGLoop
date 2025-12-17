package com.ignitech.esgcompanion.presentation.screen.instructor

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ignitech.esgcompanion.R
import com.ignitech.esgcompanion.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstructorContentScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Header
        TopAppBar(
            title = { 
                Text(
                    text = "Create Learning Content",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            },
            actions = {
                IconButton(onClick = { /* Add new content */ }) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Add Content",
                        tint = colorResource(id = R.color.interactive_primary)
                    )
                }
            }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
        
        // Quick Actions
        item {
            Text(
                text = "Quick Actions",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ContentActionCard(
                    title = "Create Lesson",
                    description = "Create a new lesson",
                    onClick = { navController.navigate("create_lesson") },
                    modifier = Modifier.weight(1f)
                )
                ContentActionCard(
                    title = "Create Assignment",
                    description = "Create assignments for students",
                    onClick = { navController.navigate("create_assignment") },
                    modifier = Modifier.weight(1f)
                )
            }
        }
        
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ContentActionCard(
                    title = "Create Question",
                    description = "Add test questions",
                    onClick = { navController.navigate("create_question") },
                    modifier = Modifier.weight(1f)
                )
                ContentActionCard(
                    title = "Upload Document",
                    description = "Upload learning materials",
                    onClick = { /* Upload document */ },
                    modifier = Modifier.weight(1f)
                )
            }
        }
        
        // Recent Content
        item {
            Text(
                text = "Recent Content",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        
        items(getRecentContentData()) { content ->
            ContentCard(content = content)
        }
        
        // Content Categories
        item {
            Text(
                text = "Content Categories",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(getContentCategoriesData()) { category ->
                    CategoryCard(category = category)
                }
            }
        }
        
        // Templates
        item {
            Text(
                text = "Content Templates",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        
        items(getContentTemplatesData()) { template ->
            TemplateCard(template = template)
        }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentActionCard(
    title: String,
    description: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() },
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.interactive_primary)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.9f),
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
fun ContentCard(
    content: ContentItem
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = colorResource(id = R.color.border_card)
        )
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = content.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = content.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Surface(
                        color = content.color.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.border(
                            width = 1.dp,
                            color = content.color.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(8.dp)
                        )
                    ) {
                        Text(
                            text = content.className,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            color = content.color,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Text(
                        text = content.date,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryCard(
    category: ContentCategory
) {
    Card(
        modifier = Modifier.width(140.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = category.color.copy(alpha = 0.3f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = category.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = category.color
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${category.count} items",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun TemplateCard(
    template: ContentTemplate
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = colorResource(id = R.color.border_card)
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = template.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = template.description,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 20.sp
                    )
                }
                Surface(
                    color = template.color.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.border(
                        width = 1.dp,
                        color = template.color.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(8.dp)
                    )
                ) {
                    Text(
                        text = template.difficulty,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = template.color,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Duration: ${template.duration}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Medium
                )
                Button(
                    onClick = { /* Use template */ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = template.color
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Use",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

// Data classes
data class ContentItem(
    val title: String,
    val description: String,
    val className: String,
    val date: String,
    val color: Color
)

data class ContentCategory(
    val name: String,
    val count: Int,
    val color: Color
)

data class ContentTemplate(
    val name: String,
    val description: String,
    val difficulty: String,
    val duration: String,
    val color: Color
)

// Mock data functions
private fun getRecentContentData(): List<ContentItem> {
    return listOf(
        ContentItem(
            title = "Lesson: Basic ESG Principles",
            description = "Introduction to ESG principles in business",
            className = "ESG-101",
            date = "2 days ago",
            color = Color(0xFF2E7D32) // Primary green color (interactive_primary)
        ),
        ContentItem(
            title = "Assignment: Sustainability Report Analysis",
            description = "Students analyze ESG reports from companies",
            className = "ESG-102",
            date = "3 days ago",
            color = Color(0xFF2E7D32) // Primary green color (interactive_primary)
        ),
        ContentItem(
            title = "Questions: Corporate Governance",
            description = "Multiple choice questions about governance",
            className = "ESG-201",
            date = "1 week ago",
            color = Color(0xFF2E7D32) // Primary green color (interactive_primary)
        )
    )
}

private fun getContentCategoriesData(): List<ContentCategory> {
    return listOf(
        ContentCategory(
            name = "Lessons",
            count = 12,
            color = Color(0xFF2E7D32) // Primary green color (interactive_primary)
        ),
        ContentCategory(
            name = "Assignments",
            count = 8,
            color = Color(0xFF2E7D32) // Primary green color (interactive_primary)
        ),
        ContentCategory(
            name = "Questions",
            count = 25,
            color = Color(0xFF2E7D32) // Primary green color (interactive_primary)
        ),
        ContentCategory(
            name = "Documents",
            count = 15,
            color = Color(0xFF2E7D32) // Primary green color (interactive_primary)
        )
    )
}

private fun getContentTemplatesData(): List<ContentTemplate> {
    return listOf(
        ContentTemplate(
            name = "Basic ESG Lesson Template",
            description = "Standard template for ESG lesson with clear structure",
            difficulty = "Basic",
            duration = "45 minutes",
            color = Color(0xFF2E7D32) // Primary green color (interactive_primary)
        ),
        ContentTemplate(
            name = "Group Assignment Template",
            description = "Template for group assignments on ESG analysis",
            difficulty = "Intermediate",
            duration = "2 weeks",
            color = Color(0xFF2E7D32) // Primary green color (interactive_primary)
        ),
        ContentTemplate(
            name = "Multiple Choice Questions Template",
            description = "Sample questions for ESG knowledge test",
            difficulty = "Basic",
            duration = "30 minutes",
            color = Color(0xFF2E7D32) // Primary green color (interactive_primary)
        )
    )
}
