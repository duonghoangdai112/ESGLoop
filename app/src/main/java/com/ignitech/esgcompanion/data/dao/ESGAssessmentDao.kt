package com.ignitech.esgcompanion.data.dao

import androidx.room.*
import com.ignitech.esgcompanion.data.entity.*
import com.ignitech.esgcompanion.data.local.entity.ESGAssessmentEntity
import com.ignitech.esgcompanion.data.local.entity.ESGAnswerEntity
import com.ignitech.esgcompanion.data.local.entity.ESGQuestionEntity
import com.ignitech.esgcompanion.domain.entity.ESGPillar
import com.ignitech.esgcompanion.domain.entity.UserRole
import kotlinx.coroutines.flow.Flow

@Dao
interface ESGAssessmentDao {
    
    // Questions
    @Query("SELECT * FROM esg_questions WHERE pillar = :pillar AND userRole = :userRole ORDER BY category, `order` ASC")
    fun getQuestionsByPillarAndRole(pillar: ESGPillar, userRole: UserRole): Flow<List<ESGQuestionEntity>>
    
    @Query("SELECT * FROM esg_questions WHERE pillar = :pillar ORDER BY category, `order` ASC")
    fun getQuestionsByPillar(pillar: ESGPillar): Flow<List<ESGQuestionEntity>>
    
    @Query("SELECT * FROM esg_questions WHERE userRole = :userRole ORDER BY pillar, category, `order` ASC")
    fun getQuestionsByRole(userRole: UserRole): Flow<List<ESGQuestionEntity>>
    
    @Query("SELECT * FROM esg_questions ORDER BY pillar, category, `order` ASC")
    fun getAllQuestions(): Flow<List<ESGQuestionEntity>>
    
    @Query("SELECT * FROM esg_questions WHERE id = :questionId")
    suspend fun getQuestionById(questionId: String): ESGQuestionEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestion(question: ESGQuestionEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<ESGQuestionEntity>)
    
    // Assessments
    @Query("SELECT * FROM esg_assessments WHERE userId = :userId ORDER BY createdAt DESC")
    fun getAssessmentsByUser(userId: String): Flow<List<ESGAssessmentEntity>>
    
    @Query("SELECT * FROM esg_assessments WHERE userId = :userId AND pillar = :pillar ORDER BY createdAt DESC")
    fun getAssessmentsByUserAndPillar(userId: String, pillar: ESGPillar): Flow<List<ESGAssessmentEntity>>
    
    @Query("SELECT * FROM esg_assessments WHERE userId = :userId AND isHistorical = :isHistorical ORDER BY assessmentYear DESC, assessmentQuarter DESC")
    fun getAssessmentsByUserAndHistorical(userId: String, isHistorical: Boolean): Flow<List<ESGAssessmentEntity>>
    
    @Query("SELECT * FROM esg_assessments WHERE userId = :userId AND assessmentYear = :year ORDER BY assessmentQuarter DESC")
    fun getAssessmentsByUserAndYear(userId: String, year: Int): Flow<List<ESGAssessmentEntity>>
    
    @Query("SELECT * FROM esg_assessments WHERE userId = :userId AND assessmentPeriod = :period")
    fun getAssessmentsByUserAndPeriod(userId: String, period: String): Flow<List<ESGAssessmentEntity>>
    
    @Query("SELECT DISTINCT assessmentYear FROM esg_assessments WHERE userId = :userId ORDER BY assessmentYear DESC")
    fun getAssessmentYearsForUser(userId: String): Flow<List<Int>>
    
    @Query("SELECT * FROM esg_assessments WHERE userId = :userId AND pillar = :pillar AND isHistorical = :isHistorical ORDER BY assessmentYear DESC, assessmentQuarter DESC")
    fun getHistoricalAssessmentsByPillar(userId: String, pillar: ESGPillar, isHistorical: Boolean): Flow<List<ESGAssessmentEntity>>
    
    @Query("SELECT * FROM esg_assessments WHERE userId = :userId AND pillar = :pillar AND assessmentPeriod = :period LIMIT 1")
    suspend fun getAssessmentByUserPillarAndPeriod(userId: String, pillar: ESGPillar, period: String): ESGAssessmentEntity?
    
    @Query("SELECT * FROM esg_assessments WHERE id = :assessmentId")
    suspend fun getAssessmentById(assessmentId: String): ESGAssessmentEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAssessment(assessment: ESGAssessmentEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAssessments(assessments: List<ESGAssessmentEntity>)
    
    @Update
    suspend fun updateAssessment(assessment: ESGAssessmentEntity)
    
    @Delete
    suspend fun deleteAssessment(assessment: ESGAssessmentEntity)
    
    // Answers
    @Query("SELECT * FROM esg_answers WHERE assessmentId = :assessmentId")
    fun getAnswersByAssessment(assessmentId: String): Flow<List<ESGAnswerEntity>>
    
    @Query("SELECT * FROM esg_answers WHERE assessmentId = :assessmentId AND questionId = :questionId")
    suspend fun getAnswerByAssessmentAndQuestion(assessmentId: String, questionId: String): ESGAnswerEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnswer(answer: ESGAnswerEntity)
    
    @Update
    suspend fun updateAnswer(answer: ESGAnswerEntity)
    
    @Delete
    suspend fun deleteAnswer(answer: ESGAnswerEntity)
    
    // Categories
    @Query("SELECT * FROM esg_categories WHERE pillar = :pillar ORDER BY `order` ASC")
    fun getCategoriesByPillar(pillar: ESGPillar): Flow<List<ESGCategoryEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: ESGCategoryEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<ESGCategoryEntity>)
    
    // Statistics
    @Query("""
        SELECT COUNT(*) FROM esg_questions 
        WHERE pillar = :pillar
    """)
    suspend fun getQuestionCountByPillar(pillar: ESGPillar): Int
    
    @Query("""
        SELECT COUNT(*) FROM esg_answers 
        WHERE assessmentId = :assessmentId
    """)
    suspend fun getAnsweredQuestionCount(assessmentId: String): Int
    
    @Query("""
        SELECT SUM(score) FROM esg_answers 
        WHERE assessmentId = :assessmentId
    """)
    suspend fun getTotalScore(assessmentId: String): Int?
    
    @Query("""
        SELECT SUM(q.weight * 4) FROM esg_questions q
        WHERE q.pillar = :pillar
    """)
    suspend fun getMaxScoreByPillar(pillar: ESGPillar): Int
}
