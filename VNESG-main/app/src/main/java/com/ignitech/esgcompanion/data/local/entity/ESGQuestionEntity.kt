package com.ignitech.esgcompanion.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ignitech.esgcompanion.domain.entity.ESGPillar

@Entity(tableName = "esg_questions")
data class ESGQuestionEntity(
    @PrimaryKey
    val id: String,
    val pillar: ESGPillar,
    val category: String,
    val question: String,
    val description: String? = null,
    val optionsJson: String, // JSON string of ESGQuestionOption list
    val weight: Int = 1,
    val isRequired: Boolean = true,
    val userRole: com.ignitech.esgcompanion.domain.entity.UserRole = com.ignitech.esgcompanion.domain.entity.UserRole.ENTERPRISE,
    val order: Int = 0
)

@Entity(tableName = "esg_question_options")
data class ESGQuestionOptionEntity(
    @PrimaryKey
    val id: String,
    val questionId: String,
    val text: String,
    val score: Int,
    val description: String? = null
)

