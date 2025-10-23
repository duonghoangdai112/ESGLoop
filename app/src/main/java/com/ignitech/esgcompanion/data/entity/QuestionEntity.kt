package com.ignitech.esgcompanion.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "assignment_questions")
data class QuestionEntity(
    @PrimaryKey
    val id: String,
    val assignmentId: String,
    val question: String,
    val type: QuestionType,
    val options: List<String>?,
    val correctAnswer: String?,
    val explanation: String? = null
)

enum class QuestionType {
    MULTIPLE_CHOICE,
    TRUE_FALSE,
    ESSAY
}
