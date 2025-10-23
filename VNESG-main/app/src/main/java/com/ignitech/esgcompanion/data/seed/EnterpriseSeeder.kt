package com.ignitech.esgcompanion.data.seed

import com.ignitech.esgcompanion.data.local.dao.EnterpriseDao
import com.ignitech.esgcompanion.data.local.entity.EnterpriseEntity

class EnterpriseSeeder(private val enterpriseDao: EnterpriseDao) {
    
    suspend fun seed() {
        println("DEBUG: Starting enterprise seeding...")
        
        // Check if already seeded
        val count = enterpriseDao.getEnterpriseCount()
        if (count > 0) {
            println("DEBUG: Enterprises already seeded (count: $count)")
            return
        }
        
        val enterprises = listOf(
            EnterpriseEntity(
                id = "ent_green_tech",
                name = "Công ty TNHH Công nghệ Xanh Việt",
                industry = "Công nghệ & Môi trường",
                location = "Hà Nội",
                description = "Doanh nghiệp chuyên cung cấp giải pháp công nghệ xanh, năng lượng tái tạo và quản lý chất thải thông minh cho các tổ chức vừa và nhỏ.",
                size = "50 - 100 nhân viên",
                esgScore = 7.8f,
                establishedYear = 2018,
                website = "https://www.greentech.vn",
                phone = "024 3856 7890",
                email = "contact@greentech.vn",
                employeeCount = 75,
                revenue = "50 - 100 tỷ VNĐ",
                certification = "ISO 14001"
            ),
            EnterpriseEntity(
                id = "ent_organic_farm",
                name = "HTX Nông nghiệp Hữu cơ Đồng Xanh",
                industry = "Nông nghiệp",
                location = "Đà Lạt, Lâm Đồng",
                description = "Hợp tác xã sản xuất và phân phối rau củ quả hữu cơ với quy trình canh tác bền vững, không sử dụng hóa chất độc hại.",
                size = "30 - 50 nhân viên",
                esgScore = 8.2f,
                establishedYear = 2015,
                website = "https://www.organicfarm.vn",
                phone = "0263 3567 890",
                email = "info@organicfarm.vn",
                employeeCount = 42,
                revenue = "20 - 50 tỷ VNĐ",
                certification = "VietGAP, GlobalGAP"
            ),
            EnterpriseEntity(
                id = "ent_eco_textile",
                name = "Công ty CP Dệt May Sinh Thái An Phước",
                industry = "Dệt may & Thời trang",
                location = "Bình Dương",
                description = "Doanh nghiệp dệt may chuyên sản xuất vải và quần áo từ nguyên liệu thân thiện môi trường, cam kết điều kiện làm việc công bằng cho người lao động.",
                size = "100 - 200 nhân viên",
                esgScore = 7.5f,
                establishedYear = 2017,
                website = "https://www.ecotextile.vn",
                phone = "0274 3789 123",
                email = "contact@ecotextile.vn",
                employeeCount = 150,
                revenue = "100 - 200 tỷ VNĐ",
                certification = "GOTS, Fair Trade"
            ),
            EnterpriseEntity(
                id = "ent_solar_solutions",
                name = "Công ty TNHH Giải pháp Năng lượng Mặt trời Việt",
                industry = "Năng lượng tái tạo",
                location = "TP. Hồ Chí Minh",
                description = "Chuyên tư vấn, thiết kế và lắp đặt hệ thống năng lượng mặt trời cho doanh nghiệp vừa và nhỏ, hộ gia đình và cơ sở công nghiệp.",
                size = "50 - 100 nhân viên",
                esgScore = 8.5f,
                establishedYear = 2016,
                website = "https://www.solarvn.com",
                phone = "028 3890 4567",
                email = "info@solarvn.com",
                employeeCount = 85,
                revenue = "80 - 150 tỷ VNĐ",
                certification = "ISO 9001, ISO 14001"
            ),
            EnterpriseEntity(
                id = "ent_recycle_plastic",
                name = "Công ty TNHH Tái chế Nhựa Xanh Miền Bắc",
                industry = "Tái chế & Xử lý chất thải",
                location = "Bắc Ninh",
                description = "Doanh nghiệp chuyên thu gom, tái chế nhựa phế liệu thành sản phẩm nhựa tái sinh, góp phần giảm thiểu ô nhiễm môi trường.",
                size = "50 - 100 nhân viên",
                esgScore = 8.0f,
                establishedYear = 2019,
                website = "https://www.recycleplastic.vn",
                phone = "0222 3678 901",
                email = "contact@recycleplastic.vn",
                employeeCount = 68,
                revenue = "30 - 60 tỷ VNĐ",
                certification = "ISO 14001, ISO 45001"
            ),
            EnterpriseEntity(
                id = "ent_coffee_sustainable",
                name = "Công ty CP Cà phê Bền vững Tây Nguyên",
                industry = "Thực phẩm & Đồ uống",
                location = "Đắk Lắk",
                description = "Doanh nghiệp chế biến và xuất khẩu cà phê với quy trình sản xuất bền vững, hỗ trợ nông dân địa phương và bảo vệ đa dạng sinh học.",
                size = "50 - 100 nhân viên",
                esgScore = 7.9f,
                establishedYear = 2014,
                website = "https://www.sustainablecoffee.vn",
                phone = "0262 3567 234",
                email = "info@sustainablecoffee.vn",
                employeeCount = 92,
                revenue = "60 - 100 tỷ VNĐ",
                certification = "UTZ, Rainforest Alliance"
            ),
            EnterpriseEntity(
                id = "ent_craft_workshop",
                name = "Xí nghiệp Thủ công Mỹ nghệ Làng Nghề Truyền thống",
                industry = "Thủ công mỹ nghệ",
                location = "Bắc Giang",
                description = "Xí nghiệp sản xuất sản phẩm thủ công từ nguyên liệu tự nhiên, tạo việc làm cho người dân địa phương và bảo tồn nghề truyền thống.",
                size = "30 - 50 nhân viên",
                esgScore = 7.6f,
                establishedYear = 2012,
                website = "https://www.craftworkshop.vn",
                phone = "0240 3456 789",
                email = "contact@craftworkshop.vn",
                employeeCount = 45,
                revenue = "15 - 30 tỷ VNĐ",
                certification = "Chứng nhận Sản phẩm OCOP"
            ),
            EnterpriseEntity(
                id = "ent_water_treatment",
                name = "Công ty TNHH Xử lý Nước Sạch Miền Trung",
                industry = "Môi trường & Nước",
                location = "Đà Nẵng",
                description = "Doanh nghiệp chuyên cung cấp giải pháp xử lý nước thải công nghiệp và nước sinh hoạt cho các doanh nghiệp vừa và nhỏ.",
                size = "50 - 100 nhân viên",
                esgScore = 8.3f,
                establishedYear = 2016,
                website = "https://www.watertreatment.vn",
                phone = "0236 3678 456",
                email = "info@watertreatment.vn",
                employeeCount = 72,
                revenue = "40 - 80 tỷ VNĐ",
                certification = "ISO 14001, ISO 9001"
            ),
            EnterpriseEntity(
                id = "ent_packaging_eco",
                name = "Công ty CP Bao bì Thân thiện Môi trường",
                industry = "Bao bì & Đóng gói",
                location = "Đồng Nai",
                description = "Sản xuất bao bì từ giấy tái chế và vật liệu phân hủy sinh học, thay thế bao bì nhựa truyền thống cho doanh nghiệp F&B.",
                size = "100 - 200 nhân viên",
                esgScore = 7.7f,
                establishedYear = 2017,
                website = "https://www.ecopackaging.vn",
                phone = "0251 3789 234",
                email = "contact@ecopackaging.vn",
                employeeCount = 135,
                revenue = "80 - 120 tỷ VNĐ",
                certification = "FSC, ISO 14001"
            ),
            EnterpriseEntity(
                id = "ent_digital_education",
                name = "Công ty TNHH Giáo dục Số Tương lai",
                industry = "Giáo dục & Đào tạo",
                location = "Hà Nội",
                description = "Doanh nghiệp cung cấp giải pháp giáo dục trực tuyến và đào tạo kỹ năng bền vững cho cộng đồng, đặc biệt tập trung vào vùng sâu vùng xa.",
                size = "30 - 50 nhân viên",
                esgScore = 8.1f,
                establishedYear = 2019,
                website = "https://www.digitaledu.vn",
                phone = "024 3567 8901",
                email = "info@digitaledu.vn",
                employeeCount = 48,
                revenue = "20 - 40 tỷ VNĐ",
                certification = "ISO 9001"
            )
        )
        
        enterpriseDao.insertEnterprises(enterprises)
        println("DEBUG: Successfully seeded ${enterprises.size} SME enterprises")
    }
}
