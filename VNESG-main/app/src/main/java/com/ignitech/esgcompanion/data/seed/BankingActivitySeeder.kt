package com.ignitech.esgcompanion.data.seed

import com.ignitech.esgcompanion.data.entity.ESGTrackerEntity
import com.ignitech.esgcompanion.data.entity.ESGTrackerType
import com.ignitech.esgcompanion.data.entity.TrackerPriority
import com.ignitech.esgcompanion.data.entity.TrackerStatus
import com.ignitech.esgcompanion.domain.entity.ESGPillar
import java.util.*

object BankingActivitySeeder {
    
    fun getBankingActivities(): List<ESGTrackerEntity> {
        println("DEBUG: BankingActivitySeeder - Generating banking activities for user_3")
        val activities = listOf(
            // Environmental (E) - 4 activities
            ESGTrackerEntity(
                id = UUID.randomUUID().toString(),
                userId = "user_3",
                activityType = ESGTrackerType.ENERGY_EFFICIENCY,
                pillar = ESGPillar.ENVIRONMENTAL,
                title = "Triển khai ngân hàng số hóa",
                description = "Chuyển đổi từ giấy tờ sang hệ thống số hóa, giảm 80% việc sử dụng giấy trong các giao dịch ngân hàng",
                status = TrackerStatus.IN_PROGRESS,
                priority = TrackerPriority.HIGH,
                plannedDate = System.currentTimeMillis() + (60 * 24 * 60 * 60 * 1000L), // 60 days from now
                actualDate = System.currentTimeMillis() - (30 * 24 * 60 * 60 * 1000L), // 30 days ago
                budget = 5000000000.0, // 5 tỷ VNĐ
                actualCost = 3250000000.0, // 3.25 tỷ VNĐ
                department = "Công nghệ thông tin",
                location = "Trụ sở chính",
                notes = "Đã hoàn thành 65% hệ thống, dự kiến hoàn thành trong 2 tháng tới"
            ),
            
            ESGTrackerEntity(
                id = UUID.randomUUID().toString(),
                userId = "user_3",
                activityType = ESGTrackerType.RENEWABLE_ENERGY,
                pillar = ESGPillar.ENVIRONMENTAL,
                title = "Lắp đặt hệ thống năng lượng mặt trời",
                description = "Lắp đặt tấm pin năng lượng mặt trời tại 20 chi nhánh để giảm tiêu thụ điện từ lưới",
                status = TrackerStatus.PLANNED,
                priority = TrackerPriority.MEDIUM,
                plannedDate = System.currentTimeMillis() + (120 * 24 * 60 * 60 * 1000L), // 120 days from now
                budget = 8000000000.0, // 8 tỷ VNĐ
                department = "Hạ tầng & Cơ sở vật chất",
                location = "20 chi nhánh trên toàn quốc",
                notes = "Đang trong giai đoạn lựa chọn nhà thầu và khảo sát địa điểm"
            ),
            
            ESGTrackerEntity(
                id = UUID.randomUUID().toString(),
                userId = "user_3",
                activityType = ESGTrackerType.CARBON_REDUCTION,
                pillar = ESGPillar.ENVIRONMENTAL,
                title = "Chương trình tài trợ xanh",
                description = "Phát triển sản phẩm tín dụng xanh với lãi suất ưu đãi cho các dự án năng lượng tái tạo và bảo vệ môi trường",
                status = TrackerStatus.IN_PROGRESS,
                priority = TrackerPriority.HIGH,
                plannedDate = System.currentTimeMillis() + (90 * 24 * 60 * 60 * 1000L), // 90 days from now
                actualDate = System.currentTimeMillis() - (60 * 24 * 60 * 60 * 1000L), // 60 days ago
                budget = 20000000000.0, // 20 tỷ VNĐ
                actualCost = 8000000000.0, // 8 tỷ VNĐ
                department = "Sản phẩm & Dịch vụ",
                location = "Toàn hệ thống",
                notes = "Đã phê duyệt 15 dự án xanh với tổng giá trị 8 tỷ VNĐ"
            ),
            
            ESGTrackerEntity(
                id = UUID.randomUUID().toString(),
                userId = "user_3",
                activityType = ESGTrackerType.ENERGY_EFFICIENCY,
                pillar = ESGPillar.ENVIRONMENTAL,
                title = "Giảm tiêu thụ năng lượng tại chi nhánh",
                description = "Thay thế hệ thống chiếu sáng LED, tối ưu hóa hệ thống điều hòa và thiết bị văn phòng",
                status = TrackerStatus.COMPLETED,
                priority = TrackerPriority.MEDIUM,
                plannedDate = System.currentTimeMillis() - (30 * 24 * 60 * 60 * 1000L), // 30 days ago
                actualDate = System.currentTimeMillis() - (180 * 24 * 60 * 60 * 1000L), // 180 days ago
                completedAt = System.currentTimeMillis() - (30 * 24 * 60 * 60 * 1000L), // 30 days ago
                budget = 3000000000.0, // 3 tỷ VNĐ
                actualCost = 2850000000.0, // 2.85 tỷ VNĐ
                department = "Hạ tầng & Cơ sở vật chất",
                location = "50 chi nhánh",
                notes = "Hoàn thành, tiết kiệm 25% chi phí điện năng hàng tháng"
            ),
            
            // Social (S) - 3 activities
            ESGTrackerEntity(
                id = UUID.randomUUID().toString(),
                userId = "user_3",
                activityType = ESGTrackerType.COMMUNITY_OUTREACH,
                pillar = ESGPillar.SOCIAL,
                title = "Chương trình tài chính toàn diện",
                description = "Mở rộng dịch vụ ngân hàng đến các vùng nông thôn và đối tượng có thu nhập thấp",
                status = TrackerStatus.IN_PROGRESS,
                priority = TrackerPriority.HIGH,
                plannedDate = System.currentTimeMillis() + (180 * 24 * 60 * 60 * 1000L), // 180 days from now
                actualDate = System.currentTimeMillis() - (90 * 24 * 60 * 60 * 1000L), // 90 days ago
                budget = 15000000000.0, // 15 tỷ VNĐ
                actualCost = 8250000000.0, // 8.25 tỷ VNĐ
                department = "Phát triển sản phẩm",
                location = "Vùng nông thôn & miền núi",
                notes = "Đã mở 25 điểm giao dịch mới tại các vùng khó khăn"
            ),
            
            ESGTrackerEntity(
                id = UUID.randomUUID().toString(),
                userId = "user_3",
                activityType = ESGTrackerType.EDUCATION_SUPPORT,
                pillar = ESGPillar.SOCIAL,
                title = "Đào tạo kỹ năng tài chính cho cộng đồng",
                description = "Tổ chức các khóa học miễn phí về quản lý tài chính cá nhân, tiết kiệm và đầu tư cho người dân",
                status = TrackerStatus.IN_PROGRESS,
                priority = TrackerPriority.MEDIUM,
                plannedDate = System.currentTimeMillis() + (135 * 24 * 60 * 60 * 1000L), // 135 days from now
                actualDate = System.currentTimeMillis() - (45 * 24 * 60 * 60 * 1000L), // 45 days ago
                budget = 2000000000.0, // 2 tỷ VNĐ
                actualCost = 600000000.0, // 600 triệu VNĐ
                department = "Phát triển cộng đồng",
                location = "Các trung tâm cộng đồng",
                notes = "Đã tổ chức 12 khóa học với 360 học viên tham gia"
            ),
            
            ESGTrackerEntity(
                id = UUID.randomUUID().toString(),
                userId = "user_3",
                activityType = ESGTrackerType.LOCAL_DEVELOPMENT,
                pillar = ESGPillar.SOCIAL,
                title = "Chương trình hỗ trợ doanh nghiệp nhỏ và vừa",
                description = "Cung cấp gói tín dụng ưu đãi và tư vấn kinh doanh cho các doanh nghiệp nhỏ và vừa",
                status = TrackerStatus.PLANNED,
                priority = TrackerPriority.HIGH,
                plannedDate = System.currentTimeMillis() + (365 * 24 * 60 * 60 * 1000L), // 365 days from now
                budget = 50000000000.0, // 50 tỷ VNĐ
                department = "Tín dụng doanh nghiệp",
                location = "Toàn quốc",
                notes = "Chương trình dự kiến hỗ trợ 1000 doanh nghiệp nhỏ và vừa"
            ),
            
            // Governance (G) - 4 activities
            ESGTrackerEntity(
                id = UUID.randomUUID().toString(),
                userId = "user_3",
                activityType = ESGTrackerType.RISK_MANAGEMENT,
                pillar = ESGPillar.GOVERNANCE,
                title = "Nâng cấp hệ thống quản lý rủi ro",
                description = "Triển khai hệ thống quản lý rủi ro tích hợp ESG vào quy trình đánh giá tín dụng",
                status = TrackerStatus.IN_PROGRESS,
                priority = TrackerPriority.HIGH,
                plannedDate = System.currentTimeMillis() + (105 * 24 * 60 * 60 * 1000L), // 105 days from now
                actualDate = System.currentTimeMillis() - (75 * 24 * 60 * 60 * 1000L), // 75 days ago
                budget = 12000000000.0, // 12 tỷ VNĐ
                actualCost = 8400000000.0, // 8.4 tỷ VNĐ
                department = "Quản lý rủi ro",
                location = "Trụ sở chính",
                notes = "Đã hoàn thành 70% hệ thống, đang trong giai đoạn testing"
            ),
            
            ESGTrackerEntity(
                id = UUID.randomUUID().toString(),
                userId = "user_3",
                activityType = ESGTrackerType.TRANSPARENCY_REPORT,
                pillar = ESGPillar.GOVERNANCE,
                title = "Cải thiện minh bạch báo cáo",
                description = "Nâng cấp hệ thống báo cáo tài chính và báo cáo bền vững theo chuẩn quốc tế",
                status = TrackerStatus.IN_PROGRESS,
                priority = TrackerPriority.MEDIUM,
                plannedDate = System.currentTimeMillis() + (60 * 24 * 60 * 60 * 1000L), // 60 days from now
                actualDate = System.currentTimeMillis() - (120 * 24 * 60 * 60 * 1000L), // 120 days ago
                budget = 4000000000.0, // 4 tỷ VNĐ
                actualCost = 3400000000.0, // 3.4 tỷ VNĐ
                department = "Kế toán & Báo cáo",
                location = "Trụ sở chính",
                notes = "Gần hoàn thành, dự kiến ra mắt báo cáo bền vững đầu tiên trong 2 tháng"
            ),
            
            ESGTrackerEntity(
                id = UUID.randomUUID().toString(),
                userId = "user_3",
                activityType = ESGTrackerType.ETHICS_TRAINING,
                pillar = ESGPillar.GOVERNANCE,
                title = "Đào tạo đạo đức kinh doanh cho nhân viên",
                description = "Tổ chức các khóa đào tạo về đạo đức kinh doanh, chống tham nhũng và tuân thủ quy định",
                status = TrackerStatus.COMPLETED,
                priority = TrackerPriority.MEDIUM,
                plannedDate = System.currentTimeMillis() - (50 * 24 * 60 * 60 * 1000L), // 50 days ago
                actualDate = System.currentTimeMillis() - (200 * 24 * 60 * 60 * 1000L), // 200 days ago
                completedAt = System.currentTimeMillis() - (50 * 24 * 60 * 60 * 1000L), // 50 days ago
                budget = 1500000000.0, // 1.5 tỷ VNĐ
                actualCost = 1420000000.0, // 1.42 tỷ VNĐ
                department = "Nhân sự & Đào tạo",
                location = "Toàn hệ thống",
                notes = "Hoàn thành, 100% nhân viên đã tham gia khóa đào tạo"
            ),
            
            ESGTrackerEntity(
                id = UUID.randomUUID().toString(),
                userId = "user_3",
                activityType = ESGTrackerType.BOARD_DIVERSITY,
                pillar = ESGPillar.GOVERNANCE,
                title = "Đa dạng hóa hội đồng quản trị",
                description = "Tăng cường sự đa dạng về giới tính, độ tuổi và chuyên môn trong hội đồng quản trị",
                status = TrackerStatus.PLANNED,
                priority = TrackerPriority.LOW,
                plannedDate = System.currentTimeMillis() + (180 * 24 * 60 * 60 * 1000L), // 180 days from now
                budget = 500000000.0, // 500 triệu VNĐ
                department = "Hội đồng quản trị",
                location = "Trụ sở chính",
                notes = "Đang trong giai đoạn lập kế hoạch và tìm kiếm ứng viên phù hợp"
            )
        )
        println("DEBUG: BankingActivitySeeder - Generated ${activities.size} activities")
        return activities
    }
}
