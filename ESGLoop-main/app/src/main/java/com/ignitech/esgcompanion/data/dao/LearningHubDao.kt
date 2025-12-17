package com.ignitech.esgcompanion.data.dao

import androidx.room.*
import com.ignitech.esgcompanion.data.entity.*
import com.ignitech.esgcompanion.domain.entity.UserRole
import kotlinx.coroutines.flow.Flow

@Dao
interface LearningHubDao {
    
    // Learning Resources
    @Query("SELECT * FROM learning_resources WHERE userRole = :userRole ORDER BY isFeatured DESC, createdAt DESC")
    fun getResourcesByUserRole(userRole: UserRole): Flow<List<LearningResourceEntity>>
    
    @Query("SELECT * FROM learning_resources WHERE userRole = :userRole AND category = :category ORDER BY isFeatured DESC, createdAt DESC")
    fun getResourcesByUserRoleAndCategory(userRole: UserRole, category: String): Flow<List<LearningResourceEntity>>
    
    @Query("SELECT * FROM learning_resources WHERE userRole = :userRole AND level = :level ORDER BY isFeatured DESC, createdAt DESC")
    fun getResourcesByUserRoleAndLevel(userRole: UserRole, level: LearningLevel): Flow<List<LearningResourceEntity>>
    
    @Query("SELECT * FROM learning_resources WHERE userRole = :userRole AND type = :type ORDER BY isFeatured DESC, createdAt DESC")
    fun getResourcesByUserRoleAndType(userRole: UserRole, type: LearningResourceType): Flow<List<LearningResourceEntity>>
    
    @Query("SELECT * FROM learning_resources WHERE userRole = :userRole AND industry = :industry ORDER BY isFeatured DESC, createdAt DESC")
    fun getResourcesByUserRoleAndIndustry(userRole: UserRole, industry: String): Flow<List<LearningResourceEntity>>
    
    @Query("SELECT * FROM learning_resources WHERE id = :resourceId")
    suspend fun getResourceById(resourceId: String): LearningResourceEntity?
    
    @Query("SELECT * FROM learning_resources WHERE userRole = :userRole AND isFeatured = 1 ORDER BY createdAt DESC LIMIT :limit")
    fun getFeaturedResources(userRole: UserRole, limit: Int = 10): Flow<List<LearningResourceEntity>>
    
    @Query("SELECT * FROM learning_resources WHERE userRole = :userRole AND title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%' OR tags LIKE '%' || :query || '%' ORDER BY isFeatured DESC, createdAt DESC")
    fun searchResources(userRole: UserRole, query: String): Flow<List<LearningResourceEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResource(resource: LearningResourceEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResources(resources: List<LearningResourceEntity>)
    
    @Update
    suspend fun updateResource(resource: LearningResourceEntity)
    
    @Delete
    suspend fun deleteResource(resource: LearningResourceEntity)
    
    // Learning Categories
    @Query("SELECT * FROM learning_categories WHERE userRole = :userRole AND isActive = 1 ORDER BY `order` ASC")
    fun getCategoriesByUserRole(userRole: UserRole): Flow<List<LearningCategoryEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: LearningCategoryEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<LearningCategoryEntity>)
    
    // Learning Progress
    @Query("SELECT * FROM learning_progress WHERE userId = :userId ORDER BY lastAccessedAt DESC")
    fun getProgressByUser(userId: String): Flow<List<LearningProgressEntity>>
    
    @Query("SELECT * FROM learning_progress WHERE userId = :userId AND resourceId = :resourceId")
    suspend fun getProgressByUserAndResource(userId: String, resourceId: String): LearningProgressEntity?
    
    @Query("SELECT * FROM learning_progress WHERE userId = :userId AND completedAt IS NOT NULL ORDER BY completedAt DESC")
    fun getCompletedResourcesByUser(userId: String): Flow<List<LearningProgressEntity>>
    
    @Query("SELECT * FROM learning_progress WHERE userId = :userId AND isBookmarked = 1 ORDER BY lastAccessedAt DESC")
    fun getBookmarkedResourcesByUser(userId: String): Flow<List<LearningProgressEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgress(progress: LearningProgressEntity)
    
    @Update
    suspend fun updateProgress(progress: LearningProgressEntity)
    
    @Query("UPDATE learning_progress SET progress = :progress, lastAccessedAt = :lastAccessedAt, timeSpent = :timeSpent WHERE userId = :userId AND resourceId = :resourceId")
    suspend fun updateProgress(userId: String, resourceId: String, progress: Float, lastAccessedAt: Long, timeSpent: Int)
    
    @Query("UPDATE learning_progress SET completedAt = :completedAt, progress = 1.0 WHERE userId = :userId AND resourceId = :resourceId")
    suspend fun markAsCompleted(userId: String, resourceId: String, completedAt: Long)
    
    @Query("UPDATE learning_progress SET isBookmarked = :isBookmarked WHERE userId = :userId AND resourceId = :resourceId")
    suspend fun updateBookmark(userId: String, resourceId: String, isBookmarked: Boolean)
    
    // Learning Certificates
    @Query("SELECT * FROM learning_certificates WHERE userId = :userId ORDER BY issuedAt DESC")
    fun getCertificatesByUser(userId: String): Flow<List<LearningCertificateEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCertificate(certificate: LearningCertificateEntity)
    
    // Learning Quizzes
    @Query("SELECT * FROM learning_quizzes WHERE resourceId = :resourceId ORDER BY `order` ASC")
    fun getQuizzesByResource(resourceId: String): Flow<List<LearningQuizEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuiz(quiz: LearningQuizEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizzes(quizzes: List<LearningQuizEntity>)
    
    // Learning Quiz Attempts
    @Query("SELECT * FROM learning_quiz_attempts WHERE userId = :userId AND resourceId = :resourceId ORDER BY attemptedAt DESC")
    fun getQuizAttemptsByUserAndResource(userId: String, resourceId: String): Flow<List<LearningQuizAttemptEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizAttempt(attempt: LearningQuizAttemptEntity)
    
    // Statistics
    @Query("SELECT COUNT(*) FROM learning_resources WHERE userRole = :userRole")
    suspend fun getResourceCountByUserRole(userRole: UserRole): Int
    
    @Query("SELECT COUNT(*) FROM learning_progress WHERE userId = :userId AND completedAt IS NOT NULL")
    suspend fun getCompletedResourceCountByUser(userId: String): Int
    
    @Query("SELECT AVG(progress) FROM learning_progress WHERE userId = :userId")
    suspend fun getAverageProgressByUser(userId: String): Float?
    
    @Query("SELECT SUM(timeSpent) FROM learning_progress WHERE userId = :userId")
    suspend fun getTotalLearningTimeByUser(userId: String): Int?
    
    // Assignment Submissions
    @Query("SELECT * FROM assignment_submissions WHERE assignmentId = :assignmentId ORDER BY submittedAt DESC")
    fun getSubmissionsByAssignment(assignmentId: String): Flow<List<AssignmentSubmissionEntity>>
    
    @Query("SELECT * FROM assignment_submissions WHERE studentId = :studentId ORDER BY submittedAt DESC")
    fun getSubmissionsByStudent(studentId: String): Flow<List<AssignmentSubmissionEntity>>
    
    @Query("SELECT * FROM assignment_submissions WHERE id = :submissionId")
    suspend fun getSubmissionById(submissionId: String): AssignmentSubmissionEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubmission(submission: AssignmentSubmissionEntity)
    
    @Update
    suspend fun updateSubmission(submission: AssignmentSubmissionEntity)
    
    @Delete
    suspend fun deleteSubmission(submission: AssignmentSubmissionEntity)
    
    // Question Attempts
    @Query("SELECT * FROM question_attempts WHERE questionId = :questionId ORDER BY attemptedAt DESC")
    fun getAttemptsByQuestion(questionId: String): Flow<List<QuestionAttemptEntity>>
    
    @Query("SELECT * FROM question_attempts WHERE studentId = :studentId ORDER BY attemptedAt DESC")
    fun getAttemptsByStudent(studentId: String): Flow<List<QuestionAttemptEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestionAttempt(attempt: QuestionAttemptEntity)
    
    @Update
    suspend fun updateQuestionAttempt(attempt: QuestionAttemptEntity)
    
    @Delete
    suspend fun deleteQuestionAttempt(attempt: QuestionAttemptEntity)
    
    // Specific queries for lessons, assignments, and questions
    @Query("SELECT * FROM learning_resources WHERE type = 'LESSON' AND authorId = :authorId ORDER BY createdAt DESC")
    fun getLessonsByAuthor(authorId: String): Flow<List<LearningResourceEntity>>
    
    @Query("SELECT * FROM learning_resources WHERE type = 'ASSIGNMENT' AND authorId = :authorId ORDER BY createdAt DESC")
    fun getAssignmentsByAuthor(authorId: String): Flow<List<LearningResourceEntity>>
    
    @Query("SELECT * FROM learning_resources WHERE type = 'QUIZ' AND authorId = :authorId ORDER BY createdAt DESC")
    fun getQuestionsByAuthor(authorId: String): Flow<List<LearningResourceEntity>>
    
    @Query("SELECT * FROM learning_resources WHERE type = 'ASSIGNMENT' AND classId = :classId ORDER BY dueDate ASC")
    fun getAssignmentsByClass(classId: String): Flow<List<LearningResourceEntity>>
}
