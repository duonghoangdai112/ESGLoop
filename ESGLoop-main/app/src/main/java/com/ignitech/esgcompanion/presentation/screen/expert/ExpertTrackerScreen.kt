package com.ignitech.esgcompanion.presentation.screen.expert

import androidx.compose.animation.*
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
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ignitech.esgcompanion.R
import com.ignitech.esgcompanion.data.entity.*
import com.ignitech.esgcompanion.domain.entity.ESGPillar
import com.ignitech.esgcompanion.presentation.viewmodel.ExpertTrackerViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpertTrackerScreen(
    modifier: Modifier = Modifier,
    viewModel: ExpertTrackerViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadTrackerData()
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("ESG Tracker - Expert") },
                actions = {
                    IconButton(onClick = { viewModel.toggleFilter() }) {
                        Icon(
                            Icons.Default.FilterList, 
                            contentDescription = "Filter",
                            tint = if (uiState.isFilterVisible) colorResource(id = R.color.interactive_primary) else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    IconButton(onClick = { viewModel.showAddActivity() }) {
                        Icon(
                            Icons.Default.Add, 
                            contentDescription = "Add Activity",
                            tint = colorResource(id = R.color.interactive_primary)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Filter Section
            item {
                AnimatedVisibility(
                    visible = uiState.isFilterVisible,
                    enter = slideInVertically() + fadeIn(),
                    exit = slideOutVertically() + fadeOut()
                ) {
                    ExpertFilterSection(
                        selectedPillar = uiState.selectedPillar,
                        selectedStatus = uiState.selectedStatus,
                        selectedPriority = uiState.selectedPriority,
                        onPillarSelected = viewModel::selectPillar,
                        onStatusSelected = viewModel::selectStatus,
                        onPrioritySelected = viewModel::selectPriority
                    )
                }
            }

            // Expert Statistics Cards
            item {
                ExpertStatisticsSection(
                    totalActivities = uiState.totalActivities,
                    completedActivities = uiState.completedActivities,
                    overdueActivities = uiState.overdueActivities,
                    verifiedActivities = uiState.verifiedActivities,
                    kpiCount = uiState.kpiCount,
                    pendingVerifications = uiState.pendingVerifications
                )
            }

            // Pillar Filter Chips
            item {
                PillarFilterSection(
                    selectedPillar = uiState.selectedPillar,
                    onPillarSelected = viewModel::selectPillar
                )
            }

            // Activities List
            if (uiState.activities.isNotEmpty()) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "ESG Activities",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${uiState.activities.size} activities",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                
                items(uiState.activities) { activity ->
                    ExpertActivityCard(
                        activity = activity,
                        onClick = { viewModel.openActivity(activity) },
                        onStatusChange = { viewModel.updateActivityStatus(activity.id, it) },
                        onVerify = { viewModel.verifyActivity(activity.id) }
                    )
                }
            } else {
                item {
                    EmptyState(
                        message = "No ESG activities yet",
                        onAddActivity = { viewModel.showAddActivity() }
                    )
                }
            }

            // KPIs Section
            if (uiState.kpis.isNotEmpty()) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "KPI ESG",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${uiState.kpis.size} KPI",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                
                items(uiState.kpis) { kpi ->
                    ExpertKPICard(
                        kpi = kpi,
                        onClick = { viewModel.openKPI(kpi) },
                        onUpdateValue = { viewModel.updateKPIValue(kpi.id, it) },
                        onVerify = { viewModel.verifyKPI(kpi.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun ExpertStatisticsSection(
    totalActivities: Int,
    completedActivities: Int,
    overdueActivities: Int,
    verifiedActivities: Int,
    kpiCount: Int,
    pendingVerifications: Int
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Row 1: Tổng hoạt động + Đã hoàn thành
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ExpertStatCard(
                title = "Total Activities",
                value = totalActivities.toString(),
                color = colorResource(id = R.color.interactive_primary),
                modifier = Modifier.weight(1f)
            )
            
            ExpertStatCard(
                title = "Completed",
                value = completedActivities.toString(),
                color = Color(0xFF4CAF50),
                modifier = Modifier.weight(1f)
            )
        }
        
        // Row 2: Đã xác minh + Chờ xác minh
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ExpertStatCard(
                title = "Verified",
                value = verifiedActivities.toString(),
                color = Color(0xFF2196F3),
                modifier = Modifier.weight(1f)
            )
            
            ExpertStatCard(
                title = "Pending Verification",
                value = pendingVerifications.toString(),
                color = Color(0xFFFF9800),
                modifier = Modifier.weight(1f)
            )
        }
        
        // Row 3: Quá hạn + KPI
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ExpertStatCard(
                title = "Overdue",
                value = overdueActivities.toString(),
                color = Color(0xFFFF5722),
                modifier = Modifier.weight(1f)
            )
            
            ExpertStatCard(
                title = "KPIs Tracked",
                value = kpiCount.toString(),
                color = Color(0xFF9C27B0),
                modifier = Modifier.weight(1f)
            )
        }
    }
}


@Composable
fun ExpertActivityCard(
    activity: ESGTrackerEntity,
    onClick: () -> Unit,
    onStatusChange: (TrackerStatus) -> Unit,
    onVerify: () -> Unit
) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true)
            ) { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp
        ),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = colorResource(id = R.color.border_card)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = activity.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StatusChip(status = activity.status)
                    if (activity.isVerified) {
                        Icon(
                            Icons.Default.Verified,
                            contentDescription = "Verified",
                            tint = Color(0xFF2196F3),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
            
            Text(
                text = activity.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Planned: ${dateFormat.format(Date(activity.plannedDate))}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Text(
                    text = when (activity.pillar) {
                        ESGPillar.ENVIRONMENTAL -> "🌱 Environmental"
                        ESGPillar.SOCIAL -> "👥 Social"
                        ESGPillar.GOVERNANCE -> "🏛️ Governance"
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            // Priority indicator
            PriorityChip(priority = activity.priority)
            
            // Action buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (activity.status == TrackerStatus.IN_PROGRESS) {
                    Button(
                        onClick = { onStatusChange(TrackerStatus.COMPLETED) },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                    ) {
                        Text("Complete")
                    }
                    
                    OutlinedButton(
                        onClick = { onStatusChange(TrackerStatus.ON_HOLD) },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Pause")
                    }
                }
                
                if (!activity.isVerified && activity.status == TrackerStatus.COMPLETED) {
                    Button(
                        onClick = onVerify,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3))
                    ) {
                        Text("Verify")
                    }
                }
            }
        }
    }
}

@Composable
fun ExpertKPICard(
    kpi: ESGTrackerKPIEntity,
    onClick: () -> Unit,
    onUpdateValue: (Double) -> Unit,
    onVerify: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true)
            ) { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp
        ),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = colorResource(id = R.color.border_card)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = kpi.kpiName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = when (kpi.pillar) {
                            ESGPillar.ENVIRONMENTAL -> "🌱"
                            ESGPillar.SOCIAL -> "👥"
                            ESGPillar.GOVERNANCE -> "🏛️"
                        },
                        style = MaterialTheme.typography.headlineSmall
                    )
                    
                    if (kpi.isActive) {
                        Icon(
                            Icons.Default.Verified,
                            contentDescription = "Verified",
                            tint = Color(0xFF2196F3),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
            
            Text(
                text = kpi.kpiDescription,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Target: ${kpi.targetValue} ${kpi.unit}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    if (kpi.currentValue != null) {
                        Text(
                            text = "Current: ${kpi.currentValue} ${kpi.unit}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { onUpdateValue(kpi.currentValue ?: 0.0) },
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.interactive_primary))
                    ) {
                        Text("Update")
                    }
                    
                    if (!kpi.isActive) {
                        Button(
                            onClick = onVerify,
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3))
                        ) {
                            Text("Verify")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PriorityChip(priority: TrackerPriority) {
    val (text, color) = when (priority) {
        TrackerPriority.LOW -> "Low" to Color(0xFF4CAF50)
        TrackerPriority.MEDIUM -> "Medium" to Color(0xFFFF9800)
        TrackerPriority.HIGH -> "High" to Color(0xFFFF5722)
        TrackerPriority.CRITICAL -> "Critical" to Color(0xFFF44336)
    }
    
    Surface(
        color = color.copy(alpha = 0.1f),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.bodySmall,
            color = color,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun ExpertFilterSection(
    selectedPillar: ESGPillar?,
    selectedStatus: TrackerStatus?,
    selectedPriority: TrackerPriority?,
    onPillarSelected: (ESGPillar?) -> Unit,
    onStatusSelected: (TrackerStatus?) -> Unit,
    onPrioritySelected: (TrackerPriority?) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = colorResource(id = R.color.border_card)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Expert Filters",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            // Status Filter
            Text(
                text = "Status",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(listOf(null) + TrackerStatus.values().toList()) { status ->
                    StatusFilterChip(
                        status = status,
                        isSelected = status == selectedStatus,
                        onClick = { onStatusSelected(status) }
                    )
                }
            }
            
            // Priority Filter
            Text(
                text = "Priority Level",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(listOf(null) + TrackerPriority.values().toList()) { priority ->
                    PriorityFilterChip(
                        priority = priority,
                        isSelected = priority == selectedPriority,
                        onClick = { onPrioritySelected(priority) }
                    )
                }
            }
        }
    }
}

@Composable
fun PriorityFilterChip(
    priority: TrackerPriority?,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberRipple()
        ) { onClick() },
        color = if (isSelected) colorResource(id = R.color.interactive_primary) else Color.Transparent,
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = if (isSelected) colorResource(id = R.color.interactive_primary) else colorResource(id = R.color.border_card)
        )
    ) {
        Text(
            text = when (priority) {
                TrackerPriority.LOW -> "Low"
                TrackerPriority.MEDIUM -> "Medium"
                TrackerPriority.HIGH -> "High"
                TrackerPriority.CRITICAL -> "Critical"
                null -> "All"
            },
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.bodySmall,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
        )
    }
}

// Reuse existing components from EnterpriseTrackerScreen

@Composable
fun PillarFilterSection(
    selectedPillar: ESGPillar?,
    onPillarSelected: (ESGPillar?) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            PillarChip(
                pillar = null,
                isSelected = selectedPillar == null,
                onClick = { onPillarSelected(null) }
            )
        }
        
        items(ESGPillar.values().toList()) { pillar ->
            PillarChip(
                pillar = pillar,
                isSelected = pillar == selectedPillar,
                onClick = { onPillarSelected(pillar) }
            )
        }
    }
}

@Composable
fun PillarChip(
    pillar: ESGPillar?,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberRipple()
        ) { onClick() },
        color = if (isSelected) colorResource(id = R.color.interactive_primary) else Color.Transparent,
        shape = RoundedCornerShape(20.dp),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = if (isSelected) colorResource(id = R.color.interactive_primary) else colorResource(id = R.color.border_card)
        )
    ) {
        Text(
            text = when (pillar) {
                ESGPillar.ENVIRONMENTAL -> "Environmental"
                ESGPillar.SOCIAL -> "Social"
                ESGPillar.GOVERNANCE -> "Governance"
                null -> "All"
            },
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun StatusChip(status: TrackerStatus) {
    val (text, color) = when (status) {
        TrackerStatus.PLANNED -> "Planned" to Color(0xFF2196F3)
        TrackerStatus.IN_PROGRESS -> "In Progress" to Color(0xFFFF9800)
        TrackerStatus.COMPLETED -> "Completed" to Color(0xFF4CAF50)
        TrackerStatus.CANCELLED -> "Cancelled" to Color(0xFFF44336)
        TrackerStatus.ON_HOLD -> "On Hold" to Color(0xFF9E9E9E)
        TrackerStatus.OVERDUE -> "Overdue" to Color(0xFFFF5722)
    }
    
    Surface(
        color = color.copy(alpha = 0.1f),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.bodySmall,
            color = color,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun StatusFilterChip(
    status: TrackerStatus?,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberRipple()
        ) { onClick() },
        color = if (isSelected) colorResource(id = R.color.interactive_primary) else Color.Transparent,
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = if (isSelected) colorResource(id = R.color.interactive_primary) else colorResource(id = R.color.border_card)
        )
    ) {
        Text(
            text = when (status) {
                TrackerStatus.PLANNED -> "Planned"
                TrackerStatus.IN_PROGRESS -> "In Progress"
                TrackerStatus.COMPLETED -> "Completed"
                TrackerStatus.CANCELLED -> "Cancelled"
                TrackerStatus.ON_HOLD -> "On Hold"
                TrackerStatus.OVERDUE -> "Overdue"
                null -> "All"
            },
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.bodySmall,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun EmptyState(
    message: String,
    onAddActivity: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            Icons.Default.Timeline,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Text(
            text = message,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        
        Button(onClick = onAddActivity) {
            Text("Add Your First Activity")
        }
    }
}
