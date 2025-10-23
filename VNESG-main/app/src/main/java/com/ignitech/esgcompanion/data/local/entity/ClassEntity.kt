package com.ignitech.esgcompanion.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "classes")
data class ClassEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val code: String,
    val description: String,
    val status: String, // ACTIVE, INACTIVE, COMPLETED
    val studentCount: Int,
    val assignmentCount: Int,
    val averageScore: Int,
    val instructorId: String,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
