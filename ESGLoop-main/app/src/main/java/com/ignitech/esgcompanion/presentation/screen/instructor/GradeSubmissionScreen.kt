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
import com.ignitech.esgcompanion.utils.AppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GradeSubmissionScreen(
    submissionId: String,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val submission = AssignmentSeeder.getSubmissionById(submissionId)
    var grade by remember { mutableStateOf(submission?.grade?.toString() ?: "") }
    var feedback by remember { mutableStateOf(submission?.feedback ?: "") }
    var isGrading by remember { mutableStateOf(false) }
    val colors = AppColors()
    
    if (submission == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Submission not found")
        }
        return
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
                    text = "Grade Submission",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        
        // Student Info
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
                        text = submission.studentName,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Submitted: ${formatDateTime(submission.submittedAt)}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    if (submission.status == AssignmentSeeder.SubmissionStatus.LATE) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Schedule,
                                contentDescription = null,
                                tint = Color(0xFFFF5722),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Late",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFFFF5722),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
        
        // Submission Content
        item {
            Text(
                text = "Submission Content",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
        
        item {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = submission.content,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        
        // Attachments
        if (submission.attachments.isNotEmpty()) {
            item {
                Text(
                    text = "Attachments",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            
            items(submission.attachments) { attachment ->
                AttachmentCard(
                    fileName = attachment,
                    onClick = { /* Handle file download/view */ }
                )
            }
        }
        
        // Grading Section
        item {
            Text(
                text = "Grading",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
        
        item {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        value = grade,
                        onValueChange = { 
                            if (it.isEmpty() || it.matches(Regex("^\\d*\\.?\\d*$")) && it.toFloatOrNull()?.let { g -> g in 0f..10f } != false) {
                                grade = it
                            }
                        },
                        label = { Text("Score (0-10)") },
                        placeholder = { 
                            Text(
                                text = "Enter score...",
                                color = colors.textSecondary.copy(alpha = 0.6f)
                            ) 
                        },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = { Icon(Icons.Default.Grade, contentDescription = null) },
                        suffix = { Text("/10") },
                        isError = grade.isNotEmpty() && (grade.toFloatOrNull()?.let { it !in 0f..10f } ?: true)
                    )
                    
                    OutlinedTextField(
                        value = feedback,
                        onValueChange = { feedback = it },
                        label = { Text("Feedback") },
                        placeholder = { 
                            Text(
                                text = "Enter feedback for student...",
                                color = colors.textSecondary.copy(alpha = 0.6f)
                            ) 
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        minLines = 4
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = { navController.popBackStack() },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = MaterialTheme.colorScheme.outline
                            )
                        ) {
                            Text("Cancel")
                        }
                        
                        Button(
                            onClick = {
                                if (grade.isNotEmpty() && grade.toFloatOrNull()?.let { it in 0f..10f } == true) {
                                    isGrading = true
                                    // TODO: Save grade and feedback
                                    navController.popBackStack()
                                }
                            },
                            modifier = Modifier.weight(1f),
                            enabled = grade.isNotEmpty() && grade.toFloatOrNull()?.let { it in 0f..10f } == true && !isGrading,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFF9800),
                                contentColor = Color.White
                            )
                        ) {
                            if (isGrading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(16.dp),
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                            Text(if (isGrading) "Saving..." else "Save Grade")
                        }
                    }
                }
            }
        }
        
        // Previous Grade (if already graded)
        if (submission.status == AssignmentSeeder.SubmissionStatus.GRADED) {
            item {
                Text(
                    text = "Current Grade",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = ESGSuccess.copy(alpha = 0.1f)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Grade,
                                contentDescription = null,
                                tint = ESGSuccess
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Score: ${String.format("%.1f", submission.grade ?: 0f)}/10",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = ESGSuccess
                            )
                        }
                        
                        if (submission.feedback?.isNotEmpty() == true) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Feedback: ${submission.feedback}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        
                        if (submission.gradedAt != null) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Graded: ${formatDateTime(submission.gradedAt)}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttachmentCard(
    fileName: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.AttachFile,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = fileName,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )
            Icon(
                Icons.Default.Download,
                contentDescription = "Download",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

private fun formatDateTime(timestamp: Long): String {
    val date = java.util.Date(timestamp)
    val formatter = java.text.SimpleDateFormat("dd/MM/yyyy HH:mm", java.util.Locale.getDefault())
    return formatter.format(date)
}
