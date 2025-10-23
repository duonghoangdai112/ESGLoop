package com.ignitech.esgcompanion.presentation.screen.instructor

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ignitech.esgcompanion.data.seed.AssignmentSeeder
import com.ignitech.esgcompanion.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignmentGradingScreen(
    assignmentId: String,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val submissions = AssignmentSeeder.getSubmissionsForAssignment(assignmentId)
    val stats = AssignmentSeeder.getGradingStatistics(assignmentId)
    var selectedFilter by remember { mutableStateOf("all") }
    
    val filteredSubmissions = when (selectedFilter) {
        "pending" -> submissions.filter { it.status == AssignmentSeeder.SubmissionStatus.SUBMITTED }
        "graded" -> submissions.filter { it.status == AssignmentSeeder.SubmissionStatus.GRADED }
        "late" -> submissions.filter { it.status == AssignmentSeeder.SubmissionStatus.LATE }
        else -> submissions
    }
    
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Text(
                    text = "Grade Assignment",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        
        // Assignment Info
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "ESG Report Analysis",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "ESG-101",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Due: 15/12/2024",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
        
        // Grading Statistics
        item {
            Text(
                text = "Grading Statistics",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
        
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatCard(
                    title = "Total Submissions",
                    value = stats.totalSubmissions.toString(),
                    color = ESGInfo,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Graded",
                    value = stats.gradedCount.toString(),
                    color = ESGSuccess,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Pending",
                    value = stats.pendingCount.toString(),
                    color = ESGWarning,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Late",
                    value = stats.lateCount.toString(),
                    color = Color(0xFFFF5722),
                    modifier = Modifier.weight(1f)
                )
            }
        }
        
        if (stats.gradedCount > 0) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = ESGSuccess.copy(alpha = 0.1f)
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.TrendingUp,
                            contentDescription = null,
                            tint = ESGSuccess
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Average Score: ${String.format("%.1f", stats.averageGrade)}/10",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = ESGSuccess
                        )
                    }
                }
            }
        }
        
        // Filter Options
        item {
            Text(
                text = "Filters",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
        
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    onClick = { selectedFilter = "all" },
                    label = { Text("All") },
                    selected = selectedFilter == "all"
                )
                FilterChip(
                    onClick = { selectedFilter = "pending" },
                    label = { Text("Pending") },
                    selected = selectedFilter == "pending"
                )
                FilterChip(
                    onClick = { selectedFilter = "graded" },
                    label = { Text("Graded") },
                    selected = selectedFilter == "graded"
                )
                FilterChip(
                    onClick = { selectedFilter = "late" },
                    label = { Text("Late") },
                    selected = selectedFilter == "late"
                )
            }
        }
        
        // Submissions List
        item {
            Text(
                text = "Submissions List (${filteredSubmissions.size})",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
        
        items(filteredSubmissions) { submission ->
            SubmissionCard(
                submission = submission,
                onClick = {
                    navController.navigate("grade_submission/${submission.id}")
                }
            )
        }
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubmissionCard(
    submission: AssignmentSeeder.StudentSubmission,
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
                        text = submission.studentName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Submitted: ${formatDateTime(submission.submittedAt)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    if (submission.attachments.isNotEmpty()) {
                        Text(
                            text = "${submission.attachments.size} attachments",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = getStatusColor(submission.status).copy(alpha = 0.1f)
                    )
                ) {
                    Text(
                        text = getStatusText(submission.status),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = getStatusColor(submission.status)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = submission.content.take(150) + if (submission.content.length > 150) "..." else "",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            if (submission.status == AssignmentSeeder.SubmissionStatus.GRADED) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Grade,
                        contentDescription = null,
                        tint = ESGSuccess,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Score: ${String.format("%.1f", submission.grade ?: 0f)}/10",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = ESGSuccess
                    )
                }
            }
        }
    }
}

private fun getStatusColor(status: AssignmentSeeder.SubmissionStatus): Color {
    return when (status) {
        AssignmentSeeder.SubmissionStatus.SUBMITTED -> ESGWarning
        AssignmentSeeder.SubmissionStatus.GRADED -> ESGSuccess
        AssignmentSeeder.SubmissionStatus.LATE -> Color(0xFFFF5722)
    }
}

private fun getStatusText(status: AssignmentSeeder.SubmissionStatus): String {
    return when (status) {
        AssignmentSeeder.SubmissionStatus.SUBMITTED -> "Pending"
        AssignmentSeeder.SubmissionStatus.GRADED -> "Graded"
        AssignmentSeeder.SubmissionStatus.LATE -> "Late"
    }
}

private fun formatDateTime(timestamp: Long): String {
    val date = java.util.Date(timestamp)
    val formatter = java.text.SimpleDateFormat("dd/MM/yyyy HH:mm", java.util.Locale.getDefault())
    return formatter.format(date)
}
