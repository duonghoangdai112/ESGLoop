package com.ignitech.esgcompanion.presentation.screen.student

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.navigation.NavController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ignitech.esgcompanion.R
import com.ignitech.esgcompanion.data.entity.AssignmentEntity
import com.ignitech.esgcompanion.data.entity.AssignmentStatus
import com.ignitech.esgcompanion.data.entity.AssignmentType
import com.ignitech.esgcompanion.data.entity.AssignmentDifficulty
import com.ignitech.esgcompanion.data.entity.ResourceType
import com.ignitech.esgcompanion.presentation.viewmodel.AssignmentDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignmentDetailScreen(
    navController: NavController,
    assignmentId: String,
    viewModel: AssignmentDetailViewModel = hiltViewModel()
) {
    val assignment by viewModel.assignment.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    
    LaunchedEffect(assignmentId) {
        viewModel.loadAssignment(assignmentId)
    }
    
    if (isLoading || assignment == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }
    
    val assignmentData = assignment!!
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Assignment Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
            // Assignment Header
            item {
                AssignmentDetailHeaderCard(assignment = assignmentData)
            }
            
            // Assignment Info
            item {
                AssignmentInfoCard(assignment = assignmentData)
            }
            
            // Instructions
            item {
                InstructionsCard(assignment = assignmentData)
            }
            
            // Requirements
            item {
                RequirementsCard(assignment = assignmentData)
            }
            
            // Resources
            item {
                ResourcesCard(assignment = assignmentData)
            }
            
            // Action Buttons
            item {
                ActionButtonsCard(
                    assignment = assignmentData,
                    onStartAssignment = { 
                        navController.navigate("assignment_work/$assignmentId")
                    }
                )
            }
        }
    }
}

@Composable
fun AssignmentDetailHeaderCard(assignment: AssignmentEntity) {
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
                text = assignment.title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.interactive_primary)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = assignment.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AssignmentDetailStatusChip(status = assignment.status)
                
                Text(
                    text = "Due: ${assignment.dueDate}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(id = R.color.interactive_primary),
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun AssignmentInfoCard(assignment: AssignmentEntity) {
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
                text = "Assignment Information",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.interactive_primary)
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            InfoRow(
                label = "Assignment Type",
                value = when (assignment.type) {
                    AssignmentType.INDIVIDUAL -> "Individual"
                    AssignmentType.GROUP -> "Group"
                    AssignmentType.PROJECT -> "Project"
                    AssignmentType.REPORT -> "Report"
                    AssignmentType.PRESENTATION -> "Presentation"
                }
            )
            
            InfoRow(
                label = "Difficulty",
                value = when (assignment.difficulty) {
                    AssignmentDifficulty.EASY -> "Easy"
                    AssignmentDifficulty.MEDIUM -> "Medium"
                    AssignmentDifficulty.HARD -> "Hard"
                }
            )
            
            InfoRow(
                label = "Estimated Time",
                value = "${assignment.estimatedTime} phút"
            )
            
            InfoRow(
                label = "Điểm tối đa",
                value = "${assignment.maxScore} điểm"
            )
        }
    }
}

@Composable
fun InstructionsCard(assignment: AssignmentEntity) {
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
                text = "Hướng dẫn thực hiện",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.interactive_primary)
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            assignment.instructions.forEachIndexed { index, instruction ->
                InstructionItem(
                    number = index + 1,
                    text = instruction
                )
                if (index < assignment.instructions.size - 1) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun RequirementsCard(assignment: AssignmentEntity) {
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
                text = "Yêu cầu",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.interactive_primary)
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            assignment.requirements.forEach { requirement ->
                RequirementItem(text = requirement)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun ResourcesCard(assignment: AssignmentEntity) {
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
                text = "Tài liệu tham khảo",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.interactive_primary)
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            assignment.resources.forEach { resource ->
                ResourceItem(resource = resource)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun ActionButtonsCard(
    assignment: AssignmentEntity,
    onStartAssignment: () -> Unit
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
            Button(
                onClick = onStartAssignment,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.interactive_primary)
                )
            ) {
                Icon(
                    Icons.Default.PlayArrow,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Bắt đầu bài tập")
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Button(
                onClick = { /* TODO: Implement save for later */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.esg_warning),
                    contentColor = Color.White
                )
            ) {
                Text("Lưu để làm sau")
            }
        }
    }
}

@Composable
fun AssignmentDetailStatusChip(status: AssignmentStatus) {
    val (text, color) = when (status) {
        AssignmentStatus.NOT_STARTED -> "Chưa bắt đầu" to colorResource(id = R.color.status_not_started)
        AssignmentStatus.IN_PROGRESS -> "Đang làm" to colorResource(id = R.color.status_in_progress)
        AssignmentStatus.SUBMITTED -> "Đã nộp" to colorResource(id = R.color.status_completed)
        AssignmentStatus.GRADED -> "Đã chấm" to colorResource(id = R.color.status_completed)
    }
    
    Surface(
        color = color.copy(alpha = 0.1f),
        shape = RoundedCornerShape(16.dp)
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
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun InstructionItem(number: Int, text: String) {
    Row(
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(
                    color = colorResource(id = R.color.interactive_primary),
                    shape = RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = number.toString(),
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun RequirementItem(text: String) {
    Row(
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            Icons.Default.CheckCircle,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = colorResource(id = R.color.interactive_primary)
        )
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun ResourceItem(resource: com.ignitech.esgcompanion.data.entity.AssignmentResource) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            when (resource.type) {
                ResourceType.DOCUMENT -> Icons.Default.Description
                ResourceType.VIDEO -> Icons.Default.PlayCircle
                ResourceType.LINK -> Icons.Default.Link
                ResourceType.TEMPLATE -> Icons.Default.ContentCopy
                ResourceType.EXAMPLE -> Icons.Default.Info
            },
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = colorResource(id = R.color.interactive_primary)
        )
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Text(
            text = resource.title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
        
        Icon(
            Icons.Default.OpenInNew,
            contentDescription = "Mở",
            modifier = Modifier.size(16.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
