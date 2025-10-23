package com.ignitech.esgcompanion.data.seed

import com.ignitech.esgcompanion.data.local.dao.QuestionDao
import com.ignitech.esgcompanion.data.local.entity.QuestionEntity
import javax.inject.Inject

class QuestionSeeder @Inject constructor(
    private val questionDao: com.ignitech.esgcompanion.data.local.dao.QuestionDao
) {
    suspend fun seedQuestions() {
        val questions = listOf(
            QuestionEntity(
                id = "q1",
                title = "ESG là gì?",
                type = "Trắc nghiệm",
                difficulty = "Dễ",
                category = "Môi trường",
                content = "ESG là viết tắt của gì?",
                options = "[\"Environmental, Social, Governance\", \"Economic, Social, Government\", \"Environmental, Society, Government\", \"Economic, Society, Governance\"]",
                correctAnswer = "Environmental, Social, Governance",
                explanation = "ESG là viết tắt của Environmental (Môi trường), Social (Xã hội), và Governance (Quản trị).",
                points = 1,
                tags = "[\"ESG\", \"Khái niệm cơ bản\", \"Môi trường\"]"
            ),
            QuestionEntity(
                id = "q2",
                title = "Tác động của biến đổi khí hậu đến doanh nghiệp?",
                type = "Tự luận",
                difficulty = "Trung bình",
                category = "Môi trường",
                content = "Hãy phân tích các tác động chính của biến đổi khí hậu đến hoạt động kinh doanh của doanh nghiệp.",
                correctAnswer = "Các tác động chính bao gồm: rủi ro vật lý, rủi ro chuyển đổi, cơ hội kinh doanh mới, yêu cầu báo cáo và minh bạch.",
                explanation = "Biến đổi khí hậu tạo ra cả rủi ro và cơ hội cho doanh nghiệp, đòi hỏi các chiến lược thích ứng và giảm thiểu.",
                points = 5,
                tags = "[\"Biến đổi khí hậu\", \"Rủi ro\", \"Cơ hội\", \"Môi trường\"]"
            ),
            QuestionEntity(
                id = "q3",
                title = "Báo cáo bền vững là bắt buộc theo quy định Việt Nam",
                type = "Đúng/Sai",
                difficulty = "Dễ",
                category = "Quản trị",
                content = "Theo quy định hiện tại của Việt Nam, tất cả doanh nghiệp đều phải báo cáo bền vững.",
                correctAnswer = "Sai",
                explanation = "Hiện tại, báo cáo bền vững chưa bắt buộc cho tất cả doanh nghiệp tại Việt Nam, chỉ áp dụng cho một số ngành nghề cụ thể.",
                points = 1,
                tags = "[\"Báo cáo bền vững\", \"Quy định\", \"Việt Nam\", \"Quản trị\"]"
            ),
            QuestionEntity(
                id = "q4",
                title = "Lợi ích của năng lượng tái tạo?",
                type = "Trắc nghiệm",
                difficulty = "Trung bình",
                category = "Môi trường",
                content = "Lợi ích chính của việc sử dụng năng lượng tái tạo là gì?",
                options = "[\"Giảm phát thải khí nhà kính\", \"Tăng chi phí vận hành\", \"Phụ thuộc vào nhiên liệu hóa thạch\", \"Tăng ô nhiễm môi trường\"]",
                correctAnswer = "Giảm phát thải khí nhà kính",
                explanation = "Năng lượng tái tạo giúp giảm phát thải khí nhà kính, giảm phụ thuộc vào nhiên liệu hóa thạch và bảo vệ môi trường.",
                points = 2,
                tags = "[\"Năng lượng tái tạo\", \"Môi trường\", \"Bền vững\"]"
            ),
            QuestionEntity(
                id = "q5",
                title = "Trách nhiệm xã hội của doanh nghiệp trong ngành ngân hàng",
                type = "Tự luận",
                difficulty = "Khó",
                category = "Xã hội",
                content = "Phân tích vai trò và trách nhiệm xã hội của các ngân hàng trong việc thúc đẩy phát triển bền vững.",
                correctAnswer = "Ngân hàng có trách nhiệm trong việc tài trợ xanh, hỗ trợ doanh nghiệp bền vững, và đảm bảo tài chính bao trùm.",
                explanation = "Ngân hàng đóng vai trò quan trọng trong việc định hướng dòng vốn đến các dự án bền vững và hỗ trợ cộng đồng.",
                points = 8,
                tags = "[\"Trách nhiệm xã hội\", \"Ngân hàng\", \"Tài chính bền vững\", \"Xã hội\"]"
            ),
            QuestionEntity(
                id = "q6",
                title = "Các tiêu chuẩn ESG quốc tế phổ biến",
                type = "Trắc nghiệm",
                difficulty = "Trung bình",
                category = "Quản trị",
                content = "Tiêu chuẩn nào sau đây được sử dụng phổ biến nhất cho báo cáo bền vững?",
                options = "[\"GRI Standards\", \"ISO 14001\", \"OHSAS 18001\", \"ISO 9001\"]",
                correctAnswer = "GRI Standards",
                explanation = "GRI (Global Reporting Initiative) Standards là bộ tiêu chuẩn quốc tế được sử dụng rộng rãi nhất cho báo cáo bền vững.",
                points = 2,
                tags = "[\"GRI\", \"Tiêu chuẩn quốc tế\", \"Báo cáo bền vững\", \"Quản trị\"]"
            ),
            QuestionEntity(
                id = "q7",
                title = "Tác động của ESG đến hiệu quả tài chính",
                type = "Tự luận",
                difficulty = "Khó",
                category = "Quản trị",
                content = "Phân tích mối quan hệ giữa thực hành ESG và hiệu quả tài chính của doanh nghiệp.",
                correctAnswer = "Thực hành ESG tốt thường dẫn đến hiệu quả tài chính tốt hơn thông qua giảm rủi ro, tăng uy tín, và cơ hội kinh doanh mới.",
                explanation = "Nhiều nghiên cứu cho thấy các doanh nghiệp có thực hành ESG tốt thường có hiệu suất tài chính vượt trội.",
                points = 8,
                tags = "[\"ESG\", \"Hiệu quả tài chính\", \"Rủi ro\", \"Quản trị\"]"
            ),
            QuestionEntity(
                id = "q8",
                title = "Đa dạng và hòa nhập trong doanh nghiệp",
                type = "Đúng/Sai",
                difficulty = "Dễ",
                category = "Xã hội",
                content = "Đa dạng và hòa nhập (D&I) chỉ quan trọng đối với các doanh nghiệp lớn.",
                correctAnswer = "Sai",
                explanation = "Đa dạng và hòa nhập quan trọng đối với tất cả doanh nghiệp, không phân biệt quy mô, vì nó mang lại lợi ích về sáng tạo và hiệu quả.",
                points = 1,
                tags = "[\"Đa dạng\", \"Hòa nhập\", \"Xã hội\", \"Quản trị\"]"
            ),
            QuestionEntity(
                id = "q9",
                title = "Quản lý rủi ro môi trường trong ngân hàng",
                type = "Tự luận",
                difficulty = "Trung bình",
                category = "Môi trường",
                content = "Các ngân hàng cần quản lý những rủi ro môi trường nào trong hoạt động tín dụng?",
                correctAnswer = "Rủi ro tín dụng môi trường, rủi ro danh tiếng, rủi ro pháp lý, và rủi ro tác động gián tiếp từ khách hàng.",
                explanation = "Ngân hàng phải đánh giá và quản lý các rủi ro môi trường liên quan đến hoạt động tín dụng và đầu tư.",
                points = 5,
                tags = "[\"Rủi ro môi trường\", \"Ngân hàng\", \"Tín dụng\", \"Môi trường\"]"
            ),
            QuestionEntity(
                id = "q10",
                title = "Báo cáo bền vững theo TCFD",
                type = "Trắc nghiệm",
                difficulty = "Khó",
                category = "Quản trị",
                content = "TCFD (Task Force on Climate-related Financial Disclosures) tập trung vào lĩnh vực nào?",
                options = "[\"Báo cáo rủi ro khí hậu\", \"Báo cáo đa dạng sinh học\", \"Báo cáo nước sạch\", \"Báo cáo chất thải\"]",
                correctAnswer = "Báo cáo rủi ro khí hậu",
                explanation = "TCFD tập trung vào việc báo cáo các rủi ro tài chính liên quan đến khí hậu và các cơ hội chuyển đổi.",
                points = 3,
                tags = "[\"TCFD\", \"Rủi ro khí hậu\", \"Báo cáo tài chính\", \"Quản trị\"]"
            )
        )

        questionDao.insertQuestions(questions)
    }
}
