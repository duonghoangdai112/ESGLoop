package com.ignitech.esgcompanion.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ignitech.esgcompanion.domain.entity.*
import com.ignitech.esgcompanion.data.entity.AssessmentAnswer

@Entity(tableName = "esg_assessments")
data class ESGAssessmentEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val title: String,
    val description: String,
    val pillar: ESGPillar,
    val status: AssessmentStatus,
    val totalScore: Int = 0,
    val maxScore: Int = 0,
    val completedAt: Long? = null,
    val createdAt: Long,
    val updatedAt: Long = System.currentTimeMillis(),
    val assessmentPeriod: String = "", // e.g., "Q1-2024", "Q2-2024", "Q3-2024", "Q4-2024"
    val assessmentYear: Int = 2024, // 2024, 2025, etc.
    val assessmentQuarter: Int = 1, // 1, 2, 3, 4
    val isHistorical: Boolean = false, // true for past assessments, false for current
    val answersJson: String = "" // JSON string of answers
)

@Entity(tableName = "esg_answers")
data class ESGAnswerEntity(
    @PrimaryKey
    val id: String,
    val assessmentId: String,
    val questionId: String,
    val answer: AssessmentAnswer,
    val score: Int = 0,
    val answeredAt: Long = System.currentTimeMillis(),
    val notes: String = ""
)

@Entity(tableName = "quiz_attempts")
data class QuizAttemptEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val quizTitle: String,
    val totalQuestions: Int,
    val correctAnswers: Int,
    val score: Int,
    val passed: Boolean,
    val completedAt: Long,
    val answersJson: String // JSON string of quiz answers
)

@Entity(tableName = "assessment_feedbacks")
data class AssessmentFeedbackEntity(
    @PrimaryKey
    val id: String,
    val assessmentId: String,
    val expertId: String,
    val pillar: ESGPillar,
    val feedback: String,
    val recommendations: String,
    val createdAt: Long
)

