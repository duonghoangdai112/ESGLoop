package com.ignitech.esgcompanion.data.seed

import com.ignitech.esgcompanion.data.entity.ReportEntity
import com.ignitech.esgcompanion.domain.entity.ReportStandard
import com.ignitech.esgcompanion.domain.entity.ReportStatus
import java.util.*

object ReportSeeder {
    
    fun getDemoReports(): List<ReportEntity> {
        val currentTime = System.currentTimeMillis()
        
        return listOf(
            // Enterprise User 1 Reports
            ReportEntity(
                id = "report_1",
                title = "Báo cáo ESG Quý 1/2024",
                description = "Báo cáo tổng hợp kết quả ESG quý 1 năm 2024",
                status = ReportStatus.PUBLISHED,
                standard = ReportStandard.GRI,
                template = "GRI",
                createdAt = currentTime - 86400000L * 30, // 30 days ago
                updatedAt = currentTime - 86400000L * 25,
                publishedAt = currentTime - 86400000L * 25,
                userId = "enterprise_user_1",
                isDraft = false
            ),
            ReportEntity(
                id = "report_2",
                title = "Báo cáo ESG Quý 2/2024",
                description = "Báo cáo tổng hợp kết quả ESG quý 2 năm 2024",
                status = ReportStatus.DRAFT,
                standard = ReportStandard.SASB,
                template = "SASB",
                createdAt = currentTime - 86400000L * 15, // 15 days ago
                updatedAt = currentTime - 86400000L * 3,
                publishedAt = null,
                userId = "enterprise_user_1",
                isDraft = true
            ),
            ReportEntity(
                id = "report_3",
                title = "Báo cáo ESG Năm 2023",
                description = "Báo cáo tổng hợp kết quả ESG năm 2023",
                status = ReportStatus.APPROVED,
                standard = ReportStandard.VN_ENVIRONMENT,
                template = "VN_ENVIRONMENT",
                createdAt = currentTime - 86400000L * 90, // 90 days ago
                updatedAt = currentTime - 86400000L * 85,
                publishedAt = currentTime - 86400000L * 85,
                userId = "enterprise_user_1",
                isDraft = false
            ),
            ReportEntity(
                id = "report_4",
                title = "Báo cáo ESG Tùy chỉnh",
                description = "Báo cáo ESG theo yêu cầu riêng của doanh nghiệp",
                status = ReportStatus.IN_REVIEW,
                standard = ReportStandard.CUSTOM,
                template = "CUSTOM",
                createdAt = currentTime - 86400000L * 7, // 7 days ago
                updatedAt = currentTime - 86400000L * 1,
                publishedAt = null,
                userId = "enterprise_user_1",
                isDraft = true
            ),
            
            // Enterprise User 2 Reports
            ReportEntity(
                id = "report_5",
                title = "Báo cáo ESG Công ty Công nghệ Xanh",
                description = "Báo cáo ESG của công ty công nghệ",
                status = ReportStatus.PUBLISHED,
                standard = ReportStandard.GRI,
                template = "GRI",
                createdAt = currentTime - 86400000L * 20,
                updatedAt = currentTime - 86400000L * 18,
                publishedAt = currentTime - 86400000L * 18,
                userId = "enterprise_user_2",
                isDraft = false
            ),
            
            // Expert User 1 Reports
            ReportEntity(
                id = "report_6",
                title = "Báo cáo Đánh giá ESG Chuyên gia",
                description = "Báo cáo đánh giá ESG từ chuyên gia",
                status = ReportStatus.PUBLISHED,
                standard = ReportStandard.GRI,
                template = "GRI",
                createdAt = currentTime - 86400000L * 45,
                updatedAt = currentTime - 86400000L * 40,
                publishedAt = currentTime - 86400000L * 40,
                userId = "expert_user_1",
                isDraft = false
            )
        )
    }
}
