package com.ignitech.esgcompanion.data.seed

import com.ignitech.esgcompanion.data.local.entity.ESGAssessmentEntity
import com.ignitech.esgcompanion.domain.entity.AssessmentStatus
import com.ignitech.esgcompanion.domain.entity.ESGPillar

object AssessmentSeeder {
    
    fun getDemoAssessments(): List<ESGAssessmentEntity> {
        val currentTime = System.currentTimeMillis()
        
        return listOf(
            // Environmental Assessments
            ESGAssessmentEntity(
                id = "env_assessment_1",
                userId = "enterprise_user_1",
                title = "Đánh giá Môi trường Q4 2024",
                description = "Đánh giá tác động môi trường quý 4",
                pillar = ESGPillar.ENVIRONMENTAL,
                status = AssessmentStatus.COMPLETED,
                totalScore = 85,
                maxScore = 100,
                completedAt = currentTime - 86400000L * 3, // 3 days ago
                createdAt = currentTime - 86400000L * 5,
                answersJson = "{}"
            ),
            ESGAssessmentEntity(
                id = "env_assessment_2",
                userId = "enterprise_user_1",
                title = "Đánh giá Môi trường Q3 2024",
                description = "Đánh giá tác động môi trường quý 3",
                pillar = ESGPillar.ENVIRONMENTAL,
                status = AssessmentStatus.COMPLETED,
                totalScore = 78,
                maxScore = 100,
                completedAt = currentTime - 86400000L * 30, // 30 days ago
                createdAt = currentTime - 86400000L * 35,
                answersJson = "{}"
            ),
            
            // Social Assessments
            ESGAssessmentEntity(
                id = "social_assessment_1",
                userId = "enterprise_user_1",
                title = "Đánh giá Xã hội Q4 2024",
                description = "Đánh giá tác động xã hội quý 4",
                pillar = ESGPillar.SOCIAL,
                status = AssessmentStatus.COMPLETED,
                totalScore = 72,
                maxScore = 100,
                completedAt = currentTime - 86400000L * 5, // 5 days ago
                createdAt = currentTime - 86400000L * 7,
                answersJson = "{}"
            ),
            ESGAssessmentEntity(
                id = "social_assessment_2",
                userId = "enterprise_user_1",
                title = "Đánh giá Xã hội Q3 2024",
                description = "Đánh giá tác động xã hội quý 3",
                pillar = ESGPillar.SOCIAL,
                status = AssessmentStatus.COMPLETED,
                totalScore = 68,
                maxScore = 100,
                completedAt = currentTime - 86400000L * 25, // 25 days ago
                createdAt = currentTime - 86400000L * 30,
                answersJson = "{}"
            ),
            
            // Governance Assessments
            ESGAssessmentEntity(
                id = "gov_assessment_1",
                userId = "enterprise_user_1",
                title = "Đánh giá Quản trị Q4 2024",
                description = "Đánh giá quản trị doanh nghiệp quý 4",
                pillar = ESGPillar.GOVERNANCE,
                status = AssessmentStatus.COMPLETED,
                totalScore = 92,
                maxScore = 100,
                completedAt = currentTime - 86400000L * 2, // 2 days ago
                createdAt = currentTime - 86400000L * 4,
                answersJson = "{}"
            ),
            ESGAssessmentEntity(
                id = "gov_assessment_2",
                userId = "enterprise_user_1",
                title = "Đánh giá Quản trị Q3 2024",
                description = "Đánh giá quản trị doanh nghiệp quý 3",
                pillar = ESGPillar.GOVERNANCE,
                status = AssessmentStatus.COMPLETED,
                totalScore = 88,
                maxScore = 100,
                completedAt = currentTime - 86400000L * 28, // 28 days ago
                createdAt = currentTime - 86400000L * 32,
                answersJson = "{}"
            ),
            
            // Incomplete assessments (should not be counted)
            ESGAssessmentEntity(
                id = "env_assessment_incomplete",
                userId = "enterprise_user_1",
                title = "Đánh giá Môi trường Q1 2025",
                description = "Đánh giá tác động môi trường quý 1 (chưa hoàn thành)",
                pillar = ESGPillar.ENVIRONMENTAL,
                status = AssessmentStatus.IN_PROGRESS,
                totalScore = 45,
                maxScore = 100,
                completedAt = null,
                createdAt = currentTime - 86400000L * 1, // 1 day ago
                answersJson = "{}"
            )
        )
    }
}
