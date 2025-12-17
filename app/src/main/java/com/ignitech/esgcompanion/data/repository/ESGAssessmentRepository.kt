package com.ignitech.esgcompanion.data.repository

import com.ignitech.esgcompanion.data.dao.ESGAssessmentDao
import com.ignitech.esgcompanion.data.dao.LearningHubDao
import com.ignitech.esgcompanion.data.dao.ESGTrackerDao
import com.ignitech.esgcompanion.data.entity.*
import com.ignitech.esgcompanion.data.local.entity.ESGAssessmentEntity
import com.ignitech.esgcompanion.data.local.entity.ESGAnswerEntity
import com.ignitech.esgcompanion.data.local.entity.ESGQuestionEntity
import com.ignitech.esgcompanion.data.seed.ESGQuestionSeeder
import com.ignitech.esgcompanion.data.seed.ESGStudentQuestionSeeder
import com.ignitech.esgcompanion.data.seed.AssessmentHistorySeeder
import com.ignitech.esgcompanion.data.seed.LearningHubSeeder
import com.ignitech.esgcompanion.domain.entity.ESGPillar
import com.ignitech.esgcompanion.domain.entity.AssessmentStatus
import com.ignitech.esgcompanion.domain.entity.UserRole
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ESGAssessmentRepository @Inject constructor(
    private val esgAssessmentDao: ESGAssessmentDao,
    private val learningHubDao: LearningHubDao,
    private val esgTrackerDao: ESGTrackerDao
) {
    
    // Initialize database with standard questions
    suspend fun initializeDatabase() {
        val existingQuestions = esgAssessmentDao.getAllQuestions().first()
        if (existingQuestions.isEmpty()) {
            // Insert enterprise questions
            esgAssessmentDao.insertQuestions(ESGQuestionSeeder.getStandardQuestions())
            esgAssessmentDao.insertCategories(ESGQuestionSeeder.getStandardCategories())
            
            // Insert student questions
            esgAssessmentDao.insertQuestions(ESGStudentQuestionSeeder.getStudentQuestions())
            esgAssessmentDao.insertCategories(ESGStudentQuestionSeeder.getStudentCategories())
        }
    }
    
    // Initialize Learning Hub data
    suspend fun initializeLearningHub() {
        // Check if data already exists (check all roles)
        val existingEnterpriseCategories = learningHubDao.getCategoriesByUserRole(UserRole.ENTERPRISE).first()
        val existingEnterpriseResources = learningHubDao.getResourcesByUserRole(UserRole.ENTERPRISE).first()
        
        println("DEBUG: Learning Hub check - Categories: ${existingEnterpriseCategories.size}, Resources: ${existingEnterpriseResources.size}")
        
        // Always seed categories if missing
        if (existingEnterpriseCategories.isEmpty()) {
            println("DEBUG: Starting to seed Learning Hub categories...")
            val categories = LearningHubSeeder.getLearningCategories()
            learningHubDao.insertCategories(categories)
            println("DEBUG: Seeded ${categories.size} learning categories")
        } else {
            println("DEBUG: Categories already exist, skipping")
        }
        
        // Always seed resources if missing
        if (existingEnterpriseResources.isEmpty()) {
            println("DEBUG: Starting to seed Learning Hub resources...")
            val resources = LearningHubSeeder.getLearningResources()
            try {
                learningHubDao.insertResources(resources)
                println("DEBUG: Seeded ${resources.size} learning resources")
            } catch (e: Exception) {
                println("DEBUG: ERROR seeding resources: ${e.message}")
                e.printStackTrace()
                // Try to seed one by one to identify problematic resource
                var successCount = 0
                resources.forEach { resource ->
                    try {
                        learningHubDao.insertResource(resource)
                        successCount++
                    } catch (ex: Exception) {
                        println("DEBUG: ERROR inserting resource ${resource.id}: ${ex.message}")
                    }
                }
                println("DEBUG: Successfully seeded $successCount out of ${resources.size} resources")
            }
        } else {
            println("DEBUG: Resources already exist, skipping")
        }
        
        println("DEBUG: Learning Hub seeding completed!")
    }
    
    // Learning Hub methods
    suspend fun getLearningCategoriesByUserRole(userRole: UserRole): List<LearningCategoryEntity> {
        return learningHubDao.getCategoriesByUserRole(userRole).first()
    }
    
    suspend fun getLearningResourcesByUserRole(userRole: UserRole): List<LearningResourceEntity> {
        return learningHubDao.getResourcesByUserRole(userRole).first()
    }
    
    suspend fun getFeaturedResources(userRole: UserRole): List<LearningResourceEntity> {
        return learningHubDao.getFeaturedResources(userRole, 5).first()
    }
    
    suspend fun searchLearningResources(userRole: UserRole, query: String): List<LearningResourceEntity> {
        return learningHubDao.searchResources(userRole, query).first()
    }
    
    suspend fun getFilteredLearningResources(
        userRole: UserRole,
        categoryId: String? = null,
        level: LearningLevel? = null,
        type: LearningResourceType? = null
    ): List<LearningResourceEntity> {
        return when {
            categoryId != null -> learningHubDao.getResourcesByUserRoleAndCategory(userRole, categoryId).first()
            level != null -> learningHubDao.getResourcesByUserRoleAndLevel(userRole, level).first()
            type != null -> learningHubDao.getResourcesByUserRoleAndType(userRole, type).first()
            else -> learningHubDao.getResourcesByUserRole(userRole).first()
        }
    }
    
    suspend fun toggleBookmark(userId: String, resourceId: String) {
        val existingProgress = learningHubDao.getProgressByUserAndResource(userId, resourceId)
        if (existingProgress != null) {
            learningHubDao.updateBookmark(userId, resourceId, !existingProgress.isBookmarked)
        } else {
            val newProgress = LearningProgressEntity(
                id = "${userId}_${resourceId}",
                userId = userId,
                resourceId = resourceId,
                isBookmarked = true
            )
            learningHubDao.insertProgress(newProgress)
        }
    }
    
    // Initialize assessment history for a user
    suspend fun initializeAssessmentHistory(userId: String) {
        val existingAssessments = esgAssessmentDao.getAssessmentsByUser(userId).first()
        if (existingAssessments.isEmpty()) {
            // Insert all assessments (including current quarter)
            val allAssessments = AssessmentHistorySeeder.getHistoricalAssessments(userId)
            esgAssessmentDao.insertAssessments(allAssessments)
        }
    }
    
    // Questions
    fun getQuestionsByPillar(pillar: ESGPillar): Flow<List<ESGQuestionEntity>> {
        return esgAssessmentDao.getQuestionsByPillar(pillar)
    }
    
    fun getQuestionsByPillarAndRole(pillar: ESGPillar, userRole: UserRole): Flow<List<ESGQuestionEntity>> {
        return esgAssessmentDao.getQuestionsByPillarAndRole(pillar, userRole)
    }
    
    fun getQuestionsByRole(userRole: UserRole): Flow<List<ESGQuestionEntity>> {
        return esgAssessmentDao.getQuestionsByRole(userRole)
    }
    
    suspend fun getQuestionById(questionId: String): ESGQuestionEntity? {
        return esgAssessmentDao.getQuestionById(questionId)
    }
    
    // Assessments
    fun getAssessmentsByUser(userId: String): Flow<List<ESGAssessmentEntity>> {
        return esgAssessmentDao.getAssessmentsByUser(userId)
    }
    
    fun getAssessmentsByUserAndPillar(userId: String, pillar: ESGPillar): Flow<List<ESGAssessmentEntity>> {
        return esgAssessmentDao.getAssessmentsByUserAndPillar(userId, pillar)
    }
    
    suspend fun getAssessmentById(assessmentId: String): ESGAssessmentEntity? {
        return esgAssessmentDao.getAssessmentById(assessmentId)
    }
    
    suspend fun createAssessment(
        userId: String,
        pillar: ESGPillar,
        title: String,
        description: String
    ): ESGAssessmentEntity {
        val assessment = ESGAssessmentEntity(
            id = generateAssessmentId(),
            userId = userId,
            title = title,
            description = description,
            pillar = pillar,
            status = AssessmentStatus.NOT_STARTED,
            totalScore = 0,
            maxScore = esgAssessmentDao.getMaxScoreByPillar(pillar),
            createdAt = System.currentTimeMillis()
        )
        esgAssessmentDao.insertAssessment(assessment)
        return assessment
    }
    
    suspend fun updateAssessment(assessment: ESGAssessmentEntity) {
        esgAssessmentDao.updateAssessment(assessment)
    }
    
    suspend fun deleteAssessment(assessment: ESGAssessmentEntity) {
        esgAssessmentDao.deleteAssessment(assessment)
    }
    
    // Answers
    fun getAnswersByAssessment(assessmentId: String): Flow<List<ESGAnswerEntity>> {
        return esgAssessmentDao.getAnswersByAssessment(assessmentId)
    }
    
    suspend fun getAnswerByAssessmentAndQuestion(assessmentId: String, questionId: String): ESGAnswerEntity? {
        return esgAssessmentDao.getAnswerByAssessmentAndQuestion(assessmentId, questionId)
    }
    
    suspend fun saveAnswer(
        assessmentId: String,
        questionId: String,
        answer: AssessmentAnswer,
        notes: String = ""
    ) {
        val question = esgAssessmentDao.getQuestionById(questionId)
        val score = calculateScore(answer, question?.weight ?: 1)
        
        val answerEntity = ESGAnswerEntity(
            id = generateAnswerId(),
            assessmentId = assessmentId,
            questionId = questionId,
            answer = answer,
            score = score,
            answeredAt = System.currentTimeMillis(),
            notes = notes
        )
        
        esgAssessmentDao.insertAnswer(answerEntity)
        
        // Update assessment progress
        updateAssessmentProgress(assessmentId)
    }
    
    suspend fun updateAnswer(answer: ESGAnswerEntity) {
        esgAssessmentDao.updateAnswer(answer)
        
        // Update assessment progress
        updateAssessmentProgress(answer.assessmentId)
    }
    
    suspend fun deleteAnswer(answer: ESGAnswerEntity) {
        esgAssessmentDao.deleteAnswer(answer)
        
        // Update assessment progress
        updateAssessmentProgress(answer.assessmentId)
    }
    
    // Categories
    fun getCategoriesByPillar(pillar: ESGPillar): Flow<List<ESGCategoryEntity>> {
        return esgAssessmentDao.getCategoriesByPillar(pillar)
    }
    
    // Statistics
    suspend fun getAssessmentProgress(assessmentId: String): Float {
        val totalQuestions = esgAssessmentDao.getQuestionCountByPillar(
            esgAssessmentDao.getAssessmentById(assessmentId)?.pillar ?: ESGPillar.ENVIRONMENTAL
        )
        val answeredQuestions = esgAssessmentDao.getAnsweredQuestionCount(assessmentId)
        return if (totalQuestions > 0) answeredQuestions.toFloat() / totalQuestions else 0f
    }
    
    suspend fun getAssessmentScore(assessmentId: String): Pair<Int, Int> {
        val totalScore = esgAssessmentDao.getTotalScore(assessmentId) ?: 0
        val assessment = esgAssessmentDao.getAssessmentById(assessmentId)
        val maxScore = assessment?.maxScore ?: 0
        return Pair(totalScore, maxScore)
    }
    
    // Private helper functions
    private fun generateAssessmentId(): String {
        return "assessment_${System.currentTimeMillis()}_${(1000..9999).random()}"
    }
    
    private fun generateAnswerId(): String {
        return "answer_${System.currentTimeMillis()}_${(1000..9999).random()}"
    }
    
    private fun calculateScore(answer: AssessmentAnswer, weight: Int): Int {
        val baseScore = when (answer) {
            AssessmentAnswer.FULLY_IMPLEMENTED -> 4
            AssessmentAnswer.IN_PROGRESS -> 3
            AssessmentAnswer.NOT_APPLICABLE -> 2
            AssessmentAnswer.NOT_IMPLEMENTED -> 1
        }
        return baseScore * weight
    }
    
    private suspend fun updateAssessmentProgress(assessmentId: String) {
        val assessment = esgAssessmentDao.getAssessmentById(assessmentId) ?: return
        val progress = getAssessmentProgress(assessmentId)
        val (totalScore, maxScore) = getAssessmentScore(assessmentId)
        
        val updatedAssessment = assessment.copy(
            totalScore = totalScore,
            status = when {
                progress >= 1.0f -> AssessmentStatus.COMPLETED
                progress > 0.0f -> AssessmentStatus.IN_PROGRESS
                else -> AssessmentStatus.NOT_STARTED
            },
            completedAt = if (progress >= 1.0f) System.currentTimeMillis() else null,
            updatedAt = System.currentTimeMillis()
        )
        
        esgAssessmentDao.updateAssessment(updatedAssessment)
    }
    
    // Assessment History
    fun getHistoricalAssessments(userId: String): Flow<List<ESGAssessmentEntity>> {
        return esgAssessmentDao.getAssessmentsByUserAndHistorical(userId, true)
    }
    
    fun getCurrentAssessments(userId: String): Flow<List<ESGAssessmentEntity>> {
        return esgAssessmentDao.getAssessmentsByUserAndHistorical(userId, false)
    }
    
    fun getAssessmentsByYear(userId: String, year: Int): Flow<List<ESGAssessmentEntity>> {
        return esgAssessmentDao.getAssessmentsByUserAndYear(userId, year)
    }
    
    fun getAssessmentsByPeriod(userId: String, period: String): Flow<List<ESGAssessmentEntity>> {
        return esgAssessmentDao.getAssessmentsByUserAndPeriod(userId, period)
    }
    
    fun getAssessmentYears(userId: String): Flow<List<Int>> {
        return esgAssessmentDao.getAssessmentYearsForUser(userId)
    }
    
    fun getHistoricalAssessmentsByPillar(userId: String, pillar: ESGPillar): Flow<List<ESGAssessmentEntity>> {
        return esgAssessmentDao.getHistoricalAssessmentsByPillar(userId, pillar, true)
    }
    
    suspend fun getCurrentAssessmentByPillar(userId: String, pillar: ESGPillar, period: String): ESGAssessmentEntity? {
        return esgAssessmentDao.getAssessmentByUserPillarAndPeriod(userId, pillar, period)
    }
    
    // ESGTracker methods
    suspend fun initializeTrackerData() {
        println("DEBUG: Starting initializeTrackerData for user_3")
        // Check if activities already exist for enterprise user
        val existingActivities = esgTrackerDao.getActivitiesByUser("user_3").first()
        println("DEBUG: Found ${existingActivities.size} existing activities for user_3")
        
        if (existingActivities.isEmpty()) {
            println("DEBUG: No existing activities found, inserting banking activities")
            // Insert banking activities for enterprise user
            val bankingActivities = com.ignitech.esgcompanion.data.seed.BankingActivitySeeder.getBankingActivities()
            println("DEBUG: Generated ${bankingActivities.size} banking activities")
            esgTrackerDao.insertActivities(bankingActivities)
            println("DEBUG: Successfully inserted banking activities")
        } else {
            println("DEBUG: Activities already exist, skipping insertion")
        }
    }
    
    suspend fun clearTrackerData() {
        // Note: No bulk delete methods available, will be handled by database recreation
        // Individual activities and KPIs will be replaced during reseeding
    }
    
    fun getTrackerActivitiesByUser(userId: String): Flow<List<ESGTrackerEntity>> {
        println("DEBUG: ESGAssessmentRepository - getTrackerActivitiesByUser called for userId: $userId")
        return esgTrackerDao.getActivitiesByUser(userId)
    }
    
    fun getTrackerKPIsByUser(userId: String): Flow<List<ESGTrackerKPIEntity>> {
        return esgTrackerDao.getKPIsByUser(userId)
    }
    
    suspend fun updateTrackerActivityStatus(activityId: String, status: TrackerStatus) {
        esgTrackerDao.updateActivityStatus(activityId, status, System.currentTimeMillis())
    }
    
    suspend fun updateTrackerKPIValue(kpiId: String, value: Double) {
        // TODO: Implement KPI value update
    }
    
    suspend fun insertTrackerActivity(activity: ESGTrackerEntity) {
        esgTrackerDao.insertActivity(activity)
    }
}
