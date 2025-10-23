package com.ignitech.esgcompanion.domain.repository

import com.ignitech.esgcompanion.common.Result
import com.ignitech.esgcompanion.domain.entity.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers(): Flow<Result<List<User>>>
    fun searchUsers(query: String): Flow<Result<List<User>>>
    fun getUserById(id: String): Flow<Result<User?>>
    suspend fun updateUser(user: User): Result<Unit>
    suspend fun deleteUser(id: String): Result<Unit>
}

