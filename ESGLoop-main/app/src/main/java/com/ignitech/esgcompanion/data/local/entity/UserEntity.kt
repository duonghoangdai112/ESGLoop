package com.ignitech.esgcompanion.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ignitech.esgcompanion.domain.entity.SubscriptionPlan
import com.ignitech.esgcompanion.domain.entity.UserRole

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: String,
    val email: String,
    val password: String,
    val name: String,
    val role: UserRole,
    val companyId: String? = null,
    val companyName: String? = null,
    val subscriptionPlan: SubscriptionPlan = SubscriptionPlan.FREE,
    val avatarUrl: String? = null,
    val createdAt: Long,
    val lastLoginAt: Long? = null,
    val isActive: Boolean = true
)
