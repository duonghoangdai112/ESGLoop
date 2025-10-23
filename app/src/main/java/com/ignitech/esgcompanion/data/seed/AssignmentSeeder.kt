package com.ignitech.esgcompanion.data.seed

import com.ignitech.esgcompanion.data.local.entity.UserEntity
import java.util.*

object AssignmentSeeder {
    
    data class StudentSubmission(
        val id: String,
        val studentId: String,
        val studentName: String,
        val assignmentId: String,
        val content: String,
        val attachments: List<String>,
        val submittedAt: Long,
        val status: SubmissionStatus,
        val grade: Float? = null,
        val feedback: String? = null,
        val gradedAt: Long? = null,
        val gradedBy: String? = null
    )
    
    enum class SubmissionStatus {
        SUBMITTED,
        GRADED,
        LATE
    }
    
    fun getSubmissionsForAssignment(assignmentId: String): List<StudentSubmission> {
        val currentTime = System.currentTimeMillis()
        
        return when (assignmentId) {
            "assign_001" -> listOf(
                StudentSubmission(
                    id = "sub_001_001",
                    studentId = "student_001",
                    studentName = "Nguyễn Văn A",
                    assignmentId = "assign_001",
                    content = "Báo cáo phân tích ESG của công ty VinGroup cho thấy công ty đã có những bước tiến đáng kể trong việc thực hiện các mục tiêu bền vững. Công ty đã đầu tư mạnh mẽ vào năng lượng tái tạo và có kế hoạch giảm 50% lượng khí thải carbon vào năm 2030...",
                    attachments = listOf("vin_group_esg_analysis.pdf", "sustainability_metrics.xlsx"),
                    submittedAt = currentTime - 86400000L * 2, // 2 ngày trước
                    status = SubmissionStatus.SUBMITTED
                ),
                StudentSubmission(
                    id = "sub_001_002",
                    studentId = "student_002",
                    studentName = "Trần Thị B",
                    assignmentId = "assign_001",
                    content = "Phân tích ESG của FPT Corporation cho thấy công ty có điểm mạnh về quản trị doanh nghiệp với cơ cấu hội đồng quản trị đa dạng và minh bạch. Tuy nhiên, vẫn cần cải thiện về mặt xã hội, đặc biệt là trong việc đảm bảo quyền lợi của người lao động...",
                    attachments = listOf("fpt_esg_report.pdf"),
                    submittedAt = currentTime - 86400000L * 1, // 1 ngày trước
                    status = SubmissionStatus.SUBMITTED
                ),
                StudentSubmission(
                    id = "sub_001_003",
                    studentId = "student_003",
                    studentName = "Lê Văn C",
                    assignmentId = "assign_001",
                    content = "Báo cáo về Vingroup cho thấy công ty đã có những nỗ lực đáng kể trong việc phát triển bền vững. Tuy nhiên, vẫn còn một số thách thức cần được giải quyết...",
                    attachments = listOf("vin_analysis.docx"),
                    submittedAt = currentTime - 86400000L * 3, // 3 ngày trước
                    status = SubmissionStatus.SUBMITTED
                ),
                StudentSubmission(
                    id = "sub_001_004",
                    studentId = "student_004",
                    studentName = "Phạm Thị D",
                    assignmentId = "assign_001",
                    content = "Phân tích ESG của Techcombank cho thấy ngân hàng đã có những bước tiến tích cực trong việc tích hợp ESG vào hoạt động kinh doanh...",
                    attachments = listOf("techcombank_esg.pdf", "financial_metrics.xlsx"),
                    submittedAt = currentTime - 86400000L * 1, // 1 ngày trước
                    status = SubmissionStatus.SUBMITTED
                ),
                StudentSubmission(
                    id = "sub_001_005",
                    studentId = "student_005",
                    studentName = "Hoàng Văn E",
                    assignmentId = "assign_001",
                    content = "Báo cáo về Vingroup cho thấy công ty đã có những thành tựu đáng kể trong việc phát triển bền vững...",
                    attachments = listOf("vin_sustainability.pdf"),
                    submittedAt = currentTime - 86400000L * 2, // 2 ngày trước
                    status = SubmissionStatus.SUBMITTED
                )
            )
            "assign_002" -> listOf(
                StudentSubmission(
                    id = "sub_002_001",
                    studentId = "student_006",
                    studentName = "Vũ Thị F",
                    assignmentId = "assign_002",
                    content = "Chiến lược bền vững cho doanh nghiệp Việt Nam cần tập trung vào 3 trụ cột chính: Môi trường, Xã hội và Quản trị...",
                    attachments = listOf("sustainability_strategy.pdf"),
                    submittedAt = currentTime - 86400000L * 5, // 5 ngày trước
                    status = SubmissionStatus.GRADED,
                    grade = 8.5f,
                    feedback = "Bài làm tốt, phân tích sâu sắc. Cần bổ sung thêm về các chỉ số đo lường cụ thể.",
                    gradedAt = currentTime - 86400000L * 2,
                    gradedBy = "instructor_001"
                ),
                StudentSubmission(
                    id = "sub_002_002",
                    studentId = "student_007",
                    studentName = "Đặng Văn G",
                    assignmentId = "assign_002",
                    content = "Thiết kế chiến lược ESG cho các doanh nghiệp vừa và nhỏ tại Việt Nam...",
                    attachments = listOf("sme_esg_strategy.pdf"),
                    submittedAt = currentTime - 86400000L * 4, // 4 ngày trước
                    status = SubmissionStatus.GRADED,
                    grade = 9.0f,
                    feedback = "Xuất sắc! Phân tích chi tiết và có tính thực tiễn cao.",
                    gradedAt = currentTime - 86400000L * 1,
                    gradedBy = "instructor_001"
                ),
                StudentSubmission(
                    id = "sub_002_003",
                    studentId = "student_008",
                    studentName = "Bùi Thị H",
                    assignmentId = "assign_002",
                    content = "Chiến lược phát triển bền vững cho ngành nông nghiệp...",
                    attachments = listOf("agriculture_esg.pdf"),
                    submittedAt = currentTime - 86400000L * 3, // 3 ngày trước
                    status = SubmissionStatus.GRADED,
                    grade = 7.5f,
                    feedback = "Tốt, nhưng cần cụ thể hóa các giải pháp thực hiện.",
                    gradedAt = currentTime - 86400000L * 1,
                    gradedBy = "instructor_001"
                ),
                StudentSubmission(
                    id = "sub_002_004",
                    studentId = "student_009",
                    studentName = "Ngô Thế I",
                    assignmentId = "assign_002",
                    content = "Chiến lược ESG cho các doanh nghiệp công nghệ...",
                    attachments = listOf("tech_esg_strategy.pdf"),
                    submittedAt = currentTime - 86400000L * 2, // 2 ngày trước
                    status = SubmissionStatus.GRADED,
                    grade = 8.0f,
                    feedback = "Bài làm tốt, có tính sáng tạo. Cần bổ sung thêm về các rủi ro ESG.",
                    gradedAt = currentTime - 86400000L * 1,
                    gradedBy = "instructor_001"
                ),
                StudentSubmission(
                    id = "sub_002_005",
                    studentId = "student_010",
                    studentName = "Đinh Thị J",
                    assignmentId = "assign_002",
                    content = "Thiết kế chiến lược bền vững cho ngành du lịch...",
                    attachments = listOf("tourism_esg.pdf"),
                    submittedAt = currentTime - 86400000L * 1, // 1 ngày trước
                    status = SubmissionStatus.GRADED,
                    grade = 8.8f,
                    feedback = "Rất tốt! Phân tích toàn diện và có tính ứng dụng cao.",
                    gradedAt = currentTime - 86400000L * 1,
                    gradedBy = "instructor_001"
                )
            )
            "assign_003" -> listOf(
                StudentSubmission(
                    id = "sub_003_001",
                    studentId = "student_011",
                    studentName = "Trương Văn K",
                    assignmentId = "assign_003",
                    content = "Báo cáo tác động môi trường của dự án xây dựng nhà máy điện mặt trời...",
                    attachments = listOf("solar_plant_eia.pdf"),
                    submittedAt = currentTime - 86400000L * 7, // 7 ngày trước (quá hạn)
                    status = SubmissionStatus.LATE,
                    grade = 6.5f,
                    feedback = "Nộp muộn, chất lượng bài làm trung bình.",
                    gradedAt = currentTime - 86400000L * 2,
                    gradedBy = "instructor_002"
                ),
                StudentSubmission(
                    id = "sub_003_002",
                    studentId = "student_012",
                    studentName = "Lê Thị L",
                    assignmentId = "assign_003",
                    content = "Đánh giá tác động môi trường của dự án khai thác than...",
                    attachments = listOf("coal_mining_eia.pdf"),
                    submittedAt = currentTime - 86400000L * 6, // 6 ngày trước (quá hạn)
                    status = SubmissionStatus.LATE,
                    grade = 7.0f,
                    feedback = "Nộp muộn nhưng chất lượng bài làm khá tốt.",
                    gradedAt = currentTime - 86400000L * 1,
                    gradedBy = "instructor_002"
                ),
                StudentSubmission(
                    id = "sub_003_003",
                    studentId = "student_013",
                    studentName = "Phạm Văn M",
                    assignmentId = "assign_003",
                    content = "Báo cáo EIA cho dự án xây dựng cảng biển...",
                    attachments = listOf("port_eia.pdf"),
                    submittedAt = currentTime - 86400000L * 5, // 5 ngày trước (quá hạn)
                    status = SubmissionStatus.LATE,
                    grade = 6.0f,
                    feedback = "Nộp muộn, cần cải thiện chất lượng phân tích.",
                    gradedAt = currentTime - 86400000L * 1,
                    gradedBy = "instructor_002"
                ),
                StudentSubmission(
                    id = "sub_003_004",
                    studentId = "student_014",
                    studentName = "Hoàng Thị N",
                    assignmentId = "assign_003",
                    content = "Đánh giá tác động môi trường của dự án thủy điện...",
                    attachments = listOf("hydro_eia.pdf"),
                    submittedAt = currentTime - 86400000L * 4, // 4 ngày trước (quá hạn)
                    status = SubmissionStatus.LATE,
                    grade = 7.5f,
                    feedback = "Nộp muộn nhưng phân tích khá chi tiết.",
                    gradedAt = currentTime - 86400000L * 1,
                    gradedBy = "instructor_002"
                ),
                StudentSubmission(
                    id = "sub_003_005",
                    studentId = "student_015",
                    studentName = "Vũ Thế O",
                    assignmentId = "assign_003",
                    content = "Báo cáo EIA cho dự án xây dựng khu công nghiệp...",
                    attachments = listOf("industrial_zone_eia.pdf"),
                    submittedAt = currentTime - 86400000L * 3, // 3 ngày trước (quá hạn)
                    status = SubmissionStatus.LATE,
                    grade = 6.8f,
                    feedback = "Nộp muộn, cần cải thiện phương pháp đánh giá.",
                    gradedAt = currentTime - 86400000L * 1,
                    gradedBy = "instructor_002"
                )
            )
            "assign_005" -> listOf(
                StudentSubmission(
                    id = "sub_005_001",
                    studentId = "student_011",
                    studentName = "Trương Văn K",
                    assignmentId = "assign_005",
                    content = "Bài tập nhóm về quản trị doanh nghiệp bền vững...",
                    attachments = listOf("group_governance.pdf"),
                    submittedAt = currentTime - 86400000L * 3, // 3 ngày trước
                    status = SubmissionStatus.GRADED,
                    grade = 9.2f,
                    feedback = "Xuất sắc! Thể hiện sự hiểu biết sâu sắc về quản trị bền vững.",
                    gradedAt = currentTime - 86400000L * 1,
                    gradedBy = "instructor_002"
                ),
                StudentSubmission(
                    id = "sub_005_002",
                    studentId = "student_012",
                    studentName = "Lê Thị L",
                    assignmentId = "assign_005",
                    content = "Phân tích về vai trò của hội đồng quản trị trong ESG...",
                    attachments = listOf("board_governance.pdf"),
                    submittedAt = currentTime - 86400000L * 2, // 2 ngày trước
                    status = SubmissionStatus.GRADED,
                    grade = 8.7f,
                    feedback = "Rất tốt! Phân tích chi tiết và có tính thực tiễn.",
                    gradedAt = currentTime - 86400000L * 1,
                    gradedBy = "instructor_002"
                ),
                StudentSubmission(
                    id = "sub_005_003",
                    studentId = "student_013",
                    studentName = "Phạm Văn M",
                    assignmentId = "assign_005",
                    content = "Báo cáo về quản trị rủi ro ESG...",
                    attachments = listOf("esg_risk_governance.pdf"),
                    submittedAt = currentTime - 86400000L * 1, // 1 ngày trước
                    status = SubmissionStatus.GRADED,
                    grade = 8.0f,
                    feedback = "Tốt, cần bổ sung thêm về các biện pháp giảm thiểu rủi ro.",
                    gradedAt = currentTime - 86400000L * 1,
                    gradedBy = "instructor_002"
                ),
                StudentSubmission(
                    id = "sub_005_004",
                    studentId = "student_014",
                    studentName = "Hoàng Thị N",
                    assignmentId = "assign_005",
                    content = "Thiết kế hệ thống quản trị ESG cho doanh nghiệp...",
                    attachments = listOf("esg_governance_system.pdf"),
                    submittedAt = currentTime - 86400000L * 2, // 2 ngày trước
                    status = SubmissionStatus.GRADED,
                    grade = 9.5f,
                    feedback = "Xuất sắc! Thiết kế hệ thống hoàn chỉnh và có tính ứng dụng cao.",
                    gradedAt = currentTime - 86400000L * 1,
                    gradedBy = "instructor_002"
                ),
                StudentSubmission(
                    id = "sub_005_005",
                    studentId = "student_015",
                    studentName = "Vũ Thế O",
                    assignmentId = "assign_005",
                    content = "Báo cáo về vai trò của CEO trong quản trị ESG...",
                    attachments = listOf("ceo_esg_governance.pdf"),
                    submittedAt = currentTime - 86400000L * 1, // 1 ngày trước
                    status = SubmissionStatus.GRADED,
                    grade = 8.3f,
                    feedback = "Tốt, cần bổ sung thêm về các case study cụ thể.",
                    gradedAt = currentTime - 86400000L * 1,
                    gradedBy = "instructor_002"
                )
            )
            else -> emptyList()
        }
    }
    
    fun getSubmissionById(submissionId: String): StudentSubmission? {
        return (getSubmissionsForAssignment("assign_001") +
                getSubmissionsForAssignment("assign_002") +
                getSubmissionsForAssignment("assign_003") +
                getSubmissionsForAssignment("assign_005"))
            .find { it.id == submissionId }
    }
    
    fun getGradingStatistics(assignmentId: String): GradingStats {
        val submissions = getSubmissionsForAssignment(assignmentId)
        val gradedSubmissions = submissions.filter { it.status == SubmissionStatus.GRADED }
        val pendingSubmissions = submissions.filter { it.status == SubmissionStatus.SUBMITTED }
        val lateSubmissions = submissions.filter { it.status == SubmissionStatus.LATE }
        
        val averageGrade = if (gradedSubmissions.isNotEmpty()) {
            gradedSubmissions.map { it.grade ?: 0f }.average().toFloat()
        } else 0f
        
        return GradingStats(
            totalSubmissions = submissions.size,
            gradedCount = gradedSubmissions.size,
            pendingCount = pendingSubmissions.size,
            lateCount = lateSubmissions.size,
            averageGrade = averageGrade
        )
    }
}

data class GradingStats(
    val totalSubmissions: Int,
    val gradedCount: Int,
    val pendingCount: Int,
    val lateCount: Int,
    val averageGrade: Float
)
