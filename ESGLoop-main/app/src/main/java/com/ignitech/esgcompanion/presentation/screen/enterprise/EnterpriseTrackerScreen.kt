package com.ignitech.esgcompanion.presentation.screen.enterprise

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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ignitech.esgcompanion.R
import com.ignitech.esgcompanion.data.entity.*
import com.ignitech.esgcompanion.domain.entity.ESGPillar
import com.ignitech.esgcompanion.presentation.viewmodel.EnterpriseTrackerViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterpriseTrackerScreen(
    navController: androidx.navigation.NavHostController,
    modifier: Modifier = Modifier,
    viewModel: EnterpriseTrackerViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navigateToAddActivity by viewModel.navigateToAddActivity.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadTrackerData()
    }
    
    // Handle navigation to AddActivityScreen
    LaunchedEffect(navigateToAddActivity) {
        if (navigateToAddActivity) {
            navController.navigate("add_activity")
            viewModel.onAddActivityNavigated()
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = "ESG Tracker",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
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
            // Filter Section with smooth animation
            item {
                AnimatedVisibility(
                    visible = uiState.isFilterVisible,
                    enter = slideInVertically() + fadeIn(),
                    exit = slideOutVertically() + fadeOut()
                ) {
                    FilterSection(
                        selectedPillar = uiState.selectedPillar,
                        selectedStatus = uiState.selectedStatus,
                        onPillarSelected = viewModel::selectPillar,
                        onStatusSelected = viewModel::selectStatus
                    )
                }
            }

            // Statistics Cards with new design
            item {
                StatisticsSection(
                    totalActivities = uiState.totalActivities,
                    completedActivities = uiState.completedActivities,
                    overdueActivities = uiState.overdueActivities,
                    kpiCount = uiState.kpiCount
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
                    ActivityCard(
                        activity = activity,
                        onClick = { viewModel.openActivity(activity) },
                        onStatusChange = { viewModel.updateActivityStatus(activity.id, it) }
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
                    KPICard(
                        kpi = kpi,
                        onClick = { viewModel.openKPI(kpi) },
                        onUpdateValue = { viewModel.updateKPIValue(kpi.id, it) }
                    )
                }
            }
        }
    }
}

@Composable
fun StatisticsSection(
    totalActivities: Int,
    completedActivities: Int,
    overdueActivities: Int,
    kpiCount: Int
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Row 1: Tổng hoạt động + Hoàn thành
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StatCard(
                title = "Total Activities",
                value = totalActivities.toString(),
                icon = Icons.Default.List,
                color = colorResource(id = R.color.interactive_primary),
                progress = if (totalActivities > 0) completedActivities.toFloat() / totalActivities else 0f,
                modifier = Modifier.weight(1f)
            )
            
            StatCard(
                title = "Completed",
                value = completedActivities.toString(),
                icon = Icons.Default.CheckCircle,
                color = Color(0xFF4CAF50),
                progress = 1f,
                modifier = Modifier.weight(1f)
            )
        }
        
        // Row 2: Quá hạn + KPI
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StatCard(
                title = "Overdue",
                value = overdueActivities.toString(),
                icon = Icons.Default.Warning,
                color = Color(0xFFFF9800),
                progress = 0f,
                modifier = Modifier.weight(1f)
            )
            
            StatCard(
                title = "KPIs Tracked",
                value = kpiCount.toString(),
                icon = Icons.Default.TrendingUp,
                color = Color(0xFF9C27B0),
                progress = 0.7f,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    progress: Float = 0f,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(100.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp
        ),
        shape = RoundedCornerShape(8.dp),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = colorResource(id = R.color.border_card)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Value
            Text(
                text = value,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = color
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            // Title
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

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
fun ActivityCard(
    activity: ESGTrackerEntity,
    onClick: () -> Unit,
    onStatusChange: (TrackerStatus) -> Unit
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
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header with title and status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = activity.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    
                    // Pillar indicator - redesigned without emoji
                    Surface(
                        color = when (activity.pillar) {
                            ESGPillar.ENVIRONMENTAL -> Color(0xFF4CAF50).copy(alpha = 0.1f)
                            ESGPillar.SOCIAL -> Color(0xFF2196F3).copy(alpha = 0.1f)
                            ESGPillar.GOVERNANCE -> Color(0xFF9C27B0).copy(alpha = 0.1f)
                        },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.border(
                            width = 1.dp,
                            color = when (activity.pillar) {
                                ESGPillar.ENVIRONMENTAL -> Color(0xFF4CAF50).copy(alpha = 0.3f)
                                ESGPillar.SOCIAL -> Color(0xFF2196F3).copy(alpha = 0.3f)
                                ESGPillar.GOVERNANCE -> Color(0xFF9C27B0).copy(alpha = 0.3f)
                            },
                            shape = RoundedCornerShape(8.dp)
                        )
                    ) {
                        Text(
                            text = when (activity.pillar) {
                                ESGPillar.ENVIRONMENTAL -> "Môi trường"
                                ESGPillar.SOCIAL -> "Xã hội"
                                ESGPillar.GOVERNANCE -> "Quản trị"
                            },
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = when (activity.pillar) {
                                ESGPillar.ENVIRONMENTAL -> Color(0xFF4CAF50)
                                ESGPillar.SOCIAL -> Color(0xFF2196F3)
                                ESGPillar.GOVERNANCE -> Color(0xFF9C27B0)
                            }
                        )
                    }
                }
                
                StatusChip(status = activity.status)
            }
            
            // Description
            Text(
                text = activity.description,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 20.sp
            )
            
            // Info section with date, priority, and department
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Date and priority row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Ngày dự kiến: ${dateFormat.format(Date(activity.plannedDate))}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Medium
                    )
                    
                    PriorityChip(priority = activity.priority)
                }
                
                // Department and location
                if (!activity.department.isNullOrEmpty() || !activity.location.isNullOrEmpty()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (!activity.department.isNullOrEmpty()) {
                            Text(
                                text = "Phòng ban: ${activity.department}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        
                        if (!activity.location.isNullOrEmpty()) {
                            Text(
                                text = "Địa điểm: ${activity.location}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
            
            // Action buttons - redesigned without icons
            if (activity.status == TrackerStatus.IN_PROGRESS) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = { onStatusChange(TrackerStatus.COMPLETED) },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4CAF50)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Hoàn thành",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    
                    OutlinedButton(
                        onClick = { onStatusChange(TrackerStatus.ON_HOLD) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    ) {
                        Text(
                            text = "Tạm dừng",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StatusChip(status: TrackerStatus) {
    val (text, color) = when (status) {
        TrackerStatus.PLANNED -> "Đã lên kế hoạch" to Color(0xFF2196F3)
        TrackerStatus.IN_PROGRESS -> "Đang thực hiện" to Color(0xFFFF9800)
        TrackerStatus.COMPLETED -> "Đã hoàn thành" to Color(0xFF4CAF50)
        TrackerStatus.CANCELLED -> "Đã hủy" to Color(0xFFF44336)
        TrackerStatus.ON_HOLD -> "Tạm dừng" to Color(0xFF9E9E9E)
        TrackerStatus.OVERDUE -> "Quá hạn" to Color(0xFFFF5722)
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
            style = MaterialTheme.typography.bodyMedium,
            color = color,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun PriorityChip(priority: TrackerPriority) {
    val (text, color) = when (priority) {
        TrackerPriority.LOW -> "Thấp" to Color(0xFF4CAF50)
        TrackerPriority.MEDIUM -> "Trung bình" to Color(0xFFFF9800)
        TrackerPriority.HIGH -> "Cao" to Color(0xFFFF5722)
        TrackerPriority.CRITICAL -> "Khẩn cấp" to Color(0xFFF44336)
    }
    
    Surface(
        color = color.copy(alpha = 0.1f),
        shape = RoundedCornerShape(6.dp),
        modifier = Modifier.border(
            width = 1.dp,
            color = color.copy(alpha = 0.3f),
            shape = RoundedCornerShape(6.dp)
        )
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            style = MaterialTheme.typography.bodySmall,
            color = color,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun KPICard(
    kpi: ESGTrackerKPIEntity,
    onClick: () -> Unit,
    onUpdateValue: (Double) -> Unit
) {
    val progress = if (kpi.currentValue != null && kpi.targetValue > 0) {
        (kpi.currentValue / kpi.targetValue).toFloat().coerceAtMost(1f)
    } else 0f
    
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
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = kpi.kpiName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    
                    // Pillar indicator
                    Surface(
                        color = when (kpi.pillar) {
                            ESGPillar.ENVIRONMENTAL -> Color(0xFF4CAF50).copy(alpha = 0.1f)
                            ESGPillar.SOCIAL -> Color(0xFF2196F3).copy(alpha = 0.1f)
                            ESGPillar.GOVERNANCE -> Color(0xFF9C27B0).copy(alpha = 0.1f)
                        },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = when (kpi.pillar) {
                                ESGPillar.ENVIRONMENTAL -> "🌱 Môi trường"
                                ESGPillar.SOCIAL -> "👥 Xã hội"
                                ESGPillar.GOVERNANCE -> "🏛️ Quản trị"
                            },
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Medium,
                            color = when (kpi.pillar) {
                                ESGPillar.ENVIRONMENTAL -> Color(0xFF4CAF50)
                                ESGPillar.SOCIAL -> Color(0xFF2196F3)
                                ESGPillar.GOVERNANCE -> Color(0xFF9C27B0)
                            }
                        )
                    }
                }
                
                // Progress indicator
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = when (kpi.pillar) {
                                ESGPillar.ENVIRONMENTAL -> Color(0xFF4CAF50).copy(alpha = 0.1f)
                                ESGPillar.SOCIAL -> Color(0xFF2196F3).copy(alpha = 0.1f)
                                ESGPillar.GOVERNANCE -> Color(0xFF9C27B0).copy(alpha = 0.1f)
                            },
                            shape = RoundedCornerShape(24.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${(progress * 100f).toInt()}%",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        color = when (kpi.pillar) {
                            ESGPillar.ENVIRONMENTAL -> Color(0xFF4CAF50)
                            ESGPillar.SOCIAL -> Color(0xFF2196F3)
                            ESGPillar.GOVERNANCE -> Color(0xFF9C27B0)
                        }
                    )
                }
            }
            
            // Description
            Text(
                text = kpi.kpiDescription,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            // Progress bar
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Mục tiêu: ${kpi.targetValue} ${kpi.unit}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    if (kpi.currentValue != null) {
                        Text(
                            text = "Hiện tại: ${kpi.currentValue} ${kpi.unit}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                
                LinearProgressIndicator(
                    progress = progress,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            
            // Update button
            Button(
                onClick = { onUpdateValue(kpi.currentValue ?: 0.0) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = when (kpi.pillar) {
                        ESGPillar.ENVIRONMENTAL -> Color(0xFF4CAF50)
                        ESGPillar.SOCIAL -> Color(0xFF2196F3)
                        ESGPillar.GOVERNANCE -> Color(0xFF9C27B0)
                    }
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    Icons.Default.Update,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Cập nhật giá trị")
            }
        }
    }
}

@Composable
fun FilterSection(
    selectedPillar: ESGPillar?,
    selectedStatus: TrackerStatus?,
    onPillarSelected: (ESGPillar?) -> Unit,
    onStatusSelected: (TrackerStatus?) -> Unit
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
                text = "Bộ lọc",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            // Status Filter
            Text(
                text = "Trạng thái",
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
        }
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
                TrackerStatus.PLANNED -> "Đã lên kế hoạch"
                TrackerStatus.IN_PROGRESS -> "Đang thực hiện"
                TrackerStatus.COMPLETED -> "Đã hoàn thành"
                TrackerStatus.CANCELLED -> "Đã hủy"
                TrackerStatus.ON_HOLD -> "Tạm dừng"
                TrackerStatus.OVERDUE -> "Quá hạn"
                null -> "Tất cả"
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
            Text("Thêm hoạt động đầu tiên")
        }
    }
}
