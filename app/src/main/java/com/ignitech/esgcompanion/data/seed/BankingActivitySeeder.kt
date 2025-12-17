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
                title = "Digital Banking Implementation",
                description = "Transition from paper-based to digital systems, reducing 80% of paper usage in banking transactions",
                status = TrackerStatus.IN_PROGRESS,
                priority = TrackerPriority.HIGH,
                plannedDate = System.currentTimeMillis() + (60 * 24 * 60 * 60 * 1000L), // 60 days from now
                actualDate = System.currentTimeMillis() - (30 * 24 * 60 * 60 * 1000L), // 30 days ago
                budget = 5000000000.0, // 5 tỷ VNĐ
                actualCost = 3250000000.0, // 3.25 tỷ VNĐ
                department = "Information Technology",
                location = "Headquarters",
                notes = "65% of system completed, expected to complete in next 2 months"
            ),
            
            ESGTrackerEntity(
                id = UUID.randomUUID().toString(),
                userId = "user_3",
                activityType = ESGTrackerType.RENEWABLE_ENERGY,
                pillar = ESGPillar.ENVIRONMENTAL,
                title = "Solar Energy System Installation",
                description = "Install solar panels at 20 branches to reduce electricity consumption from the grid",
                status = TrackerStatus.PLANNED,
                priority = TrackerPriority.MEDIUM,
                plannedDate = System.currentTimeMillis() + (120 * 24 * 60 * 60 * 1000L), // 120 days from now
                budget = 8000000000.0, // 8 tỷ VNĐ
                department = "Infrastructure & Facilities",
                location = "20 branches nationwide",
                notes = "Currently in contractor selection and site survey phase"
            ),
            
            ESGTrackerEntity(
                id = UUID.randomUUID().toString(),
                userId = "user_3",
                activityType = ESGTrackerType.CARBON_REDUCTION,
                pillar = ESGPillar.ENVIRONMENTAL,
                title = "Green Financing Program",
                description = "Develop green credit products with preferential interest rates for renewable energy and environmental protection projects",
                status = TrackerStatus.IN_PROGRESS,
                priority = TrackerPriority.HIGH,
                plannedDate = System.currentTimeMillis() + (90 * 24 * 60 * 60 * 1000L), // 90 days from now
                actualDate = System.currentTimeMillis() - (60 * 24 * 60 * 60 * 1000L), // 60 days ago
                budget = 20000000000.0, // 20 tỷ VNĐ
                actualCost = 8000000000.0, // 8 tỷ VNĐ
                department = "Products & Services",
                location = "Bank-wide",
                notes = "Approved 15 green projects with total value of 8 billion VND"
            ),
            
            ESGTrackerEntity(
                id = UUID.randomUUID().toString(),
                userId = "user_3",
                activityType = ESGTrackerType.ENERGY_EFFICIENCY,
                pillar = ESGPillar.ENVIRONMENTAL,
                title = "Reduce Energy Consumption at Branches",
                description = "Replace LED lighting system, optimize air conditioning system and office equipment",
                status = TrackerStatus.COMPLETED,
                priority = TrackerPriority.MEDIUM,
                plannedDate = System.currentTimeMillis() - (30 * 24 * 60 * 60 * 1000L), // 30 days ago
                actualDate = System.currentTimeMillis() - (180 * 24 * 60 * 60 * 1000L), // 180 days ago
                completedAt = System.currentTimeMillis() - (30 * 24 * 60 * 60 * 1000L), // 30 days ago
                budget = 3000000000.0, // 3 tỷ VNĐ
                actualCost = 2850000000.0, // 2.85 tỷ VNĐ
                department = "Infrastructure & Facilities",
                location = "50 branches",
                notes = "Completed, saving 25% of monthly electricity costs"
            ),
            
            // Social (S) - 3 activities
            ESGTrackerEntity(
                id = UUID.randomUUID().toString(),
                userId = "user_3",
                activityType = ESGTrackerType.COMMUNITY_OUTREACH,
                pillar = ESGPillar.SOCIAL,
                title = "Financial Inclusion Program",
                description = "Expand banking services to rural areas and low-income populations",
                status = TrackerStatus.IN_PROGRESS,
                priority = TrackerPriority.HIGH,
                plannedDate = System.currentTimeMillis() + (180 * 24 * 60 * 60 * 1000L), // 180 days from now
                actualDate = System.currentTimeMillis() - (90 * 24 * 60 * 60 * 1000L), // 90 days ago
                budget = 15000000000.0, // 15 tỷ VNĐ
                actualCost = 8250000000.0, // 8.25 tỷ VNĐ
                department = "Product Development",
                location = "Rural & Mountainous Areas",
                notes = "Opened 25 new transaction points in disadvantaged areas"
            ),
            
            ESGTrackerEntity(
                id = UUID.randomUUID().toString(),
                userId = "user_3",
                activityType = ESGTrackerType.EDUCATION_SUPPORT,
                pillar = ESGPillar.SOCIAL,
                title = "Financial Literacy Training for Community",
                description = "Organize free courses on personal financial management, savings and investment for citizens",
                status = TrackerStatus.IN_PROGRESS,
                priority = TrackerPriority.MEDIUM,
                plannedDate = System.currentTimeMillis() + (135 * 24 * 60 * 60 * 1000L), // 135 days from now
                actualDate = System.currentTimeMillis() - (45 * 24 * 60 * 60 * 1000L), // 45 days ago
                budget = 2000000000.0, // 2 tỷ VNĐ
                actualCost = 600000000.0, // 600 triệu VNĐ
                department = "Community Development",
                location = "Community Centers",
                notes = "Organized 12 courses with 360 participants"
            ),
            
            ESGTrackerEntity(
                id = UUID.randomUUID().toString(),
                userId = "user_3",
                activityType = ESGTrackerType.LOCAL_DEVELOPMENT,
                pillar = ESGPillar.SOCIAL,
                title = "SME Support Program",
                description = "Provide preferential credit packages and business consulting for small and medium enterprises",
                status = TrackerStatus.PLANNED,
                priority = TrackerPriority.HIGH,
                plannedDate = System.currentTimeMillis() + (365 * 24 * 60 * 60 * 1000L), // 365 days from now
                budget = 50000000000.0, // 50 tỷ VNĐ
                department = "Corporate Credit",
                location = "Nationwide",
                notes = "Program expected to support 1000 small and medium enterprises"
            ),
            
            // Governance (G) - 4 activities
            ESGTrackerEntity(
                id = UUID.randomUUID().toString(),
                userId = "user_3",
                activityType = ESGTrackerType.RISK_MANAGEMENT,
                pillar = ESGPillar.GOVERNANCE,
                title = "Upgrade Risk Management System",
                description = "Deploy risk management system integrating ESG into credit assessment process",
                status = TrackerStatus.IN_PROGRESS,
                priority = TrackerPriority.HIGH,
                plannedDate = System.currentTimeMillis() + (105 * 24 * 60 * 60 * 1000L), // 105 days from now
                actualDate = System.currentTimeMillis() - (75 * 24 * 60 * 60 * 1000L), // 75 days ago
                budget = 12000000000.0, // 12 tỷ VNĐ
                actualCost = 8400000000.0, // 8.4 tỷ VNĐ
                department = "Risk Management",
                location = "Headquarters",
                notes = "70% of system completed, currently in testing phase"
            ),
            
            ESGTrackerEntity(
                id = UUID.randomUUID().toString(),
                userId = "user_3",
                activityType = ESGTrackerType.TRANSPARENCY_REPORT,
                pillar = ESGPillar.GOVERNANCE,
                title = "Improve Reporting Transparency",
                description = "Upgrade financial reporting and sustainability reporting systems according to international standards",
                status = TrackerStatus.IN_PROGRESS,
                priority = TrackerPriority.MEDIUM,
                plannedDate = System.currentTimeMillis() + (60 * 24 * 60 * 60 * 1000L), // 60 days from now
                actualDate = System.currentTimeMillis() - (120 * 24 * 60 * 60 * 1000L), // 120 days ago
                budget = 4000000000.0, // 4 tỷ VNĐ
                actualCost = 3400000000.0, // 3.4 tỷ VNĐ
                department = "Accounting & Reporting",
                location = "Headquarters",
                notes = "Nearly completed, expected to launch first sustainability report in 2 months"
            ),
            
            ESGTrackerEntity(
                id = UUID.randomUUID().toString(),
                userId = "user_3",
                activityType = ESGTrackerType.ETHICS_TRAINING,
                pillar = ESGPillar.GOVERNANCE,
                title = "Business Ethics Training for Employees",
                description = "Organize training courses on business ethics, anti-corruption and regulatory compliance",
                status = TrackerStatus.COMPLETED,
                priority = TrackerPriority.MEDIUM,
                plannedDate = System.currentTimeMillis() - (50 * 24 * 60 * 60 * 1000L), // 50 days ago
                actualDate = System.currentTimeMillis() - (200 * 24 * 60 * 60 * 1000L), // 200 days ago
                completedAt = System.currentTimeMillis() - (50 * 24 * 60 * 60 * 1000L), // 50 days ago
                budget = 1500000000.0, // 1.5 tỷ VNĐ
                actualCost = 1420000000.0, // 1.42 tỷ VNĐ
                department = "Human Resources & Training",
                location = "Bank-wide",
                notes = "Completed, 100% of employees have participated in the training"
            ),
            
            ESGTrackerEntity(
                id = UUID.randomUUID().toString(),
                userId = "user_3",
                activityType = ESGTrackerType.BOARD_DIVERSITY,
                pillar = ESGPillar.GOVERNANCE,
                title = "Board Diversity Enhancement",
                description = "Strengthen diversity in gender, age and expertise in the board of directors",
                status = TrackerStatus.PLANNED,
                priority = TrackerPriority.LOW,
                plannedDate = System.currentTimeMillis() + (180 * 24 * 60 * 60 * 1000L), // 180 days from now
                budget = 500000000.0, // 500 triệu VNĐ
                department = "Board of Directors",
                location = "Headquarters",
                notes = "Currently in planning phase and searching for suitable candidates"
            )
        )
        println("DEBUG: BankingActivitySeeder - Generated ${activities.size} activities")
        return activities
    }
}
