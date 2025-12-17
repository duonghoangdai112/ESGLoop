package com.ignitech.esgcompanion.data.local

import androidx.room.*
import com.ignitech.esgcompanion.data.entity.QuestionAttemptEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionAttemptDao {
    
    @Query("SELECT * FROM question_attempts WHERE questionId = :questionId AND studentId = :studentId ORDER BY attemptedAt DESC")
    fun getAttemptsByQuestionAndStudent(questionId: String, studentId: String): Flow<List<QuestionAttemptEntity>>
    
    @Query("SELECT * FROM question_attempts WHERE studentId = :studentId ORDER BY attemptedAt DESC")
    fun getAttemptsByStudent(studentId: String): Flow<List<QuestionAttemptEntity>>
    
    @Query("SELECT * FROM question_attempts WHERE questionId = :questionId ORDER BY attemptedAt DESC")
    fun getAttemptsByQuestion(questionId: String): Flow<List<QuestionAttemptEntity>>
    
    @Query("SELECT * FROM question_attempts WHERE id = :attemptId")
    suspend fun getAttemptById(attemptId: String): QuestionAttemptEntity?
    
    @Query("SELECT * FROM question_attempts WHERE questionId = :questionId AND studentId = :studentId ORDER BY attemptedAt DESC LIMIT 1")
    suspend fun getLatestAttempt(questionId: String, studentId: String): QuestionAttemptEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttempt(attempt: QuestionAttemptEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttempts(attempts: List<QuestionAttemptEntity>)
    
    @Update
    suspend fun updateAttempt(attempt: QuestionAttemptEntity)
    
    @Delete
    suspend fun deleteAttempt(attempt: QuestionAttemptEntity)
    
    @Query("DELETE FROM question_attempts WHERE questionId = :questionId AND studentId = :studentId")
    suspend fun deleteAttemptsByQuestionAndStudent(questionId: String, studentId: String)
    
    @Query("SELECT COUNT(*) FROM question_attempts WHERE questionId = :questionId AND studentId = :studentId")
    suspend fun getAttemptCount(questionId: String, studentId: String): Int
}
