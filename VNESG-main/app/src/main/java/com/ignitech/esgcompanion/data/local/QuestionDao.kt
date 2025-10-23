package com.ignitech.esgcompanion.data.local

import androidx.room.*
import com.ignitech.esgcompanion.data.entity.QuestionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {
    
    @Query("SELECT * FROM assignment_questions")
    fun getAllQuestions(): Flow<List<QuestionEntity>>
    
    @Query("SELECT * FROM assignment_questions WHERE id = :id")
    suspend fun getQuestionById(id: String): QuestionEntity?
    
    @Query("SELECT * FROM assignment_questions WHERE type = :type")
    fun getQuestionsByType(type: String): Flow<List<QuestionEntity>>
    
    @Query("SELECT * FROM assignment_questions WHERE assignmentId = :assignmentId")
    fun getQuestionsByAssignmentId(assignmentId: String): Flow<List<QuestionEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestion(question: QuestionEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<QuestionEntity>)
    
    @Update
    suspend fun updateQuestion(question: QuestionEntity)
    
    @Delete
    suspend fun deleteQuestion(question: QuestionEntity)
    
    @Query("DELETE FROM assignment_questions")
    suspend fun deleteAllQuestions()
    
    @Query("SELECT COUNT(*) FROM assignment_questions")
    suspend fun getQuestionCount(): Int
}
