package com.ignitech.esgcompanion.data.seed

import com.ignitech.esgcompanion.data.local.entity.ESGAssessmentEntity
import com.ignitech.esgcompanion.domain.entity.ESGPillar
import com.ignitech.esgcompanion.domain.entity.AssessmentStatus
import java.util.*

object AssessmentHistorySeeder {
    
    fun getHistoricalAssessments(userId: String): List<ESGAssessmentEntity> {
        val currentTime = System.currentTimeMillis()
        val oneDay = 24 * 60 * 60 * 1000L
        
        return listOf(
            // ========== Q2-2025 (Quý hiện tại) ==========
            // Environmental
            ESGAssessmentEntity(
                id = "env_q2_2025",
                userId = userId,
                title = "Đánh giá Môi trường Q2 2025",
                description = "Đánh giá tác động môi trường quý 2 năm 2025",
                pillar = ESGPillar.ENVIRONMENTAL,
                status = AssessmentStatus.COMPLETED,
                totalScore = 88,
                maxScore = 100,
                completedAt = currentTime - oneDay * 5,
                createdAt = currentTime - oneDay * 10,
                assessmentPeriod = "Q2-2025",
                assessmentYear = 2025,
                assessmentQuarter = 2,
                isHistorical = false
            ),
            // Social
            ESGAssessmentEntity(
                id = "social_q2_2025",
                userId = userId,
                title = "Đánh giá Xã hội Q2 2025",
                description = "Đánh giá tác động xã hội quý 2 năm 2025",
                pillar = ESGPillar.SOCIAL,
                status = AssessmentStatus.COMPLETED,
                totalScore = 92,
                maxScore = 100,
                completedAt = currentTime - oneDay * 3,
                createdAt = currentTime - oneDay * 8,
                assessmentPeriod = "Q2-2025",
                assessmentYear = 2025,
                assessmentQuarter = 2,
                isHistorical = false
            ),
            // Governance
            ESGAssessmentEntity(
                id = "gov_q2_2025",
                userId = userId,
                title = "Đánh giá Quản trị Q2 2025",
                description = "Đánh giá quản trị doanh nghiệp quý 2 năm 2025",
                pillar = ESGPillar.GOVERNANCE,
                status = AssessmentStatus.COMPLETED,
                totalScore = 90,
                maxScore = 100,
                completedAt = currentTime - oneDay * 2,
                createdAt = currentTime - oneDay * 7,
                assessmentPeriod = "Q2-2025",
                assessmentYear = 2025,
                assessmentQuarter = 2,
                isHistorical = false
            ),
            
            // ========== Q1-2025 ==========
            // Environmental
            ESGAssessmentEntity(
                id = "env_q1_2025",
                userId = userId,
                title = "Đánh giá Môi trường Q1 2025",
                description = "Đánh giá tác động môi trường quý 1 năm 2025",
                pillar = ESGPillar.ENVIRONMENTAL,
                status = AssessmentStatus.COMPLETED,
                totalScore = 85,
                maxScore = 100,
                completedAt = currentTime - oneDay * 95,
                createdAt = currentTime - oneDay * 100,
                assessmentPeriod = "Q1-2025",
                assessmentYear = 2025,
                assessmentQuarter = 1,
                isHistorical = true
            ),
            // Social
            ESGAssessmentEntity(
                id = "social_q1_2025",
                userId = userId,
                title = "Đánh giá Xã hội Q1 2025",
                description = "Đánh giá tác động xã hội quý 1 năm 2025",
                pillar = ESGPillar.SOCIAL,
                status = AssessmentStatus.COMPLETED,
                totalScore = 90,
                maxScore = 100,
                completedAt = currentTime - oneDay * 93,
                createdAt = currentTime - oneDay * 98,
                assessmentPeriod = "Q1-2025",
                assessmentYear = 2025,
                assessmentQuarter = 1,
                isHistorical = true
            ),
            // Governance
            ESGAssessmentEntity(
                id = "gov_q1_2025",
                userId = userId,
                title = "Đánh giá Quản trị Q1 2025",
                description = "Đánh giá quản trị doanh nghiệp quý 1 năm 2025",
                pillar = ESGPillar.GOVERNANCE,
                status = AssessmentStatus.COMPLETED,
                totalScore = 87,
                maxScore = 100,
                completedAt = currentTime - oneDay * 92,
                createdAt = currentTime - oneDay * 97,
                assessmentPeriod = "Q1-2025",
                assessmentYear = 2025,
                assessmentQuarter = 1,
                isHistorical = true
            ),
            
            // ========== Q4-2024 ==========
            // Environmental
            ESGAssessmentEntity(
                id = "env_q4_2024",
                userId = userId,
                title = "Đánh giá Môi trường Q4 2024",
                description = "Đánh giá tác động môi trường quý 4 năm 2024",
                pillar = ESGPillar.ENVIRONMENTAL,
                status = AssessmentStatus.COMPLETED,
                totalScore = 82,
                maxScore = 100,
                completedAt = currentTime - oneDay * 185,
                createdAt = currentTime - oneDay * 190,
                assessmentPeriod = "Q4-2024",
                assessmentYear = 2024,
                assessmentQuarter = 4,
                isHistorical = true
            ),
            // Social
            ESGAssessmentEntity(
                id = "social_q4_2024",
                userId = userId,
                title = "Đánh giá Xã hội Q4 2024",
                description = "Đánh giá tác động xã hội quý 4 năm 2024",
                pillar = ESGPillar.SOCIAL,
                status = AssessmentStatus.COMPLETED,
                totalScore = 88,
                maxScore = 100,
                completedAt = currentTime - oneDay * 183,
                createdAt = currentTime - oneDay * 188,
                assessmentPeriod = "Q4-2024",
                assessmentYear = 2024,
                assessmentQuarter = 4,
                isHistorical = true
            ),
            // Governance
            ESGAssessmentEntity(
                id = "gov_q4_2024",
                userId = userId,
                title = "Đánh giá Quản trị Q4 2024",
                description = "Đánh giá quản trị doanh nghiệp quý 4 năm 2024",
                pillar = ESGPillar.GOVERNANCE,
                status = AssessmentStatus.COMPLETED,
                totalScore = 85,
                maxScore = 100,
                completedAt = currentTime - oneDay * 182,
                createdAt = currentTime - oneDay * 187,
                assessmentPeriod = "Q4-2024",
                assessmentYear = 2024,
                assessmentQuarter = 4,
                isHistorical = true
            ),
            
            // ========== Q3-2024 ==========
            // Environmental
            ESGAssessmentEntity(
                id = "env_q3_2024",
                userId = userId,
                title = "Đánh giá Môi trường Q3 2024",
                description = "Đánh giá tác động môi trường quý 3 năm 2024",
                pillar = ESGPillar.ENVIRONMENTAL,
                status = AssessmentStatus.COMPLETED,
                totalScore = 80,
                maxScore = 100,
                completedAt = currentTime - oneDay * 275,
                createdAt = currentTime - oneDay * 280,
                assessmentPeriod = "Q3-2024",
                assessmentYear = 2024,
                assessmentQuarter = 3,
                isHistorical = true
            ),
            // Social
            ESGAssessmentEntity(
                id = "social_q3_2024",
                userId = userId,
                title = "Đánh giá Xã hội Q3 2024",
                description = "Đánh giá tác động xã hội quý 3 năm 2024",
                pillar = ESGPillar.SOCIAL,
                status = AssessmentStatus.COMPLETED,
                totalScore = 86,
                maxScore = 100,
                completedAt = currentTime - oneDay * 273,
                createdAt = currentTime - oneDay * 278,
                assessmentPeriod = "Q3-2024",
                assessmentYear = 2024,
                assessmentQuarter = 3,
                isHistorical = true
            ),
            // Governance
            ESGAssessmentEntity(
                id = "gov_q3_2024",
                userId = userId,
                title = "Đánh giá Quản trị Q3 2024",
                description = "Đánh giá quản trị doanh nghiệp quý 3 năm 2024",
                pillar = ESGPillar.GOVERNANCE,
                status = AssessmentStatus.COMPLETED,
                totalScore = 83,
                maxScore = 100,
                completedAt = currentTime - oneDay * 272,
                createdAt = currentTime - oneDay * 277,
                assessmentPeriod = "Q3-2024",
                assessmentYear = 2024,
                assessmentQuarter = 3,
                isHistorical = true
            ),
            
            // ========== Q2-2024 ==========
            // Environmental
            ESGAssessmentEntity(
                id = "env_q2_2024",
                userId = userId,
                title = "Đánh giá Môi trường Q2 2024",
                description = "Đánh giá tác động môi trường quý 2 năm 2024",
                pillar = ESGPillar.ENVIRONMENTAL,
                status = AssessmentStatus.COMPLETED,
                totalScore = 78,
                maxScore = 100,
                completedAt = currentTime - oneDay * 365,
                createdAt = currentTime - oneDay * 370,
                assessmentPeriod = "Q2-2024",
                assessmentYear = 2024,
                assessmentQuarter = 2,
                isHistorical = true
            ),
            // Social
            ESGAssessmentEntity(
                id = "social_q2_2024",
                userId = userId,
                title = "Đánh giá Xã hội Q2 2024",
                description = "Đánh giá tác động xã hội quý 2 năm 2024",
                pillar = ESGPillar.SOCIAL,
                status = AssessmentStatus.COMPLETED,
                totalScore = 84,
                maxScore = 100,
                completedAt = currentTime - oneDay * 363,
                createdAt = currentTime - oneDay * 368,
                assessmentPeriod = "Q2-2024",
                assessmentYear = 2024,
                assessmentQuarter = 2,
                isHistorical = true
            ),
            // Governance
            ESGAssessmentEntity(
                id = "gov_q2_2024",
                userId = userId,
                title = "Đánh giá Quản trị Q2 2024",
                description = "Đánh giá quản trị doanh nghiệp quý 2 năm 2024",
                pillar = ESGPillar.GOVERNANCE,
                status = AssessmentStatus.COMPLETED,
                totalScore = 81,
                maxScore = 100,
                completedAt = currentTime - oneDay * 362,
                createdAt = currentTime - oneDay * 367,
                assessmentPeriod = "Q2-2024",
                assessmentYear = 2024,
                assessmentQuarter = 2,
                isHistorical = true
            ),
            
            // ========== Q1-2024 ==========
            // Environmental
            ESGAssessmentEntity(
                id = "env_q1_2024",
                userId = userId,
                title = "Đánh giá Môi trường Q1 2024",
                description = "Đánh giá tác động môi trường quý 1 năm 2024",
                pillar = ESGPillar.ENVIRONMENTAL,
                status = AssessmentStatus.COMPLETED,
                totalScore = 75,
                maxScore = 100,
                completedAt = currentTime - oneDay * 455,
                createdAt = currentTime - oneDay * 460,
                assessmentPeriod = "Q1-2024",
                assessmentYear = 2024,
                assessmentQuarter = 1,
                isHistorical = true
            ),
            // Social
            ESGAssessmentEntity(
                id = "social_q1_2024",
                userId = userId,
                title = "Đánh giá Xã hội Q1 2024",
                description = "Đánh giá tác động xã hội quý 1 năm 2024",
                pillar = ESGPillar.SOCIAL,
                status = AssessmentStatus.COMPLETED,
                totalScore = 82,
                maxScore = 100,
                completedAt = currentTime - oneDay * 453,
                createdAt = currentTime - oneDay * 458,
                assessmentPeriod = "Q1-2024",
                assessmentYear = 2024,
                assessmentQuarter = 1,
                isHistorical = true
            ),
            // Governance
            ESGAssessmentEntity(
                id = "gov_q1_2024",
                userId = userId,
                title = "Đánh giá Quản trị Q1 2024",
                description = "Đánh giá quản trị doanh nghiệp quý 1 năm 2024",
                pillar = ESGPillar.GOVERNANCE,
                status = AssessmentStatus.COMPLETED,
                totalScore = 79,
                maxScore = 100,
                completedAt = currentTime - oneDay * 452,
                createdAt = currentTime - oneDay * 457,
                assessmentPeriod = "Q1-2024",
                assessmentYear = 2024,
                assessmentQuarter = 1,
                isHistorical = true
            )
        )
    }
}
