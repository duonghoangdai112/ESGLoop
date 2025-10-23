package com.ignitech.esgcompanion.presentation.screen.expert

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.hilt.navigation.compose.hiltViewModel
import com.ignitech.esgcompanion.R
import com.ignitech.esgcompanion.presentation.viewmodel.EnterpriseESGDataViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterpriseESGDataScreen(
    navController: NavController,
    enterpriseId: String,
    enterpriseName: String,
    modifier: Modifier = Modifier,
    viewModel: EnterpriseESGDataViewModel = hiltViewModel()
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val assessmentData by viewModel.assessmentData.collectAsState()
    val activities by viewModel.activities.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    
    LaunchedEffect(enterpriseId) {
        viewModel.loadEnterpriseData(enterpriseId)
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Header
        TopAppBar(
            title = { 
                Column {
                    Text(
                        text = "ESG Data",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = enterpriseName,
                        style = MaterialTheme.typography.bodyMedium,
                        color = colorResource(id = R.color.text_secondary)
                    )
                }
            },
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            // Tab Row
            item {
                ESGDataTabRow(
                    selectedTab = selectedTab,
                    onTabSelected = { selectedTab = it }
                )
            }
            
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Content based on selected tab
            when (selectedTab) {
                0 -> {
                    // Assessment Data Tab
                    item {
                        Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                            AssessmentDataSection(
                                assessmentData = assessmentData,
                                isLoading = isLoading
                            )
                        }
                    }
                }
                1 -> {
                    // Activity Tracker Tab
                    item {
                        Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                            ActivityTrackerSection(
                                activities = activities,
                                isLoading = isLoading,
                                viewModel = viewModel
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ESGDataTabRow(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ESGDataTab(
            label = "ESG Assessment",
            color = colorResource(id = R.color.interactive_primary),
            isSelected = selectedTab == 0,
            onClick = { onTabSelected(0) },
            modifier = Modifier.weight(1f)
        )
        
        ESGDataTab(
            label = "ESG Activities",
            color = colorResource(id = R.color.interactive_primary),
            isSelected = selectedTab == 1,
            onClick = { onTabSelected(1) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun ESGDataTab(
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

@Composable
fun AssessmentDataSection(
    assessmentData: com.ignitech.esgcompanion.presentation.viewmodel.AssessmentData?,
    isLoading: Boolean
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "ESG Assessment Results",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        
        if (isLoading) {
            // Loading state
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = colorResource(id = R.color.interactive_primary)
                )
            }
        } else if (assessmentData == null || assessmentData.totalAnswers == 0) {
            // Empty state
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color = colorResource(id = R.color.border_card)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No assessment data",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Enterprise has not completed any ESG assessments yet",
                        style = MaterialTheme.typography.bodyMedium,
                        color = colorResource(id = R.color.text_secondary),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        } else {
            // ESG Score Overview
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color = colorResource(id = R.color.border_card)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Overall ESG Score",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${assessmentData.overallScore}/100",
                            style = MaterialTheme.typography.displayMedium,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.interactive_primary)
                        )
                        Surface(
                            color = colorResource(id = R.color.interactive_primary).copy(alpha = 0.1f),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = getScoreLabel(assessmentData.overallScore),
                                style = MaterialTheme.typography.bodyMedium,
                                color = colorResource(id = R.color.interactive_primary),
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }
            
            // Pillar Scores
            Text(
                text = "Pillar Scores",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            PillarScoreCard(
                title = "Environmental (E)",
                score = assessmentData.environmentalScore,
                total = 100,
                color = colorResource(id = R.color.interactive_primary)
            )
            
            PillarScoreCard(
                title = "Social (S)",
                score = assessmentData.socialScore,
                total = 100,
                color = colorResource(id = R.color.interactive_primary)
            )
            
            PillarScoreCard(
                title = "Governance (G)",
                score = assessmentData.governanceScore,
                total = 100,
                color = colorResource(id = R.color.esg_warning)
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
    }
}

fun getScoreLabel(score: Int): String {
    return when {
        score >= 90 -> "Excellent"
        score >= 75 -> "Good"
        score >= 60 -> "Fair"
        score >= 40 -> "Average"
        else -> "Needs Improvement"
    }
}

@Composable
fun ActivityTrackerSection(
    activities: List<com.ignitech.esgcompanion.data.entity.ESGTrackerEntity>,
    isLoading: Boolean,
    viewModel: EnterpriseESGDataViewModel
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Recorded ESG Activities",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        
        if (isLoading) {
            // Loading state
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = colorResource(id = R.color.interactive_primary)
                )
            }
            return
        }
        
        if (activities.isEmpty()) {
            // Empty state
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color = colorResource(id = R.color.border_card)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No ESG activities yet",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "The enterprise has not recorded any ESG activities yet",
                        style = MaterialTheme.typography.bodyMedium,
                        color = colorResource(id = R.color.text_secondary),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
            return
        }
        
        // Activity Summary
        val environmentalCount = viewModel.getActivityCountByPillar(com.ignitech.esgcompanion.domain.entity.ESGPillar.ENVIRONMENTAL)
        val socialCount = viewModel.getActivityCountByPillar(com.ignitech.esgcompanion.domain.entity.ESGPillar.SOCIAL)
        val governanceCount = viewModel.getActivityCountByPillar(com.ignitech.esgcompanion.domain.entity.ESGPillar.GOVERNANCE)
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(
                width = 1.dp,
                color = colorResource(id = R.color.border_card)
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ActivitySummaryItem(
                    count = activities.size.toString(),
                    label = "Total Activities",
                    color = colorResource(id = R.color.interactive_primary)
                )
                ActivitySummaryItem(
                    count = environmentalCount.toString(),
                    label = "Environmental",
                    color = colorResource(id = R.color.interactive_primary)
                )
                ActivitySummaryItem(
                    count = socialCount.toString(),
                    label = "Social",
                    color = colorResource(id = R.color.interactive_primary)
                )
                ActivitySummaryItem(
                    count = governanceCount.toString(),
                    label = "Governance",
                    color = colorResource(id = R.color.esg_warning)
                )
            }
        }
        
        // Recent Activities
        Text(
            text = "Recent Activities",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        
        // Display recent activities (limit to 10)
        activities.take(10).forEach { activity ->
            val pillarName = when (activity.pillar) {
                com.ignitech.esgcompanion.domain.entity.ESGPillar.ENVIRONMENTAL -> "Environmental"
                com.ignitech.esgcompanion.domain.entity.ESGPillar.SOCIAL -> "Social"
                com.ignitech.esgcompanion.domain.entity.ESGPillar.GOVERNANCE -> "Governance"
            }
            
            val pillarColor = if (activity.pillar == com.ignitech.esgcompanion.domain.entity.ESGPillar.GOVERNANCE) {
                colorResource(id = R.color.esg_warning)
            } else {
                colorResource(id = R.color.interactive_primary)
            }
            
            // Convert timestamp to date string
            val dateFormat = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
            val dateString = dateFormat.format(java.util.Date(activity.plannedDate))
            
            ESGActivityCard(
                title = activity.title,
                pillar = pillarName,
                pillarColor = pillarColor,
                date = dateString,
                impact = activity.description ?: "No description"
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun PillarScoreCard(
    title: String,
    score: Int,
    total: Int,
    color: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(
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
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "$score/$total",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = color
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = score / total.toFloat(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = color,
                trackColor = colorResource(id = R.color.border_card)
            )
        }
    }
}

@Composable
fun AssessmentHistoryCard(
    period: String,
    score: Int,
    date: String,
    status: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(
            width = 1.dp,
            color = colorResource(id = R.color.border_card)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = period,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = date,
                    style = MaterialTheme.typography.bodySmall,
                    color = colorResource(id = R.color.text_secondary)
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "$score/100",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.interactive_primary)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Surface(
                    color = colorResource(id = R.color.interactive_primary).copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = status,
                        style = MaterialTheme.typography.bodySmall,
                        color = colorResource(id = R.color.interactive_primary),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ActivitySummaryItem(
    count: String,
    label: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = count,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = colorResource(id = R.color.text_secondary),
            fontSize = 11.sp
        )
    }
}

@Composable
fun ESGActivityCard(
    title: String,
    pillar: String,
    pillarColor: Color,
    date: String,
    impact: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(
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
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                Surface(
                    color = pillarColor.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = pillar,
                        style = MaterialTheme.typography.bodySmall,
                        color = pillarColor,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = date,
                    style = MaterialTheme.typography.bodySmall,
                    color = colorResource(id = R.color.text_secondary)
                )
                Text(
                    text = impact,
                    style = MaterialTheme.typography.bodySmall,
                    color = colorResource(id = R.color.text_secondary)
                )
            }
        }
    }
}

