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
                name = "Green Tech Vietnam Co., Ltd.",
                industry = "Technology & Environment",
                location = "Hanoi",
                description = "Enterprise specializing in green technology solutions, renewable energy and smart waste management for small and medium organizations.",
                size = "50 - 100 employees",
                esgScore = 7.8f,
                establishedYear = 2018,
                website = "https://www.greentech.vn",
                phone = "024 3856 7890",
                email = "contact@greentech.vn",
                employeeCount = 75,
                revenue = "50 - 100 billion VND",
                certification = "ISO 14001"
            ),
            EnterpriseEntity(
                id = "ent_organic_farm",
                name = "Dong Xanh Organic Agriculture Cooperative",
                industry = "Agriculture",
                location = "Da Lat, Lam Dong",
                description = "Cooperative producing and distributing organic fruits and vegetables with sustainable farming practices, no use of toxic chemicals.",
                size = "30 - 50 employees",
                esgScore = 8.2f,
                establishedYear = 2015,
                website = "https://www.organicfarm.vn",
                phone = "0263 3567 890",
                email = "info@organicfarm.vn",
                employeeCount = 42,
                revenue = "20 - 50 billion VND",
                certification = "VietGAP, GlobalGAP"
            ),
            EnterpriseEntity(
                id = "ent_eco_textile",
                name = "An Phuoc Ecological Textile Joint Stock Company",
                industry = "Textiles & Fashion",
                location = "Binh Duong",
                description = "Textile enterprise specializing in producing fabrics and clothing from eco-friendly materials, committed to fair working conditions for workers.",
                size = "100 - 200 employees",
                esgScore = 7.5f,
                establishedYear = 2017,
                website = "https://www.ecotextile.vn",
                phone = "0274 3789 123",
                email = "contact@ecotextile.vn",
                employeeCount = 150,
                revenue = "100 - 200 billion VND",
                certification = "GOTS, Fair Trade"
            ),
            EnterpriseEntity(
                id = "ent_solar_solutions",
                name = "Vietnam Solar Solutions Co., Ltd.",
                industry = "Renewable Energy",
                location = "Ho Chi Minh City",
                description = "Specializing in consulting, designing and installing solar energy systems for small and medium enterprises, households and industrial facilities.",
                size = "50 - 100 employees",
                esgScore = 8.5f,
                establishedYear = 2016,
                website = "https://www.solarvn.com",
                phone = "028 3890 4567",
                email = "info@solarvn.com",
                employeeCount = 85,
                revenue = "80 - 150 billion VND",
                certification = "ISO 9001, ISO 14001"
            ),
            EnterpriseEntity(
                id = "ent_recycle_plastic",
                name = "Northern Green Plastic Recycling Co., Ltd.",
                industry = "Recycling & Waste Management",
                location = "Bac Ninh",
                description = "Enterprise specializing in collecting and recycling plastic waste into recycled plastic products, contributing to reducing environmental pollution.",
                size = "50 - 100 employees",
                esgScore = 8.0f,
                establishedYear = 2019,
                website = "https://www.recycleplastic.vn",
                phone = "0222 3678 901",
                email = "contact@recycleplastic.vn",
                employeeCount = 68,
                revenue = "30 - 60 billion VND",
                certification = "ISO 14001, ISO 45001"
            ),
            EnterpriseEntity(
                id = "ent_coffee_sustainable",
                name = "Central Highlands Sustainable Coffee Joint Stock Company",
                industry = "Food & Beverages",
                location = "Dak Lak",
                description = "Enterprise processing and exporting coffee with sustainable production processes, supporting local farmers and protecting biodiversity.",
                size = "50 - 100 employees",
                esgScore = 7.9f,
                establishedYear = 2014,
                website = "https://www.sustainablecoffee.vn",
                phone = "0262 3567 234",
                email = "info@sustainablecoffee.vn",
                employeeCount = 92,
                revenue = "60 - 100 billion VND",
                certification = "UTZ, Rainforest Alliance"
            ),
            EnterpriseEntity(
                id = "ent_craft_workshop",
                name = "Traditional Craft Village Handicrafts Factory",
                industry = "Handicrafts",
                location = "Bac Giang",
                description = "Factory producing handmade products from natural materials, creating jobs for local people and preserving traditional crafts.",
                size = "30 - 50 employees",
                esgScore = 7.6f,
                establishedYear = 2012,
                website = "https://www.craftworkshop.vn",
                phone = "0240 3456 789",
                email = "contact@craftworkshop.vn",
                employeeCount = 45,
                revenue = "15 - 30 billion VND",
                certification = "OCOP Product Certification"
            ),
            EnterpriseEntity(
                id = "ent_water_treatment",
                name = "Central Water Treatment Co., Ltd.",
                industry = "Environment & Water",
                location = "Da Nang",
                description = "Enterprise specializing in providing wastewater and domestic water treatment solutions for small and medium enterprises.",
                size = "50 - 100 employees",
                esgScore = 8.3f,
                establishedYear = 2016,
                website = "https://www.watertreatment.vn",
                phone = "0236 3678 456",
                email = "info@watertreatment.vn",
                employeeCount = 72,
                revenue = "40 - 80 billion VND",
                certification = "ISO 14001, ISO 9001"
            ),
            EnterpriseEntity(
                id = "ent_packaging_eco",
                name = "Eco-Friendly Packaging Joint Stock Company",
                industry = "Packaging",
                location = "Dong Nai",
                description = "Producing packaging from recycled paper and biodegradable materials, replacing traditional plastic packaging for F&B businesses.",
                size = "100 - 200 employees",
                esgScore = 7.7f,
                establishedYear = 2017,
                website = "https://www.ecopackaging.vn",
                phone = "0251 3789 234",
                email = "contact@ecopackaging.vn",
                employeeCount = 135,
                revenue = "80 - 120 billion VND",
                certification = "FSC, ISO 14001"
            ),
            EnterpriseEntity(
                id = "ent_digital_education",
                name = "Future Digital Education Co., Ltd.",
                industry = "Education & Training",
                location = "Hanoi",
                description = "Enterprise providing online education solutions and sustainable skills training for the community, especially focusing on remote areas.",
                size = "30 - 50 employees",
                esgScore = 8.1f,
                establishedYear = 2019,
                website = "https://www.digitaledu.vn",
                phone = "024 3567 8901",
                email = "info@digitaledu.vn",
                employeeCount = 48,
                revenue = "20 - 40 billion VND",
                certification = "ISO 9001"
            )
        )
        
        enterpriseDao.insertEnterprises(enterprises)
        println("DEBUG: Successfully seeded ${enterprises.size} SME enterprises")
    }
}
