package com.ignitech.esgcompanion.data.local.dao

import androidx.room.*
import com.ignitech.esgcompanion.data.local.entity.EnterpriseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EnterpriseDao {
    
    @Query("SELECT * FROM enterprises ORDER BY name ASC")
    fun getAllEnterprises(): Flow<List<EnterpriseEntity>>
    
    @Query("SELECT * FROM enterprises WHERE id = :enterpriseId")
    suspend fun getEnterpriseById(enterpriseId: String): EnterpriseEntity?
    
    @Query("SELECT * FROM enterprises WHERE industry = :industry ORDER BY name ASC")
    fun getEnterprisesByIndustry(industry: String): Flow<List<EnterpriseEntity>>
    
    @Query("SELECT * FROM enterprises WHERE location LIKE '%' || :location || '%' ORDER BY name ASC")
    fun getEnterprisesByLocation(location: String): Flow<List<EnterpriseEntity>>
    
    @Query("SELECT * FROM enterprises WHERE esgScore >= :minScore ORDER BY esgScore DESC")
    fun getEnterprisesByMinScore(minScore: Float): Flow<List<EnterpriseEntity>>
    
    @Query("SELECT * FROM enterprises WHERE name LIKE '%' || :query || '%' OR industry LIKE '%' || :query || '%' ORDER BY name ASC")
    fun searchEnterprises(query: String): Flow<List<EnterpriseEntity>>
    
    @Query("SELECT DISTINCT industry FROM enterprises ORDER BY industry ASC")
    fun getAllIndustries(): Flow<List<String>>
    
    @Query("SELECT DISTINCT location FROM enterprises ORDER BY location ASC")
    fun getAllLocations(): Flow<List<String>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEnterprise(enterprise: EnterpriseEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEnterprises(enterprises: List<EnterpriseEntity>)
    
    @Update
    suspend fun updateEnterprise(enterprise: EnterpriseEntity)
    
    @Delete
    suspend fun deleteEnterprise(enterprise: EnterpriseEntity)
    
    @Query("DELETE FROM enterprises")
    suspend fun deleteAllEnterprises()
    
    @Query("SELECT COUNT(*) FROM enterprises")
    suspend fun getEnterpriseCount(): Int
}

