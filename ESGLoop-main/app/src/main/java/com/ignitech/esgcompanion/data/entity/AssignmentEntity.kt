package com.ignitech.esgcompanion.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "assignments")
data class AssignmentEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val detailedDescription: String,
    val dueDate: String,
    val status: AssignmentStatus,
    val type: AssignmentType,
    val difficulty: AssignmentDifficulty,
    val estimatedTime: Int, // in minutes
    val maxScore: Int,
    val currentScore: Int? = null,
    val instructions: List<String>,
    val requirements: List<String>,
    val resources: List<AssignmentResource>,
    val submissionType: SubmissionType,
    val createdAt: Date,
    val updatedAt: Date
)

data class AssignmentResource(
    val id: String,
    val title: String,
    val type: ResourceType,
    val url: String? = null,
    val content: String? = null
)

enum class AssignmentStatus {
    NOT_STARTED,
    IN_PROGRESS,
    SUBMITTED,
    GRADED
}

enum class AssignmentType {
    INDIVIDUAL,
    GROUP,
    PROJECT,
    REPORT,
    PRESENTATION
}

enum class AssignmentDifficulty {
    EASY,
    MEDIUM,
    HARD
}

enum class ResourceType {
    DOCUMENT,
    VIDEO,
    LINK,
    TEMPLATE,
    EXAMPLE
}

enum class SubmissionType {
    FILE_UPLOAD,
    TEXT_INPUT,
    MULTIPLE_CHOICE,
    ESSAY
}

// Sample data
object AssignmentSampleData {
    val assignments = com.ignitech.esgcompanion.data.seed.ESGAssignmentSeeder.getESGAssignments()
}
