package com.ignitech.esgcompanion.presentation.screen.expert

import androidx.compose.animation.*
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ignitech.esgcompanion.domain.entity.*
import com.ignitech.esgcompanion.presentation.viewmodel.ExpertReportViewModel
import com.ignitech.esgcompanion.utils.AppColors
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpertReportScreen(
    navController: androidx.navigation.NavHostController,
    modifier: Modifier = Modifier,
    viewModel: ExpertReportViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val colors = AppColors()

    LaunchedEffect(Unit) {
        viewModel.loadReportData()
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { 
                    Column {
                        Text(
                            text = "ESG Reports",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Review and approve reports",
                            style = MaterialTheme.typography.bodySmall,
                            color = colors.textSecondary
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.showCreateReport() }) {
                        Icon(
                            Icons.Default.Add, 
                            contentDescription = "Create new report",
                            tint = colors.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colors.backgroundSurface,
                    titleContentColor = colors.textPrimary
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(colors.backgroundSurface),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Quick Stats
            item {
                ExpertQuickStatsSection(
                    totalReports = uiState.totalReports,
                    pendingReviews = uiState.pendingReviews,
                    approvedReports = uiState.approvedReports
                )
            }

            // Filter Section
            item {
                FilterSection(
                    selectedStatus = uiState.selectedStatus,
                    onStatusSelected = viewModel::selectStatus
                )
            }

            // Reports List
            item {
                Text(
                    text = "Reports Pending Review",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = colors.textPrimary
                )
            }
            
            if (uiState.reports.isNotEmpty()) {
                items(uiState.reports) { report ->
                    ExpertReportCard(
                        report = report,
                        onClick = { viewModel.openReport(report.id) },
                        onApprove = { viewModel.approveReport(report.id) },
                        onReject = { viewModel.rejectReport(report.id) }
                    )
                }
            } else {
                item {
                    ExpertReportEmptyState(
                        message = "No reports pending review",
                        onCreateReport = { viewModel.showCreateReport() }
                    )
                }
            }
        }
    }
}

@Composable
fun ExpertQuickStatsSection(
    totalReports: Int,
    pendingReviews: Int,
    approvedReports: Int
) {
    val colors = AppColors()
    
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        StatCard(
            title = "Total Reports",
            value = totalReports.toString(),
            color = colors.primary,
            modifier = Modifier.weight(1f)
        )
        
        StatCard(
            title = "Pending Review",
            value = pendingReviews.toString(),
            color = Color(0xFFFF9800),
            modifier = Modifier.weight(1f)
        )
        
        StatCard(
            title = "Approved",
            value = approvedReports.toString(),
            color = Color(0xFF4CAF50),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(80.dp),
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.1f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = color
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                color = color,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterSection(
    selectedStatus: ReportStatus?,
    onStatusSelected: (ReportStatus?) -> Unit
) {
    val colors = AppColors()
    
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            FilterChip(
                selected = selectedStatus == null,
                onClick = { onStatusSelected(null) },
                label = { Text("All") }
            )
        }
        
        items(ReportStatus.values().toList()) { status ->
            FilterChip(
                selected = status == selectedStatus,
                onClick = { onStatusSelected(status) },
                label = { Text(getStatusText(status)) }
            )
        }
    }
}

@Composable
fun ExpertReportCard(
    report: ExpertReportSummary,
    onClick: () -> Unit,
    onApprove: () -> Unit,
    onReject: () -> Unit
) {
    val colors = AppColors()
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = report.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = colors.textPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    
                    Text(
                        text = "Company: ${report.companyName}",
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.textSecondary
                    )
                }
                
                StatusChip(status = report.status)
            }
            
            Text(
                text = report.description,
                style = MaterialTheme.typography.bodyMedium,
                color = colors.textSecondary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Submitted: ${dateFormat.format(Date(report.submittedAt))}",
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.textSecondary
                )
                
                if (report.status == ReportStatus.IN_REVIEW) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = onApprove,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF4CAF50)
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(
                                Icons.Default.Check,
                                contentDescription = "Approve",
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Approve")
                        }
                        
                        OutlinedButton(
                            onClick = onReject,
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Color(0xFFF44336)
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Reject",
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Reject")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatusChip(status: ReportStatus) {
    val (text, color) = when (status) {
        ReportStatus.DRAFT -> "Draft" to Color(0xFFFF9800)
        ReportStatus.IN_REVIEW -> "Pending Review" to Color(0xFF2196F3)
        ReportStatus.APPROVED -> "Approved" to Color(0xFF4CAF50)
        ReportStatus.PUBLISHED -> "Published" to Color(0xFF2E7D32)
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
fun ExpertReportEmptyState(
    message: String,
    onCreateReport: () -> Unit
) {
    val colors = AppColors()
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            Icons.Default.Assessment,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = colors.textSecondary
        )
        
        Text(
            text = message,
            style = MaterialTheme.typography.titleMedium,
            color = colors.textSecondary,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        
        Button(onClick = onCreateReport) {
            Text("Create Your First Report")
        }
    }
}

private fun getStatusText(status: ReportStatus): String {
    return when (status) {
        ReportStatus.DRAFT -> "Draft"
        ReportStatus.IN_REVIEW -> "Pending Review"
        ReportStatus.APPROVED -> "Approved"
        ReportStatus.PUBLISHED -> "Published"
    }
}

