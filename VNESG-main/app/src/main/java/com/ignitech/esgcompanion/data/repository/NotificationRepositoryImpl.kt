package com.ignitech.esgcompanion.data.repository

import com.ignitech.esgcompanion.common.Result
import com.ignitech.esgcompanion.data.local.dao.NotificationDao
import com.ignitech.esgcompanion.data.mapper.NotificationMapper
import com.ignitech.esgcompanion.domain.entity.Notification
import com.ignitech.esgcompanion.domain.entity.NotificationType
import com.ignitech.esgcompanion.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRepositoryImpl @Inject constructor(
    private val notificationDao: NotificationDao
) : NotificationRepository {
    
    override suspend fun getNotificationsByUserId(userId: String): Flow<Result<List<Notification>>> {
        return notificationDao.getNotificationsByUserId(userId).map { entities ->
            try {
                val notifications = entities.map { NotificationMapper.toDomain(it) }
                Result.Success(notifications)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }
    
    override suspend fun getUnreadNotificationsByUserId(userId: String): Flow<Result<List<Notification>>> {
        return notificationDao.getUnreadNotificationsByUserId(userId).map { entities ->
            try {
                val notifications = entities.map { NotificationMapper.toDomain(it) }
                Result.Success(notifications)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }
    
    override suspend fun getNotificationsByUserIdAndType(userId: String, type: NotificationType): Flow<Result<List<Notification>>> {
        return notificationDao.getNotificationsByUserIdAndType(userId, type).map { entities ->
            try {
                val notifications = entities.map { NotificationMapper.toDomain(it) }
                Result.Success(notifications)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }
    
    override suspend fun getUnreadCount(userId: String): Flow<Result<Int>> {
        return notificationDao.getUnreadCount(userId).map { count ->
            try {
                Result.Success(count)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }
    
    override suspend fun getNotificationById(notificationId: String): Result<Notification?> {
        return try {
            val entity = notificationDao.getNotificationById(notificationId)
            val notification = entity?.let { NotificationMapper.toDomain(it) }
            Result.Success(notification)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
    
    override suspend fun insertNotification(notification: Notification): Result<Unit> {
        return try {
            val entity = NotificationMapper.toEntity(notification)
            notificationDao.insertNotification(entity)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
    
    override suspend fun insertNotifications(notifications: List<Notification>): Result<Unit> {
        return try {
            val entities = notifications.map { NotificationMapper.toEntity(it) }
            notificationDao.insertNotifications(entities)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
    
    override suspend fun updateNotification(notification: Notification): Result<Unit> {
        return try {
            val entity = NotificationMapper.toEntity(notification)
            notificationDao.updateNotification(entity)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
    
    override suspend fun markAsRead(notificationId: String): Result<Unit> {
        return try {
            notificationDao.markAsRead(notificationId)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
    
    override suspend fun markAllAsRead(userId: String): Result<Unit> {
        return try {
            notificationDao.markAllAsRead(userId)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
    
    override suspend fun deleteNotification(notificationId: String): Result<Unit> {
        return try {
            notificationDao.deleteNotification(notificationId)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
    
    override suspend fun deleteAllNotificationsForUser(userId: String): Result<Unit> {
        return try {
            notificationDao.deleteAllNotificationsForUser(userId)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
    
    override suspend fun deleteReadNotificationsForUser(userId: String): Result<Unit> {
        return try {
            notificationDao.deleteReadNotificationsForUser(userId)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
    
    override suspend fun deleteOldNotifications(cutoffTime: Long): Result<Unit> {
        return try {
            notificationDao.deleteOldNotifications(cutoffTime)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
