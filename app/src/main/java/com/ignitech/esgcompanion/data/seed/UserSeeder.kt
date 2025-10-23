package com.ignitech.esgcompanion.data.seed

import com.ignitech.esgcompanion.data.local.entity.UserEntity
import com.ignitech.esgcompanion.domain.entity.SubscriptionPlan
import com.ignitech.esgcompanion.domain.entity.UserRole

object UserSeeder {
    
    fun getDemoUsers(): List<UserEntity> {
        return listOf(
            UserEntity(
                id = "user_1",
                email = "student@example.com",
                password = "password123",
                name = "Nguyễn Văn A",
                role = UserRole.ACADEMIC,
                companyId = null,
                companyName = null,
                subscriptionPlan = SubscriptionPlan.FREE,
                avatarUrl = null,
                createdAt = System.currentTimeMillis(),
                lastLoginAt = System.currentTimeMillis(),
                isActive = true
            ),
            UserEntity(
                id = "user_2",
                email = "instructor@example.com",
                password = "password123",
                name = "Trần Thị B",
                role = UserRole.INSTRUCTOR,
                companyId = null,
                companyName = "Đại học ABC",
                subscriptionPlan = SubscriptionPlan.PRO,
                avatarUrl = null,
                createdAt = System.currentTimeMillis(),
                lastLoginAt = System.currentTimeMillis(),
                isActive = false
            ),
            UserEntity(
                id = "user_3",
                email = "enterprise@example.com",
                password = "password123",
                name = "Công ty XYZ",
                role = UserRole.ENTERPRISE,
                companyId = "company_1",
                companyName = "Công ty TNHH XYZ",
                subscriptionPlan = SubscriptionPlan.ENTERPRISE,
                avatarUrl = null,
                createdAt = System.currentTimeMillis(),
                lastLoginAt = System.currentTimeMillis(),
                isActive = false
            ),
            UserEntity(
                id = "user_4",
                email = "expert@example.com",
                password = "password123",
                name = "Dr. Nguyễn Văn An",
                role = UserRole.EXPERT,
                companyId = null,
                companyName = "Chuyên gia ESG độc lập",
                subscriptionPlan = SubscriptionPlan.PRO,
                avatarUrl = null,
                createdAt = System.currentTimeMillis(),
                lastLoginAt = System.currentTimeMillis(),
                isActive = true
            )
        )
    }
}