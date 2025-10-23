package com.ignitech.esgcompanion.data.local.dao

import androidx.room.*
import com.ignitech.esgcompanion.data.entity.ESGScoreResult
import com.ignitech.esgcompanion.data.entity.PillarScore

data class PillarScoreRaw(
    val pillar: String,
    val averageScore: Double
)

@Dao
interface ESGScoresDao {
    @Query("""
        SELECT 
            AVG(totalScore * 100.0 / maxScore) as averageScore,
            COUNT(*) as assessmentCount
        FROM esg_assessments 
        WHERE userId = :userId AND status = 'COMPLETED'
    """)
    suspend fun getOverallESGScore(userId: String): ESGScoreResult?
    
    @Query("""
        SELECT 
            pillar as pillar,
            AVG(totalScore * 100.0 / maxScore) as averageScore
        FROM esg_assessments 
        WHERE userId = :userId AND status = 'COMPLETED'
        GROUP BY pillar
    """)
    suspend fun getESGScoresByPillar(userId: String): List<PillarScoreRaw>
    
    @Query("""
        SELECT 
            pillar as pillar,
            AVG(totalScore * 100.0 / maxScore) as averageScore
        FROM esg_assessments 
        WHERE userId = :userId AND status = 'COMPLETED'
        AND completedAt >= :fromTimestamp
        GROUP BY pillar
    """)
    suspend fun getRecentESGScoresByPillar(userId: String, fromTimestamp: Long): List<PillarScoreRaw>
}
