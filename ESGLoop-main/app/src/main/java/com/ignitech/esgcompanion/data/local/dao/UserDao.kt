package com.ignitech.esgcompanion.data.local.dao

import androidx.room.*
import com.ignitech.esgcompanion.data.local.entity.UserEntity
import com.ignitech.esgcompanion.domain.entity.UserRole
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: String): UserEntity?
    
    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserByEmail(email: String): UserEntity?
    
    @Query("SELECT * FROM users WHERE role = :role")
    fun getUsersByRole(role: UserRole): Flow<List<UserEntity>>
    
    @Query("SELECT * FROM users WHERE isActive = 1")
    fun getAllUsers(): Flow<List<UserEntity>>
    
    @Query("SELECT * FROM users WHERE name LIKE '%' || :query || '%' OR email LIKE '%' || :query || '%'")
    fun searchUsers(query: String): Flow<List<UserEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserEntity>)
    
    @Update
    suspend fun updateUser(user: UserEntity)
    
    @Query("DELETE FROM users WHERE id = :userId")
    suspend fun deleteUser(userId: String)
    
    @Query("UPDATE users SET lastLoginAt = :loginTime WHERE id = :userId")
    suspend fun updateLastLogin(userId: String, loginTime: Long)
    
    @Query("SELECT * FROM users WHERE isActive = 1 LIMIT 1")
    suspend fun getCurrentUser(): UserEntity?
    
    @Query("UPDATE users SET isActive = 0")
    suspend fun clearCurrentUser()
    
    @Query("UPDATE users SET isActive = 1 WHERE id = :userId")
    suspend fun setCurrentUser(userId: String)
    
    @Query("DELETE FROM users")
    suspend fun clearUser()
}
