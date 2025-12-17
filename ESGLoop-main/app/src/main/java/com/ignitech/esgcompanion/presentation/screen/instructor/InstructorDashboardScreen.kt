package com.ignitech.esgcompanion.presentation.screen.instructor

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ignitech.esgcompanion.R
import com.ignitech.esgcompanion.domain.entity.ESGPillar
import com.ignitech.esgcompanion.domain.entity.ESGPillarInfo
import com.ignitech.esgcompanion.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstructorDashboardScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Header
        TopAppBar(
            title = { 
                Column {
                    Text(
                        text = "ESG Companion",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Instructor",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            actions = {
                IconButton(onClick = { navController.navigate("settings") }) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(
                                color = colorResource(id = R.color.interactive_primary),
                                shape = androidx.compose.foundation.shape.CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "GV",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        )
        
        // Content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Class Overview
            item {
                Column {
                    Text(
                        text = "Class Overview",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        ClassStatCard(
                            title = "Total Students",
                            value = "45",
                            color = colorResource(id = R.color.interactive_primary),
                            modifier = Modifier.weight(1f)
                        )
                        ClassStatCard(
                            title = "Ungraded Assignments",
                            value = "12",
                            color = colorResource(id = R.color.esg_warning),
                            modifier = Modifier.weight(1f)
                        )
                        ClassStatCard(
                            title = "Average Score",
                            value = "85%",
                            color = colorResource(id = R.color.esg_success),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
            
            // Recent Activities
            item {
                Text(
                    text = "Recent Activities",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            
            items(getRecentActivities()) { activity ->
                ActivityCard(activity = activity)
            }
            
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
                    InstructorQuickActionCard(
                        title = "Create Assignment",
                        icon = Icons.Default.Create,
                        onClick = { /* Create assignment */ },
                        modifier = Modifier.weight(1f)
                    )
                    InstructorQuickActionCard(
                        title = "Grade Assignments",
                        icon = Icons.Default.Grade,
                        onClick = { /* Grade assignments */ },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            
            // Student Performance by ESG Pillar
            item {
                Text(
                    text = "Achievement by ESG Category",
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
                    ESGPillarCard(
                        pillarInfo = ESGPillarInfo.ALL_PILLARS[0],
                        averageScore = 88,
                        modifier = Modifier.weight(1f)
                    )
                    ESGPillarCard(
                        pillarInfo = ESGPillarInfo.ALL_PILLARS[1],
                        averageScore = 82,
                        modifier = Modifier.weight(1f)
                    )
                    ESGPillarCard(
                        pillarInfo = ESGPillarInfo.ALL_PILLARS[2],
                        averageScore = 85,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            
            // Upcoming Deadlines
            item {
                Text(
                    text = "Upcoming Deadlines",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            
            items(getUpcomingDeadlines()) { deadline ->
                DeadlineCard(deadline = deadline)
            }
        }
    }
}

@Composable
fun ClassStatCard(
    title: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = colorResource(id = R.color.border_card)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun ActivityCard(
    activity: Activity
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = colorResource(id = R.color.border_card)
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = activity.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = activity.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = activity.time,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun ESGPillarCard(
    pillarInfo: ESGPillarInfo,
    averageScore: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = colorResource(id = R.color.border_card)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = pillarInfo.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Avg Score: $averageScore%",
                style = MaterialTheme.typography.bodyLarge,
                color = when {
                    averageScore >= 85 -> colorResource(id = R.color.esg_success)
                    averageScore >= 70 -> colorResource(id = R.color.esg_warning)
                    else -> Color(0xFFFF5722)
                },
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun DeadlineCard(
    deadline: Deadline
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = if (deadline.isUrgent) Color(0xFFFF5722).copy(alpha = 0.5f) 
                   else colorResource(id = R.color.border_card)
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = deadline.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = deadline.className,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = deadline.dueDate,
                style = MaterialTheme.typography.bodySmall,
                color = if (deadline.isUrgent) Color(0xFFFF5722) else colorResource(id = R.color.interactive_primary),
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstructorQuickActionCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = colorResource(id = R.color.border_card)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

// Data classes
data class Activity(
    val title: String,
    val description: String,
    val time: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val color: Color
)

data class Deadline(
    val title: String,
    val className: String,
    val dueDate: String,
    val isUrgent: Boolean
)

// Mock data functions
private fun getRecentActivities(): List<Activity> {
    return listOf(
        Activity(
            title = "Student Nguyen Van A submitted assignment",
            description = "Assignment: ESG Report Analysis",
            time = "2 hours ago",
            icon = Icons.Default.Assignment,
            color = Color(0xFF2196F3)
        ),
        Activity(
            title = "ESG-101 class completed the test",
            description = "Average score: 85%",
            time = "4 hours ago",
            icon = Icons.Default.Quiz,
            color = Color(0xFF4CAF50)
        ),
        Activity(
            title = "Created new assignment",
            description = "Sustainable strategy design",
            time = "1 day ago",
            icon = Icons.Default.Create,
            color = Color(0xFFFF9800)
        )
    )
}

private fun getUpcomingDeadlines(): List<Deadline> {
    return listOf(
        Deadline(
            title = "Submit ESG Report",
            className = "ESG-101",
            dueDate = "Today",
            isUrgent = true
        ),
        Deadline(
            title = "Group Assignment",
            className = "ESG-102",
            dueDate = "In 2 days",
            isUrgent = false
        ),
        Deadline(
            title = "Project Presentation",
            className = "ESG-201",
            dueDate = "In 1 week",
            isUrgent = false
        )
    )
}
