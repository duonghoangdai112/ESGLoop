package com.ignitech.esgcompanion.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ignitech.esgcompanion.domain.entity.NotificationPriority
import com.ignitech.esgcompanion.domain.entity.NotificationType

@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val message: String,
    val type: NotificationType,
    val userId: String,
    val isRead: Boolean = false,
    val createdAt: Long,
    val actionUrl: String? = null,
    val priority: NotificationPriority = NotificationPriority.NORMAL,
    val metadata: String = "{}" // JSON string for metadata
)
