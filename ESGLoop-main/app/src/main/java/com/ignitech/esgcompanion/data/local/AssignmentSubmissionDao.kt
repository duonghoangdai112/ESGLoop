package com.ignitech.esgcompanion.data.local

import androidx.room.*
import com.ignitech.esgcompanion.data.entity.AssignmentSubmissionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AssignmentSubmissionDao {
    
    @Query("SELECT * FROM assignment_submissions WHERE assignmentId = :assignmentId AND studentId = :studentId ORDER BY submittedAt DESC")
    fun getSubmissionsByAssignmentAndStudent(assignmentId: String, studentId: String): Flow<List<AssignmentSubmissionEntity>>
    
    @Query("SELECT * FROM assignment_submissions WHERE studentId = :studentId ORDER BY submittedAt DESC")
    fun getSubmissionsByStudent(studentId: String): Flow<List<AssignmentSubmissionEntity>>
    
    @Query("SELECT * FROM assignment_submissions WHERE assignmentId = :assignmentId ORDER BY submittedAt DESC")
    fun getSubmissionsByAssignment(assignmentId: String): Flow<List<AssignmentSubmissionEntity>>
    
    @Query("SELECT * FROM assignment_submissions WHERE id = :submissionId")
    suspend fun getSubmissionById(submissionId: String): AssignmentSubmissionEntity?
    
    @Query("SELECT * FROM assignment_submissions WHERE assignmentId = :assignmentId AND studentId = :studentId ORDER BY submittedAt DESC LIMIT 1")
    suspend fun getLatestSubmission(assignmentId: String, studentId: String): AssignmentSubmissionEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubmission(submission: AssignmentSubmissionEntity)
    
    @Update
    suspend fun updateSubmission(submission: AssignmentSubmissionEntity)
    
    @Delete
    suspend fun deleteSubmission(submission: AssignmentSubmissionEntity)
    
    @Query("DELETE FROM assignment_submissions WHERE assignmentId = :assignmentId AND studentId = :studentId")
    suspend fun deleteSubmissionsByAssignmentAndStudent(assignmentId: String, studentId: String)
    
    @Query("SELECT COUNT(*) FROM assignment_submissions WHERE assignmentId = :assignmentId AND studentId = :studentId")
    suspend fun getSubmissionCount(assignmentId: String, studentId: String): Int
}
