package com.ignitech.esgcompanion.data.dao

import androidx.room.*
import com.ignitech.esgcompanion.data.entity.*
import com.ignitech.esgcompanion.domain.entity.ESGPillar
import kotlinx.coroutines.flow.Flow

@Dao
interface ESGTrackerDao {
    
    // Activities
    @Query("SELECT * FROM esg_tracker_activities WHERE userId = :userId ORDER BY plannedDate DESC")
    fun getActivitiesByUser(userId: String): Flow<List<ESGTrackerEntity>>
    
    @Query("SELECT * FROM esg_tracker_activities WHERE userId = :userId AND pillar = :pillar ORDER BY plannedDate DESC")
    fun getActivitiesByUserAndPillar(userId: String, pillar: ESGPillar): Flow<List<ESGTrackerEntity>>
    
    @Query("SELECT * FROM esg_tracker_activities WHERE userId = :userId AND status = :status ORDER BY plannedDate DESC")
    fun getActivitiesByUserAndStatus(userId: String, status: TrackerStatus): Flow<List<ESGTrackerEntity>>
    
    @Query("SELECT * FROM esg_tracker_activities WHERE userId = :userId AND activityType = :activityType ORDER BY plannedDate DESC")
    fun getActivitiesByUserAndType(userId: String, activityType: ESGTrackerType): Flow<List<ESGTrackerEntity>>
    
    @Query("SELECT * FROM esg_tracker_activities WHERE id = :activityId")
    suspend fun getActivityById(activityId: String): ESGTrackerEntity?
    
    @Query("SELECT * FROM esg_tracker_activities WHERE userId = :userId AND plannedDate BETWEEN :startDate AND :endDate ORDER BY plannedDate ASC")
    fun getActivitiesByDateRange(userId: String, startDate: Long, endDate: Long): Flow<List<ESGTrackerEntity>>
    
    @Query("SELECT * FROM esg_tracker_activities WHERE userId = :userId AND status = 'OVERDUE' ORDER BY plannedDate ASC")
    fun getOverdueActivities(userId: String): Flow<List<ESGTrackerEntity>>
    
    @Query("SELECT * FROM esg_tracker_activities WHERE userId = :userId AND isRecurring = 1 ORDER BY plannedDate DESC")
    fun getRecurringActivities(userId: String): Flow<List<ESGTrackerEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivity(activity: ESGTrackerEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivities(activities: List<ESGTrackerEntity>)
    
    @Update
    suspend fun updateActivity(activity: ESGTrackerEntity)
    
    @Delete
    suspend fun deleteActivity(activity: ESGTrackerEntity)
    
    @Query("UPDATE esg_tracker_activities SET status = :status, updatedAt = :updatedAt WHERE id = :activityId")
    suspend fun updateActivityStatus(activityId: String, status: TrackerStatus, updatedAt: Long)
    
    @Query("UPDATE esg_tracker_activities SET actualDate = :actualDate, completedAt = :completedAt, status = 'COMPLETED', updatedAt = :updatedAt WHERE id = :activityId")
    suspend fun markActivityCompleted(activityId: String, actualDate: Long, completedAt: Long, updatedAt: Long)
    
    // Attachments
    @Query("SELECT * FROM esg_tracker_attachments WHERE activityId = :activityId ORDER BY uploadedAt DESC")
    fun getAttachmentsByActivity(activityId: String): Flow<List<ESGTrackerAttachmentEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttachment(attachment: ESGTrackerAttachmentEntity)
    
    @Delete
    suspend fun deleteAttachment(attachment: ESGTrackerAttachmentEntity)
    
    // KPIs
    @Query("SELECT * FROM esg_tracker_kpis WHERE userId = :userId ORDER BY createdAt DESC")
    fun getKPIsByUser(userId: String): Flow<List<ESGTrackerKPIEntity>>
    
    @Query("SELECT * FROM esg_tracker_kpis WHERE userId = :userId AND pillar = :pillar ORDER BY createdAt DESC")
    fun getKPIsByUserAndPillar(userId: String, pillar: ESGPillar): Flow<List<ESGTrackerKPIEntity>>
    
    @Query("SELECT * FROM esg_tracker_kpis WHERE id = :kpiId")
    suspend fun getKPIById(kpiId: String): ESGTrackerKPIEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKPI(kpi: ESGTrackerKPIEntity)
    
    @Update
    suspend fun updateKPI(kpi: ESGTrackerKPIEntity)
    
    @Delete
    suspend fun deleteKPI(kpi: ESGTrackerKPIEntity)
    
    // KPI Values
    @Query("SELECT * FROM esg_tracker_kpi_values WHERE kpiId = :kpiId ORDER BY measuredAt DESC")
    fun getKPIValuesByKPI(kpiId: String): Flow<List<ESGTrackerKPIValueEntity>>
    
    @Query("SELECT * FROM esg_tracker_kpi_values WHERE kpiId = :kpiId AND year = :year ORDER BY measuredAt ASC")
    fun getKPIValuesByKPIAndYear(kpiId: String, year: Int): Flow<List<ESGTrackerKPIValueEntity>>
    
    @Query("SELECT * FROM esg_tracker_kpi_values WHERE kpiId = :kpiId AND year = :year AND quarter = :quarter ORDER BY measuredAt ASC")
    fun getKPIValuesByKPIAndQuarter(kpiId: String, year: Int, quarter: Int): Flow<List<ESGTrackerKPIValueEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKPIValue(kpiValue: ESGTrackerKPIValueEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKPIValues(kpiValues: List<ESGTrackerKPIValueEntity>)
    
    @Update
    suspend fun updateKPIValue(kpiValue: ESGTrackerKPIValueEntity)
    
    @Delete
    suspend fun deleteKPIValue(kpiValue: ESGTrackerKPIValueEntity)
    
    // Timeline
    @Query("SELECT * FROM esg_tracker_timeline WHERE activityId = :activityId ORDER BY eventDate DESC")
    fun getTimelineByActivity(activityId: String): Flow<List<ESGTrackerTimelineEntity>>
    
    @Query("SELECT * FROM esg_tracker_timeline WHERE activityId IN (SELECT id FROM esg_tracker_activities WHERE userId = :userId) ORDER BY eventDate DESC LIMIT :limit")
    fun getTimelineByUser(userId: String, limit: Int = 50): Flow<List<ESGTrackerTimelineEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTimelineEvent(timelineEvent: ESGTrackerTimelineEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTimelineEvents(timelineEvents: List<ESGTrackerTimelineEntity>)
    
    // Statistics
    @Query("SELECT COUNT(*) FROM esg_tracker_activities WHERE userId = :userId")
    suspend fun getActivityCountByUser(userId: String): Int
    
    @Query("SELECT COUNT(*) FROM esg_tracker_activities WHERE userId = :userId AND status = 'COMPLETED'")
    suspend fun getCompletedActivityCountByUser(userId: String): Int
    
    @Query("SELECT COUNT(*) FROM esg_tracker_activities WHERE userId = :userId AND status = 'OVERDUE'")
    suspend fun getOverdueActivityCountByUser(userId: String): Int
    
    @Query("SELECT COUNT(*) FROM esg_tracker_activities WHERE userId = :userId AND pillar = :pillar")
    suspend fun getActivityCountByUserAndPillar(userId: String, pillar: ESGPillar): Int
    
    @Query("SELECT COUNT(*) FROM esg_tracker_kpis WHERE userId = :userId")
    suspend fun getKPICountByUser(userId: String): Int
    
    @Query("SELECT COUNT(*) FROM esg_tracker_kpis WHERE userId = :userId AND pillar = :pillar")
    suspend fun getKPICountByUserAndPillar(userId: String, pillar: ESGPillar): Int
    
    // Search
    @Query("SELECT * FROM esg_tracker_activities WHERE userId = :userId AND (title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%' OR tags LIKE '%' || :query || '%') ORDER BY plannedDate DESC")
    fun searchActivities(userId: String, query: String): Flow<List<ESGTrackerEntity>>
    
    @Query("SELECT * FROM esg_tracker_kpis WHERE userId = :userId AND (kpiName LIKE '%' || :query || '%' OR kpiDescription LIKE '%' || :query || '%') ORDER BY createdAt DESC")
    fun searchKPIs(userId: String, query: String): Flow<List<ESGTrackerKPIEntity>>
}
