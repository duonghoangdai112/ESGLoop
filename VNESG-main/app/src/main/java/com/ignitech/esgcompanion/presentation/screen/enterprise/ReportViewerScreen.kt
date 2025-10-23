package com.ignitech.esgcompanion.presentation.screen.enterprise

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ignitech.esgcompanion.presentation.viewmodel.ReportViewerViewModel
import com.ignitech.esgcompanion.utils.AppColors
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportViewerScreen(
    reportId: String,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: ReportViewerViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val colors = AppColors()

    LaunchedEffect(reportId) {
        viewModel.loadReport(reportId)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = "View Report",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.shareReport() }) {
                        Icon(
                            Icons.Default.Share,
                            contentDescription = "Chia sẻ"
                        )
                    }
                    IconButton(onClick = { viewModel.exportReport() }) {
                        Icon(
                            Icons.Default.Download,
                            contentDescription = "Export file"
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
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            
            uiState.error != null -> {
                ErrorState(
                    error = uiState.error!!,
                    onRetry = { viewModel.loadReport(reportId) },
                    onBack = { navController.popBackStack() }
                )
            }
            
            uiState.report != null -> {
                ReportContent(
                    report = uiState.report!!,
                    reportSections = uiState.reportSections,
                    onBack = { navController.popBackStack() },
                    modifier = Modifier.padding(paddingValues)
                )
            }
            
            else -> {
                ReportViewerEmptyState(
                    message = "Report not found",
                    onBack = { navController.popBackStack() },
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}

@Composable
fun ReportContent(
    report: com.ignitech.esgcompanion.domain.entity.ReportSummary,
    reportSections: List<com.ignitech.esgcompanion.data.entity.ReportSectionEntity>,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = AppColors()
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(colors.backgroundSurface),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Report Header
        item {
            ReportHeaderCard(
                title = report.title,
                description = report.description,
                status = report.status,
                createdAt = dateFormat.format(Date(report.createdAt)),
                standard = report.standard
            )
        }
        
        // Report Content
        if (reportSections.isNotEmpty()) {
            items(reportSections) { section ->
                ReportSectionCard(
                    section = section
                )
            }
        } else {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
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
                            text = "Report has no content",
                            style = MaterialTheme.typography.titleMedium,
                            color = colors.textSecondary,
                            textAlign = TextAlign.Center
                        )
                        
                        Text(
                            text = "This report has not been created with detailed content yet",
                            style = MaterialTheme.typography.bodyMedium,
                            color = colors.textSecondary,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ReportHeaderCard(
    title: String,
    description: String,
    status: com.ignitech.esgcompanion.domain.entity.ReportStatus,
    createdAt: String,
    standard: com.ignitech.esgcompanion.domain.entity.ReportStandard
) {
    val colors = AppColors()
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = colors.textPrimary,
                    modifier = Modifier.weight(1f)
                )
                
                ReportViewerStatusChip(status = status)
            }
            
            Text(
                text = description,
                style = MaterialTheme.typography.bodyLarge,
                color = colors.textSecondary
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Created: $createdAt",
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.textSecondary
                )
                
                Text(
                    text = "Standard: ${standard.name}",
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.textSecondary
                )
            }
        }
    }
}


@Composable
fun ReportViewerStatusChip(status: com.ignitech.esgcompanion.domain.entity.ReportStatus) {
    val (text, color) = when (status) {
        com.ignitech.esgcompanion.domain.entity.ReportStatus.DRAFT -> "Draft" to Color(0xFFFF9800)
        com.ignitech.esgcompanion.domain.entity.ReportStatus.IN_REVIEW -> "In Review" to Color(0xFF2196F3)
        com.ignitech.esgcompanion.domain.entity.ReportStatus.APPROVED -> "Approved" to Color(0xFF4CAF50)
        com.ignitech.esgcompanion.domain.entity.ReportStatus.PUBLISHED -> "Published" to Color(0xFF2E7D32)
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
fun ErrorState(
    error: String,
    onRetry: () -> Unit,
    onBack: () -> Unit
) {
    val colors = AppColors()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            Icons.Default.Error,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = Color(0xFFF44336)
        )
        
        Text(
            text = "An error occurred",
            style = MaterialTheme.typography.titleMedium,
            color = colors.textPrimary
        )
        
        Text(
            text = error,
            style = MaterialTheme.typography.bodyMedium,
            color = colors.textSecondary,
            textAlign = TextAlign.Center
        )
        
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(onClick = onBack) {
                Text("Back")
            }
            
            Button(onClick = onRetry) {
                Text("Retry")
            }
        }
    }
}

@Composable
fun ReportSectionCard(
    section: com.ignitech.esgcompanion.data.entity.ReportSectionEntity
) {
    val colors = AppColors()
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = section.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = colors.textPrimary,
                    modifier = Modifier.weight(1f)
                )
                
                Surface(
                    color = if (section.isCompleted) Color(0xFF4CAF50) else Color(0xFFFF9800),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = if (section.isCompleted) "Completed" else "Incomplete",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            
            if (section.description.isNotEmpty()) {
                Text(
                    text = section.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.textSecondary
                )
            }
            
            if (section.data.isNotEmpty() && section.data != "{}") {
                Text(
                    text = "Data: ${section.data}",
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.textSecondary,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                )
            }
        }
    }
}

@Composable
fun ReportViewerEmptyState(
    message: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = AppColors()
    
    Column(
        modifier = modifier
            .fillMaxSize()
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
            textAlign = TextAlign.Center
        )
        
        Button(onClick = onBack) {
            Text("Back")
        }
    }
}
