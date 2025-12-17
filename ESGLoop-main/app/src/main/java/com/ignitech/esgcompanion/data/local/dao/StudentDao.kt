package com.ignitech.esgcompanion.data.local.dao

import androidx.room.*
import com.ignitech.esgcompanion.data.local.entity.StudentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {
    @Query("SELECT * FROM students WHERE classId = :classId ORDER BY name ASC")
    fun getStudentsByClass(classId: String): Flow<List<StudentEntity>>
    
    @Query("SELECT * FROM students WHERE id = :studentId")
    suspend fun getStudentById(studentId: String): StudentEntity?
    
    @Query("SELECT * FROM students WHERE classId = :classId AND status = :status ORDER BY name ASC")
    fun getStudentsByClassAndStatus(classId: String, status: String): Flow<List<StudentEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(studentEntity: StudentEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudents(students: List<StudentEntity>)
    
    @Update
    suspend fun updateStudent(studentEntity: StudentEntity)
    
    @Delete
    suspend fun deleteStudent(studentEntity: StudentEntity)
    
    @Query("DELETE FROM students WHERE classId = :classId")
    suspend fun deleteStudentsByClass(classId: String)
    
    @Query("DELETE FROM students")
    suspend fun deleteAllStudents()
}
