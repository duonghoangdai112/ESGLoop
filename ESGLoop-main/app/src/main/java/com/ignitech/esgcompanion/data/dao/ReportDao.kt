package com.ignitech.esgcompanion.data.dao

import androidx.room.*
import com.ignitech.esgcompanion.data.entity.ReportEntity
import com.ignitech.esgcompanion.data.entity.ReportSectionEntity
import com.ignitech.esgcompanion.data.entity.ReportFieldEntity
import com.ignitech.esgcompanion.domain.entity.ReportStatus
import com.ignitech.esgcompanion.domain.entity.ReportStandard
import kotlinx.coroutines.flow.Flow

@Dao
interface ReportDao {
    
    // Report operations
    @Query("SELECT * FROM reports WHERE userId = :userId ORDER BY updatedAt DESC")
    fun getAllReports(userId: String): Flow<List<ReportEntity>>
    
    @Query("SELECT * FROM reports WHERE id = :reportId")
    suspend fun getReportById(reportId: String): ReportEntity?
    
    @Query("SELECT * FROM reports WHERE userId = :userId AND status = :status ORDER BY updatedAt DESC")
    fun getReportsByStatus(userId: String, status: ReportStatus): Flow<List<ReportEntity>>
    
    @Query("SELECT * FROM reports WHERE userId = :userId AND standard = :standard ORDER BY updatedAt DESC")
    fun getReportsByStandard(userId: String, standard: ReportStandard): Flow<List<ReportEntity>>
    
    @Query("SELECT * FROM reports WHERE userId = :userId AND isDraft = 1 ORDER BY updatedAt DESC")
    fun getDraftReports(userId: String): Flow<List<ReportEntity>>
    
    @Query("SELECT * FROM reports WHERE userId = :userId AND isDraft = 0 ORDER BY updatedAt DESC")
    fun getPublishedReports(userId: String): Flow<List<ReportEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReport(report: ReportEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReports(reports: List<ReportEntity>)
    
    @Update
    suspend fun updateReport(report: ReportEntity)
    
    @Delete
    suspend fun deleteReport(report: ReportEntity)
    
    @Query("DELETE FROM reports WHERE id = :reportId")
    suspend fun deleteReportById(reportId: String)
    
    // Report Section operations
    @Query("SELECT * FROM report_sections WHERE reportId = :reportId ORDER BY sectionId ASC")
    fun getReportSections(reportId: String): Flow<List<ReportSectionEntity>>
    
    @Query("SELECT * FROM report_sections WHERE reportId = :reportId AND sectionId = :sectionId")
    suspend fun getReportSection(reportId: String, sectionId: String): ReportSectionEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReportSection(section: ReportSectionEntity)
    
    @Update
    suspend fun updateReportSection(section: ReportSectionEntity)
    
    @Delete
    suspend fun deleteReportSection(section: ReportSectionEntity)
    
    @Query("DELETE FROM report_sections WHERE reportId = :reportId")
    suspend fun deleteReportSections(reportId: String)
    
    // Report Field operations
    @Query("SELECT * FROM report_fields WHERE reportId = :reportId AND sectionId = :sectionId ORDER BY fieldId ASC")
    fun getReportFields(reportId: String, sectionId: String): Flow<List<ReportFieldEntity>>
    
    @Query("SELECT * FROM report_fields WHERE reportId = :reportId ORDER BY sectionId ASC, fieldId ASC")
    fun getAllReportFields(reportId: String): Flow<List<ReportFieldEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReportField(field: ReportFieldEntity)
    
    @Update
    suspend fun updateReportField(field: ReportFieldEntity)
    
    @Delete
    suspend fun deleteReportField(field: ReportFieldEntity)
    
    @Query("DELETE FROM report_fields WHERE reportId = :reportId")
    suspend fun deleteReportFields(reportId: String)
    
    @Query("DELETE FROM report_fields WHERE reportId = :reportId AND sectionId = :sectionId")
    suspend fun deleteReportFieldsBySection(reportId: String, sectionId: String)
    
    // Statistics
    @Query("SELECT COUNT(*) FROM reports WHERE userId = :userId")
    suspend fun getTotalReportsCount(userId: String): Int
    
    @Query("SELECT COUNT(*) FROM reports WHERE userId = :userId AND status = :status")
    suspend fun getReportsCountByStatus(userId: String, status: ReportStatus): Int
    
    @Query("SELECT COUNT(*) FROM reports WHERE userId = :userId AND isDraft = 1")
    suspend fun getDraftReportsCount(userId: String): Int
    
    @Query("SELECT COUNT(*) FROM reports WHERE userId = :userId AND isDraft = 0")
    suspend fun getPublishedReportsCount(userId: String): Int
}
