package com.ignitech.esgcompanion.domain.entity

import com.ignitech.esgcompanion.data.entity.*

data class ReportData(
    val companyInfo: CompanyInfo,
    val assessmentData: AssessmentReportData,
    val trackerData: TrackerReportData,
    val learningData: LearningReportData,
    val customData: CustomReportData? = null
)

data class CompanyInfo(
    val name: String,
    val industry: String,
    val size: String,
    val address: String,
    val logo: String? = null,
    val esgCommitment: String? = null
)

data class AssessmentReportData(
    val currentScores: Map<ESGPillar, Int>,
    val previousScores: Map<ESGPillar, Int>? = null,
    val improvementSuggestions: List<String>,
    val progressChart: List<ProgressPoint>,
    val assessmentHistory: List<AssessmentResult>
)

data class ProgressPoint(
    val date: Long,
    val environmentalScore: Int,
    val socialScore: Int,
    val governanceScore: Int,
    val totalScore: Int
)

data class AssessmentResult(
    val date: Long,
    val pillar: ESGPillar,
    val score: Int,
    val maxScore: Int,
    val percentage: Float
)

data class TrackerReportData(
    val activities: List<ActivityEvidence>,
    val kpis: List<KPIValue>,
    val attachments: List<EvidenceFile>,
    val timeline: List<TimelineEvent>
)

data class ActivityEvidence(
    val title: String,
    val description: String,
    val pillar: ESGPillar,
    val status: TrackerStatus,
    val plannedDate: Long,
    val completedDate: Long?,
    val attachments: List<String>,
    val notes: String?
)

data class KPIValue(
    val name: String,
    val description: String,
    val pillar: ESGPillar,
    val currentValue: Double,
    val targetValue: Double,
    val unit: String,
    val lastMeasured: Long,
    val isVerified: Boolean
)

data class EvidenceFile(
    val fileName: String,
    val filePath: String,
    val fileType: String,
    val uploadedAt: Long,
    val description: String? = null
)

data class TimelineEvent(
    val eventType: String,
    val description: String,
    val timestamp: Long,
    val pillar: ESGPillar
)

data class LearningReportData(
    val completedCourses: List<CourseProgress>,
    val certificates: List<Certificate>,
    val totalTrainingHours: Int,
    val employeeProgress: List<EmployeeProgress>
)

data class CourseProgress(
    val courseName: String,
    val category: String,
    val completedAt: Long,
    val score: Int? = null,
    val duration: Int // in minutes
)

data class Certificate(
    val name: String,
    val issuer: String,
    val issuedAt: Long,
    val expiryDate: Long? = null,
    val filePath: String? = null
)

data class EmployeeProgress(
    val employeeName: String,
    val department: String,
    val coursesCompleted: Int,
    val certificatesEarned: Int,
    val totalHours: Int
)

data class CustomReportData(
    val strategy: String? = null,
    val riskAssessment: String? = null,
    val impactSummary: String? = null,
    val additionalNotes: String? = null
)

enum class ReportType {
    QUARTERLY,
    ANNUAL,
    CUSTOM
}

enum class ReportStatus {
    DRAFT,
    IN_REVIEW,
    APPROVED,
    PUBLISHED
}

enum class ReportStandard {
    GRI,
    SASB,
    VN_ENVIRONMENT,
    CUSTOM
}
