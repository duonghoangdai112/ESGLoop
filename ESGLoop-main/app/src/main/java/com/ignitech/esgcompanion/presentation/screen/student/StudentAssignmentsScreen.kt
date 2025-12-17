package com.ignitech.esgcompanion.presentation.screen.student

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import android.widget.Toast
import com.ignitech.esgcompanion.R
import com.ignitech.esgcompanion.data.entity.AssignmentStatus
import com.ignitech.esgcompanion.data.entity.AssignmentType
import com.ignitech.esgcompanion.data.entity.AssignmentDifficulty
import com.ignitech.esgcompanion.presentation.viewmodel.StudentAssignmentsViewModel

@Composable
fun StudentAssignmentsScreen(
    modifier: Modifier = Modifier,
    navController: androidx.navigation.NavController? = null,
    viewModel: StudentAssignmentsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val assignments by viewModel.assignments.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    
    if (isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "ESG Assignments",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            
            if (assignments.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No assignments yet",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                items(assignments) { assignment ->
                    AssignmentCard(
                        assignment = assignment,
                        onStartClick = { 
                            navController?.navigate("assignment_work/${assignment.id}")
                        },
                        onDetailClick = { 
                            navController?.navigate("assignment_detail/${assignment.id}")
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun AssignmentCard(
    assignment: com.ignitech.esgcompanion.data.entity.AssignmentEntity,
    onStartClick: () -> Unit,
    onDetailClick: () -> Unit
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
                        text = assignment.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.interactive_primary)
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Text(
                        text = "Due: ${assignment.dueDate}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = colorResource(id = R.color.interactive_primary),
                        fontWeight = FontWeight.Medium
                    )
                }
                
                AssignmentStatusChip(status = assignment.status)
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = assignment.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Assignment Info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                InfoChip(
                    icon = Icons.Default.Schedule,
                    text = "${assignment.estimatedTime} min"
                )
                
                InfoChip(
                    icon = Icons.Default.Star,
                    text = when (assignment.difficulty) {
                        AssignmentDifficulty.EASY -> "Easy"
                        AssignmentDifficulty.MEDIUM -> "Medium"
                        AssignmentDifficulty.HARD -> "Hard"
                    }
                )
                
                InfoChip(
                    icon = Icons.Default.Assignment,
                    text = when (assignment.type) {
                        AssignmentType.INDIVIDUAL -> "Individual"
                        AssignmentType.GROUP -> "Group"
                        AssignmentType.PROJECT -> "Project"
                        AssignmentType.REPORT -> "Report"
                        AssignmentType.PRESENTATION -> "Presentation"
                    }
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onStartClick,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.interactive_primary)
                    ),
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Start")
                }
                
                OutlinedButton(
                    onClick = onDetailClick,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.Black
                    ),
                    border = BorderStroke(
                        width = 1.dp,
                        color = colorResource(id = R.color.border_card)
                    )
                ) {
                    Text("Details")
                }
            }
        }
    }
}

@Composable
fun AssignmentStatusChip(status: AssignmentStatus) {
    val (text, color) = when (status) {
        AssignmentStatus.NOT_STARTED -> "Not Started" to colorResource(id = R.color.status_not_started)
        AssignmentStatus.IN_PROGRESS -> "In Progress" to colorResource(id = R.color.status_in_progress)
        AssignmentStatus.SUBMITTED -> "Submitted" to colorResource(id = R.color.status_completed)
        AssignmentStatus.GRADED -> "Graded" to colorResource(id = R.color.status_completed)
    }
    
    Surface(
        color = color.copy(alpha = 0.1f),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium,
            color = color
        )
    }
}

@Composable
fun InfoChip(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.width(4.dp))
        
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
