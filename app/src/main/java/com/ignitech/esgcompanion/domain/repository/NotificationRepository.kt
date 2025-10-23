package com.ignitech.esgcompanion.domain.repository

import com.ignitech.esgcompanion.common.Result
import com.ignitech.esgcompanion.domain.entity.Notification
import com.ignitech.esgcompanion.domain.entity.NotificationType
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    suspend fun getNotificationsByUserId(userId: String): Flow<Result<List<Notification>>>
    suspend fun getUnreadNotificationsByUserId(userId: String): Flow<Result<List<Notification>>>
    suspend fun getNotificationsByUserIdAndType(userId: String, type: NotificationType): Flow<Result<List<Notification>>>
    suspend fun getUnreadCount(userId: String): Flow<Result<Int>>
    suspend fun getNotificationById(notificationId: String): Result<Notification?>
    suspend fun insertNotification(notification: Notification): Result<Unit>
    suspend fun insertNotifications(notifications: List<Notification>): Result<Unit>
    suspend fun updateNotification(notification: Notification): Result<Unit>
    suspend fun markAsRead(notificationId: String): Result<Unit>
    suspend fun markAllAsRead(userId: String): Result<Unit>
    suspend fun deleteNotification(notificationId: String): Result<Unit>
    suspend fun deleteAllNotificationsForUser(userId: String): Result<Unit>
    suspend fun deleteReadNotificationsForUser(userId: String): Result<Unit>
    suspend fun deleteOldNotifications(cutoffTime: Long): Result<Unit>
}
