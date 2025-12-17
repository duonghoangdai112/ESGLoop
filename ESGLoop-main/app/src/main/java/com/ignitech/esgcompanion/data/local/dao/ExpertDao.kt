package com.ignitech.esgcompanion.data.local.dao

import androidx.room.*
import com.ignitech.esgcompanion.data.local.entity.ExpertEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpertDao {
    @Query("SELECT * FROM experts ORDER BY rating DESC")
    fun getAllExperts(): Flow<List<ExpertEntity>>

    @Query("SELECT * FROM experts WHERE isAvailable = 1 ORDER BY rating DESC LIMIT 3")
    fun getFeaturedExperts(): Flow<List<ExpertEntity>>

    @Query("SELECT * FROM experts WHERE id = :expertId")
    suspend fun getExpertById(expertId: String): ExpertEntity?

    @Query("SELECT * FROM experts WHERE specialization LIKE '%' || :specialization || '%'")
    fun getExpertsBySpecialization(specialization: String): Flow<List<ExpertEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpert(expert: ExpertEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExperts(experts: List<ExpertEntity>)

    @Update
    suspend fun updateExpert(expert: ExpertEntity)

    @Delete
    suspend fun deleteExpert(expert: ExpertEntity)

    @Query("DELETE FROM experts")
    suspend fun deleteAllExperts()
}
