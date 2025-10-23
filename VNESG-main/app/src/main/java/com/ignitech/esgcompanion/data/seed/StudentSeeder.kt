package com.ignitech.esgcompanion.data.seed

import com.ignitech.esgcompanion.data.local.entity.StudentEntity

object StudentSeeder {
    fun getStudents(): List<StudentEntity> {
        return listOf(
            // Lớp ESG-101 (ESG và Phát triển Bền vững) - 45 học sinh
            StudentEntity(
                id = "student_001",
                name = "Nguyễn Văn An",
                email = "an.nguyen@student.com",
                studentId = "SV001",
                classId = "class_001",
                averageScore = 88,
                completedAssignments = 8,
                totalAssignments = 8,
                status = "ACTIVE"
            ),
            StudentEntity(
                id = "student_002",
                name = "Trần Thị Bình",
                email = "binh.tran@student.com",
                studentId = "SV002",
                classId = "class_001",
                averageScore = 92,
                completedAssignments = 8,
                totalAssignments = 8,
                status = "ACTIVE"
            ),
            StudentEntity(
                id = "student_003",
                name = "Lê Văn Cường",
                email = "cuong.le@student.com",
                studentId = "SV003",
                classId = "class_001",
                averageScore = 75,
                completedAssignments = 7,
                totalAssignments = 8,
                status = "ACTIVE"
            ),
            StudentEntity(
                id = "student_004",
                name = "Phạm Thị Dung",
                email = "dung.pham@student.com",
                studentId = "SV004",
                classId = "class_001",
                averageScore = 85,
                completedAssignments = 8,
                totalAssignments = 8,
                status = "ACTIVE"
            ),
            StudentEntity(
                id = "student_005",
                name = "Hoàng Văn Em",
                email = "em.hoang@student.com",
                studentId = "SV005",
                classId = "class_001",
                averageScore = 90,
                completedAssignments = 8,
                totalAssignments = 8,
                status = "ACTIVE"
            ),
            
            // Lớp ESG-201 (Quản lý Rủi ro ESG) - 32 học sinh
            StudentEntity(
                id = "student_006",
                name = "Vũ Thị Phương",
                email = "phuong.vu@student.com",
                studentId = "SV006",
                classId = "class_002",
                averageScore = 82,
                completedAssignments = 10,
                totalAssignments = 12,
                status = "ACTIVE"
            ),
            StudentEntity(
                id = "student_007",
                name = "Đặng Văn Giang",
                email = "giang.dang@student.com",
                studentId = "SV007",
                classId = "class_002",
                averageScore = 78,
                completedAssignments = 11,
                totalAssignments = 12,
                status = "ACTIVE"
            ),
            StudentEntity(
                id = "student_008",
                name = "Bùi Thị Hoa",
                email = "hoa.bui@student.com",
                studentId = "SV008",
                classId = "class_002",
                averageScore = 85,
                completedAssignments = 12,
                totalAssignments = 12,
                status = "ACTIVE"
            ),
            StudentEntity(
                id = "student_009",
                name = "Phan Văn Khoa",
                email = "khoa.phan@student.com",
                studentId = "SV009",
                classId = "class_002",
                averageScore = 80,
                completedAssignments = 10,
                totalAssignments = 12,
                status = "ACTIVE"
            ),
            StudentEntity(
                id = "student_010",
                name = "Lý Thị Lan",
                email = "lan.ly@student.com",
                studentId = "SV010",
                classId = "class_002",
                averageScore = 88,
                completedAssignments = 11,
                totalAssignments = 12,
                status = "ACTIVE"
            ),
            
            // Lớp ESG-301 (Báo cáo ESG Thực hành) - 28 học sinh (đã hoàn thành)
            StudentEntity(
                id = "student_011",
                name = "Ngô Văn Minh",
                email = "minh.ngo@student.com",
                studentId = "SV011",
                classId = "class_003",
                averageScore = 95,
                completedAssignments = 15,
                totalAssignments = 15,
                status = "GRADUATED"
            ),
            StudentEntity(
                id = "student_012",
                name = "Đinh Thị Nga",
                email = "nga.dinh@student.com",
                studentId = "SV012",
                classId = "class_003",
                averageScore = 90,
                completedAssignments = 15,
                totalAssignments = 15,
                status = "GRADUATED"
            ),
            StudentEntity(
                id = "student_013",
                name = "Tạ Văn Oanh",
                email = "oanh.ta@student.com",
                studentId = "SV013",
                classId = "class_003",
                averageScore = 88,
                completedAssignments = 14,
                totalAssignments = 15,
                status = "GRADUATED"
            ),
            StudentEntity(
                id = "student_014",
                name = "Võ Thị Phúc",
                email = "phuc.vo@student.com",
                studentId = "SV014",
                classId = "class_003",
                averageScore = 92,
                completedAssignments = 15,
                totalAssignments = 15,
                status = "GRADUATED"
            ),
            StudentEntity(
                id = "student_015",
                name = "Hồ Văn Quang",
                email = "quang.ho@student.com",
                studentId = "SV015",
                classId = "class_003",
                averageScore = 87,
                completedAssignments = 15,
                totalAssignments = 15,
                status = "GRADUATED"
            ),
            
            // Lớp ESG-501 (Phân tích ESG cho Doanh nghiệp) - 38 học sinh
            StudentEntity(
                id = "student_016",
                name = "Lương Thị Rồng",
                email = "rong.luong@student.com",
                studentId = "SV016",
                classId = "class_005",
                averageScore = 89,
                completedAssignments = 8,
                totalAssignments = 10,
                status = "ACTIVE"
            ),
            StudentEntity(
                id = "student_017",
                name = "Chu Văn Sơn",
                email = "son.chu@student.com",
                studentId = "SV017",
                classId = "class_005",
                averageScore = 85,
                completedAssignments = 9,
                totalAssignments = 10,
                status = "ACTIVE"
            ),
            StudentEntity(
                id = "student_018",
                name = "Trịnh Thị Tâm",
                email = "tam.trinh@student.com",
                studentId = "SV018",
                classId = "class_005",
                averageScore = 91,
                completedAssignments = 10,
                totalAssignments = 10,
                status = "ACTIVE"
            ),
            StudentEntity(
                id = "student_019",
                name = "Lưu Văn Uy",
                email = "uy.luu@student.com",
                studentId = "SV019",
                classId = "class_005",
                averageScore = 83,
                completedAssignments = 8,
                totalAssignments = 10,
                status = "ACTIVE"
            ),
            StudentEntity(
                id = "student_020",
                name = "Nguyễn Thị Vân",
                email = "van.nguyen@student.com",
                studentId = "SV020",
                classId = "class_005",
                averageScore = 87,
                completedAssignments = 9,
                totalAssignments = 10,
                status = "ACTIVE"
            ),
            
            // Lớp ESG-601 (Chứng chỉ ESG Quốc tế) - 25 học sinh
            StudentEntity(
                id = "student_021",
                name = "Đỗ Văn Xuyên",
                email = "xuyen.do@student.com",
                studentId = "SV021",
                classId = "class_006",
                averageScore = 94,
                completedAssignments = 5,
                totalAssignments = 6,
                status = "ACTIVE"
            ),
            StudentEntity(
                id = "student_022",
                name = "Bùi Thị Yến",
                email = "yen.bui@student.com",
                studentId = "SV022",
                classId = "class_006",
                averageScore = 91,
                completedAssignments = 6,
                totalAssignments = 6,
                status = "ACTIVE"
            ),
            StudentEntity(
                id = "student_023",
                name = "Phạm Văn Zũng",
                email = "zung.pham@student.com",
                studentId = "SV023",
                classId = "class_006",
                averageScore = 88,
                completedAssignments = 5,
                totalAssignments = 6,
                status = "ACTIVE"
            ),
            StudentEntity(
                id = "student_024",
                name = "Lê Thị Ánh",
                email = "anh.le@student.com",
                studentId = "SV024",
                classId = "class_006",
                averageScore = 93,
                completedAssignments = 6,
                totalAssignments = 6,
                status = "ACTIVE"
            ),
            StudentEntity(
                id = "student_025",
                name = "Hoàng Văn Bảo",
                email = "bao.hoang@student.com",
                studentId = "SV025",
                classId = "class_006",
                averageScore = 90,
                completedAssignments = 6,
                totalAssignments = 6,
                status = "ACTIVE"
            ),
            
            // Lớp ESG-701 (ESG và Đầu tư Bền vững) - 35 học sinh (đã hoàn thành)
            StudentEntity(
                id = "student_026",
                name = "Vũ Thị Cẩm",
                email = "cam.vu@student.com",
                studentId = "SV026",
                classId = "class_007",
                averageScore = 89,
                completedAssignments = 9,
                totalAssignments = 9,
                status = "GRADUATED"
            ),
            StudentEntity(
                id = "student_027",
                name = "Đặng Văn Dũng",
                email = "dung.dang@student.com",
                studentId = "SV027",
                classId = "class_007",
                averageScore = 86,
                completedAssignments = 9,
                totalAssignments = 9,
                status = "GRADUATED"
            ),
            StudentEntity(
                id = "student_028",
                name = "Bùi Thị Em",
                email = "em.bui@student.com",
                studentId = "SV028",
                classId = "class_007",
                averageScore = 92,
                completedAssignments = 9,
                totalAssignments = 9,
                status = "GRADUATED"
            ),
            StudentEntity(
                id = "student_029",
                name = "Phan Văn Phong",
                email = "phong.phan@student.com",
                studentId = "SV029",
                classId = "class_007",
                averageScore = 84,
                completedAssignments = 8,
                totalAssignments = 9,
                status = "GRADUATED"
            ),
            StudentEntity(
                id = "student_030",
                name = "Lý Thị Gia",
                email = "gia.ly@student.com",
                studentId = "SV030",
                classId = "class_007",
                averageScore = 91,
                completedAssignments = 9,
                totalAssignments = 9,
                status = "GRADUATED"
            ),
            
            // Lớp ESG-801 (Quản trị ESG trong Công ty) - 42 học sinh
            StudentEntity(
                id = "student_031",
                name = "Ngô Văn Hùng",
                email = "hung.ngo@student.com",
                studentId = "SV031",
                classId = "class_008",
                averageScore = 86,
                completedAssignments = 9,
                totalAssignments = 11,
                status = "ACTIVE"
            ),
            StudentEntity(
                id = "student_032",
                name = "Đinh Thị Iên",
                email = "ien.dinh@student.com",
                studentId = "SV032",
                classId = "class_008",
                averageScore = 88,
                completedAssignments = 10,
                totalAssignments = 11,
                status = "ACTIVE"
            ),
            StudentEntity(
                id = "student_033",
                name = "Tạ Văn Khoa",
                email = "khoa.ta@student.com",
                studentId = "SV033",
                classId = "class_008",
                averageScore = 82,
                completedAssignments = 8,
                totalAssignments = 11,
                status = "ACTIVE"
            ),
            StudentEntity(
                id = "student_034",
                name = "Võ Thị Linh",
                email = "linh.vo@student.com",
                studentId = "SV034",
                classId = "class_008",
                averageScore = 90,
                completedAssignments = 11,
                totalAssignments = 11,
                status = "ACTIVE"
            ),
            StudentEntity(
                id = "student_035",
                name = "Hồ Văn Mạnh",
                email = "manh.ho@student.com",
                studentId = "SV035",
                classId = "class_008",
                averageScore = 85,
                completedAssignments = 9,
                totalAssignments = 11,
                status = "ACTIVE"
            )
        )
    }
}