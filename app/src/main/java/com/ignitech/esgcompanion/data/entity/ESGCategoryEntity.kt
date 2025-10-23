package com.ignitech.esgcompanion.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ignitech.esgcompanion.domain.entity.ESGPillar

@Entity(tableName = "esg_categories")
data class ESGCategoryEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val pillar: ESGPillar,
    val order: Int = 0,
    val isActive: Boolean = true,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
