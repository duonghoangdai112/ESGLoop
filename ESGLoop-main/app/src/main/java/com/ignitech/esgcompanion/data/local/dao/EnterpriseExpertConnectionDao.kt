package com.ignitech.esgcompanion.data.local.dao

import androidx.room.*
import com.ignitech.esgcompanion.data.local.entity.EnterpriseExpertConnectionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EnterpriseExpertConnectionDao {
    
    @Query("SELECT * FROM enterprise_expert_connections WHERE enterpriseId = :enterpriseId")
    fun getConnectionsByEnterprise(enterpriseId: String): Flow<List<EnterpriseExpertConnectionEntity>>
    
    @Query("SELECT * FROM enterprise_expert_connections WHERE enterpriseId = :enterpriseId AND connectionStatus = 'connected'")
    fun getConnectedExperts(enterpriseId: String): Flow<List<EnterpriseExpertConnectionEntity>>
    
    @Query("SELECT * FROM enterprise_expert_connections WHERE enterpriseId = :enterpriseId AND connectionStatus = 'pending'")
    fun getPendingConnections(enterpriseId: String): Flow<List<EnterpriseExpertConnectionEntity>>
    
    @Query("SELECT * FROM enterprise_expert_connections WHERE enterpriseId = :enterpriseId AND expertId = :expertId")
    suspend fun getConnection(enterpriseId: String, expertId: String): EnterpriseExpertConnectionEntity?
    
    @Query("SELECT COUNT(*) FROM enterprise_expert_connections WHERE enterpriseId = :enterpriseId AND connectionStatus = 'connected'")
    suspend fun getConnectedCount(enterpriseId: String): Int
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConnection(connection: EnterpriseExpertConnectionEntity)
    
    @Update
    suspend fun updateConnection(connection: EnterpriseExpertConnectionEntity)
    
    @Delete
    suspend fun deleteConnection(connection: EnterpriseExpertConnectionEntity)
    
    @Query("DELETE FROM enterprise_expert_connections WHERE enterpriseId = :enterpriseId")
    suspend fun deleteAllConnectionsForEnterprise(enterpriseId: String)
    
    @Query("DELETE FROM enterprise_expert_connections")
    suspend fun deleteAllConnections()
}
