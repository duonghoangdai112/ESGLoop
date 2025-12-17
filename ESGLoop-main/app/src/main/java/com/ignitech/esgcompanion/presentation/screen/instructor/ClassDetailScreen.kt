package com.ignitech.esgcompanion.presentation.screen.instructor

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ignitech.esgcompanion.R
import com.ignitech.esgcompanion.presentation.viewmodel.ClassDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassDetailScreen(
    navController: NavController,
    classId: String,
    viewModel: ClassDetailViewModel,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    LaunchedEffect(classId) {
        viewModel.loadClassData(classId)
    }
    
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Header
        TopAppBar(
            title = { 
                Column {
                    Text(
                        text = uiState.classEntity?.name ?: "Class Details",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = uiState.classEntity?.code ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                IconButton(onClick = { /* Settings */ }) {
                    Icon(Icons.Default.Settings, contentDescription = "Settings")
                }
            }
        )
        
        // Custom Tab Row
        ClassTabRow(
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it }
        )
        
        // Content
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            uiState.error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Error: ${uiState.error}",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
            else -> {
                when (selectedTab) {
                    0 -> AssignmentsTab(classId = classId)
                    1 -> StudentsTab(students = uiState.students)
                }
            }
        }
    }
}

@Composable
fun AssignmentsTab(
    classId: String,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(getAssignments(classId)) { assignment ->
            AssignmentCard(assignment = assignment)
        }
    }
}

@Composable
fun StudentsTab(
    students: List<com.ignitech.esgcompanion.data.local.entity.StudentEntity>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(students) { student ->
            StudentCard(student = student)
        }
    }
}

@Composable
fun AssignmentCard(
    assignment: ClassAssignment
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
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
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
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = assignment.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                StatusChip(status = assignment.status)
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Due: ${assignment.dueDate}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "${assignment.submittedCount}/${assignment.totalStudents} submitted",
                    style = MaterialTheme.typography.bodySmall,
                    color = colorResource(id = R.color.interactive_primary),
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun StudentCard(
    student: com.ignitech.esgcompanion.data.local.entity.StudentEntity
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = colorResource(id = R.color.interactive_primary),
                        shape = RoundedCornerShape(24.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = student.name.take(2).uppercase(),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = student.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${student.studentId} - ${student.email}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "Avg: ${student.averageScore}%",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = when {
                        student.averageScore >= 85 -> colorResource(id = R.color.esg_success)
                        student.averageScore >= 70 -> colorResource(id = R.color.esg_warning)
                        else -> Color(0xFFFF5722)
                    }
                )
                Text(
                    text = "${student.completedAssignments}/${student.totalAssignments} completed",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun StatusChip(status: ClassAssignmentStatus) {
    val (text, color) = when (status) {
        ClassAssignmentStatus.ACTIVE -> "Active" to colorResource(id = R.color.esg_success)
        ClassAssignmentStatus.CLOSED -> "Closed" to colorResource(id = R.color.esg_warning)
        ClassAssignmentStatus.DRAFT -> "Draft" to colorResource(id = R.color.interactive_primary)
    }
    
    Surface(
        color = color.copy(alpha = 0.1f),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.border(
            width = 1.dp,
            color = color.copy(alpha = 0.3f),
            shape = RoundedCornerShape(8.dp)
        )
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.bodySmall,
            color = color,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun ClassTabRow(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ClassTab(
            label = "Assignments",
            color = colorResource(id = R.color.interactive_primary),
            isSelected = selectedTab == 0,
            onClick = { onTabSelected(0) },
            modifier = Modifier.weight(1f)
        )
        
        ClassTab(
            label = "Students",
            color = colorResource(id = R.color.interactive_primary),
            isSelected = selectedTab == 1,
            onClick = { onTabSelected(1) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun ClassTab(
    label: String,
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val animatedScale by animateFloatAsState(
        targetValue = if (isSelected) 1.05f else 1f,
        animationSpec = tween(200),
        label = "scale"
    )
    
    Surface(
        modifier = modifier
            .padding(horizontal = 2.dp)
            .scale(animatedScale),
        shape = MaterialTheme.shapes.medium,
        color = if (isSelected) 
            color.copy(alpha = 0.1f) 
        else 
            Color.Transparent,
        border = BorderStroke(
            width = if (isSelected) 2.dp else 1.dp,
            color = if (isSelected) 
                color 
            else 
                colorResource(id = R.color.border_light)
        ),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Label
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = if (isSelected) 
                    color 
                else 
                    MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = if (isSelected) 
                    FontWeight.Bold 
                else 
                    FontWeight.Medium
            )
        }
    }
}

// Data classes
data class ClassAssignment(
    val id: String,
    val title: String,
    val description: String,
    val status: ClassAssignmentStatus,
    val dueDate: String,
    val submittedCount: Int,
    val totalStudents: Int
)

data class ClassStudent(
    val id: String,
    val name: String,
    val email: String,
    val averageScore: Int,
    val completedAssignments: Int,
    val totalAssignments: Int
)

enum class ClassAssignmentStatus {
    ACTIVE, CLOSED, DRAFT
}

// Mock data functions
private fun getClassById(classId: String): com.ignitech.esgcompanion.data.local.entity.ClassEntity? {
    return com.ignitech.esgcompanion.data.seed.ClassSeeder.getClasses().find { it.id == classId }
}

private fun getAssignments(classId: String): List<ClassAssignment> {
    return listOf(
        ClassAssignment(
            id = "assign_001",
            title = "ESG Report Analysis",
            description = "Analyze and evaluate ESG report of a real company",
            status = ClassAssignmentStatus.ACTIVE,
            dueDate = "25/12/2024",
            submittedCount = 32,
            totalStudents = 45
        ),
        ClassAssignment(
            id = "assign_002",
            title = "Sustainable Strategy Design",
            description = "Design sustainable development strategy for enterprises",
            status = ClassAssignmentStatus.ACTIVE,
            dueDate = "30/12/2024",
            submittedCount = 28,
            totalStudents = 45
        ),
        ClassAssignment(
            id = "assign_003",
            title = "ESG Risk Assessment",
            description = "Conduct ESG risk assessment for a specific industry",
            status = ClassAssignmentStatus.CLOSED,
            dueDate = "15/12/2024",
            submittedCount = 45,
            totalStudents = 45
        )
    )
}

private fun getStudents(classId: String): List<ClassStudent> {
    return listOf(
        ClassStudent(
            id = "student_001",
            name = "Nguyen Van An",
            email = "an.nguyen@email.com",
            averageScore = 88,
            completedAssignments = 8,
            totalAssignments = 10
        ),
        ClassStudent(
            id = "student_002",
            name = "Tran Thi Binh",
            email = "binh.tran@email.com",
            averageScore = 92,
            completedAssignments = 9,
            totalAssignments = 10
        ),
        ClassStudent(
            id = "student_003",
            name = "Le Van Cuong",
            email = "cuong.le@email.com",
            averageScore = 75,
            completedAssignments = 7,
            totalAssignments = 10
        ),
        ClassStudent(
            id = "student_004",
            name = "Pham Thi Dung",
            email = "dung.pham@email.com",
            averageScore = 85,
            completedAssignments = 8,
            totalAssignments = 10
        )
    )
}
