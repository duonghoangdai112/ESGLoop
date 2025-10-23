package com.ignitech.esgcompanion.data.repository

import com.ignitech.esgcompanion.common.Result
import com.ignitech.esgcompanion.data.local.dao.UserDao
import com.ignitech.esgcompanion.data.mapper.UserMapper
import com.ignitech.esgcompanion.domain.entity.User
import com.ignitech.esgcompanion.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val userMapper: UserMapper
) : UserRepository {
    
    override fun getUsers(): Flow<Result<List<User>>> = flow {
        try {
            emit(Result.Loading)
            userDao.getAllUsers().collect { userEntities ->
                val users = userEntities.map { userMapper.toDomain(it) }
                emit(Result.Success(users))
            }
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }
    
    override fun searchUsers(query: String): Flow<Result<List<User>>> = flow {
        try {
            emit(Result.Loading)
            userDao.searchUsers(query).collect { userEntities ->
                val users = userEntities.map { userMapper.toDomain(it) }
                emit(Result.Success(users))
            }
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }
    
    override fun getUserById(id: String): Flow<Result<User?>> = flow {
        try {
            emit(Result.Loading)
            val user = userDao.getUserById(id)?.let { userMapper.toDomain(it) }
            emit(Result.Success(user))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }
    
    override suspend fun updateUser(user: User): Result<Unit> {
        return try {
            val userEntity = userMapper.toEntity(user)
            userDao.updateUser(userEntity)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
    
    override suspend fun deleteUser(id: String): Result<Unit> {
        return try {
            userDao.deleteUser(id)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}

