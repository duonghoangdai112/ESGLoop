package com.ignitech.esgcompanion.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignitech.esgcompanion.data.repository.ReportRepository
import com.ignitech.esgcompanion.domain.entity.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

data class ExpertReportUiState(
    val isLoading: Boolean = false,
    val totalReports: Int = 0,
    val pendingReviews: Int = 0,
    val approvedReports: Int = 0,
    val reports: List<ExpertReportSummary> = emptyList(),
    val selectedStatus: ReportStatus? = null,
    val error: String? = null
)

@HiltViewModel
class ExpertReportViewModel @Inject constructor(
    private val reportRepository: ReportRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ExpertReportUiState())
    val uiState: StateFlow<ExpertReportUiState> = _uiState.asStateFlow()
    
    private val userId = "expert_user_1" // TODO: Get from auth
    
    fun loadReportData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            try {
                // Load mock data for now
                val mockReports = generateMockReports()
                
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        totalReports = mockReports.size,
                        pendingReviews = mockReports.count { it.status == ReportStatus.IN_REVIEW },
                        approvedReports = mockReports.count { it.status == ReportStatus.APPROVED },
                        reports = mockReports
                    )
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Có lỗi xảy ra khi tải dữ liệu"
                    )
                }
            }
        }
    }
    
    fun selectStatus(status: ReportStatus?) {
        _uiState.update { it.copy(selectedStatus = status) }
        // TODO: Filter reports by status
    }
    
    fun showCreateReport() {
        // TODO: Navigate to report builder
    }
    
    fun openReport(reportId: String) {
        // TODO: Navigate to report viewer
    }
    
    fun approveReport(reportId: String) {
        viewModelScope.launch {
            // TODO: Implement approve functionality
            _uiState.update { currentState ->
                currentState.copy(
                    reports = currentState.reports.map { report ->
                        if (report.id == reportId) {
                            report.copy(status = ReportStatus.APPROVED)
                        } else report
                    }
                )
            }
        }
    }
    
    fun rejectReport(reportId: String) {
        viewModelScope.launch {
            // TODO: Implement reject functionality
            _uiState.update { currentState ->
                currentState.copy(
                    reports = currentState.reports.map { report ->
                        if (report.id == reportId) {
                            report.copy(status = ReportStatus.DRAFT)
                        } else report
                    }
                )
            }
        }
    }
    
    private fun generateMockReports(): List<ExpertReportSummary> {
        return listOf(
            ExpertReportSummary(
                id = "1",
                title = "Báo cáo ESG Quý 1/2024",
                description = "Báo cáo tổng hợp kết quả ESG quý 1",
                companyName = "Công ty ABC",
                status = ReportStatus.IN_REVIEW,
                standard = ReportStandard.GRI,
                submittedAt = System.currentTimeMillis() - 86400000L * 2
            ),
            ExpertReportSummary(
                id = "2",
                title = "Báo cáo ESG Quý 2/2024",
                description = "Báo cáo tổng hợp kết quả ESG quý 2",
                companyName = "Công ty XYZ",
                status = ReportStatus.IN_REVIEW,
                standard = ReportStandard.SASB,
                submittedAt = System.currentTimeMillis() - 86400000L * 1
            ),
            ExpertReportSummary(
                id = "3",
                title = "Báo cáo ESG Năm 2023",
                description = "Báo cáo tổng hợp kết quả ESG năm 2023",
                companyName = "Công ty DEF",
                status = ReportStatus.APPROVED,
                standard = ReportStandard.VN_ENVIRONMENT,
                submittedAt = System.currentTimeMillis() - 86400000L * 5
            )
        )
    }
}
