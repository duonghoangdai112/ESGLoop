package com.ignitech.esgcompanion.data.seed

import com.ignitech.esgcompanion.data.local.entity.EnterpriseExpertConnectionEntity
import java.util.UUID

object EnterpriseExpertConnectionSeeder {
    
    fun getConnectionData(enterpriseId: String): List<EnterpriseExpertConnectionEntity> {
        return listOf(
            // Đã kết nối
            EnterpriseExpertConnectionEntity(
                id = UUID.randomUUID().toString(),
                enterpriseId = enterpriseId,
                expertId = "expert_001", // Dr. Nguyễn Văn An
                connectionStatus = "connected",
                connectedAt = System.currentTimeMillis() - (7 * 24 * 60 * 60 * 1000L), // 7 ngày trước
                lastInteractionAt = System.currentTimeMillis() - (2 * 24 * 60 * 60 * 1000L), // 2 ngày trước
                totalConsultations = 3,
                totalQuestions = 8,
                rating = 4.8f,
                notes = "Chuyên gia rất nhiệt tình và chuyên nghiệp"
            ),
            EnterpriseExpertConnectionEntity(
                id = UUID.randomUUID().toString(),
                enterpriseId = enterpriseId,
                expertId = "expert_002", // Ms. Trần Thị Bình
                connectionStatus = "connected",
                connectedAt = System.currentTimeMillis() - (14 * 24 * 60 * 60 * 1000L), // 14 ngày trước
                lastInteractionAt = System.currentTimeMillis() - (5 * 24 * 60 * 60 * 1000L), // 5 ngày trước
                totalConsultations = 2,
                totalQuestions = 5,
                rating = 4.9f,
                notes = "Tư vấn về môi trường rất chi tiết"
            ),
            EnterpriseExpertConnectionEntity(
                id = UUID.randomUUID().toString(),
                enterpriseId = enterpriseId,
                expertId = "expert_003", // Mr. Lê Văn Cường
                connectionStatus = "connected",
                connectedAt = System.currentTimeMillis() - (21 * 24 * 60 * 60 * 1000L), // 21 ngày trước
                lastInteractionAt = System.currentTimeMillis() - (1 * 24 * 60 * 60 * 1000L), // 1 ngày trước
                totalConsultations = 5,
                totalQuestions = 12,
                rating = 4.7f,
                notes = "Hỗ trợ quản trị doanh nghiệp rất tốt"
            ),
            
            // Đang chờ phản hồi
            EnterpriseExpertConnectionEntity(
                id = UUID.randomUUID().toString(),
                enterpriseId = enterpriseId,
                expertId = "expert_004", // Dr. Phạm Thị Dung
                connectionStatus = "pending",
                connectedAt = null,
                lastInteractionAt = null,
                totalConsultations = 0,
                totalQuestions = 0,
                rating = null,
                notes = "Đã gửi yêu cầu kết nối"
            ),
            EnterpriseExpertConnectionEntity(
                id = UUID.randomUUID().toString(),
                enterpriseId = enterpriseId,
                expertId = "expert_007", // Dr. Đặng Văn Giang
                connectionStatus = "pending",
                connectedAt = null,
                lastInteractionAt = null,
                totalConsultations = 0,
                totalQuestions = 0,
                rating = null,
                notes = "Chờ phản hồi từ chuyên gia"
            )
        )
    }
}
