package com.ignitech.esgcompanion.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.ignitech.esgcompanion.data.converter.ESGTypeConverters
import com.ignitech.esgcompanion.domain.entity.ReportStandard
import com.ignitech.esgcompanion.domain.entity.ReportStatus

@Entity(tableName = "reports")
@TypeConverters(ESGTypeConverters::class)
data class ReportEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val status: ReportStatus,
    val standard: ReportStandard,
    val template: String, // JSON string of template configuration
    val createdAt: Long,
    val updatedAt: Long,
    val publishedAt: Long? = null,
    val userId: String,
    val version: Int = 1,
    val isDraft: Boolean = true
)

@Entity(tableName = "report_sections")
@TypeConverters(ESGTypeConverters::class)
data class ReportSectionEntity(
    @PrimaryKey val id: String,
    val reportId: String,
    val sectionId: String, // ID from template (e.g., "1", "2", "3")
    val title: String,
    val description: String,
    val isCompleted: Boolean = false,
    val completedAt: Long? = null,
    val data: String, // JSON string of form data
    val createdAt: Long,
    val updatedAt: Long
)

@Entity(tableName = "report_fields")
@TypeConverters(ESGTypeConverters::class)
data class ReportFieldEntity(
    @PrimaryKey val id: String,
    val reportId: String,
    val sectionId: String,
    val fieldId: String,
    val fieldType: String, // TEXT, NUMBER, DATE, SELECT, etc.
    val label: String,
    val value: String,
    val isRequired: Boolean = false,
    val options: String? = null, // JSON string of options for SELECT fields
    val createdAt: Long,
    val updatedAt: Long
)
