package com.ignitech.esgcompanion.data.seed

import com.ignitech.esgcompanion.data.entity.*
import com.ignitech.esgcompanion.data.local.entity.ESGQuestionEntity
import com.ignitech.esgcompanion.domain.entity.ESGPillar
import com.ignitech.esgcompanion.domain.entity.UserRole

object ESGStudentQuestionSeeder {
    
    fun getStudentQuestions(): List<ESGQuestionEntity> {
        return listOf(
            // ENVIRONMENTAL PILLAR - Student Focus
            // Climate Awareness
            ESGQuestionEntity(
                id = "student_env_climate_001",
                pillar = ESGPillar.ENVIRONMENTAL,
                category = "Climate Awareness",
                question = "Bạn có hiểu biết về biến đổi khí hậu và tác động của nó không?",
                description = "Đánh giá kiến thức về biến đổi khí hậu, nguyên nhân và hậu quả",
                optionsJson = """[
                    {"id": "opt1", "text": "Rất hiểu biết", "score": 4, "description": "Hiểu rõ nguyên nhân và tác động"},
                    {"id": "opt2", "text": "Hiểu biết cơ bản", "score": 3, "description": "Biết một số khái niệm cơ bản"},
                    {"id": "opt3", "text": "Hiểu biết ít", "score": 2, "description": "Chỉ biết tên gọi"},
                    {"id": "opt4", "text": "Không hiểu biết", "score": 1, "description": "Chưa từng nghe đến"}
                ]""",
                weight = 4,
                isRequired = true,
                userRole = UserRole.ACADEMIC,
                order = 1
            ),
            ESGQuestionEntity(
                id = "student_env_climate_002",
                pillar = ESGPillar.ENVIRONMENTAL,
                category = "Climate Awareness",
                question = "Bạn có thực hiện các hành động tiết kiệm năng lượng không?",
                description = "Đánh giá việc tiết kiệm điện, nước và các nguồn năng lượng khác",
                optionsJson = """[
                    {"id": "opt1", "text": "Thường xuyên", "score": 4, "description": "Luôn thực hiện tiết kiệm năng lượng"},
                    {"id": "opt2", "text": "Thỉnh thoảng", "score": 3, "description": "Có thực hiện khi nhớ"},
                    {"id": "opt3", "text": "Hiếm khi", "score": 2, "description": "Ít khi thực hiện"},
                    {"id": "opt4", "text": "Không bao giờ", "score": 1, "description": "Chưa từng thực hiện"}
                ]""",
                weight = 3,
                isRequired = true,
                userRole = UserRole.ACADEMIC,
                order = 2
            ),
            
            // Waste Reduction
            ESGQuestionEntity(
                id = "student_env_waste_001",
                pillar = ESGPillar.ENVIRONMENTAL,
                category = "Waste Reduction",
                question = "Bạn có thực hành giảm thiểu rác thải không?",
                description = "Đánh giá việc giảm thiểu, tái sử dụng và tái chế rác thải",
                optionsJson = """[
                    {"id": "opt1", "text": "Rất tích cực", "score": 4, "description": "Luôn giảm thiểu và tái chế"},
                    {"id": "opt2", "text": "Tích cực", "score": 3, "description": "Thường xuyên thực hiện"},
                    {"id": "opt3", "text": "Ít tích cực", "score": 2, "description": "Thỉnh thoảng thực hiện"},
                    {"id": "opt4", "text": "Không tích cực", "score": 1, "description": "Hiếm khi thực hiện"}
                ]""",
                weight = 3,
                isRequired = true,
                userRole = UserRole.ACADEMIC,
                order = 1
            ),
            ESGQuestionEntity(
                id = "student_env_waste_002",
                pillar = ESGPillar.ENVIRONMENTAL,
                category = "Waste Reduction",
                question = "Bạn có sử dụng túi vải thay vì túi nilon không?",
                description = "Đánh giá việc sử dụng túi thân thiện với môi trường",
                optionsJson = """[
                    {"id": "opt1", "text": "Luôn luôn", "score": 4, "description": "Luôn mang túi vải khi đi mua sắm"},
                    {"id": "opt2", "text": "Thường xuyên", "score": 3, "description": "Thường mang túi vải"},
                    {"id": "opt3", "text": "Thỉnh thoảng", "score": 2, "description": "Đôi khi mang túi vải"},
                    {"id": "opt4", "text": "Hiếm khi", "score": 1, "description": "Ít khi mang túi vải"}
                ]""",
                weight = 2,
                isRequired = false,
                userRole = UserRole.ACADEMIC,
                order = 2
            ),
            
            // SOCIAL PILLAR - Student Focus
            // Community Engagement
            ESGQuestionEntity(
                id = "student_soc_community_001",
                pillar = ESGPillar.SOCIAL,
                category = "Community Engagement",
                question = "Bạn có tham gia các hoạt động tình nguyện không?",
                description = "Đánh giá mức độ tham gia hoạt động cộng đồng",
                optionsJson = """[
                    {"id": "opt1", "text": "Rất tích cực", "score": 4, "description": "Tham gia thường xuyên"},
                    {"id": "opt2", "text": "Tích cực", "score": 3, "description": "Tham gia định kỳ"},
                    {"id": "opt3", "text": "Ít tích cực", "score": 2, "description": "Tham gia thỉnh thoảng"},
                    {"id": "opt4", "text": "Không tham gia", "score": 1, "description": "Chưa từng tham gia"}
                ]""",
                weight = 3,
                isRequired = true,
                userRole = UserRole.ACADEMIC,
                order = 1
            ),
            ESGQuestionEntity(
                id = "student_soc_community_002",
                pillar = ESGPillar.SOCIAL,
                category = "Community Engagement",
                question = "Bạn có quan tâm đến các vấn đề xã hội không?",
                description = "Đánh giá mức độ quan tâm đến các vấn đề xã hội",
                optionsJson = """[
                    {"id": "opt1", "text": "Rất quan tâm", "score": 4, "description": "Theo dõi và thảo luận thường xuyên"},
                    {"id": "opt2", "text": "Quan tâm", "score": 3, "description": "Có theo dõi và quan tâm"},
                    {"id": "opt3", "text": "Ít quan tâm", "score": 2, "description": "Thỉnh thoảng quan tâm"},
                    {"id": "opt4", "text": "Không quan tâm", "score": 1, "description": "Ít khi quan tâm"}
                ]""",
                weight = 2,
                isRequired = false,
                userRole = UserRole.ACADEMIC,
                order = 2
            ),
            
            // Diversity & Inclusion
            ESGQuestionEntity(
                id = "student_soc_diversity_001",
                pillar = ESGPillar.SOCIAL,
                category = "Diversity & Inclusion",
                question = "Bạn có tôn trọng sự đa dạng và hòa nhập không?",
                description = "Đánh giá thái độ đối với sự đa dạng văn hóa, tôn giáo, giới tính",
                optionsJson = """[
                    {"id": "opt1", "text": "Rất tôn trọng", "score": 4, "description": "Luôn tôn trọng và ủng hộ"},
                    {"id": "opt2", "text": "Tôn trọng", "score": 3, "description": "Thường tôn trọng"},
                    {"id": "opt3", "text": "Ít tôn trọng", "score": 2, "description": "Thỉnh thoảng tôn trọng"},
                    {"id": "opt4", "text": "Không tôn trọng", "score": 1, "description": "Ít khi tôn trọng"}
                ]""",
                weight = 3,
                isRequired = true,
                userRole = UserRole.ACADEMIC,
                order = 1
            ),
            
            // GOVERNANCE PILLAR - Student Focus
            // Academic Integrity
            ESGQuestionEntity(
                id = "student_gov_integrity_001",
                pillar = ESGPillar.GOVERNANCE,
                category = "Academic Integrity",
                question = "Bạn có tuân thủ quy tắc học thuật không?",
                description = "Đánh giá việc tuân thủ quy tắc học thuật, không gian lận",
                optionsJson = """[
                    {"id": "opt1", "text": "Luôn tuân thủ", "score": 4, "description": "Luôn tuân thủ nghiêm ngặt"},
                    {"id": "opt2", "text": "Thường tuân thủ", "score": 3, "description": "Thường tuân thủ"},
                    {"id": "opt3", "text": "Ít tuân thủ", "score": 2, "description": "Thỉnh thoảng tuân thủ"},
                    {"id": "opt4", "text": "Không tuân thủ", "score": 1, "description": "Ít khi tuân thủ"}
                ]""",
                weight = 4,
                isRequired = true,
                userRole = UserRole.ACADEMIC,
                order = 1
            ),
            ESGQuestionEntity(
                id = "student_gov_integrity_002",
                pillar = ESGPillar.GOVERNANCE,
                category = "Academic Integrity",
                question = "Bạn có báo cáo các hành vi gian lận không?",
                description = "Đánh giá việc báo cáo các hành vi gian lận trong học tập",
                optionsJson = """[
                    {"id": "opt1", "text": "Luôn báo cáo", "score": 4, "description": "Luôn báo cáo khi phát hiện"},
                    {"id": "opt2", "text": "Thường báo cáo", "score": 3, "description": "Thường báo cáo"},
                    {"id": "opt3", "text": "Ít báo cáo", "score": 2, "description": "Thỉnh thoảng báo cáo"},
                    {"id": "opt4", "text": "Không báo cáo", "score": 1, "description": "Ít khi báo cáo"}
                ]""",
                weight = 3,
                isRequired = false,
                userRole = UserRole.ACADEMIC,
                order = 2
            ),
            
            // Transparency
            ESGQuestionEntity(
                id = "student_gov_transparency_001",
                pillar = ESGPillar.GOVERNANCE,
                category = "Transparency",
                question = "Bạn có minh bạch trong học tập không?",
                description = "Đánh giá tính minh bạch trong quá trình học tập",
                optionsJson = """[
                    {"id": "opt1", "text": "Rất minh bạch", "score": 4, "description": "Luôn minh bạch và công khai"},
                    {"id": "opt2", "text": "Minh bạch", "score": 3, "description": "Thường minh bạch"},
                    {"id": "opt3", "text": "Ít minh bạch", "score": 2, "description": "Thỉnh thoảng minh bạch"},
                    {"id": "opt4", "text": "Không minh bạch", "score": 1, "description": "Ít khi minh bạch"}
                ]""",
                weight = 2,
                isRequired = false,
                userRole = UserRole.ACADEMIC,
                order = 1
            )
        )
    }
    
    fun getStudentCategories(): List<ESGCategoryEntity> {
        return listOf(
            ESGCategoryEntity(
                id = "student_env_climate",
                name = "Climate Awareness",
                description = "Kiến thức về biến đổi khí hậu và hành động",
                pillar = ESGPillar.ENVIRONMENTAL,
                order = 1
            ),
            ESGCategoryEntity(
                id = "student_env_waste",
                name = "Waste Reduction",
                description = "Giảm thiểu rác thải và tái chế",
                pillar = ESGPillar.ENVIRONMENTAL,
                order = 2
            ),
            ESGCategoryEntity(
                id = "student_soc_community",
                name = "Community Engagement",
                description = "Tham gia hoạt động cộng đồng",
                pillar = ESGPillar.SOCIAL,
                order = 1
            ),
            ESGCategoryEntity(
                id = "student_soc_diversity",
                name = "Diversity & Inclusion",
                description = "Đa dạng và hòa nhập",
                pillar = ESGPillar.SOCIAL,
                order = 2
            ),
            ESGCategoryEntity(
                id = "student_gov_integrity",
                name = "Academic Integrity",
                description = "Tính toàn vẹn học thuật",
                pillar = ESGPillar.GOVERNANCE,
                order = 1
            ),
            ESGCategoryEntity(
                id = "student_gov_transparency",
                name = "Transparency",
                description = "Minh bạch trong học tập",
                pillar = ESGPillar.GOVERNANCE,
                order = 2
            )
        )
    }
}