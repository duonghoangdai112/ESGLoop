package com.ignitech.esgcompanion.data.local

import androidx.room.*
import com.ignitech.esgcompanion.data.entity.AssignmentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AssignmentDao {
    
    @Query("SELECT * FROM assignments")
    fun getAllAssignments(): Flow<List<AssignmentEntity>>
    
    @Query("SELECT * FROM assignments WHERE id = :id")
    suspend fun getAssignmentById(id: String): AssignmentEntity?
    
    @Query("SELECT * FROM assignments WHERE status = :status")
    fun getAssignmentsByStatus(status: String): Flow<List<AssignmentEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAssignment(assignment: AssignmentEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAssignments(assignments: List<AssignmentEntity>)
    
    @Update
    suspend fun updateAssignment(assignment: AssignmentEntity)
    
    @Delete
    suspend fun deleteAssignment(assignment: AssignmentEntity)
    
    @Query("DELETE FROM assignments")
    suspend fun deleteAllAssignments()
    
    @Query("SELECT COUNT(*) FROM assignments")
    suspend fun getAssignmentCount(): Int
}
