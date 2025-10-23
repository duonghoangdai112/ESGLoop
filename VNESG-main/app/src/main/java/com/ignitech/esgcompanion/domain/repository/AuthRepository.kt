package com.ignitech.esgcompanion.domain.repository

import com.ignitech.esgcompanion.common.Result
import com.ignitech.esgcompanion.domain.entity.User
import com.ignitech.esgcompanion.domain.entity.UserRole
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(email: String, password: String): Flow<Result<User?>>
    fun register(email: String, password: String, name: String, role: UserRole): Flow<Result<User?>>
    fun getCurrentUser(): Flow<Result<User?>>
    suspend fun logout(): Result<Unit>
}
