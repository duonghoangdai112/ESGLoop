package com.ignitech.esgcompanion.data.local.dao

import androidx.room.*
import com.ignitech.esgcompanion.domain.entity.ESGPillar
import com.ignitech.esgcompanion.data.local.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AssessmentDao {
    
    // ESG Assessments
    @Query("SELECT * FROM esg_assessments WHERE userId = :userId ORDER BY createdAt DESC")
    fun getAssessmentsByUser(userId: String): Flow<List<ESGAssessmentEntity>>
    
    @Query("SELECT * FROM esg_assessments WHERE userId = :userId AND pillar = :pillar")
    fun getAssessmentsByUserAndPillar(userId: String, pillar: ESGPillar): Flow<List<ESGAssessmentEntity>>
    
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
    
    // ESG Answers
    @Query("SELECT * FROM esg_answers WHERE assessmentId = :assessmentId")
    suspend fun getAnswersByAssessment(assessmentId: String): List<ESGAnswerEntity>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnswer(answer: ESGAnswerEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnswers(answers: List<ESGAnswerEntity>)
    
    // Quiz Attempts
    @Query("SELECT * FROM quiz_attempts WHERE userId = :userId ORDER BY completedAt DESC")
    fun getQuizAttemptsByUser(userId: String): Flow<List<QuizAttemptEntity>>
    
    @Query("SELECT * FROM quiz_attempts WHERE id = :attemptId")
    suspend fun getQuizAttemptById(attemptId: String): QuizAttemptEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizAttempt(attempt: QuizAttemptEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizAttempts(attempts: List<QuizAttemptEntity>)
    
    // Assessment Feedbacks
    @Query("SELECT * FROM assessment_feedbacks WHERE assessmentId = :assessmentId")
    suspend fun getFeedbacksByAssessment(assessmentId: String): List<AssessmentFeedbackEntity>
    
    @Query("SELECT * FROM assessment_feedbacks WHERE expertId = :expertId")
    fun getFeedbacksByExpert(expertId: String): Flow<List<AssessmentFeedbackEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeedback(feedback: AssessmentFeedbackEntity)
    
    // Statistics for Regulatory
    @Query("SELECT AVG(totalScore) FROM esg_assessments WHERE pillar = :pillar AND status = 'COMPLETED'")
    suspend fun getAverageScoreByPillar(pillar: ESGPillar): Double?
    
    @Query("SELECT COUNT(*) FROM esg_assessments WHERE status = 'COMPLETED'")
    suspend fun getCompletedAssessmentsCount(): Int
    
    @Query("SELECT pillar, AVG(totalScore) as avgScore, COUNT(*) as count FROM esg_assessments WHERE status = 'COMPLETED' GROUP BY pillar")
    suspend fun getPillarStatistics(): List<PillarStats>
}

data class PillarStats(
    val pillar: ESGPillar,
    val avgScore: Double,
    val count: Int
)
