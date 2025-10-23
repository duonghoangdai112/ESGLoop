package com.ignitech.esgcompanion.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ignitech.esgcompanion.domain.entity.ESGPillar

@Entity(tableName = "esg_tracker_activities")
data class ESGTrackerEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val activityType: ESGTrackerType,
    val pillar: ESGPillar,
    val title: String,
    val description: String,
    val status: TrackerStatus,
    val priority: TrackerPriority = TrackerPriority.MEDIUM,
    val plannedDate: Long, // Ngày dự kiến thực hiện
    val actualDate: Long? = null, // Ngày thực tế thực hiện
    val completedAt: Long? = null, // Ngày hoàn thành
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val createdBy: String = "", // Người tạo
    val assignedTo: String? = null, // Người được giao
    val department: String? = null, // Phòng ban
    val location: String? = null, // Địa điểm
    val budget: Double? = null, // Ngân sách
    val actualCost: Double? = null, // Chi phí thực tế
    val tags: String = "", // JSON string of tags
    val notes: String = "", // Ghi chú
    val isRecurring: Boolean = false, // Hoạt động định kỳ
    val recurringType: RecurringType? = null, // Loại định kỳ
    val parentActivityId: String? = null, // Hoạt động cha (cho sub-tasks)
    val assessmentId: String? = null, // Liên kết với đánh giá ESG
    val kpiId: String? = null, // Liên kết với KPI
    val isPublic: Boolean = true, // Công khai hay riêng tư
    val isVerified: Boolean = false, // Đã xác minh chưa
    val verifiedBy: String? = null, // Người xác minh
    val verifiedAt: Long? = null // Thời gian xác minh
)

@Entity(tableName = "esg_tracker_attachments")
data class ESGTrackerAttachmentEntity(
    @PrimaryKey
    val id: String,
    val activityId: String,
    val fileName: String,
    val filePath: String,
    val fileType: AttachmentType,
    val fileSize: Long,
    val uploadedAt: Long = System.currentTimeMillis(),
    val uploadedBy: String,
    val description: String = "",
    val isPublic: Boolean = true
)

@Entity(tableName = "esg_tracker_kpis")
data class ESGTrackerKPIEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val pillar: ESGPillar,
    val kpiName: String,
    val kpiDescription: String,
    val unit: String, // Đơn vị đo lường
    val targetValue: Double, // Giá trị mục tiêu
    val currentValue: Double? = null, // Giá trị hiện tại
    val baselineValue: Double? = null, // Giá trị cơ sở
    val measurementFrequency: MeasurementFrequency, // Tần suất đo lường
    val lastUpdated: Long? = null, // Lần cập nhật cuối
    val nextUpdate: Long? = null, // Lần cập nhật tiếp theo
    val isActive: Boolean = true,
    val createdAt: Long = System.currentTimeMillis(),
    val createdBy: String = "",
    val department: String? = null,
    val category: String = "", // Phân loại KPI
    val formula: String = "", // Công thức tính toán
    val dataSource: String = "", // Nguồn dữ liệu
    val notes: String = ""
)

@Entity(tableName = "esg_tracker_kpi_values")
data class ESGTrackerKPIValueEntity(
    @PrimaryKey
    val id: String,
    val kpiId: String,
    val value: Double,
    val measuredAt: Long,
    val period: String, // Tháng/Quý/Năm
    val year: Int,
    val quarter: Int? = null,
    val month: Int? = null,
    val week: Int? = null,
    val notes: String = "",
    val uploadedBy: String,
    val attachmentId: String? = null, // File minh chứng
    val isVerified: Boolean = false,
    val verifiedBy: String? = null,
    val verifiedAt: Long? = null
)

@Entity(tableName = "esg_tracker_timeline")
data class ESGTrackerTimelineEntity(
    @PrimaryKey
    val id: String,
    val activityId: String,
    val eventType: TimelineEventType,
    val title: String,
    val description: String,
    val eventDate: Long,
    val createdBy: String,
    val isSystemGenerated: Boolean = false, // Tự động tạo hay thủ công
    val metadata: String = "" // JSON string for additional data
)

enum class ESGTrackerType {
    // Environmental Activities
    ENERGY_EFFICIENCY,      // Tiết kiệm năng lượng
    WASTE_REDUCTION,        // Giảm rác thải
    WATER_CONSERVATION,     // Tiết kiệm nước
    RENEWABLE_ENERGY,       // Năng lượng tái tạo
    CARBON_REDUCTION,       // Giảm phát thải carbon
    POLLUTION_CONTROL,      // Kiểm soát ô nhiễm
    BIODIVERSITY,           // Đa dạng sinh học
    SUSTAINABLE_MATERIALS,  // Vật liệu bền vững
    
    // Social Activities
    EMPLOYEE_TRAINING,      // Đào tạo nhân viên
    SAFETY_IMPROVEMENT,     // Cải thiện an toàn
    COMMUNITY_OUTREACH,     // Hỗ trợ cộng đồng
    DIVERSITY_INCLUSION,    // Đa dạng và hòa nhập
    WORK_LIFE_BALANCE,      // Cân bằng công việc-cuộc sống
    HEALTH_WELLNESS,        // Sức khỏe và phúc lợi
    EDUCATION_SUPPORT,      // Hỗ trợ giáo dục
    LOCAL_DEVELOPMENT,      // Phát triển địa phương
    
    // Governance Activities
    POLICY_UPDATE,          // Cập nhật chính sách
    COMPLIANCE_AUDIT,       // Kiểm toán tuân thủ
    RISK_MANAGEMENT,        // Quản lý rủi ro
    TRANSPARENCY_REPORT,    // Báo cáo minh bạch
    STAKEHOLDER_ENGAGEMENT, // Tương tác với bên liên quan
    ETHICS_TRAINING,        // Đào tạo đạo đức
    BOARD_DIVERSITY,        // Đa dạng hội đồng quản trị
    SUSTAINABILITY_STRATEGY // Chiến lược bền vững
}

enum class TrackerStatus {
    PLANNED,        // Đã lên kế hoạch
    IN_PROGRESS,    // Đang thực hiện
    COMPLETED,      // Đã hoàn thành
    CANCELLED,      // Đã hủy
    ON_HOLD,        // Tạm dừng
    OVERDUE         // Quá hạn
}

enum class TrackerPriority {
    LOW,        // Thấp
    MEDIUM,     // Trung bình
    HIGH,       // Cao
    CRITICAL    // Khẩn cấp
}

enum class RecurringType {
    DAILY,      // Hàng ngày
    WEEKLY,     // Hàng tuần
    MONTHLY,    // Hàng tháng
    QUARTERLY,  // Hàng quý
    YEARLY      // Hàng năm
}

enum class AttachmentType {
    IMAGE,      // Hình ảnh
    DOCUMENT,   // Tài liệu
    VIDEO,      // Video
    AUDIO,      // Âm thanh
    SPREADSHEET, // Bảng tính
    PDF,        // PDF
    OTHER       // Khác
}

enum class MeasurementFrequency {
    DAILY,      // Hàng ngày
    WEEKLY,     // Hàng tuần
    MONTHLY,    // Hàng tháng
    QUARTERLY,  // Hàng quý
    YEARLY      // Hàng năm
}

enum class TimelineEventType {
    CREATED,        // Tạo mới
    UPDATED,        // Cập nhật
    STARTED,        // Bắt đầu
    COMPLETED,      // Hoàn thành
    CANCELLED,      // Hủy bỏ
    ATTACHMENT_ADDED, // Thêm đính kèm
    COMMENT_ADDED,  // Thêm bình luận
    STATUS_CHANGED, // Thay đổi trạng thái
    KPI_UPDATED,    // Cập nhật KPI
    VERIFIED        // Xác minh
}
