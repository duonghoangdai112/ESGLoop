package com.ignitech.esgcompanion.data.seed

import com.ignitech.esgcompanion.data.local.entity.ClassEntity

object ClassSeeder {
    fun getClasses(): List<ClassEntity> {
        return listOf(
            ClassEntity(
                id = "class_001",
                name = "ESG và Phát triển Bền vững",
                code = "ESG-101",
                description = "Khóa học cơ bản về ESG và phát triển bền vững trong doanh nghiệp",
                status = "ACTIVE",
                studentCount = 45,
                assignmentCount = 8,
                averageScore = 85,
                instructorId = "instructor_001"
            ),
            ClassEntity(
                id = "class_002",
                name = "Quản lý Rủi ro ESG",
                code = "ESG-201",
                description = "Chuyên sâu về quản lý và đánh giá rủi ro ESG",
                status = "ACTIVE",
                studentCount = 32,
                assignmentCount = 12,
                averageScore = 78,
                instructorId = "instructor_001"
            ),
            ClassEntity(
                id = "class_003",
                name = "Báo cáo ESG Thực hành",
                code = "ESG-301",
                description = "Thực hành viết báo cáo ESG theo các tiêu chuẩn quốc tế",
                status = "COMPLETED",
                studentCount = 28,
                assignmentCount = 15,
                averageScore = 92,
                instructorId = "instructor_001"
            ),
            ClassEntity(
                id = "class_004",
                name = "ESG trong Ngân hàng",
                code = "ESG-401",
                description = "Ứng dụng ESG trong lĩnh vực ngân hàng và tài chính",
                status = "INACTIVE",
                studentCount = 0,
                assignmentCount = 0,
                averageScore = 0,
                instructorId = "instructor_001"
            ),
            ClassEntity(
                id = "class_005",
                name = "Phân tích ESG cho Doanh nghiệp",
                code = "ESG-501",
                description = "Phân tích và đánh giá ESG cho các doanh nghiệp vừa và nhỏ",
                status = "ACTIVE",
                studentCount = 38,
                assignmentCount = 10,
                averageScore = 88,
                instructorId = "instructor_001"
            ),
            ClassEntity(
                id = "class_006",
                name = "Chứng chỉ ESG Quốc tế",
                code = "ESG-601",
                description = "Chuẩn bị cho các chứng chỉ ESG quốc tế như GRI, SASB",
                status = "ACTIVE",
                studentCount = 25,
                assignmentCount = 6,
                averageScore = 90,
                instructorId = "instructor_001"
            ),
            ClassEntity(
                id = "class_007",
                name = "ESG và Đầu tư Bền vững",
                code = "ESG-701",
                description = "Tìm hiểu về đầu tư bền vững và tác động ESG",
                status = "COMPLETED",
                studentCount = 35,
                assignmentCount = 9,
                averageScore = 87,
                instructorId = "instructor_001"
            ),
            ClassEntity(
                id = "class_008",
                name = "Quản trị ESG trong Công ty",
                code = "ESG-801",
                description = "Xây dựng hệ thống quản trị ESG trong tổ chức",
                status = "ACTIVE",
                studentCount = 42,
                assignmentCount = 11,
                averageScore = 83,
                instructorId = "instructor_001"
            )
        )
    }
}
