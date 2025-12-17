package com.ignitech.esgcompanion.data.repository

import com.ignitech.esgcompanion.data.dao.LearningHubDao
import com.ignitech.esgcompanion.data.entity.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LearningHubRepository @Inject constructor(
    private val learningHubDao: LearningHubDao
) {
    
    // Lesson methods
    suspend fun createLesson(
        title: String,
        description: String,
        content: String,
        classId: String,
        className: String,
        duration: Int,
        difficulty: LearningLevel,
        authorId: String,
        authorName: String,
        objectives: List<String> = emptyList(),
        materials: List<String> = emptyList(),
        tags: List<String> = emptyList()
    ): String {
        val lessonId = "lesson_${System.currentTimeMillis()}"
        val lesson = LearningResourceEntity(
            id = lessonId,
            title = title,
            description = description,
            content = content,
            type = LearningResourceType.LESSON,
            category = "Bài giảng",
            subcategory = "",
            level = difficulty,
            userRole = com.ignitech.esgcompanion.domain.entity.UserRole.ACADEMIC,
            duration = duration,
            difficulty = when (difficulty) {
                LearningLevel.BEGINNER -> 1
                LearningLevel.INTERMEDIATE -> 3
                LearningLevel.ADVANCED -> 4
                LearningLevel.EXPERT -> 5
            },
            authorId = authorId,
            authorName = authorName,
            classId = classId,
            className = className,
            lessonDuration = duration,
            lessonObjectives = objectives.joinToString(","),
            lessonMaterials = materials.joinToString(","),
            tags = tags.joinToString(","),
            isPublished = true,
            publishedAt = System.currentTimeMillis()
        )
        
        learningHubDao.insertResource(lesson)
        return lessonId
    }
    
    fun getLessonsByAuthor(authorId: String): Flow<List<LearningResourceEntity>> {
        return learningHubDao.getLessonsByAuthor(authorId)
    }
    
    // Assignment methods
    suspend fun createAssignment(
        title: String,
        description: String,
        instructions: String,
        type: String,
        classId: String,
        className: String,
        difficulty: String,
        maxScore: Int,
        dueDate: Long,
        authorId: String,
        authorName: String,
        requirements: List<String> = emptyList(),
        attachments: List<String> = emptyList(),
        tags: List<String> = emptyList(),
        timeLimit: Int? = null,
        attempts: Int = 1
    ): String {
        val assignmentId = "assignment_${System.currentTimeMillis()}"
        val assignment = LearningResourceEntity(
            id = assignmentId,
            title = title,
            description = description,
            content = instructions,
            type = LearningResourceType.ASSIGNMENT,
            category = "Bài tập",
            subcategory = type,
            level = when (difficulty) {
                "Dễ" -> LearningLevel.BEGINNER
                "Trung bình" -> LearningLevel.INTERMEDIATE
                "Khó" -> LearningLevel.ADVANCED
                "Rất khó" -> LearningLevel.EXPERT
                else -> LearningLevel.INTERMEDIATE
            },
            userRole = com.ignitech.esgcompanion.domain.entity.UserRole.ACADEMIC,
            authorId = authorId,
            authorName = authorName,
            classId = classId,
            className = className,
            dueDate = dueDate,
            maxScore = maxScore,
            timeLimit = timeLimit,
            attempts = attempts,
            instructions = instructions,
            requirements = requirements.joinToString(","),
            attachments = attachments.joinToString(","),
            tags = tags.joinToString(","),
            isGraded = true,
            isPublished = true,
            publishedAt = System.currentTimeMillis()
        )
        
        learningHubDao.insertResource(assignment)
        return assignmentId
    }
    
    fun getAssignmentsByAuthor(authorId: String): Flow<List<LearningResourceEntity>> {
        return learningHubDao.getAssignmentsByAuthor(authorId)
    }
    
    fun getAssignmentsByClass(classId: String): Flow<List<LearningResourceEntity>> {
        return learningHubDao.getAssignmentsByClass(classId)
    }
    
    // Question methods
    suspend fun createQuestion(
        questionText: String,
        type: String,
        difficulty: String,
        category: String,
        points: Int,
        explanation: String,
        authorId: String,
        authorName: String,
        options: List<String> = emptyList(),
        correctAnswer: String = "",
        tags: List<String> = emptyList(),
        hint: String = ""
    ): String {
        val questionId = "question_${System.currentTimeMillis()}"
        val question = LearningResourceEntity(
            id = questionId,
            title = questionText,
            description = "",
            content = questionText,
            type = LearningResourceType.QUIZ,
            category = category,
            subcategory = type,
            level = when (difficulty) {
                "Dễ" -> LearningLevel.BEGINNER
                "Trung bình" -> LearningLevel.INTERMEDIATE
                "Khó" -> LearningLevel.ADVANCED
                "Rất khó" -> LearningLevel.EXPERT
                else -> LearningLevel.INTERMEDIATE
            },
            userRole = com.ignitech.esgcompanion.domain.entity.UserRole.ACADEMIC,
            authorId = authorId,
            authorName = authorName,
            questionType = when (type) {
                "Trắc nghiệm" -> QuizQuestionType.MULTIPLE_CHOICE
                "Đúng/Sai" -> QuizQuestionType.TRUE_FALSE
                "Điền từ" -> QuizQuestionType.FILL_BLANK
                "Tự luận" -> QuizQuestionType.ESSAY
                "Ghép cặp" -> QuizQuestionType.MATCHING
                else -> QuizQuestionType.MULTIPLE_CHOICE
            },
            options = options.joinToString(","),
            correctAnswer = correctAnswer,
            explanation = explanation,
            points = points,
            hint = hint,
            tags = tags.joinToString(","),
            isPublished = true,
            publishedAt = System.currentTimeMillis()
        )
        
        learningHubDao.insertResource(question)
        return questionId
    }
    
    fun getQuestionsByAuthor(authorId: String): Flow<List<LearningResourceEntity>> {
        return learningHubDao.getQuestionsByAuthor(authorId)
    }
    
    // Submission methods
    suspend fun submitAssignment(
        assignmentId: String,
        studentId: String,
        studentName: String,
        content: String,
        attachments: List<String> = emptyList(),
        attemptNumber: Int = 1
    ): String {
        val submissionId = "submission_${System.currentTimeMillis()}"
        val submission = AssignmentSubmissionEntity(
            id = submissionId,
            assignmentId = assignmentId,
            studentId = studentId,
            studentName = studentName,
            content = content,
            attachments = attachments.joinToString(","),
            attemptNumber = attemptNumber
        )
        
        learningHubDao.insertSubmission(submission)
        return submissionId
    }
    
    fun getSubmissionsByAssignment(assignmentId: String): Flow<List<AssignmentSubmissionEntity>> {
        return learningHubDao.getSubmissionsByAssignment(assignmentId)
    }
    
    fun getSubmissionsByStudent(studentId: String): Flow<List<AssignmentSubmissionEntity>> {
        return learningHubDao.getSubmissionsByStudent(studentId)
    }
    
    suspend fun gradeSubmission(
        submissionId: String,
        score: Int,
        feedback: String,
        gradedBy: String
    ) {
        val submission = learningHubDao.getSubmissionById(submissionId)
        submission?.let {
            val updatedSubmission = it.copy(
                score = score,
                feedback = feedback,
                gradedAt = System.currentTimeMillis(),
                gradedBy = gradedBy
            )
            learningHubDao.updateSubmission(updatedSubmission)
        }
    }
    
    // Question attempt methods
    suspend fun submitQuestionAnswer(
        questionId: String,
        studentId: String,
        answer: String,
        isCorrect: Boolean,
        pointsEarned: Int = 0,
        timeSpent: Int = 0
    ): String {
        val attemptId = "attempt_${System.currentTimeMillis()}"
        val attempt = QuestionAttemptEntity(
            id = attemptId,
            questionId = questionId,
            studentId = studentId,
            studentAnswer = answer,
            isCorrect = isCorrect,
            pointsEarned = pointsEarned,
            timeSpent = timeSpent
        )
        
        learningHubDao.insertQuestionAttempt(attempt)
        return attemptId
    }
    
    fun getAttemptsByQuestion(questionId: String): Flow<List<QuestionAttemptEntity>> {
        return learningHubDao.getAttemptsByQuestion(questionId)
    }
    
    fun getAttemptsByStudent(studentId: String): Flow<List<QuestionAttemptEntity>> {
        return learningHubDao.getAttemptsByStudent(studentId)
    }
}
