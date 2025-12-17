package com.ignitech.esgcompanion.data.repository

import com.ignitech.esgcompanion.common.Result
import com.ignitech.esgcompanion.data.local.dao.UserDao
import com.ignitech.esgcompanion.data.local.PreferencesManager
import com.ignitech.esgcompanion.data.mapper.UserMapper
import com.ignitech.esgcompanion.domain.entity.User
import com.ignitech.esgcompanion.domain.entity.UserRole
import com.ignitech.esgcompanion.domain.entity.SubscriptionPlan
import com.ignitech.esgcompanion.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val userMapper: UserMapper,
    private val preferencesManager: PreferencesManager
) : AuthRepository {
    
    override fun login(email: String, password: String): Flow<Result<User?>> = flow {
        try {
            emit(Result.Loading)
            android.util.Log.d("AuthRepositoryImpl", "Attempting login for email: $email")
            
            // Tìm user theo email
            val user = userDao.getUserByEmail(email)
            android.util.Log.d("AuthRepositoryImpl", "Found user: $user")
            
            if (user == null) {
                // User không tồn tại
                android.util.Log.d("AuthRepositoryImpl", "User not found")
                emit(Result.Error(Exception("Tài khoản không tồn tại")))
            } else if (user.password != password) {
                // Password không đúng
                android.util.Log.d("AuthRepositoryImpl", "Password incorrect")
                emit(Result.Error(Exception("Mật khẩu không chính xác")))
            } else {
                // Login thành công - set isActive = true
                android.util.Log.d("AuthRepositoryImpl", "Login successful, setting isActive = true")
                userDao.setCurrentUser(user.id) // Set isActive = true
                // Save user ID to preferences for session persistence
                preferencesManager.setCurrentUserId(user.id)
                android.util.Log.d("AuthRepositoryImpl", "Login successful for user: ${user.name}")
                emit(Result.Success(userMapper.toDomain(user)))
            }
        } catch (e: Exception) {
            android.util.Log.e("AuthRepositoryImpl", "Login error", e)
            emit(Result.Error(e))
        }
    }
    
    override fun register(
        email: String,
        password: String,
        name: String,
        role: UserRole
    ): Flow<Result<User?>> = flow {
        try {
            emit(Result.Loading)
            val existingUser = userDao.getUserByEmail(email)
            if (existingUser != null) {
                emit(Result.Success(null)) // User already exists
            } else {
                val newUser = User(
                    id = System.currentTimeMillis().toString(),
                    email = email,
                    password = password,
                    name = name,
                    role = role,
                    subscriptionPlan = SubscriptionPlan.FREE,
                    createdAt = System.currentTimeMillis()
                )
                val userEntity = userMapper.toEntity(newUser)
                userDao.insertUser(userEntity)
                userDao.setCurrentUser(newUser.id)
                // Save user ID to preferences for session persistence
                preferencesManager.setCurrentUserId(newUser.id)
                emit(Result.Success(newUser))
            }
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }
    
    override fun getCurrentUser(): Flow<Result<User?>> = flow {
        try {
            emit(Result.Loading)
            android.util.Log.d("AuthRepositoryImpl", "Getting current user...")
            
            // First try to get current user from database
            var currentUser = userDao.getCurrentUser()
            android.util.Log.d("AuthRepositoryImpl", "Current user from database: $currentUser")
            
            // If no current user in database, try to restore from preferences
            if (currentUser == null) {
                val savedUserId = preferencesManager.getCurrentUserId()
                android.util.Log.d("AuthRepositoryImpl", "Saved user ID from preferences: $savedUserId")
                if (savedUserId != null) {
                    // Check if user exists before restoring
                    val userExists = userDao.getUserById(savedUserId)
                    if (userExists != null) {
                        // Restore user session
                        userDao.setCurrentUser(savedUserId)
                        currentUser = userDao.getCurrentUser()
                        android.util.Log.d("AuthRepositoryImpl", "Restored user from preferences: $currentUser")
                    } else {
                        android.util.Log.d("AuthRepositoryImpl", "User with ID $savedUserId not found, clearing preferences")
                        preferencesManager.clearCurrentUserId()
                    }
                }
            }
            
            if (currentUser != null) {
                android.util.Log.d("AuthRepositoryImpl", "Returning current user: ${currentUser.name}")
                emit(Result.Success(userMapper.toDomain(currentUser)))
            } else {
                android.util.Log.d("AuthRepositoryImpl", "No current user found")
                emit(Result.Success(null))
            }
        } catch (e: Exception) {
            android.util.Log.e("AuthRepositoryImpl", "Error getting current user", e)
            emit(Result.Error(e))
        }
    }
    
    override suspend fun logout(): Result<Unit> {
        return try {
            android.util.Log.d("AuthRepositoryImpl", "Logging out user")
            userDao.clearCurrentUser() // Set isActive = false for all users
            preferencesManager.clearCurrentUserId() // Clear user ID from preferences
            android.util.Log.d("AuthRepositoryImpl", "Logout successful")
            Result.Success(Unit)
        } catch (e: Exception) {
            android.util.Log.e("AuthRepositoryImpl", "Logout error", e)
            Result.Error(e)
        }
    }
}

