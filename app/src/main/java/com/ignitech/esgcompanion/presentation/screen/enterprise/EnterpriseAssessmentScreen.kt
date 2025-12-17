package com.ignitech.esgcompanion.presentation.screen.enterprise

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.BorderStroke
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.ui.draw.scale
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.colorResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ignitech.esgcompanion.domain.entity.*
import com.ignitech.esgcompanion.ui.theme.*
import com.ignitech.esgcompanion.R
import com.ignitech.esgcompanion.utils.AppColors
import com.ignitech.esgcompanion.utils.AppColorsData
import com.ignitech.esgcompanion.data.local.entity.ESGAssessmentEntity
import com.ignitech.esgcompanion.presentation.viewmodel.EnterpriseAssessmentViewModel

private data class StatusData(
    val text: String,
    val backgroundColor: androidx.compose.ui.graphics.Color,
    val textColor: androidx.compose.ui.graphics.Color,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterpriseAssessmentScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: EnterpriseAssessmentViewModel = hiltViewModel()
) {
    val colors = AppColors()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var selectedPillar by remember { mutableStateOf<ESGPillar?>(ESGPillar.ENVIRONMENTAL) }
    
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Header
        TopAppBar(
            title = { 
                Text(
                    text = "ESG Assessment",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            },
            actions = {
                IconButton(onClick = { 
                    navController.navigate("assessment_history") {
                        popUpTo("enterprise_assessment") { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }) {
                    Icon(
                        Icons.Default.History,
                        contentDescription = "Assessment History",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        )
        
        // Content
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = colorResource(id = R.color.interactive_primary)
                )
            }
        } else if (uiState.error != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = uiState.error ?: "An error occurred",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                    Button(onClick = { viewModel.refresh() }) {
                        Text("Retry")
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Assessment Period Info
                item {
                    Text(
                        text = "${uiState.currentPeriod} Assessment",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            
            item {
                PillarTabRow(
                    selectedPillar = selectedPillar,
                    onPillarSelected = { selectedPillar = it }
                )
            }
            
            // Start New Assessment Section
            if (selectedPillar != null) {
                item {
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
                                text = "Start New Assessment",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Create ${selectedPillar?.let { getPillarName(it) }} assessment for your enterprise",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            // Manual Assessment Button
                            Button(
                                onClick = { 
                                    selectedPillar?.let { pillar ->
                                        navController.navigate("esg_assessment_work/${pillar.name}")
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                enabled = selectedPillar != null,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = colorResource(id = R.color.interactive_primary)
                                )
                            ) {
                                Icon(
                                    Icons.Default.Edit,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Start Manual Assessment")
                            }
                            
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            // Upload Excel Button
                            OutlinedButton(
                                onClick = { 
                                    // TODO: Implement Excel upload functionality
                                    // This could open a file picker or show upload dialog
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = colorResource(id = R.color.interactive_primary)
                                ),
                                border = BorderStroke(
                                    1.dp, 
                                    colorResource(id = R.color.interactive_primary)
                                )
                            ) {
                                Icon(
                                    Icons.Default.Upload,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Upload file Excel")
                            }
                        }
                    }
                }
            } else {
                // Start New Assessment Button (All Pillars)
                item {
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
                                text = "Start New Assessment",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Select an ESG pillar above to start assessment",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "You can assess manually or upload an Excel file",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
            
            // Overall Progress
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Overall Progress",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    CustomCircularProgressIndicator(
                        progress = uiState.overallProgress,
                        color = colorResource(id = R.color.interactive_primary)
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Progress Details
                    ProgressDetailsSection(uiState = uiState)
                }
            }
            }
        }
    }
}

@Composable
fun ProgressDetailsSection(uiState: com.ignitech.esgcompanion.presentation.viewmodel.EnterpriseAssessmentUiState) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Overall Statistics
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ProgressStatItem(
                title = "Completed",
                value = "${uiState.completedAssessments}",
                subtitle = "assessments"
            )
            ProgressStatItem(
                title = "In Progress",
                value = "${uiState.inProgressAssessments}",
                subtitle = "assessments"
            )
            ProgressStatItem(
                title = "Average Score",
                value = "${uiState.averageScore}",
                subtitle = "points"
            )
        }
        
        Spacer(modifier = Modifier.height(20.dp))
        
        // Pillar Progress
        Text(
            text = "Progress by Pillar",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PillarProgressItem(
                pillar = ESGPillar.ENVIRONMENTAL,
                progress = uiState.environmentalProgress,
                score = uiState.environmentalScore
            )
            PillarProgressItem(
                pillar = ESGPillar.SOCIAL,
                progress = uiState.socialProgress,
                score = uiState.socialScore
            )
            PillarProgressItem(
                pillar = ESGPillar.GOVERNANCE,
                progress = uiState.governanceProgress,
                score = uiState.governanceScore
            )
        }
    }
}

@Composable
fun ProgressStatItem(
    title: String,
    value: String,
    subtitle: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.interactive_primary)
        )
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun PillarProgressItem(
    pillar: ESGPillar,
    progress: Float,
    score: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = getPillarName(pillar),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )
        
        Text(
            text = "${score} pts",
            style = MaterialTheme.typography.bodySmall,
            color = colorResource(id = R.color.interactive_primary),
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .weight(1f)
                .height(6.dp),
            color = colorResource(id = R.color.interactive_primary),
            trackColor = colorResource(id = R.color.border_light)
        )
        
        Text(
            text = "${(progress * 100).toInt()}%",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
fun CustomCircularProgressIndicator(
    progress: Float,
    color: Color
) {
    Box(
        modifier = Modifier
            .size(140.dp)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        // Background circle
        androidx.compose.material3.CircularProgressIndicator(
            progress = 1f,
            modifier = Modifier.size(140.dp),
            color = color.copy(alpha = 0.2f),
            strokeWidth = 8.dp
        )
        
        // Progress circle
        androidx.compose.material3.CircularProgressIndicator(
            progress = progress,
            modifier = Modifier.size(140.dp),
            color = color,
            strokeWidth = 8.dp
        )
        
        // Progress text
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${(progress * 100).toInt()}%",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                text = "Completed",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun PillarTabRow(
    selectedPillar: ESGPillar?,
    onPillarSelected: (ESGPillar?) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
            PillarTab(
                pillar = ESGPillar.ENVIRONMENTAL,
                label = "Environmental",
                color = colorResource(id = R.color.interactive_primary),
                isSelected = selectedPillar == ESGPillar.ENVIRONMENTAL,
                onClick = { onPillarSelected(ESGPillar.ENVIRONMENTAL) },
                modifier = Modifier.weight(1f)
            )
            
            PillarTab(
                pillar = ESGPillar.SOCIAL,
                label = "Social", 
                color = colorResource(id = R.color.interactive_primary),
                isSelected = selectedPillar == ESGPillar.SOCIAL,
                onClick = { onPillarSelected(ESGPillar.SOCIAL) },
                modifier = Modifier.weight(1f)
            )
            
            PillarTab(
                pillar = ESGPillar.GOVERNANCE,
                label = "Governance",
                color = colorResource(id = R.color.interactive_primary),
                isSelected = selectedPillar == ESGPillar.GOVERNANCE,
                onClick = { onPillarSelected(ESGPillar.GOVERNANCE) },
                modifier = Modifier.weight(1f)
            )
    }
}

@Composable
fun PillarTab(
    pillar: ESGPillar,
    label: String,
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val animatedScale by animateFloatAsState(
        targetValue = if (isSelected) 1.05f else 1f,
        animationSpec = tween(200)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssessmentCard(
    assessment: ESGAssessment,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = colorResource(id = R.color.border_card),
                shape = MaterialTheme.shapes.medium
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple()
            ) { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = assessment.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = assessment.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                StatusChip(status = assessment.status)
            }
            
            if (assessment.status == AssessmentStatus.COMPLETED) {
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Score: ${assessment.totalScore}/${assessment.maxScore}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = colorResource(id = R.color.interactive_primary)
                    )
                }
            }
        }
    }
}

@Composable
fun StatusChip(status: AssessmentStatus) {
    val colors = AppColors()
    val statusData = when (status) {
        AssessmentStatus.NOT_STARTED -> 
            StatusData(
                "Not Started", 
                colors.statusNotStarted.copy(alpha = 0.1f), 
                colors.statusNotStarted, 
                Icons.Default.Schedule
            )
        AssessmentStatus.IN_PROGRESS -> 
            StatusData(
                "In Progress", 
                colors.statusInProgress.copy(alpha = 0.15f), 
                colors.statusInProgress, 
                Icons.Default.AccessTime
            )
        AssessmentStatus.COMPLETED -> 
            StatusData(
                "Completed", 
                colors.statusCompleted.copy(alpha = 0.15f), 
                colors.statusCompleted, 
                Icons.Default.CheckCircle
            )
        AssessmentStatus.DRAFT -> 
            StatusData(
                "Draft", 
                colors.statusInProgress.copy(alpha = 0.1f), 
                colors.statusInProgress, 
                Icons.Default.Edit
            )
    }
    
    val (text, backgroundColor, textColor, icon) = statusData
    
    Surface(
        modifier = Modifier,
        color = backgroundColor,
        shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                modifier = Modifier.size(12.dp),
                tint = textColor
            )
            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall,
                color = textColor,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun PillarSection(
    pillar: ESGPillar,
    assessments: List<ESGAssessment>,
    navController: NavController
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
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = getPillarIcon(pillar),
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = getPillarName(pillar),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "${assessments.count { it.status == AssessmentStatus.COMPLETED }}/${assessments.size}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(id = R.color.interactive_primary)
                )
            }
            
            Spacer(modifier = Modifier.height(6.dp))
            
            assessments.forEach { assessment ->
                AssessmentCard(
                    assessment = assessment,
                    onClick = { 
                        // TODO: Navigate to assessment detail screen
                        // navController.navigate("assessment_detail/${assessment.id}")
                    }
                )
                if (assessment != assessments.last()) {
                    Spacer(modifier = Modifier.height(3.dp))
                }
            }
        }
    }
}

@Composable
fun ProgressIndicator(
    label: String,
    progress: Float,
    color: Color
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "${(progress * 100).toInt()}%",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = color
            )
        }
        Spacer(modifier = Modifier.height(3.dp))
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier.fillMaxWidth(),
            color = color,
            trackColor = color.copy(alpha = 0.2f)
        )
    }
}

// Mock data functions
private fun getAssessmentsForPillar(pillar: ESGPillar): List<ESGAssessment> {
    return when (pillar) {
        ESGPillar.ENVIRONMENTAL -> listOf(
            ESGAssessment(
                id = "env_001",
                userId = "enterprise_001",
                title = "Environmental Impact Assessment",
                description = "Assess environmental impact of production activities",
                pillar = ESGPillar.ENVIRONMENTAL,
                status = AssessmentStatus.COMPLETED,
                totalScore = 85,
                maxScore = 100,
                completedAt = System.currentTimeMillis() - 86400000 * 7,
                createdAt = System.currentTimeMillis() - 86400000 * 10,
                answers = emptyList()
            ),
            ESGAssessment(
                id = "env_002",
                userId = "enterprise_001",
                title = "Waste Management",
                description = "Assess waste management and recycling system",
                pillar = ESGPillar.ENVIRONMENTAL,
                status = AssessmentStatus.IN_PROGRESS,
                totalScore = 60,
                maxScore = 100,
                completedAt = null,
                createdAt = System.currentTimeMillis() - 86400000 * 5,
                answers = emptyList()
            )
        )
        ESGPillar.SOCIAL -> listOf(
            ESGAssessment(
                id = "social_001",
                userId = "enterprise_001",
                title = "Labor Rights",
                description = "Assess labor rights and employee benefits",
                pillar = ESGPillar.SOCIAL,
                status = AssessmentStatus.NOT_STARTED,
                totalScore = 0,
                maxScore = 100,
                completedAt = null,
                createdAt = System.currentTimeMillis() - 86400000 * 3,
                answers = emptyList()
            )
        )
        ESGPillar.GOVERNANCE -> listOf(
            ESGAssessment(
                id = "gov_001",
                userId = "enterprise_001",
                title = "Corporate Governance",
                description = "Assess management structure and financial transparency",
                pillar = ESGPillar.GOVERNANCE,
                status = AssessmentStatus.COMPLETED,
                totalScore = 78,
                maxScore = 100,
                completedAt = System.currentTimeMillis() - 86400000 * 14,
                createdAt = System.currentTimeMillis() - 86400000 * 20,
                answers = emptyList()
            )
        )
    }
}


private fun getPillarName(pillar: ESGPillar): String {
    return when (pillar) {
        ESGPillar.ENVIRONMENTAL -> "Environmental"
        ESGPillar.SOCIAL -> "Social"
        ESGPillar.GOVERNANCE -> "Governance"
    }
}

private fun getPillarIcon(pillar: ESGPillar): String {
    return when (pillar) {
        ESGPillar.ENVIRONMENTAL -> "E"
        ESGPillar.SOCIAL -> "S"
        ESGPillar.GOVERNANCE -> "G"
    }
}

