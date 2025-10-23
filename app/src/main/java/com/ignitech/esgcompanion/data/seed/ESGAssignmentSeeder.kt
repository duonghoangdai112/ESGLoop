package com.ignitech.esgcompanion.data.seed

import com.ignitech.esgcompanion.data.entity.*
import java.util.Date

object ESGAssignmentSeeder {
    
    fun getESGAssignments(): List<AssignmentEntity> {
        return listOf(
            // ===== BÀI TẬP 1: KIỂM TRA GIỮA KỲ (CHUẨN) =====
            AssignmentEntity(
                id = "esg_midterm_1",
                title = "Kiểm tra giữa kỳ - ESG Fundamentals",
                description = "Kiểm tra kiến thức cơ bản về ESG với 10 câu trắc nghiệm và 2 câu tự luận",
                detailedDescription = "",
                dueDate = "20/12/2024",
                status = AssignmentStatus.NOT_STARTED,
                type = AssignmentType.REPORT,
                difficulty = AssignmentDifficulty.MEDIUM,
                estimatedTime = 60, // 60 phút
                maxScore = 20,
                instructions = listOf(
                    "Đọc kỹ đề bài trước khi làm",
                    "Phần trắc nghiệm: Chọn 1 đáp án đúng nhất",
                    "Phần tự luận: Trình bày rõ ràng, có cấu trúc",
                    "Kiểm tra lại bài trước khi nộp",
                    "Nộp bài đúng thời gian quy định"
                ),
                requirements = listOf(
                    "Hoàn thành đầy đủ 10 câu trắc nghiệm",
                    "Trả lời 2 câu tự luận với độ dài phù hợp",
                    "Nộp bài trong thời gian 60 phút",
                    "Không sử dụng tài liệu tham khảo",
                    "Trình bày rõ ràng, dễ đọc"
                ),
                resources = listOf(
                    AssignmentResource(
                        id = "res_midterm_1",
                        title = "GRI Standards - Hướng dẫn báo cáo bền vững",
                        type = ResourceType.DOCUMENT,
                        url = "https://www.globalreporting.org/standards/"
                    ),
                    AssignmentResource(
                        id = "res_midterm_2",
                        title = "SASB Standards - Tiêu chuẩn kế toán bền vững",
                        type = ResourceType.DOCUMENT,
                        url = "https://www.sasb.org/standards/"
                    ),
                    AssignmentResource(
                        id = "res_midterm_3",
                        title = "TCFD Recommendations - Báo cáo rủi ro khí hậu",
                        type = ResourceType.DOCUMENT,
                        url = "https://www.fsb-tcfd.org/recommendations/"
                    )
                ),
                submissionType = SubmissionType.MULTIPLE_CHOICE,
                createdAt = Date(),
                updatedAt = Date()
            ),

            // ===== BÀI TẬP 2: THI CUỐI KỲ =====
            AssignmentEntity(
                id = "esg_final_2",
                title = "Thi Cuối Kỳ: Môn ESG (Environmental – Social – Governance)",
                description = "Thi cuối kỳ với hình thức tự luận và phân tích tình huống doanh nghiệp",
                detailedDescription = "",
                dueDate = "15/01/2025",
                status = AssignmentStatus.NOT_STARTED,
                type = AssignmentType.REPORT,
                difficulty = AssignmentDifficulty.HARD,
                estimatedTime = 90, // 90 phút
                maxScore = 20,
                instructions = listOf(
                    "Thời gian làm bài: 90 phút",
                    "Hình thức: Tự luận + Phân tích tình huống",
                    "Tổng điểm: 20 điểm",
                    "Đọc kỹ đề bài trước khi làm",
                    "Trình bày rõ ràng, có cấu trúc"
                ),
                requirements = listOf(
                    "Hoàn thành đầy đủ 3 câu tự luận ngắn (12 điểm)",
                    "Phân tích case study TechGreen (8 điểm)",
                    "Nộp bài trong thời gian 90 phút",
                    "Trình bày rõ ràng, dễ đọc",
                    "Có ví dụ minh họa cụ thể"
                ),
                resources = listOf(
                    AssignmentResource(
                        id = "res_final_1",
                        title = "UN SDGs - Sustainable Development Goals",
                        type = ResourceType.DOCUMENT,
                        url = "https://www.un.org/sustainabledevelopment/sustainable-development-goals/"
                    ),
                    AssignmentResource(
                        id = "res_final_2",
                        title = "ESG vs CSR - Key Differences",
                        type = ResourceType.DOCUMENT,
                        url = "https://www.investopedia.com/articles/investing/022516/difference-between-csr-and-esg.asp"
                    ),
                    AssignmentResource(
                        id = "res_final_3",
                        title = "ESG Risk Management Framework",
                        type = ResourceType.DOCUMENT,
                        url = "https://www.mckinsey.com/capabilities/sustainability/our-insights"
                    )
                ),
                submissionType = SubmissionType.ESSAY,
                createdAt = Date(),
                updatedAt = Date()
            )
        )
    }
    
    fun getESGQuestions(): List<QuestionEntity> {
        return listOf(
            // Câu hỏi cho bài tập 1 (Kiểm tra giữa kỳ)
            QuestionEntity(
                id = "q1_1",
                assignmentId = "esg_midterm_1",
                question = "Yếu tố nào KHÔNG nằm trong bộ tiêu chí ESG?",
                type = QuestionType.MULTIPLE_CHOICE,
                options = listOf("Môi trường", "Công nghệ", "Xã hội", "Quản trị"),
                correctAnswer = "Công nghệ",
                explanation = "ESG bao gồm 3 trụ cột: Environmental (Môi trường), Social (Xã hội), và Governance (Quản trị). Công nghệ không phải là một trụ cột của ESG."
            ),
            QuestionEntity(
                id = "q1_2",
                assignmentId = "esg_midterm_1",
                question = "Phát thải Scope 2 bao gồm:",
                type = QuestionType.MULTIPLE_CHOICE,
                options = listOf(
                    "Khí thải từ nhà máy do doanh nghiệp trực tiếp vận hành",
                    "Khí thải từ hoạt động giao hàng",
                    "Khí thải từ điện tiêu thụ được mua về",
                    "Khí thải từ khách hàng sử dụng sản phẩm"
                ),
                correctAnswer = "Khí thải từ điện tiêu thụ được mua về",
                explanation = "Scope 2 bao gồm phát thải gián tiếp từ việc mua năng lượng (điện, hơi nước, làm mát) từ nhà cung cấp bên ngoài."
            ),
            QuestionEntity(
                id = "q1_3",
                assignmentId = "esg_midterm_1",
                question = "Chính sách trả lương công bằng cho nhân viên nằm trong yếu tố nào của ESG?",
                type = QuestionType.MULTIPLE_CHOICE,
                options = listOf("Environmental", "Social", "Governance", "Tất cả đều đúng"),
                correctAnswer = "Social",
                explanation = "Chính sách trả lương công bằng thuộc về trụ cột Social (Xã hội) vì liên quan đến quyền lợi và điều kiện làm việc của nhân viên."
            ),
            QuestionEntity(
                id = "q1_4",
                assignmentId = "esg_midterm_1",
                question = "Tổ chức nào đã khởi xướng báo cáo ESG đầu tiên trên thế giới?",
                type = QuestionType.MULTIPLE_CHOICE,
                options = listOf("UNDP", "SASB", "GRI", "WEF"),
                correctAnswer = "GRI",
                explanation = "GRI (Global Reporting Initiative) được thành lập năm 1997 và là tổ chức đầu tiên phát triển khung báo cáo bền vững toàn cầu."
            ),
            QuestionEntity(
                id = "q1_5",
                assignmentId = "esg_midterm_1",
                question = "ESG được xem là yếu tố:",
                type = QuestionType.MULTIPLE_CHOICE,
                options = listOf(
                    "Bắt buộc theo pháp luật ở mọi quốc gia",
                    "Chỉ dành cho công ty lớn",
                    "Hỗ trợ doanh nghiệp phát triển bền vững",
                    "Không liên quan đến tài chính"
                ),
                correctAnswer = "Hỗ trợ doanh nghiệp phát triển bền vững",
                explanation = "ESG là khung đánh giá giúp doanh nghiệp phát triển bền vững, quản lý rủi ro và tạo giá trị dài hạn cho các bên liên quan."
            ),
            QuestionEntity(
                id = "q1_6",
                assignmentId = "esg_midterm_1",
                question = "Ví dụ nào sau đây KHÔNG thuộc yếu tố môi trường (E)?",
                type = QuestionType.MULTIPLE_CHOICE,
                options = listOf(
                    "Giảm phát thải khí CO₂",
                    "Tái chế rác thải",
                    "Cân bằng giới tính trong ban lãnh đạo",
                    "Sử dụng năng lượng tái tạo"
                ),
                correctAnswer = "Cân bằng giới tính trong ban lãnh đạo",
                explanation = "Cân bằng giới tính trong ban lãnh đạo thuộc về trụ cột Social (Xã hội), không phải Environmental (Môi trường)."
            ),
            QuestionEntity(
                id = "q1_7",
                assignmentId = "esg_midterm_1",
                question = "Trong ESG, \"governance\" KHÔNG bao gồm yếu tố nào sau đây:",
                type = QuestionType.MULTIPLE_CHOICE,
                options = listOf(
                    "Kiểm toán nội bộ",
                    "Hội đồng quản trị",
                    "Chiến dịch bảo vệ môi trường",
                    "Công khai báo cáo tài chính"
                ),
                correctAnswer = "Chiến dịch bảo vệ môi trường",
                explanation = "Chiến dịch bảo vệ môi trường thuộc về trụ cột Environmental (Môi trường), không phải Governance (Quản trị)."
            ),
            QuestionEntity(
                id = "q1_8",
                assignmentId = "esg_midterm_1",
                question = "Công ty bị phạt vì che giấu dữ liệu khí thải sẽ bị đánh giá thấp ở yếu tố nào?",
                type = QuestionType.MULTIPLE_CHOICE,
                options = listOf("Social", "Governance", "Environmental", "CSR"),
                correctAnswer = "Governance",
                explanation = "Che giấu dữ liệu vi phạm nguyên tắc minh bạch và trung thực trong quản trị doanh nghiệp, thuộc về trụ cột Governance."
            ),
            QuestionEntity(
                id = "q1_9",
                assignmentId = "esg_midterm_1",
                question = "KPI ESG nào sau đây là phù hợp với yếu tố xã hội (S)?",
                type = QuestionType.MULTIPLE_CHOICE,
                options = listOf(
                    "Lượng nước tiêu thụ",
                    "Tỷ lệ nhân viên nghỉ việc",
                    "Số lượng cổ đông lớn",
                    "Mức độ tiêu thụ điện"
                ),
                correctAnswer = "Tỷ lệ nhân viên nghỉ việc",
                explanation = "Tỷ lệ nhân viên nghỉ việc (turnover rate) là chỉ số quan trọng trong trụ cột Social, phản ánh mức độ hài lòng và gắn bó của nhân viên."
            ),
            QuestionEntity(
                id = "q1_10",
                assignmentId = "esg_midterm_1",
                question = "\"Greenwashing\" là gì?",
                type = QuestionType.MULTIPLE_CHOICE,
                options = listOf(
                    "Một công nghệ làm sạch môi trường",
                    "Một kỹ thuật truyền thông tích cực",
                    "Việc giả tạo thể hiện ESG để đánh bóng hình ảnh",
                    "Quá trình xanh hóa toàn diện doanh nghiệp"
                ),
                correctAnswer = "Việc giả tạo thể hiện ESG để đánh bóng hình ảnh",
                explanation = "Greenwashing là hành vi tạo ra ấn tượng sai lệch về cam kết môi trường hoặc ESG của doanh nghiệp để đánh bóng hình ảnh mà không có hành động thực tế."
            ),

            // Câu hỏi cho bài tập 2 (Thi cuối kỳ)
            QuestionEntity(
                id = "q2_1",
                assignmentId = "esg_final_2",
                question = "Trình bày mối quan hệ giữa ESG và Mục tiêu Phát triển Bền vững (SDGs). ESG và SDGs có gì giống và khác nhau? Làm rõ vai trò của ESG trong việc đạt được các mục tiêu SDGs do Liên Hợp Quốc đề ra. Nêu 2 ví dụ cụ thể về mục tiêu SDG nào có thể liên quan trực tiếp tới từng yếu tố E, S hoặc G.",
                type = QuestionType.ESSAY,
                options = null,
                correctAnswer = null,
                explanation = "Câu hỏi tự luận về mối quan hệ giữa ESG và SDGs, yêu cầu phân tích sâu và đưa ra ví dụ cụ thể."
            ),
            QuestionEntity(
                id = "q2_2",
                assignmentId = "esg_final_2",
                question = "Nêu và phân tích 3 rủi ro chính mà doanh nghiệp có thể gặp phải nếu không triển khai ESG đúng cách: Rủi ro về pháp lý, Rủi ro về tài chính/đầu tư, Rủi ro về uy tín/thương hiệu. Có thể dẫn chứng bằng 1-2 ví dụ thực tế nếu cần.",
                type = QuestionType.ESSAY,
                options = null,
                correctAnswer = null,
                explanation = "Câu hỏi tự luận về rủi ro ESG, yêu cầu phân tích và đưa ra ví dụ thực tế."
            ),
            QuestionEntity(
                id = "q2_3",
                assignmentId = "esg_final_2",
                question = "So sánh ESG và CSR (Corporate Social Responsibility). Điểm giống nhau giữa ESG và CSR là gì? Điểm khác biệt lớn nhất giữa ESG và CSR nằm ở đâu (mục tiêu, cách triển khai, đo lường, độ gắn kết chiến lược)? Cho ví dụ minh họa một hoạt động CSR truyền thống và một hoạt động ESG có tính chiến lược.",
                type = QuestionType.ESSAY,
                options = null,
                correctAnswer = null,
                explanation = "Câu hỏi tự luận so sánh ESG và CSR, yêu cầu phân tích và đưa ra ví dụ minh họa."
            ),
            QuestionEntity(
                id = "q2_4",
                assignmentId = "esg_final_2",
                question = "Case Study TechGreen: Công ty TechGreen là startup công nghệ giáo dục gặp 3 vấn đề: (1) Tỷ lệ nghỉ việc cao, nhân viên làm việc quá tải, (2) Chưa công bố chính sách bảo mật dữ liệu, (3) Tiêu thụ nhiều điện năng từ lưới truyền thống. Phân loại từng vấn đề vào 3 yếu tố ESG và phân tích hậu quả nếu tiếp tục bỏ qua.",
                type = QuestionType.ESSAY,
                options = null,
                correctAnswer = null,
                explanation = "Case study phân tích tình huống doanh nghiệp TechGreen, yêu cầu phân loại vấn đề theo ESG và đánh giá hậu quả."
            ),
            QuestionEntity(
                id = "q2_5",
                assignmentId = "esg_final_2",
                question = "Đề xuất kế hoạch hành động ESG trong 6 tháng tới cho TechGreen, bao gồm: Mỗi vấn đề đưa ra 1 hành động cụ thể, Xác định bộ phận chịu trách nhiệm và Nêu KPI đo lường kết quả (ví dụ: mức giảm tiêu thụ điện, mức độ hài lòng nhân viên, số người được đào tạo về bảo mật…).",
                type = QuestionType.ESSAY,
                options = null,
                correctAnswer = null,
                explanation = "Câu hỏi tự luận về kế hoạch hành động ESG, yêu cầu đề xuất cụ thể với KPI đo lường."
            )
        )
    }
}
