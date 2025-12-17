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
import com.ignitech.esgcompanion.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstructorGradingScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var selectedFilter by remember { mutableStateOf("all") }
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Grading and Assessment",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        
        // Grading Statistics
        item {
            val allAssignments = getAssignments()
            val pendingCount = allAssignments.count { it.status == AssignmentStatus.PENDING }
            val gradedCount = allAssignments.count { it.status == AssignmentStatus.GRADED }
            val overdueCount = allAssignments.count { it.status == AssignmentStatus.OVERDUE }
            val totalCount = allAssignments.size
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                )
            ) {
                Column {
                    Text(
                        text = "📊 Grading Statistics",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        GradingStatCard(
                            title = "Pending",
                            value = pendingCount.toString(),
                            color = Color(0xFFFF5722),
                            modifier = Modifier.weight(1f)
                        )
                        GradingStatCard(
                            title = "Graded",
                            value = gradedCount.toString(),
                            color = ESGSuccess,
                            modifier = Modifier.weight(1f)
                        )
                        GradingStatCard(
                            title = "Overdue",
                            value = overdueCount.toString(),
                            color = ESGWarning,
                            modifier = Modifier.weight(1f)
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
                    onClick = { selectedFilter = "overdue" },
                    label = { Text("Overdue") },
                    selected = selectedFilter == "overdue"
                )
            }
        }
        
        // Filter assignments based on selected filter
        val filteredAssignments = when (selectedFilter) {
            "pending" -> getAssignments().filter { it.status == AssignmentStatus.PENDING }
            "graded" -> getAssignments().filter { it.status == AssignmentStatus.GRADED }
            "overdue" -> getAssignments().filter { it.status == AssignmentStatus.OVERDUE }
            else -> getAssignments()
        }
        
        // Assignment List
        item {
            Text(
                text = "Assignment List (${filteredAssignments.size})",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
        
        items(filteredAssignments) { assignment ->
            AssignmentCard(
                assignment = assignment,
                onGradeClick = {
                    navController.navigate("assignment_grading/${assignment.id}")
                },
                onViewGradesClick = {
                    navController.navigate("assignment_grades/${assignment.id}")
                }
            )
        }
    }
}

@Composable
fun GradingStatCard(
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
                .padding(16.dp),
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
fun AssignmentCard(
    assignment: Assignment,
    onGradeClick: () -> Unit,
    onViewGradesClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
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
                        text = assignment.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = assignment.className,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Due: ${assignment.dueDate}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = assignment.status.color.copy(alpha = 0.1f)
                    )
                ) {
                    Text(
                        text = assignment.status.text,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = assignment.status.color
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
                        icon = Icons.Default.People,
                        text = "${assignment.submittedCount}/${assignment.totalStudents} students"
                    )
                    InfoItem(
                        icon = Icons.Default.Schedule,
                        text = assignment.duration
                    )
                }
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (assignment.status == AssignmentStatus.PENDING) {
                        Button(
                            onClick = onGradeClick,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFF9800),
                                contentColor = Color.White
                            )
                        ) {
                            Text("Grade")
                        }
                    } else {
                        Button(
                            onClick = onViewGradesClick,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = ESGSuccess,
                                contentColor = Color.White
                            )
                        ) {
                            Text("View Grades")
                        }
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

// Data classes
data class Assignment(
    val id: String,
    val title: String,
    val className: String,
    val dueDate: String,
    val duration: String,
    val totalStudents: Int,
    val submittedCount: Int,
    val status: AssignmentStatus
)

enum class AssignmentStatus(
    val text: String,
    val color: Color
) {
    PENDING("Pending", Color(0xFFFF5722)),
    GRADED("Graded", ESGSuccess),
    OVERDUE("Overdue", Color(0xFFFF9800))
}

// Mock data functions
private fun getAssignments(): List<Assignment> {
    return listOf(
        Assignment(
            id = "assign_001",
            title = "ESG Report Analysis",
            className = "ESG-101",
            dueDate = "15/12/2024",
            duration = "1 week",
            totalStudents = 25,
            submittedCount = 20,
            status = AssignmentStatus.PENDING
        ),
        Assignment(
            id = "assign_002",
            title = "Sustainable Strategy Design",
            className = "ESG-102",
            dueDate = "10/12/2024",
            duration = "2 weeks",
            totalStudents = 20,
            submittedCount = 18,
            status = AssignmentStatus.GRADED
        ),
        Assignment(
            id = "assign_003",
            title = "Environmental Impact Report",
            className = "ESG-201",
            dueDate = "05/12/2024",
            duration = "1 week",
            totalStudents = 15,
            submittedCount = 12,
            status = AssignmentStatus.OVERDUE
        ),
        Assignment(
            id = "assign_004",
            title = "ESG Project Presentation",
            className = "ESG-101",
            dueDate = "20/12/2024",
            duration = "3 days",
            totalStudents = 25,
            submittedCount = 0,
            status = AssignmentStatus.PENDING
        ),
        Assignment(
            id = "assign_005",
            title = "Group Assignment on Governance",
            className = "ESG-201",
            dueDate = "08/12/2024",
            duration = "2 weeks",
            totalStudents = 15,
            submittedCount = 15,
            status = AssignmentStatus.GRADED
        )
    )
}
