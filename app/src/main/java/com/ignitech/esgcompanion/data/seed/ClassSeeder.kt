package com.ignitech.esgcompanion.data.seed

import com.ignitech.esgcompanion.data.local.entity.ClassEntity

object ClassSeeder {
    fun getClasses(): List<ClassEntity> {
        return listOf(
            ClassEntity(
                id = "class_001",
                name = "ESG and Sustainable Development",
                code = "ESG-101",
                description = "Basic course on ESG and sustainable development in business",
                status = "ACTIVE",
                studentCount = 45,
                assignmentCount = 8,
                averageScore = 85,
                instructorId = "instructor_001"
            ),
            ClassEntity(
                id = "class_002",
                name = "ESG Risk Management",
                code = "ESG-201",
                description = "In-depth study of ESG risk management and assessment",
                status = "ACTIVE",
                studentCount = 32,
                assignmentCount = 12,
                averageScore = 78,
                instructorId = "instructor_001"
            ),
            ClassEntity(
                id = "class_003",
                name = "ESG Reporting Practice",
                code = "ESG-301",
                description = "Practice writing ESG reports according to international standards",
                status = "COMPLETED",
                studentCount = 28,
                assignmentCount = 15,
                averageScore = 92,
                instructorId = "instructor_001"
            ),
            ClassEntity(
                id = "class_004",
                name = "ESG in Banking",
                code = "ESG-401",
                description = "Application of ESG in banking and finance",
                status = "INACTIVE",
                studentCount = 0,
                assignmentCount = 0,
                averageScore = 0,
                instructorId = "instructor_001"
            ),
            ClassEntity(
                id = "class_005",
                name = "ESG Analysis for Enterprises",
                code = "ESG-501",
                description = "ESG analysis and assessment for small and medium enterprises",
                status = "ACTIVE",
                studentCount = 38,
                assignmentCount = 10,
                averageScore = 88,
                instructorId = "instructor_001"
            ),
            ClassEntity(
                id = "class_006",
                name = "International ESG Certification",
                code = "ESG-601",
                description = "Preparation for international ESG certifications such as GRI, SASB",
                status = "ACTIVE",
                studentCount = 25,
                assignmentCount = 6,
                averageScore = 90,
                instructorId = "instructor_001"
            ),
            ClassEntity(
                id = "class_007",
                name = "ESG and Sustainable Investment",
                code = "ESG-701",
                description = "Understanding sustainable investment and ESG impact",
                status = "COMPLETED",
                studentCount = 35,
                assignmentCount = 9,
                averageScore = 87,
                instructorId = "instructor_001"
            ),
            ClassEntity(
                id = "class_008",
                name = "ESG Governance in Companies",
                code = "ESG-801",
                description = "Building ESG governance systems in organizations",
                status = "ACTIVE",
                studentCount = 42,
                assignmentCount = 11,
                averageScore = 83,
                instructorId = "instructor_001"
            )
        )
    }
}
