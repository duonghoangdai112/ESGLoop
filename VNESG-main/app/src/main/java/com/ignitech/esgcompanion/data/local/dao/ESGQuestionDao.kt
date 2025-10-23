package com.ignitech.esgcompanion.data.local.dao

import androidx.room.*
import com.ignitech.esgcompanion.data.local.entity.ESGQuestionEntity
import com.ignitech.esgcompanion.domain.entity.ESGPillar
import kotlinx.coroutines.flow.Flow

@Dao
interface ESGQuestionDao {
    
    @Query("SELECT * FROM esg_questions WHERE pillar = :pillar ORDER BY `order` ASC")
    fun getQuestionsByPillar(pillar: ESGPillar): Flow<List<ESGQuestionEntity>>
    
    @Query("SELECT * FROM esg_questions ORDER BY pillar, `order` ASC")
    fun getAllQuestions(): Flow<List<ESGQuestionEntity>>
    
    @Query("SELECT * FROM esg_questions WHERE id = :questionId")
    suspend fun getQuestionById(questionId: String): ESGQuestionEntity?
    
    @Query("SELECT COUNT(*) FROM esg_questions")
    suspend fun getQuestionCount(): Int
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<ESGQuestionEntity>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestion(question: ESGQuestionEntity)
    
    @Update
    suspend fun updateQuestion(question: ESGQuestionEntity)
    
    @Delete
    suspend fun deleteQuestion(question: ESGQuestionEntity)
    
    @Query("DELETE FROM esg_questions")
    suspend fun deleteAllQuestions()
    
    @Query("SELECT DISTINCT pillar FROM esg_questions")
    suspend fun getAvailablePillars(): List<ESGPillar>
    
    @Query("SELECT DISTINCT category FROM esg_questions WHERE pillar = :pillar")
    suspend fun getCategoriesByPillar(pillar: ESGPillar): List<String>
}