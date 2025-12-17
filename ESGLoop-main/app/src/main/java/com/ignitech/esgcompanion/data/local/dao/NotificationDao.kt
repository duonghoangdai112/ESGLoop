package com.ignitech.esgcompanion.data.local.dao

import androidx.room.*
import com.ignitech.esgcompanion.data.local.entity.NotificationEntity
import com.ignitech.esgcompanion.domain.entity.NotificationType
import com.ignitech.esgcompanion.domain.entity.NotificationPriority
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {
    
    @Query("SELECT * FROM notifications WHERE userId = :userId ORDER BY createdAt DESC")
    fun getNotificationsByUserId(userId: String): Flow<List<NotificationEntity>>
    
    @Query("SELECT * FROM notifications WHERE userId = :userId AND isRead = 0 ORDER BY createdAt DESC")
    fun getUnreadNotificationsByUserId(userId: String): Flow<List<NotificationEntity>>
    
    @Query("SELECT * FROM notifications WHERE userId = :userId AND type = :type ORDER BY createdAt DESC")
    fun getNotificationsByUserIdAndType(userId: String, type: NotificationType): Flow<List<NotificationEntity>>
    
    @Query("SELECT COUNT(*) FROM notifications WHERE userId = :userId AND isRead = 0")
    fun getUnreadCount(userId: String): Flow<Int>
    
    @Query("SELECT * FROM notifications WHERE id = :notificationId")
    suspend fun getNotificationById(notificationId: String): NotificationEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: NotificationEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotifications(notifications: List<NotificationEntity>)
    
    @Update
    suspend fun updateNotification(notification: NotificationEntity)
    
    @Query("UPDATE notifications SET isRead = 1 WHERE id = :notificationId")
    suspend fun markAsRead(notificationId: String)
    
    @Query("UPDATE notifications SET isRead = 1 WHERE userId = :userId")
    suspend fun markAllAsRead(userId: String)
    
    @Query("DELETE FROM notifications WHERE id = :notificationId")
    suspend fun deleteNotification(notificationId: String)
    
    @Query("DELETE FROM notifications WHERE userId = :userId")
    suspend fun deleteAllNotificationsForUser(userId: String)
    
    @Query("DELETE FROM notifications WHERE userId = :userId AND isRead = 1")
    suspend fun deleteReadNotificationsForUser(userId: String)
    
    @Query("DELETE FROM notifications WHERE createdAt < :cutoffTime")
    suspend fun deleteOldNotifications(cutoffTime: Long)
}
