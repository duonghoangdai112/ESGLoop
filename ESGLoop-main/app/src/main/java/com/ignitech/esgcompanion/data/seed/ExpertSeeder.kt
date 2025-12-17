package com.ignitech.esgcompanion.data.seed

import com.ignitech.esgcompanion.data.local.entity.ExpertEntity

object ExpertSeeder {
    
    fun getExpertData(): List<ExpertEntity> {
        return listOf(
            ExpertEntity(
                id = "expert_001",
                name = "Dr. Nguyễn Văn An",
                specialization = "Chuyên gia ESG ngân hàng",
                rating = 4.9f,
                hourlyRate = 500,
                experience = "15 năm kinh nghiệm",
                isAvailable = true,
                description = "Chuyên gia hàng đầu về ESG trong lĩnh vực ngân hàng với hơn 15 năm kinh nghiệm. Đã tư vấn cho hơn 50 ngân hàng lớn tại Việt Nam và khu vực Đông Nam Á.",
                education = "Tiến sĩ Kinh tế - Đại học Harvard, Thạc sĩ Tài chính - Đại học Stanford",
                certifications = "CFA, FRM, GRI Standards, SASB Standards",
                languages = "Tiếng Việt, Tiếng Anh, Tiếng Pháp",
                responseTime = "Trong vòng 2 giờ",
                completedProjects = 127
            ),
            ExpertEntity(
                id = "expert_002",
                name = "Ms. Trần Thị Bình",
                specialization = "Tư vấn bền vững môi trường",
                rating = 4.8f,
                hourlyRate = 400,
                experience = "12 năm kinh nghiệm",
                isAvailable = true,
                description = "Chuyên gia về bền vững môi trường và quản lý rủi ro khí hậu. Có kinh nghiệm sâu về carbon footprint và circular economy.",
                education = "Thạc sĩ Khoa học Môi trường - Đại học Melbourne, Cử nhân Hóa học - Đại học Quốc gia Hà Nội",
                certifications = "LEED AP, ISO 14001 Lead Auditor, CDP Climate Change",
                languages = "Tiếng Việt, Tiếng Anh",
                responseTime = "Trong vòng 4 giờ",
                completedProjects = 89
            ),
            ExpertEntity(
                id = "expert_003",
                name = "Mr. Lê Văn Cường",
                specialization = "Quản trị doanh nghiệp ESG",
                rating = 4.7f,
                hourlyRate = 450,
                experience = "10 năm kinh nghiệm",
                isAvailable = true,
                description = "Chuyên gia về quản trị doanh nghiệp và tuân thủ ESG. Có kinh nghiệm xây dựng hệ thống quản lý ESG cho các doanh nghiệp lớn.",
                education = "MBA - INSEAD, Cử nhân Quản trị Kinh doanh - Đại học Kinh tế TP.HCM",
                certifications = "CIA, CISA, COSO Framework, King IV",
                languages = "Tiếng Việt, Tiếng Anh, Tiếng Nhật",
                responseTime = "Trong vòng 3 giờ",
                completedProjects = 95
            ),
            ExpertEntity(
                id = "expert_004",
                name = "Dr. Phạm Thị Dung",
                specialization = "Đánh giá rủi ro ESG",
                rating = 4.6f,
                hourlyRate = 350,
                experience = "8 năm kinh nghiệm",
                isAvailable = true,
                description = "Chuyên gia về đánh giá và quản lý rủi ro ESG. Có kinh nghiệm xây dựng mô hình định lượng rủi ro ESG cho ngân hàng.",
                education = "Tiến sĩ Tài chính - Đại học London School of Economics, Thạc sĩ Toán học - Đại học Bách khoa Hà Nội",
                certifications = "FRM, PRM, CFA, GARP",
                languages = "Tiếng Việt, Tiếng Anh",
                responseTime = "Trong vòng 6 giờ",
                completedProjects = 67
            ),
            ExpertEntity(
                id = "expert_005",
                name = "Mr. Hoàng Văn Em",
                specialization = "Báo cáo bền vững",
                rating = 4.5f,
                hourlyRate = 300,
                experience = "6 năm kinh nghiệm",
                isAvailable = true,
                description = "Chuyên gia về báo cáo bền vững và minh bạch thông tin ESG. Có kinh nghiệm chuẩn bị báo cáo theo GRI, SASB, TCFD.",
                education = "Thạc sĩ Kế toán - Đại học RMIT, Cử nhân Kế toán - Đại học Kinh tế Quốc dân",
                certifications = "CPA, GRI Standards, SASB FSA, TCFD",
                languages = "Tiếng Việt, Tiếng Anh",
                responseTime = "Trong vòng 8 giờ",
                completedProjects = 54
            ),
            ExpertEntity(
                id = "expert_006",
                name = "Ms. Vũ Thị Phương",
                specialization = "Tuân thủ pháp lý ESG",
                rating = 4.4f,
                hourlyRate = 320,
                experience = "7 năm kinh nghiệm",
                isAvailable = true,
                description = "Chuyên gia về tuân thủ pháp lý và quy định ESG. Có kinh nghiệm tư vấn cho các doanh nghiệp về các yêu cầu pháp lý ESG mới.",
                education = "Thạc sĩ Luật - Đại học Luật Hà Nội, Cử nhân Luật Kinh tế - Đại học Luật TP.HCM",
                certifications = "Luật sư, GRI Standards, ISO 37001",
                languages = "Tiếng Việt, Tiếng Anh, Tiếng Trung",
                responseTime = "Trong vòng 5 giờ",
                completedProjects = 72
            ),
            ExpertEntity(
                id = "expert_007",
                name = "Dr. Đặng Văn Giang",
                specialization = "Đầu tư bền vững",
                rating = 4.8f,
                hourlyRate = 480,
                experience = "11 năm kinh nghiệm",
                isAvailable = true,
                description = "Chuyên gia về đầu tư bền vững và tài chính xanh. Có kinh nghiệm quản lý quỹ ESG và tư vấn chiến lược đầu tư bền vững.",
                education = "Tiến sĩ Tài chính - Đại học Oxford, MBA - Wharton School",
                certifications = "CFA, CAIA, PRI Signatory, UN PRI",
                languages = "Tiếng Việt, Tiếng Anh, Tiếng Đức",
                responseTime = "Trong vòng 2 giờ",
                completedProjects = 103
            ),
            ExpertEntity(
                id = "expert_008",
                name = "Ms. Ngô Thị Hoa",
                specialization = "Nhân quyền và lao động",
                rating = 4.3f,
                hourlyRate = 280,
                experience = "5 năm kinh nghiệm",
                isAvailable = true,
                description = "Chuyên gia về nhân quyền, lao động và đa dạng hóa. Có kinh nghiệm xây dựng chính sách nhân sự bền vững và tuân thủ quyền lao động.",
                education = "Thạc sĩ Nhân quyền - Đại học Columbia, Cử nhân Xã hội học - Đại học Khoa học Xã hội và Nhân văn",
                certifications = "SHRM-CP, ILO Standards, UN Global Compact",
                languages = "Tiếng Việt, Tiếng Anh",
                responseTime = "Trong vòng 6 giờ",
                completedProjects = 41
            ),
            ExpertEntity(
                id = "expert_009",
                name = "Mr. Bùi Văn Inh",
                specialization = "Công nghệ xanh",
                rating = 4.6f,
                hourlyRate = 380,
                experience = "9 năm kinh nghiệm",
                isAvailable = true,
                description = "Chuyên gia về công nghệ xanh và chuyển đổi số bền vững. Có kinh nghiệm tư vấn về Fintech xanh và blockchain cho ESG.",
                education = "Thạc sĩ Công nghệ Thông tin - MIT, Cử nhân Khoa học Máy tính - Đại học Bách khoa TP.HCM",
                certifications = "AWS Solutions Architect, Google Cloud Professional, Microsoft Azure",
                languages = "Tiếng Việt, Tiếng Anh, Tiếng Nhật",
                responseTime = "Trong vòng 4 giờ",
                completedProjects = 78
            ),
            ExpertEntity(
                id = "expert_010",
                name = "Dr. Lý Thị Kim",
                specialization = "Kinh tế tuần hoàn",
                rating = 4.7f,
                hourlyRate = 420,
                experience = "13 năm kinh nghiệm",
                isAvailable = true,
                description = "Chuyên gia về kinh tế tuần hoàn và quản lý tài nguyên. Có kinh nghiệm xây dựng mô hình kinh doanh tuần hoàn cho các doanh nghiệp.",
                education = "Tiến sĩ Kinh tế Môi trường - Đại học Cambridge, Thạc sĩ Kinh tế - Đại học Kinh tế Quốc dân",
                certifications = "Cradle to Cradle, Ellen MacArthur Foundation, Circular Economy",
                languages = "Tiếng Việt, Tiếng Anh, Tiếng Pháp",
                responseTime = "Trong vòng 3 giờ",
                completedProjects = 86
            )
        )
    }
}
