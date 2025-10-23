package com.ignitech.esgcompanion.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "enterprises")
data class EnterpriseEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val industry: String,
    val location: String,
    val description: String,
    val size: String,
    val esgScore: Float,
    val establishedYear: Int,
    val website: String,
    val phone: String,
    val email: String,
    val employeeCount: Int,
    val revenue: String,
    val certification: String // ESG certifications
)

