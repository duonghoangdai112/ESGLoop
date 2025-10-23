package com.ignitech.esgcompanion.domain.entity

enum class AssessmentStatus {
    NOT_STARTED,
    IN_PROGRESS,
    COMPLETED,
    DRAFT
}


// Domain entities
data class ESGAssessment(
    val id: String,
    val userId: String,
    val title: String,
    val description: String,
    val pillar: ESGPillar,
    val status: AssessmentStatus,
    val totalScore: Int,
    val maxScore: Int,
    val completedAt: Long?,
    val createdAt: Long,
    val answers: List<ESGAnswer>
)

data class ESGAnswer(
    val id: String,
    val assessmentId: String,
    val questionId: String,
    val selectedOptionId: String,
    val score: Int,
    val answeredAt: Long
)

data class QuizAttempt(
    val id: String,
    val userId: String,
    val quizTitle: String,
    val totalQuestions: Int,
    val correctAnswers: Int,
    val score: Int,
    val passed: Boolean,
    val completedAt: Long,
    val answers: List<QuizAnswer>
)

data class QuizAnswer(
    val questionId: String,
    val selectedOptionId: String,
    val isCorrect: Boolean,
    val answeredAt: Long
)

data class AssessmentFeedback(
    val id: String,
    val assessmentId: String,
    val expertId: String,
    val pillar: ESGPillar,
    val feedback: String,
    val recommendations: String,
    val createdAt: Long
)
