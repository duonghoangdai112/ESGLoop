package com.ignitech.esgcompanion.presentation.screen.instructor

import androidx.compose.foundation.background
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
fun AssignmentGradesScreen(
    assignmentId: String,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val submissions = AssignmentSeeder.getSubmissionsForAssignment(assignmentId)
    val stats = AssignmentSeeder.getGradingStatistics(assignmentId)
    var selectedFilter by remember { mutableStateOf("all") }
    
    val filteredSubmissions = when (selectedFilter) {
        "graded" -> submissions.filter { it.status == AssignmentSeeder.SubmissionStatus.GRADED }
        "ungraded" -> submissions.filter { it.status == AssignmentSeeder.SubmissionStatus.SUBMITTED }
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
                    text = "View Grades",
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
        
        // Grade Statistics
        item {
            Text(
                text = "Grade Statistics",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
        
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                GradeStatCard(
                    title = "Average",
                    value = String.format("%.1f", stats.averageGrade),
                    color = ESGSuccess,
                    modifier = Modifier.weight(1f)
                )
                GradeStatCard(
                    title = "Highest",
                    value = String.format("%.1f", getHighestGrade(submissions)),
                    color = ESGInfo,
                    modifier = Modifier.weight(1f)
                )
                GradeStatCard(
                    title = "Lowest",
                    value = String.format("%.1f", getLowestGrade(submissions)),
                    color = ESGWarning,
                    modifier = Modifier.weight(1f)
                )
                GradeStatCard(
                    title = "Graded",
                    value = "${stats.gradedCount}/${stats.totalSubmissions}",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )
            }
        }
        
        // Grade Distribution
        if (stats.gradedCount > 0) {
            item {
                Text(
                    text = "Grade Distribution",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            
            item {
                GradeDistributionChart(
                    submissions = submissions.filter { it.status == AssignmentSeeder.SubmissionStatus.GRADED }
                )
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
                    onClick = { selectedFilter = "graded" },
                    label = { Text("Graded") },
                    selected = selectedFilter == "graded"
                )
                FilterChip(
                    onClick = { selectedFilter = "ungraded" },
                    label = { Text("Pending") },
                    selected = selectedFilter == "ungraded"
                )
                FilterChip(
                    onClick = { selectedFilter = "late" },
                    label = { Text("Late") },
                    selected = selectedFilter == "late"
                )
            }
        }
        
        // Grades List
        item {
            Text(
                text = "Grade List (${filteredSubmissions.size})",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
        
        items(filteredSubmissions) { submission ->
            GradeCard(
                submission = submission,
                onClick = {
                    if (submission.status == AssignmentSeeder.SubmissionStatus.SUBMITTED) {
                        navController.navigate("grade_submission/${submission.id}")
                    }
                }
            )
        }
    }
}

@Composable
fun GradeStatCard(
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

@Composable
fun GradeDistributionChart(
    submissions: List<AssignmentSeeder.StudentSubmission>
) {
    val gradeRanges = listOf(
        "9-10" to submissions.count { (it.grade ?: 0f) in 9f..10f },
        "8-9" to submissions.count { (it.grade ?: 0f) in 8f..9f },
        "7-8" to submissions.count { (it.grade ?: 0f) in 7f..8f },
        "6-7" to submissions.count { (it.grade ?: 0f) in 6f..7f },
        "0-6" to submissions.count { (it.grade ?: 0f) in 0f..6f }
    )
    
    val maxCount = gradeRanges.maxOfOrNull { it.second } ?: 1
    
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            gradeRanges.forEach { (range, count) ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = range,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.width(40.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(20.dp)
                    ) {
                        val progress = if (maxCount > 0) count.toFloat() / maxCount else 0f
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(progress)
                                .background(
                                    color = when (range) {
                                        "9-10" -> ESGSuccess
                                        "8-9" -> ESGInfo
                                        "7-8" -> ESGWarning
                                        "6-7" -> Color(0xFFFF9800)
                                        else -> Color(0xFFFF5722)
                                    }
                                )
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = count.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GradeCard(
    submission: AssignmentSeeder.StudentSubmission,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = if (submission.status == AssignmentSeeder.SubmissionStatus.SUBMITTED) onClick else { {} }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Student Avatar
            Card(
                modifier = Modifier.size(48.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = submission.studentName.split(" ").lastOrNull()?.firstOrNull()?.toString() ?: "?",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = submission.studentName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "Submitted: ${formatDateTime(submission.submittedAt)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (submission.status == AssignmentSeeder.SubmissionStatus.LATE) {
                    Text(
                        text = "Late",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFFF5722),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            Column(
                horizontalAlignment = Alignment.End
            ) {
                when (submission.status) {
                    AssignmentSeeder.SubmissionStatus.GRADED -> {
                        Text(
                            text = "${String.format("%.1f", submission.grade ?: 0f)}/10",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = getGradeColor(submission.grade ?: 0f)
                        )
                        Text(
                            text = "Graded",
                            style = MaterialTheme.typography.bodySmall,
                            color = ESGSuccess
                        )
                    }
                    AssignmentSeeder.SubmissionStatus.SUBMITTED -> {
                        Text(
                            text = "Pending",
                            style = MaterialTheme.typography.bodyMedium,
                            color = ESGWarning,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Click to grade",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    AssignmentSeeder.SubmissionStatus.LATE -> {
                        if (submission.grade != null) {
                            Text(
                                text = "${String.format("%.1f", submission.grade)}/10",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = getGradeColor(submission.grade)
                            )
                            Text(
                                text = "Graded",
                                style = MaterialTheme.typography.bodySmall,
                                color = ESGSuccess
                            )
                        } else {
                            Text(
                                text = "Pending",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFFFF5722),
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Click to grade",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun getHighestGrade(submissions: List<AssignmentSeeder.StudentSubmission>): Float {
    return submissions
        .filter { it.grade != null }
        .maxOfOrNull { it.grade ?: 0f } ?: 0f
}

private fun getLowestGrade(submissions: List<AssignmentSeeder.StudentSubmission>): Float {
    return submissions
        .filter { it.grade != null }
        .minOfOrNull { it.grade ?: 0f } ?: 0f
}

private fun getGradeColor(grade: Float): Color {
    return when {
        grade >= 9f -> ESGSuccess
        grade >= 8f -> ESGInfo
        grade >= 7f -> ESGWarning
        grade >= 6f -> Color(0xFFFF9800)
        else -> Color(0xFFFF5722)
    }
}

private fun formatDateTime(timestamp: Long): String {
    val date = java.util.Date(timestamp)
    val formatter = java.text.SimpleDateFormat("dd/MM/yyyy HH:mm", java.util.Locale.getDefault())
    return formatter.format(date)
}
