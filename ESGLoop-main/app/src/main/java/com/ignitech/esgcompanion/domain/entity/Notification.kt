package com.ignitech.esgcompanion.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Notification(
    val id: String,
    val title: String,
    val message: String,
    val type: NotificationType,
    val userId: String,
    val isRead: Boolean = false,
    val createdAt: Long,
    val actionUrl: String? = null,
    val priority: NotificationPriority = NotificationPriority.NORMAL,
    val metadata: Map<String, String> = emptyMap()
) : Parcelable

enum class NotificationType {
    SYSTEM,         // Thông báo hệ thống
    ASSESSMENT,     // Thông báo đánh giá ESG
    REPORT,         // Thông báo báo cáo
    LEARNING,       // Thông báo học tập
    REMINDER,       // Nhắc nhở
    SECURITY,       // Bảo mật
    PROMOTION,      // Khuyến mãi
    UPDATE          // Cập nhật ứng dụng
}

enum class NotificationPriority {
    LOW,
    NORMAL,
    HIGH,
    URGENT
}
