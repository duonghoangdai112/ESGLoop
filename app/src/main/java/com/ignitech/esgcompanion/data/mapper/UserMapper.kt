package com.ignitech.esgcompanion.data.mapper

import com.ignitech.esgcompanion.data.local.entity.UserEntity
import com.ignitech.esgcompanion.domain.entity.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserMapper @Inject constructor() {
    
    fun toDomain(entity: UserEntity): User {
        return User(
            id = entity.id,
            email = entity.email,
            password = entity.password,
            name = entity.name,
            role = entity.role,
            companyId = entity.companyId,
            companyName = entity.companyName,
            subscriptionPlan = entity.subscriptionPlan,
            avatarUrl = entity.avatarUrl,
            createdAt = entity.createdAt,
            lastLoginAt = entity.lastLoginAt,
            isActive = entity.isActive
        )
    }
    
    fun toEntity(domain: User): UserEntity {
        return UserEntity(
            id = domain.id,
            email = domain.email,
            password = domain.password,
            name = domain.name,
            role = domain.role,
            companyId = domain.companyId,
            companyName = domain.companyName,
            subscriptionPlan = domain.subscriptionPlan,
            avatarUrl = domain.avatarUrl,
            createdAt = domain.createdAt,
            lastLoginAt = domain.lastLoginAt,
            isActive = domain.isActive
        )
    }
}
