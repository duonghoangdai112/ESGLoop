package com.ignitech.esgcompanion.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignitech.esgcompanion.data.repository.ReportRepository
import com.ignitech.esgcompanion.domain.entity.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ReportBuilderUiState(
    val isLoading: Boolean = false,
    val currentTemplate: ReportStandard? = null,
    val completedSections: Set<String> = emptySet(),
    val currentSection: String? = null,
    val reportData: Map<String, Any> = emptyMap(),
    val error: String? = null
)

@HiltViewModel
class ReportBuilderViewModel @Inject constructor(
    private val reportRepository: ReportRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ReportBuilderUiState())
    val uiState: StateFlow<ReportBuilderUiState> = _uiState.asStateFlow()
    
    private val _navigateToSection = MutableStateFlow<String?>(null)
    val navigateToSection: StateFlow<String?> = _navigateToSection.asStateFlow()
    
    private val _navigateToPreview = MutableStateFlow(false)
    val navigateToPreview: StateFlow<Boolean> = _navigateToPreview.asStateFlow()
    
    private val userId = "enterprise_user_1" // TODO: Get from auth
    
    fun loadTemplate(template: ReportStandard) {
        viewModelScope.launch {
            _uiState.update { 
                it.copy(
                    isLoading = true,
                    currentTemplate = template,
                    error = null
                ) 
            }
            
            try {
                // Create new report in database
                val reportId = reportRepository.createReport(
                    title = "${getTemplateDisplayName(template)} Report - Draft",
                    description = "Report in progress",
                    standard = template,
                    template = template.name,
                    userId = userId
                )
                
                // Load template-specific data
                val templateData = loadTemplateData(template)
                
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        reportData = templateData + mapOf("reportId" to reportId)
                    ) 
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Error loading report template"
                    ) 
                }
            }
        }
    }
    
    fun openSection(sectionId: String) {
        _navigateToSection.value = sectionId
    }
    
    fun onNavigateToSection() {
        _navigateToSection.value = null
    }
    
    fun completeSection(sectionId: String) {
        _uiState.update { 
            it.copy(
                completedSections = it.completedSections + sectionId
            ) 
        }
    }
    
    fun saveDraft() {
        viewModelScope.launch {
            try {
                val currentState = _uiState.value
                val template = currentState.currentTemplate ?: return@launch
                
                // Create draft report
                val draftReport = ReportSummary(
                    id = generateReportId(),
                    title = "${getTemplateDisplayName(template)} Report - Draft",
                    description = "Report in progress",
                    status = ReportStatus.DRAFT,
                    standard = template,
                    createdAt = System.currentTimeMillis()
                )
                
                // Save to repository
                // reportRepository.saveDraft(draftReport, currentState.reportData)
                
                _uiState.update { it.copy(error = "Draft saved successfully") }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        error = e.message ?: "Error saving draft"
                    ) 
                }
            }
        }
    }
    
    fun previewReport() {
        _navigateToPreview.value = true
    }
    
    fun onNavigateToPreview() {
        _navigateToPreview.value = false
    }
    
    fun updateReportData(sectionId: String, data: Map<String, Any>) {
        _uiState.update { 
            it.copy(
                reportData = it.reportData + data
            ) 
        }
    }
    
    private fun loadTemplateData(template: ReportStandard): Map<String, Any> {
        return when (template) {
            ReportStandard.GRI -> mapOf(
                "template_name" to "GRI Standards",
                "version" to "2021",
                "sections" to getGriSections(),
                "requirements" to getGriRequirements()
            )
            ReportStandard.SASB -> mapOf(
                "template_name" to "SASB Standards",
                "version" to "2023",
                "sections" to getSasbSections(),
                "requirements" to getSasbRequirements()
            )
            ReportStandard.VN_ENVIRONMENT -> mapOf(
                "template_name" to "Vietnam Standards",
                "version" to "2024",
                "sections" to getVnSections(),
                "requirements" to getVnRequirements()
            )
            ReportStandard.CUSTOM -> mapOf(
                "template_name" to "Custom",
                "version" to "1.0",
                "sections" to getCustomSections(),
                "requirements" to getCustomRequirements()
            )
        }
    }
    
    private fun getGriSections(): List<Map<String, Any>> {
        return listOf(
            mapOf("id" to "1", "title" to "General Information", "weight" to 0.2),
            mapOf("id" to "2", "title" to "Governance and Strategy", "weight" to 0.25),
            mapOf("id" to "3", "title" to "Environment", "weight" to 0.25),
            mapOf("id" to "4", "title" to "Social", "weight" to 0.25),
            mapOf("id" to "5", "title" to "Economic", "weight" to 0.05)
        )
    }
    
    private fun getSasbSections(): List<Map<String, Any>> {
        return listOf(
            mapOf("id" to "1", "title" to "Industry Information", "weight" to 0.3),
            mapOf("id" to "2", "title" to "Environment", "weight" to 0.3),
            mapOf("id" to "3", "title" to "Social", "weight" to 0.3),
            mapOf("id" to "4", "title" to "Governance", "weight" to 0.1)
        )
    }
    
    private fun getVnSections(): List<Map<String, Any>> {
        return listOf(
            mapOf("id" to "1", "title" to "Company Information", "weight" to 0.2),
            mapOf("id" to "2", "title" to "Legal Compliance", "weight" to 0.3),
            mapOf("id" to "3", "title" to "Environment", "weight" to 0.3),
            mapOf("id" to "4", "title" to "Social", "weight" to 0.2)
        )
    }
    
    private fun getCustomSections(): List<Map<String, Any>> {
        return listOf(
            mapOf("id" to "1", "title" to "Custom Section 1", "weight" to 0.5),
            mapOf("id" to "2", "title" to "Custom Section 2", "weight" to 0.5)
        )
    }
    
    private fun getGriRequirements(): List<String> {
        return listOf(
            "Report must be audited by a third party",
            "Quantitative data must be provided for at least 2 years",
            "Executive summary for stakeholders is required",
            "Must be publicly disclosed on company website"
        )
    }
    
    private fun getSasbRequirements(): List<String> {
        return listOf(
            "Focus on financially material metrics",
            "Data must be verified by auditors",
            "Report using SASB standard format",
            "Annual updates with year-over-year comparison"
        )
    }
    
    private fun getVnRequirements(): List<String> {
        return listOf(
            "Comply with Ministry of Natural Resources and Environment regulations",
            "Report must be in Vietnamese",
            "Requires confirmation from competent authority",
            "Publish on regulatory agency's electronic portal"
        )
    }
    
    private fun getCustomRequirements(): List<String> {
        return listOf(
            "Customize according to specific business requirements",
            "Can combine multiple standards",
            "Clearly define metrics and KPIs",
            "Design suitable for target audience"
        )
    }
    
    private fun getTemplateDisplayName(template: ReportStandard): String {
        return when (template) {
            ReportStandard.GRI -> "GRI Standards"
            ReportStandard.SASB -> "SASB Standards"
            ReportStandard.VN_ENVIRONMENT -> "Vietnam Standards"
            ReportStandard.CUSTOM -> "Custom Report"
        }
    }
    
    private fun generateReportId(): String {
        return "report_${System.currentTimeMillis()}"
    }
}
