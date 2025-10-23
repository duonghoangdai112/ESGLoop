package com.ignitech.esgcompanion.presentation.screen.enterprise

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.BorderStroke
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.res.colorResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.ignitech.esgcompanion.domain.entity.ESGPillar
import com.ignitech.esgcompanion.R
import com.ignitech.esgcompanion.presentation.viewmodel.AssessmentHistoryViewModel
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssessmentHistoryScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: AssessmentHistoryViewModel = hiltViewModel()
) {
    val userId = "user_3" // Demo enterprise user
    val uiState by viewModel.uiState.collectAsState()
    
    var selectedYear by remember { mutableStateOf(Calendar.getInstance().get(Calendar.YEAR)) }
    var showYearDialog by remember { mutableStateOf(false) }
    
    // Load assessment history on first composition
    LaunchedEffect(Unit) {
        viewModel.loadAssessmentHistory(userId)
    }
    
    // Load assessments when year changes
    LaunchedEffect(selectedYear) {
        viewModel.loadAssessmentsByYear(userId, selectedYear)
    }
    
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Header
        TopAppBar(
            title = { 
                Text(
                    text = "Assessment History $selectedYear",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            actions = {
                IconButton(onClick = { showYearDialog = true }) {
                    Icon(
                        Icons.Default.FilterList,
                        contentDescription = "Select Year",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        )
        
        // Content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Timeline view
            item {
                Text(
                    text = "Quarterly Assessments",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            
            // Show loading state
            if (uiState.isLoading) {
                item {
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
                }
            }
            
            // Show error state
            else if (uiState.error != null) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        )
                    ) {
                        Text(
                            text = uiState.error ?: "An error occurred",
                            modifier = Modifier.padding(16.dp),
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }
            }
            
            // Show data from database
            else {
                // Group assessments by period (quarter)
                val assessmentsByPeriod = (uiState.historicalAssessments + uiState.currentAssessments)
                    .groupBy { it.assessmentPeriod }
                    .toSortedMap(compareByDescending { 
                        // Sort by quarter: Q4 > Q3 > Q2 > Q1
                        it.substringBefore("-").replace("Q", "").toIntOrNull() ?: 0
                    })
                
                if (assessmentsByPeriod.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    Icons.Default.Assessment,
                                    contentDescription = null,
                                    modifier = Modifier.size(48.dp),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "No assessment data for year $selectedYear",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                } else {
                    assessmentsByPeriod.forEach { (period, assessments) ->
                        item {
                            // Calculate scores for each pillar
                            val scores = ESGPillar.values().map { pillar ->
                                val pillarAssessments = assessments.filter { it.pillar == pillar }
                                if (pillarAssessments.isNotEmpty()) {
                                    val totalScore = pillarAssessments.sumOf { it.totalScore }
                                    val maxScore = pillarAssessments.sumOf { it.maxScore }
                                    if (maxScore > 0) (totalScore.toFloat() / maxScore * 100).toInt() else 0
                                } else {
                                    0
                                }
                            }
                            
                            PeriodCard(
                                period = period,
                                scores = scores
                            )
                        }
                    }
                }
            }
        }
        
        // Year selection dialog
        if (showYearDialog) {
            AlertDialog(
                onDismissRequest = { showYearDialog = false },
                containerColor = Color.White,
                title = {
                    Text(
                        text = "Select Year",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    Column {
                        val availableYears = if (uiState.assessmentYears.isNotEmpty()) {
                            uiState.assessmentYears.sortedDescending()
                        } else {
                            listOf(Calendar.getInstance().get(Calendar.YEAR))
                        }
                        
                        availableYears.forEach { year ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (selectedYear == year) 
                                        colorResource(id = R.color.interactive_primary).copy(alpha = 0.1f) 
                                    else Color.Transparent
                                ),
                                border = BorderStroke(
                                    width = 1.dp,
                                    color = if (selectedYear == year) 
                                        colorResource(id = R.color.interactive_primary) 
                                    else colorResource(id = R.color.border_light)
                                )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable(
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = rememberRipple()
                                        ) {
                                            selectedYear = year
                                            showYearDialog = false
                                        }
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        if (selectedYear == year) Icons.Default.RadioButtonChecked else Icons.Default.RadioButtonUnchecked,
                                        contentDescription = null,
                                        tint = if (selectedYear == year) 
                                            colorResource(id = R.color.interactive_primary) 
                                        else MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(
                                        text = year.toString(),
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = if (selectedYear == year) 
                                            colorResource(id = R.color.interactive_primary) 
                                        else MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showYearDialog = false }) {
                        Text(
                            "Close",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            )
        }
    }
}


@Composable
fun PeriodCard(
    period: String,
    scores: List<Int>
) {
    val completedCount = scores.count { it > 0 }
    val totalCount = scores.size
    val progress = if (totalCount > 0) completedCount.toFloat() / totalCount else 0f
    val averageScore = if (scores.any { it > 0 }) scores.filter { it > 0 }.average().toInt() else 0
    
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
            // Header with period and status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = period,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = if (completedCount == totalCount) "Completed" else "${completedCount}/${totalCount} assessments",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                // Average score or status badge
                Surface(
                    shape = MaterialTheme.shapes.small,
                    color = if (averageScore > 0) 
                        colorResource(id = R.color.interactive_primary).copy(alpha = 0.1f)
                    else 
                        MaterialTheme.colorScheme.surfaceVariant,
                    border = BorderStroke(
                        width = 1.dp,
                        color = if (averageScore > 0) 
                            colorResource(id = R.color.interactive_primary)
                        else 
                            colorResource(id = R.color.border_light)
                    )
                ) {
                    Text(
                        text = if (averageScore > 0) "$averageScore%" else "Not assessed",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Medium,
                        color = if (averageScore > 0) 
                            colorResource(id = R.color.interactive_primary)
                        else 
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Progress bar
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Progress",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "${(progress * 100).toInt()}%",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium,
                        color = colorResource(id = R.color.interactive_primary)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                LinearProgressIndicator(
                    progress = progress,
                    modifier = Modifier.fillMaxWidth(),
                    color = colorResource(id = R.color.interactive_primary),
                    trackColor = colorResource(id = R.color.border_light)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Pillar breakdown
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ESGPillar.values().forEachIndexed { index, pillar ->
                    PillarIndicator(
                        pillar = pillar,
                        score = scores.getOrNull(index) ?: 0
                    )
                }
            }
        }
    }
}

@Composable
fun PillarIndicator(
    pillar: ESGPillar,
    score: Int
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Score display
        Surface(
            shape = androidx.compose.foundation.shape.CircleShape,
            color = if (score > 0) 
                colorResource(id = R.color.interactive_primary).copy(alpha = 0.1f)
            else 
                MaterialTheme.colorScheme.surfaceVariant,
            border = BorderStroke(
                width = 1.dp,
                color = if (score > 0) 
                    colorResource(id = R.color.interactive_primary)
                else 
                    colorResource(id = R.color.border_light)
            ),
            modifier = Modifier.size(48.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (score > 0) "$score%" else "-",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (score > 0) 
                        colorResource(id = R.color.interactive_primary)
                    else 
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Pillar name
        Text(
            text = getPillarName(pillar),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Medium
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
        ESGPillar.ENVIRONMENTAL -> "🌱"
        ESGPillar.SOCIAL -> "👥"
        ESGPillar.GOVERNANCE -> "🏛️"
    }
}
