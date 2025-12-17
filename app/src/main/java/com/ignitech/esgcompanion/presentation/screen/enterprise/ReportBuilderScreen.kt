package com.ignitech.esgcompanion.presentation.screen.enterprise

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ignitech.esgcompanion.domain.entity.*
import com.ignitech.esgcompanion.presentation.viewmodel.ReportBuilderViewModel
import com.ignitech.esgcompanion.utils.AppColors
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportBuilderScreen(
    navController: androidx.navigation.NavHostController,
    template: ReportStandard,
    modifier: Modifier = Modifier,
    viewModel: ReportBuilderViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navigateToSection by viewModel.navigateToSection.collectAsStateWithLifecycle()
    val colors = AppColors()

    LaunchedEffect(template) {
        viewModel.loadTemplate(template)
    }
    
    LaunchedEffect(navigateToSection) {
        navigateToSection?.let { sectionId ->
            navController.navigate("report_section_detail/$sectionId/${template.name}")
            viewModel.onNavigateToSection()
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { 
                    Column {
                        Text(
                            text = "Create ${getTemplateDisplayName(template)} Report",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = getTemplateDescription(template),
                            style = MaterialTheme.typography.bodySmall,
                            color = colors.textSecondary
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { 
                        // Simple pop back - this should work now
                        navController.popBackStack()
                    }) {
                        Icon(
                            Icons.Default.ArrowBack, 
                            contentDescription = "Back",
                            tint = colors.textPrimary
                        )
                    }
                },
                actions = {
                    // Removed preview button from title bar
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
            // Template Info
            item {
                TemplateInfoCard(template = template)
            }

            // Report Sections
            item {
                Text(
                    text = "Report Sections",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = colors.textPrimary
                )
            }
            
            items(getTemplateSections(template)) { section ->
                ReportSectionCard(
                    section = section,
                    onSectionClick = { viewModel.openSection(section.id) },
                    isCompleted = uiState.completedSections.contains(section.id)
                )
            }

            // Additional Requirements
            item {
                Text(
                    text = "Additional Requirements",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = colors.textPrimary
                )
            }
            
            item {
                AdditionalRequirementsCard(template = template)
            }

            // Action Buttons
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = { viewModel.saveDraft() },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color(0xFFFF9800)
                        ),
                        border = BorderStroke(1.dp, Color(0xFFFF9800))
                    ) {
                        Icon(Icons.Default.Save, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Save Draft")
                    }
                    
                    Button(
                        onClick = { viewModel.previewReport() },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.Preview, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Preview")
                    }
                }
            }
        }
    }
}

@Composable
fun TemplateInfoCard(template: ReportStandard) {
    val colors = AppColors()
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = colors.primary.copy(alpha = 0.1f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = getTemplateIcon(template),
                style = MaterialTheme.typography.headlineLarge
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = getTemplateDisplayName(template),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = colors.textPrimary
                )
                
                Text(
                    text = getTemplateDescription(template),
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.textSecondary
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = getTemplateBenefits(template),
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.textSecondary
                )
            }
        }
    }
}

@Composable
fun ReportSectionCard(
    section: ReportSection,
    onSectionClick: () -> Unit,
    isCompleted: Boolean
) {
    val colors = AppColors()
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple()
            ) { onSectionClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isCompleted) colors.success.copy(alpha = 0.1f) else Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (isCompleted) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                contentDescription = null,
                tint = if (isCompleted) colors.success else colors.textSecondary,
                modifier = Modifier.size(24.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = section.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    color = colors.textPrimary
                )
                
                Text(
                    text = section.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.textSecondary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = "Requirements: ${section.requirements.joinToString(", ")}",
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.primary,
                    fontWeight = FontWeight.Medium
                )
            }
            
            Icon(
                Icons.Default.ChevronRight,
                contentDescription = "Open",
                tint = colors.textSecondary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun AdditionalRequirementsCard(template: ReportStandard) {
    val colors = AppColors()
    val requirements = getTemplateAdditionalRequirements(template)
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = colors.backgroundSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Important Notes",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = colors.textPrimary
            )
            
            requirements.forEach { requirement ->
                Row(
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "• ",
                        color = colors.primary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = requirement,
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.textSecondary,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

// Helper functions
private fun getTemplateDisplayName(template: ReportStandard): String {
    return when (template) {
        ReportStandard.GRI -> "GRI Standards"
        ReportStandard.SASB -> "SASB Standards"
        ReportStandard.VN_ENVIRONMENT -> "Chuẩn Việt Nam"
        ReportStandard.CUSTOM -> "Báo cáo tùy chỉnh"
    }
}

private fun getTemplateDescription(template: ReportStandard): String {
    return when (template) {
        ReportStandard.GRI -> "Chuẩn báo cáo bền vững toàn cầu phổ biến nhất"
        ReportStandard.SASB -> "Tiêu chuẩn kế toán bền vững định hướng tài chính"
        ReportStandard.VN_ENVIRONMENT -> "Chuẩn báo cáo phù hợp với quy định Việt Nam"
        ReportStandard.CUSTOM -> "Mẫu báo cáo linh hoạt theo nhu cầu doanh nghiệp"
    }
}

private fun getTemplateIcon(template: ReportStandard): String {
    return when (template) {
        ReportStandard.GRI -> "🌍"
        ReportStandard.SASB -> "💰"
        ReportStandard.VN_ENVIRONMENT -> "🇻🇳"
        ReportStandard.CUSTOM -> "⚙️"
    }
}

private fun getTemplateBenefits(template: ReportStandard): String {
    return when (template) {
        ReportStandard.GRI -> "Phù hợp cho báo cáo toàn diện, minh bạch cao"
        ReportStandard.SASB -> "Tập trung vào tác động tài chính, phù hợp nhà đầu tư"
        ReportStandard.VN_ENVIRONMENT -> "Tuân thủ pháp luật VN, dễ hiểu với stakeholders"
        ReportStandard.CUSTOM -> "Linh hoạt, phù hợp nhu cầu đặc thù"
    }
}

private fun getTemplateSections(template: ReportStandard): List<ReportSection> {
    return when (template) {
        ReportStandard.GRI -> listOf(
            ReportSection("1", "Overview", "Introduction to the enterprise and report", listOf("Company overview", "Report scope", "Methodology")),
            ReportSection("2", "Governance and Strategy", "Governance structure and ESG strategy", listOf("Governance structure", "ESG strategy", "Risk management")),
            ReportSection("3", "Environment", "Environmental impact and measures", listOf("Emissions", "Water", "Waste", "Biodiversity")),
            ReportSection("4", "Social", "Social impact and responsibility", listOf("Labor", "Community", "Human rights", "Safety")),
            ReportSection("5", "Economic", "Economic and financial impact", listOf("Economic performance", "Investment", "Tax", "Local impact"))
        )
        ReportStandard.SASB -> listOf(
            ReportSection("1", "Industry Information", "Industry-specific information", listOf("Industry segments", "ESG risks", "Opportunities")),
            ReportSection("2", "Environment", "Financial environmental impact", listOf("Emissions", "Water", "Waste", "Energy")),
            ReportSection("3", "Social", "Financial social impact", listOf("Labor", "Safety", "Community", "Products")),
            ReportSection("4", "Governance", "Governance and risk", listOf("Governance structure", "Risk management", "Compliance", "Ethics"))
        )
        ReportStandard.VN_ENVIRONMENT -> listOf(
            ReportSection("1", "Enterprise Information", "Basic enterprise information", listOf("Introduction", "Business activities", "Scale")),
            ReportSection("2", "Legal Compliance", "Vietnamese regulatory compliance", listOf("Licenses", "Environmental reports", "Labor safety")),
            ReportSection("3", "Environment", "Environmental protection", listOf("Waste treatment", "Energy efficiency", "Emission reduction")),
            ReportSection("4", "Social", "Social responsibility", listOf("Labor", "Community", "Training", "Welfare"))
        )
        ReportStandard.CUSTOM -> listOf(
            ReportSection("1", "Custom Section 1", "Report section description", listOf("Requirement 1", "Requirement 2")),
            ReportSection("2", "Custom Section 2", "Report section description", listOf("Requirement 1", "Requirement 2"))
        )
    }
}

private fun getTemplateAdditionalRequirements(template: ReportStandard): List<String> {
    return when (template) {
        ReportStandard.GRI -> listOf(
            "Report must be audited by a third party",
            "Quantitative data required for at least 2 years",
            "Executive summary report required for stakeholders",
            "Must be publicly published on company website"
        )
        ReportStandard.SASB -> listOf(
            "Focus on financially material metrics",
            "Data must be verified by auditors",
            "Report in standard SASB format",
            "Annual updates with year-over-year comparison"
        )
        ReportStandard.VN_ENVIRONMENT -> listOf(
            "Comply with Ministry of Natural Resources and Environment regulations",
            "Report in Vietnamese",
            "Confirmation from competent authority required",
            "Publish on the management agency's electronic information portal"
        )
        ReportStandard.CUSTOM -> listOf(
            "Customize according to specific enterprise requirements",
            "Can combine multiple standards",
            "Clearly define metrics and KPIs",
            "Design appropriate for report audience"
        )
    }
}

// Data classes
data class ReportSection(
    val id: String,
    val title: String,
    val description: String,
    val requirements: List<String>
)
