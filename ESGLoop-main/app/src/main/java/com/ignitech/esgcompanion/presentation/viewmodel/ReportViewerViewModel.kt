package com.ignitech.esgcompanion.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ignitech.esgcompanion.data.repository.ReportRepository
import com.ignitech.esgcompanion.domain.entity.ReportSummary
import com.ignitech.esgcompanion.domain.entity.ReportStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ReportViewerUiState(
    val isLoading: Boolean = false,
    val report: ReportSummary? = null,
    val reportSections: List<com.ignitech.esgcompanion.data.entity.ReportSectionEntity> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class ReportViewerViewModel @Inject constructor(
    private val reportRepository: ReportRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ReportViewerUiState())
    val uiState: StateFlow<ReportViewerUiState> = _uiState.asStateFlow()
    
    fun loadReport(reportId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            try {
                val reportEntity = reportRepository.getReportById(reportId)
                if (reportEntity != null) {
                    // Convert ReportEntity to ReportSummary
                    val reportSummary = ReportSummary(
                        id = reportEntity.id,
                        title = reportEntity.title,
                        description = reportEntity.description,
                        status = reportEntity.status,
                        standard = reportEntity.standard,
                        createdAt = reportEntity.createdAt
                    )
                    
                    // Load report sections
                    val sections = reportRepository.getReportSections(reportId).first()
                    
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            report = reportSummary,
                            reportSections = sections,
                            error = null
                        )
                    }
                } else {
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            report = null,
                            reportSections = emptyList(),
                            error = "Báo cáo không tồn tại"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        report = null,
                        reportSections = emptyList(),
                        error = e.message ?: "Có lỗi xảy ra khi tải báo cáo"
                    )
                }
            }
        }
    }
    
    fun shareReport() {
        // TODO: Implement share functionality
        // This could open Android's share intent
    }
    
    fun exportReport() {
        // TODO: Implement export functionality
        // This could export to PDF or other formats
    }
}
