package com.ignitech.esgcompanion.data.repository

import com.ignitech.esgcompanion.data.dao.ESGAssessmentDao
import com.ignitech.esgcompanion.data.dao.ESGTrackerDao
import com.ignitech.esgcompanion.data.dao.LearningHubDao
import com.ignitech.esgcompanion.data.dao.ReportDao
import com.ignitech.esgcompanion.data.entity.*
import com.ignitech.esgcompanion.domain.entity.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReportRepository @Inject constructor(
    private val esgAssessmentDao: ESGAssessmentDao,
    private val esgTrackerDao: ESGTrackerDao,
    private val learningHubDao: LearningHubDao,
    private val reportDao: ReportDao
) {
    
    suspend fun generateReportData(userId: String, reportType: ReportType): ReportData {
        // Tổng hợp dữ liệu từ các nguồn
        val companyInfo = getCompanyInfo(userId)
        val assessmentData = getAssessmentData(userId)
        val trackerData = getTrackerData(userId)
        val learningData = getLearningData(userId)
        
        return ReportData(
            companyInfo = companyInfo,
            assessmentData = assessmentData,
            trackerData = trackerData,
            learningData = learningData
        )
    }
    
    private suspend fun getCompanyInfo(userId: String): CompanyInfo {
        // TODO: Lấy thông tin doanh nghiệp từ user profile
        return CompanyInfo(
            name = "Công ty ABC",
            industry = "Sản xuất",
            size = "Vừa và nhỏ",
            address = "Hà Nội, Việt Nam",
            esgCommitment = "Cam kết phát triển bền vững và trách nhiệm xã hội"
        )
    }
    
    private suspend fun getAssessmentData(userId: String): AssessmentReportData {
        // Lấy dữ liệu đánh giá ESG
        val assessments = esgAssessmentDao.getAssessmentsByUser(userId).first()
        val latestAssessment = assessments.firstOrNull()
        
        val currentScores = if (latestAssessment != null) {
            // TODO: Calculate individual pillar scores from answers
            mapOf(
                ESGPillar.ENVIRONMENTAL to (latestAssessment.totalScore / 3),
                ESGPillar.SOCIAL to (latestAssessment.totalScore / 3),
                ESGPillar.GOVERNANCE to (latestAssessment.totalScore / 3)
            )
        } else {
            mapOf(
                ESGPillar.ENVIRONMENTAL to 0,
                ESGPillar.SOCIAL to 0,
                ESGPillar.GOVERNANCE to 0
            )
        }
        
        val progressChart = assessments.map { assessment ->
            ProgressPoint(
                date = assessment.completedAt ?: 0L,
                environmentalScore = assessment.totalScore / 3,
                socialScore = assessment.totalScore / 3,
                governanceScore = assessment.totalScore / 3,
                totalScore = assessment.totalScore
            )
        }
        
        val improvementSuggestions = generateImprovementSuggestions(currentScores)
        
        return AssessmentReportData(
            currentScores = currentScores,
            improvementSuggestions = improvementSuggestions,
            progressChart = progressChart,
            assessmentHistory = emptyList() // TODO: Implement
        )
    }
    
    private suspend fun getTrackerData(userId: String): TrackerReportData {
        // Lấy dữ liệu từ Tracker
        val activities = esgTrackerDao.getActivitiesByUser(userId).first()
        val kpis = esgTrackerDao.getKPIsByUser(userId).first()
        
        // TODO: Implement data conversion
        return TrackerReportData(
            activities = emptyList(),
            kpis = emptyList(),
            attachments = emptyList(),
            timeline = emptyList()
        )
    }
    
    private suspend fun getLearningData(userId: String): LearningReportData {
        // Lấy dữ liệu từ Learning Hub
        val resources = learningHubDao.getResourcesByUserRole(UserRole.ENTERPRISE).first()
        val progress = learningHubDao.getProgressByUser(userId).first()
        
        // TODO: Implement data conversion
        return LearningReportData(
            completedCourses = emptyList(),
            certificates = emptyList(),
            totalTrainingHours = 0,
            employeeProgress = emptyList()
        )
    }
    
    private fun generateImprovementSuggestions(scores: Map<ESGPillar, Int>): List<String> {
        val suggestions = mutableListOf<String>()
        
        scores.forEach { (pillar, score) ->
            when {
                score < 30 -> {
                    suggestions.add("Cần cải thiện đáng kể trong lĩnh vực ${getPillarName(pillar)}")
                }
                score < 60 -> {
                    suggestions.add("Có thể cải thiện thêm trong lĩnh vực ${getPillarName(pillar)}")
                }
                score >= 80 -> {
                    suggestions.add("Xuất sắc trong lĩnh vực ${getPillarName(pillar)}")
                }
            }
        }
        
        return suggestions
    }
    
    private fun getPillarName(pillar: ESGPillar): String {
        return when (pillar) {
            ESGPillar.ENVIRONMENTAL -> "Môi trường"
            ESGPillar.SOCIAL -> "Xã hội"
            ESGPillar.GOVERNANCE -> "Quản trị"
        }
    }
    
    // Report CRUD operations
    suspend fun createReport(
        title: String,
        description: String,
        standard: ReportStandard,
        template: String,
        userId: String
    ): String {
        val reportId = "report_${System.currentTimeMillis()}"
        val currentTime = System.currentTimeMillis()
        
        val report = ReportEntity(
            id = reportId,
            title = title,
            description = description,
            status = ReportStatus.DRAFT,
            standard = standard,
            template = template,
            createdAt = currentTime,
            updatedAt = currentTime,
            userId = userId,
            isDraft = true
        )
        
        reportDao.insertReport(report)
        return reportId
    }
    
    suspend fun saveReport(report: ReportEntity) {
        reportDao.updateReport(report)
    }
    
    suspend fun saveDraftReport(reportId: String, title: String, description: String) {
        val currentTime = System.currentTimeMillis()
        val report = reportDao.getReportById(reportId)
        if (report != null) {
            val updatedReport = report.copy(
                title = title,
                description = description,
                updatedAt = currentTime,
                isDraft = true
            )
            reportDao.updateReport(updatedReport)
        }
    }
    
    suspend fun publishReport(reportId: String) {
        val currentTime = System.currentTimeMillis()
        val report = reportDao.getReportById(reportId)
        if (report != null) {
            val publishedReport = report.copy(
                status = ReportStatus.PUBLISHED,
                isDraft = false,
                publishedAt = currentTime,
                updatedAt = currentTime
            )
            reportDao.updateReport(publishedReport)
        }
    }
    
    suspend fun deleteReport(reportId: String) {
        reportDao.deleteReportById(reportId)
        reportDao.deleteReportSections(reportId)
        reportDao.deleteReportFields(reportId)
    }
    
    fun getAllReports(userId: String): Flow<List<ReportSummary>> {
        return reportDao.getAllReports(userId).map { reports ->
            reports.map { report ->
                ReportSummary(
                    id = report.id,
                    title = report.title,
                    description = report.description,
                    status = report.status,
                    standard = report.standard,
                    createdAt = report.createdAt
                )
            }
        }
    }
    
    fun getDraftReports(userId: String): Flow<List<ReportSummary>> {
        return reportDao.getDraftReports(userId).map { reports ->
            reports.map { report ->
                ReportSummary(
                    id = report.id,
                    title = report.title,
                    description = report.description,
                    status = report.status,
                    standard = report.standard,
                    createdAt = report.createdAt
                )
            }
        }
    }
    
    fun getPublishedReports(userId: String): Flow<List<ReportSummary>> {
        return reportDao.getPublishedReports(userId).map { reports ->
            reports.map { report ->
                ReportSummary(
                    id = report.id,
                    title = report.title,
                    description = report.description,
                    status = report.status,
                    standard = report.standard,
                    createdAt = report.createdAt
                )
            }
        }
    }
    
    suspend fun getReportById(reportId: String): ReportEntity? {
        return reportDao.getReportById(reportId)
    }
    
    // Report Section operations
    suspend fun saveSectionData(
        reportId: String,
        sectionId: String,
        title: String,
        description: String,
        data: Map<String, String>
    ) {
        val currentTime = System.currentTimeMillis()
        
        // Save section
        val section = ReportSectionEntity(
            id = "${reportId}_${sectionId}",
            reportId = reportId,
            sectionId = sectionId,
            title = title,
            description = description,
            isCompleted = data.values.any { it.isNotEmpty() },
            completedAt = if (data.values.any { it.isNotEmpty() }) currentTime else null,
            data = data.toString(), // Convert to JSON string
            createdAt = currentTime,
            updatedAt = currentTime
        )
        reportDao.insertReportSection(section)
        
        // Save individual fields
        data.forEach { (fieldId, value) ->
            val field = ReportFieldEntity(
                id = "${reportId}_${sectionId}_${fieldId}",
                reportId = reportId,
                sectionId = sectionId,
                fieldId = fieldId,
                fieldType = "TEXT", // Default type
                label = fieldId,
                value = value,
                createdAt = currentTime,
                updatedAt = currentTime
            )
            reportDao.insertReportField(field)
        }
    }
    
    fun getReportSections(reportId: String): Flow<List<ReportSectionEntity>> {
        return reportDao.getReportSections(reportId)
    }
    
    fun getReportFields(reportId: String, sectionId: String): Flow<List<ReportFieldEntity>> {
        return reportDao.getReportFields(reportId, sectionId)
    }
    
    suspend fun completeSection(reportId: String, sectionId: String) {
        val currentTime = System.currentTimeMillis()
        val section = reportDao.getReportSection(reportId, sectionId)
        if (section != null) {
            val completedSection = section.copy(
                isCompleted = true,
                completedAt = currentTime,
                updatedAt = currentTime
            )
            reportDao.updateReportSection(completedSection)
        }
    }
    
    // Statistics
    suspend fun getTotalReportsCount(userId: String): Int {
        return reportDao.getTotalReportsCount(userId)
    }
    
    suspend fun getDraftReportsCount(userId: String): Int {
        return reportDao.getDraftReportsCount(userId)
    }
    
    suspend fun getPublishedReportsCount(userId: String): Int {
        return reportDao.getPublishedReportsCount(userId)
    }
}
