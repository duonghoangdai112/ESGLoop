package com.ignitech.esgcompanion.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignitech.esgcompanion.data.repository.ReportRepository
import com.ignitech.esgcompanion.domain.entity.*
import com.ignitech.esgcompanion.presentation.screen.enterprise.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ReportSectionDetailUiState(
    val isLoading: Boolean = false,
    val section: ReportSection? = null,
    val formFields: List<FormField> = emptyList(),
    val completedFields: Int = 0,
    val error: String? = null
)

@HiltViewModel
class ReportSectionDetailViewModel @Inject constructor(
    private val reportRepository: ReportRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ReportSectionDetailUiState())
    val uiState: StateFlow<ReportSectionDetailUiState> = _uiState.asStateFlow()
    
    fun loadSection(sectionId: String, template: ReportStandard) {
        viewModelScope.launch {
            _uiState.update { 
                it.copy(
                    isLoading = true,
                    error = null
                ) 
            }
            
            try {
                val section = getSectionById(sectionId, template)
                val formFields = generateFormFields(sectionId, template)
                
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        section = section,
                        formFields = formFields,
                        completedFields = formFields.count { field -> field.value.isNotEmpty() }
                    ) 
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Error loading report section"
                    ) 
                }
            }
        }
    }
    
    fun updateFieldValue(fieldId: String, value: String) {
        _uiState.update { currentState ->
            val updatedFields = currentState.formFields.map { field ->
                if (field.id == fieldId) {
                    field.copy(value = value)
                } else {
                    field
                }
            }
            
            currentState.copy(
                formFields = updatedFields,
                completedFields = updatedFields.count { field -> field.value.isNotEmpty() }
            )
        }
    }
    
    fun saveSection() {
        viewModelScope.launch {
            try {
                val currentState = _uiState.value
                val sectionData = currentState.formFields.associate { field ->
                    field.id to field.value
                }
                
                // Get reportId from current template context
                val reportId = getCurrentReportId()
                if (reportId != null && currentState.section != null) {
                    reportRepository.saveSectionData(
                        reportId = reportId,
                        sectionId = currentState.section.id,
                        title = currentState.section.title,
                        description = currentState.section.description,
                        data = sectionData
                    )
                }
                
                _uiState.update { it.copy(error = "Saved successfully") }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        error = e.message ?: "Error saving data"
                    ) 
                }
            }
        }
    }
    
    fun saveDraft() {
        saveSection()
    }
    
    fun completeSection() {
        viewModelScope.launch {
            try {
                val currentState = _uiState.value
                val sectionData = currentState.formFields.associate { field ->
                    field.id to field.value
                }
                
                // Save section data first
                val reportId = getCurrentReportId()
                if (reportId != null && currentState.section != null) {
                    reportRepository.saveSectionData(
                        reportId = reportId,
                        sectionId = currentState.section.id,
                        title = currentState.section.title,
                        description = currentState.section.description,
                        data = sectionData
                    )
                    
                    // Mark section as completed
                    reportRepository.completeSection(reportId, currentState.section.id)
                }
                
                _uiState.update { it.copy(error = "Section completed") }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        error = e.message ?: "Error completing section"
                    ) 
                }
            }
        }
    }
    
    private fun getCurrentReportId(): String? {
        // This should be passed from the parent screen or stored in state
        // For now, we'll generate a temporary ID
        return "temp_report_${System.currentTimeMillis()}"
    }
    
    private fun getSectionById(sectionId: String, template: ReportStandard): ReportSection? {
        return getTemplateSections(template).find { it.id == sectionId }
    }
    
    private fun generateFormFields(sectionId: String, template: ReportStandard): List<FormField> {
        return when (template) {
            ReportStandard.GRI -> getGriFormFields(sectionId)
            ReportStandard.SASB -> getSasbFormFields(sectionId)
            ReportStandard.VN_ENVIRONMENT -> getVnFormFields(sectionId)
            ReportStandard.CUSTOM -> getCustomFormFields(sectionId)
        }
    }
    
    private fun getGriFormFields(sectionId: String): List<FormField> {
        return when (sectionId) {
            "1" -> listOf( // General Information
                FormField("company_name", "Company Name", "Company's full name", "Enter company name", FieldType.TEXT, isRequired = true),
                FormField("reporting_period", "Reporting Period", "Reporting time frame", "Select reporting period", FieldType.SELECT, isRequired = true, options = listOf("2024", "2023", "2022")),
                FormField("reporting_scope", "Reporting Scope", "Description of scope and boundaries", "Describe reporting scope", FieldType.MULTILINE_TEXT, isRequired = true),
                FormField("methodology", "Methodology", "Data collection and processing methods", "Describe methodology", FieldType.MULTILINE_TEXT),
                FormField("stakeholder_engagement", "Stakeholder Consultation", "Was stakeholder consultation conducted?", "", FieldType.CHECKBOX)
            )
            "2" -> listOf( // Governance and Strategy
                FormField("governance_structure", "Governance Structure", "Description of ESG governance structure", "Describe governance structure", FieldType.MULTILINE_TEXT, isRequired = true),
                FormField("esg_strategy", "ESG Strategy", "ESG strategy and objectives", "Describe ESG strategy", FieldType.MULTILINE_TEXT, isRequired = true),
                FormField("risk_management", "Risk Management", "ESG risk management system", "Describe risk management system", FieldType.MULTILINE_TEXT),
                FormField("esg_committee", "ESG Committee", "Is there a dedicated ESG committee?", "", FieldType.CHECKBOX),
                FormField("esg_budget", "ESG Budget", "Budget allocated for ESG (million VND)", "Enter amount", FieldType.NUMBER)
            )
            "3" -> listOf( // Environment
                FormField("ghg_emissions", "Greenhouse Gas Emissions", "Total CO2 emissions (tons)", "Enter amount", FieldType.NUMBER, isRequired = true),
                FormField("energy_consumption", "Energy Consumption", "Total energy consumption (MWh)", "Enter amount", FieldType.NUMBER, isRequired = true),
                FormField("water_consumption", "Water Consumption", "Total water consumption (m³)", "Enter amount", FieldType.NUMBER),
                FormField("waste_generated", "Waste Generated", "Total waste generated (tons)", "Enter amount", FieldType.NUMBER),
                FormField("renewable_energy", "Renewable Energy", "Renewable energy ratio (%)", "Enter percentage", FieldType.NUMBER),
                FormField("environmental_initiatives", "Environmental Initiatives", "Environmental protection initiatives", "Describe initiatives", FieldType.MULTILINE_TEXT)
            )
            "4" -> listOf( // Social
                FormField("total_employees", "Total Employees", "Number of employees", "Enter number", FieldType.NUMBER, isRequired = true),
                FormField("female_employees", "Female Employees", "Percentage of female employees (%)", "Enter percentage", FieldType.NUMBER),
                FormField("training_hours", "Training Hours", "Total employee training hours", "Enter hours", FieldType.NUMBER),
                FormField("safety_incidents", "Safety Incidents", "Number of workplace accidents", "Enter number", FieldType.NUMBER),
                FormField("community_investment", "Community Investment", "Amount invested in community (million VND)", "Enter amount", FieldType.NUMBER),
                FormField("human_rights_policy", "Human Rights Policy", "Is there a human rights policy?", "", FieldType.CHECKBOX)
            )
            "5" -> listOf( // Economic
                FormField("revenue", "Revenue", "Total revenue (million VND)", "Enter revenue", FieldType.NUMBER, isRequired = true),
                FormField("tax_contribution", "Tax Contribution", "Total taxes paid (million VND)", "Enter amount", FieldType.NUMBER),
                FormField("local_procurement", "Local Procurement", "Percentage of procurement from local suppliers (%)", "Enter percentage", FieldType.NUMBER),
                FormField("economic_impact", "Economic Impact", "Description of economic impact on local area", "Describe impact", FieldType.MULTILINE_TEXT)
            )
            else -> emptyList()
        }
    }
    
    private fun getSasbFormFields(sectionId: String): List<FormField> {
        return when (sectionId) {
            "1" -> listOf( // Industry Information
                FormField("industry_sector", "Industry Sector", "Primary industry sector", "Select sector", FieldType.SELECT, isRequired = true, options = listOf("Energy", "Manufacturing", "Financial", "Real Estate", "Technology")),
                FormField("business_model", "Business Model", "Description of business model", "Describe model", FieldType.MULTILINE_TEXT, isRequired = true),
                FormField("key_risks", "Key Risks", "Main ESG risks", "Describe risks", FieldType.MULTILINE_TEXT),
                FormField("market_position", "Market Position", "Competitive market position", "Describe position", FieldType.MULTILINE_TEXT)
            )
            "2" -> listOf( // Environment
                FormField("carbon_intensity", "Carbon Intensity", "Tons CO2/million VND revenue", "Enter data", FieldType.NUMBER, isRequired = true),
                FormField("water_intensity", "Water Intensity", "m³ water/million VND revenue", "Enter data", FieldType.NUMBER),
                FormField("waste_intensity", "Waste Intensity", "Tons waste/million VND revenue", "Enter data", FieldType.NUMBER),
                FormField("renewable_energy_ratio", "Renewable Energy Ratio", "Renewable energy ratio (%)", "Enter ratio", FieldType.NUMBER)
            )
            "3" -> listOf( // Social
                FormField("employee_turnover", "Employee Turnover", "Employee turnover rate (%)", "Enter rate", FieldType.NUMBER, isRequired = true),
                FormField("safety_rate", "Safety Rate", "Safe working hours", "Enter hours", FieldType.NUMBER),
                FormField("diversity_ratio", "Diversity Ratio", "Diversity ratio in leadership (%)", "Enter ratio", FieldType.NUMBER),
                FormField("customer_satisfaction", "Customer Satisfaction", "Customer satisfaction score (1-10)", "Enter score", FieldType.NUMBER)
            )
            "4" -> listOf( // Governance
                FormField("board_independence", "Board Independence", "Independent board members ratio (%)", "Enter ratio", FieldType.NUMBER, isRequired = true),
                FormField("esg_oversight", "ESG Oversight", "Is there ESG oversight at board level?", "", FieldType.CHECKBOX),
                FormField("risk_management_framework", "Risk Management Framework", "Description of risk management framework", "Describe framework", FieldType.MULTILINE_TEXT),
                FormField("compliance_rate", "Compliance Rate", "Regulatory compliance rate (%)", "Enter rate", FieldType.NUMBER)
            )
            else -> emptyList()
        }
    }
    
    private fun getVnFormFields(sectionId: String): List<FormField> {
        return when (sectionId) {
            "1" -> listOf( // Company Information
                FormField("company_name_vn", "Company Name", "Company's full name in Vietnamese", "Enter company name", FieldType.TEXT, isRequired = true),
                FormField("business_license", "Business License", "Business license number", "Enter license number", FieldType.TEXT, isRequired = true),
                FormField("business_scope", "Business Scope", "Description of business operations scope", "Describe scope", FieldType.MULTILINE_TEXT, isRequired = true),
                FormField("establishment_date", "Establishment Date", "Company establishment date", "Select date", FieldType.DATE),
                FormField("headquarters", "Headquarters", "Headquarters address", "Enter address", FieldType.TEXT)
            )
            "2" -> listOf( // Legal Compliance
                FormField("environmental_license", "Environmental License", "Environmental license number", "Enter license number", FieldType.TEXT, isRequired = true),
                FormField("safety_license", "Occupational Safety License", "Safety license number", "Enter license number", FieldType.TEXT),
                FormField("compliance_status", "Compliance Status", "Regulatory compliance status", "Select status", FieldType.SELECT, isRequired = true, options = listOf("Fully Compliant", "Partially Compliant", "Non-Compliant")),
                FormField("violation_records", "Violations", "Any regulatory violations?", "", FieldType.CHECKBOX),
                FormField("penalty_amount", "Penalty Amount", "Total penalty amount (million VND)", "Enter amount", FieldType.NUMBER)
            )
            "3" -> listOf( // Environment
                FormField("waste_treatment", "Waste Treatment", "Waste treatment methods", "Describe methods", FieldType.MULTILINE_TEXT, isRequired = true),
                FormField("emission_control", "Emission Control", "Emission control measures", "Describe measures", FieldType.MULTILINE_TEXT),
                FormField("energy_efficiency", "Energy Efficiency", "Energy saving measures", "Describe measures", FieldType.MULTILINE_TEXT),
                FormField("environmental_investment", "Environmental Investment", "Amount invested in environment (million VND)", "Enter amount", FieldType.NUMBER)
            )
            "4" -> listOf( // Social
                FormField("employee_count", "Employee Count", "Total number of employees", "Enter number", FieldType.NUMBER, isRequired = true),
                FormField("local_employment", "Local Employment", "Local hiring rate (%)", "Enter rate", FieldType.NUMBER),
                FormField("training_programs", "Training Programs", "Employee training programs", "Describe programs", FieldType.MULTILINE_TEXT),
                FormField("community_support", "Community Support", "Community support activities", "Describe activities", FieldType.MULTILINE_TEXT)
            )
            else -> emptyList()
        }
    }
    
    private fun getCustomFormFields(sectionId: String): List<FormField> {
        return when (sectionId) {
            "1" -> listOf(
                FormField("custom_field_1", "Custom Field 1", "Field description", "Enter value", FieldType.TEXT, isRequired = true),
                FormField("custom_field_2", "Custom Field 2", "Field description", "Enter value", FieldType.TEXT)
            )
            "2" -> listOf(
                FormField("custom_field_3", "Custom Field 3", "Field description", "Enter value", FieldType.TEXT, isRequired = true),
                FormField("custom_field_4", "Custom Field 4", "Field description", "Enter value", FieldType.MULTILINE_TEXT)
            )
            else -> emptyList()
        }
    }
    
    private fun getTemplateSections(template: ReportStandard): List<ReportSection> {
        return when (template) {
            ReportStandard.GRI -> listOf(
                ReportSection("1", "General Information", "Company and report introduction", listOf("Company overview", "Reporting scope", "Methodology")),
                ReportSection("2", "Governance and Strategy", "Governance structure and ESG strategy", listOf("Governance structure", "ESG strategy", "Risk management")),
                ReportSection("3", "Environment", "Environmental impact and measures", listOf("Emissions", "Water", "Waste", "Biodiversity")),
                ReportSection("4", "Social", "Social impact and responsibility", listOf("Labor", "Community", "Human rights", "Safety")),
                ReportSection("5", "Economic", "Economic and financial impact", listOf("Economic efficiency", "Investment", "Taxes", "Local impact"))
            )
            ReportStandard.SASB -> listOf(
                ReportSection("1", "Industry Information", "Industry-specific information", listOf("Industry segment", "ESG risks", "Opportunities")),
                ReportSection("2", "Environment", "Environmental financial impact", listOf("Emissions", "Water", "Waste", "Energy")),
                ReportSection("3", "Social", "Social financial impact", listOf("Labor", "Safety", "Community", "Products")),
                ReportSection("4", "Governance", "Governance and risk", listOf("Governance structure", "Risk management", "Compliance", "Ethics"))
            )
            ReportStandard.VN_ENVIRONMENT -> listOf(
                ReportSection("1", "Company Information", "Basic company information", listOf("Introduction", "Business sector", "Scale")),
                ReportSection("2", "Legal Compliance", "Compliance with VN regulations", listOf("Licenses", "Environmental reports", "Occupational safety")),
                ReportSection("3", "Environment", "Environmental protection", listOf("Waste treatment", "Energy saving", "Emission reduction")),
                ReportSection("4", "Social", "Social responsibility", listOf("Labor", "Community", "Training", "Welfare"))
            )
            ReportStandard.CUSTOM -> listOf(
                ReportSection("1", "Custom Section 1", "Report section description", listOf("Requirement 1", "Requirement 2")),
                ReportSection("2", "Custom Section 2", "Report section description", listOf("Requirement 1", "Requirement 2"))
            )
        }
    }
}
