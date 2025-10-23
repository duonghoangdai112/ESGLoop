package com.ignitech.esgcompanion.data.local.dao

import androidx.room.*
import com.ignitech.esgcompanion.data.local.entity.ClassEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ClassDao {
    @Query("SELECT * FROM classes WHERE instructorId = :instructorId ORDER BY createdAt DESC")
    fun getClassesByInstructor(instructorId: String): Flow<List<ClassEntity>>
    
    @Query("SELECT * FROM classes WHERE id = :classId")
    suspend fun getClassById(classId: String): ClassEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClass(classEntity: ClassEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClasses(classes: List<ClassEntity>)
    
    @Update
    suspend fun updateClass(classEntity: ClassEntity)
    
    @Delete
    suspend fun deleteClass(classEntity: ClassEntity)
    
    @Query("DELETE FROM classes")
    suspend fun deleteAllClasses()
}
