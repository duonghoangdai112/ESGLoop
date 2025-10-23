package com.ignitech.esgcompanion.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "enterprise_expert_connections")
data class EnterpriseExpertConnectionEntity(
    @PrimaryKey
    val id: String,
    val enterpriseId: String,
    val expertId: String,
    val connectionStatus: String, // "pending", "connected", "rejected", "blocked"
    val connectedAt: Long? = null,
    val lastInteractionAt: Long? = null,
    val totalConsultations: Int = 0,
    val totalQuestions: Int = 0,
    val rating: Float? = null,
    val notes: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
