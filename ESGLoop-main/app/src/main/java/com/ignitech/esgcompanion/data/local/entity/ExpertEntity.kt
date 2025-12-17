package com.ignitech.esgcompanion.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "experts")
data class ExpertEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val specialization: String,
    val rating: Float,
    val hourlyRate: Int,
    val experience: String,
    val isAvailable: Boolean = true,
    val description: String = "",
    val education: String = "",
    val certifications: String = "",
    val languages: String = "",
    val responseTime: String = "",
    val completedProjects: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
)
