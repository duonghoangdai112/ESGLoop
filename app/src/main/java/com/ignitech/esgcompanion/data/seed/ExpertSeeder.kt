package com.ignitech.esgcompanion.data.seed

import com.ignitech.esgcompanion.data.local.entity.ExpertEntity

object ExpertSeeder {
    
    fun getExpertData(): List<ExpertEntity> {
        return listOf(
            ExpertEntity(
                id = "expert_001",
                name = "Dr. Nguyễn Văn An",
                specialization = "Banking ESG Expert",
                rating = 4.9f,
                hourlyRate = 500,
                experience = "15 years of experience",
                isAvailable = true,
                description = "Leading ESG expert in banking with over 15 years of experience. Has consulted for over 50 major banks in Vietnam and Southeast Asia.",
                education = "Ph.D. in Economics - Harvard University, Master of Finance - Stanford University",
                certifications = "CFA, FRM, GRI Standards, SASB Standards",
                languages = "Vietnamese, English, French",
                responseTime = "Within 2 hours",
                completedProjects = 127
            ),
            ExpertEntity(
                id = "expert_002",
                name = "Ms. Trần Thị Bình",
                specialization = "Environmental Sustainability Consultant",
                rating = 4.8f,
                hourlyRate = 400,
                experience = "12 years of experience",
                isAvailable = true,
                description = "Expert in environmental sustainability and climate risk management. Deep experience in carbon footprint and circular economy.",
                education = "Master of Environmental Science - University of Melbourne, Bachelor of Chemistry - Vietnam National University, Hanoi",
                certifications = "LEED AP, ISO 14001 Lead Auditor, CDP Climate Change",
                languages = "Vietnamese, English",
                responseTime = "Within 4 hours",
                completedProjects = 89
            ),
            ExpertEntity(
                id = "expert_003",
                name = "Mr. Lê Văn Cường",
                specialization = "ESG Corporate Governance",
                rating = 4.7f,
                hourlyRate = 450,
                experience = "10 years of experience",
                isAvailable = true,
                description = "Expert in corporate governance and ESG compliance. Experience in building ESG management systems for large enterprises.",
                education = "MBA - INSEAD, Bachelor of Business Administration - Ho Chi Minh City University of Economics",
                certifications = "CIA, CISA, COSO Framework, King IV",
                languages = "Vietnamese, English, Japanese",
                responseTime = "Within 3 hours",
                completedProjects = 95
            ),
            ExpertEntity(
                id = "expert_004",
                name = "Dr. Phạm Thị Dung",
                specialization = "ESG Risk Assessment",
                rating = 4.6f,
                hourlyRate = 350,
                experience = "8 years of experience",
                isAvailable = true,
                description = "Expert in ESG risk assessment and management. Experience in building quantitative ESG risk models for banks.",
                education = "Ph.D. in Finance - London School of Economics, Master of Mathematics - Hanoi University of Science and Technology",
                certifications = "FRM, PRM, CFA, GARP",
                languages = "Vietnamese, English",
                responseTime = "Within 6 hours",
                completedProjects = 67
            ),
            ExpertEntity(
                id = "expert_005",
                name = "Mr. Hoàng Văn Em",
                specialization = "Sustainability Reporting",
                rating = 4.5f,
                hourlyRate = 300,
                experience = "6 years of experience",
                isAvailable = true,
                description = "Expert in sustainability reporting and ESG information transparency. Experience in preparing reports according to GRI, SASB, TCFD.",
                education = "Master of Accounting - RMIT University, Bachelor of Accounting - National Economics University",
                certifications = "CPA, GRI Standards, SASB FSA, TCFD",
                languages = "Vietnamese, English",
                responseTime = "Within 8 hours",
                completedProjects = 54
            ),
            ExpertEntity(
                id = "expert_006",
                name = "Ms. Vũ Thị Phương",
                specialization = "ESG Legal Compliance",
                rating = 4.4f,
                hourlyRate = 320,
                experience = "7 years of experience",
                isAvailable = true,
                description = "Expert in ESG legal compliance and regulations. Experience in advising enterprises on new ESG legal requirements.",
                education = "Master of Law - Hanoi Law University, Bachelor of Economic Law - Ho Chi Minh City Law University",
                certifications = "Lawyer, GRI Standards, ISO 37001",
                languages = "Vietnamese, English, Chinese",
                responseTime = "Within 5 hours",
                completedProjects = 72
            ),
            ExpertEntity(
                id = "expert_007",
                name = "Dr. Đặng Văn Giang",
                specialization = "Sustainable Investment",
                rating = 4.8f,
                hourlyRate = 480,
                experience = "11 years of experience",
                isAvailable = true,
                description = "Expert in sustainable investment and green finance. Experience in managing ESG funds and advising on sustainable investment strategies.",
                education = "Ph.D. in Finance - University of Oxford, MBA - Wharton School",
                certifications = "CFA, CAIA, PRI Signatory, UN PRI",
                languages = "Vietnamese, English, German",
                responseTime = "Within 2 hours",
                completedProjects = 103
            ),
            ExpertEntity(
                id = "expert_008",
                name = "Ms. Ngô Thị Hoa",
                specialization = "Human Rights & Labor",
                rating = 4.3f,
                hourlyRate = 280,
                experience = "5 years of experience",
                isAvailable = true,
                description = "Expert in human rights, labor and diversity. Experience in building sustainable HR policies and labor rights compliance.",
                education = "Master of Human Rights - Columbia University, Bachelor of Sociology - University of Social Sciences and Humanities",
                certifications = "SHRM-CP, ILO Standards, UN Global Compact",
                languages = "Vietnamese, English",
                responseTime = "Within 6 hours",
                completedProjects = 41
            ),
            ExpertEntity(
                id = "expert_009",
                name = "Mr. Bùi Văn Inh",
                specialization = "Green Technology",
                rating = 4.6f,
                hourlyRate = 380,
                experience = "9 years of experience",
                isAvailable = true,
                description = "Expert in green technology and sustainable digital transformation. Experience in consulting on green Fintech and blockchain for ESG.",
                education = "Master of Information Technology - MIT, Bachelor of Computer Science - Ho Chi Minh City University of Technology",
                certifications = "AWS Solutions Architect, Google Cloud Professional, Microsoft Azure",
                languages = "Vietnamese, English, Japanese",
                responseTime = "Within 4 hours",
                completedProjects = 78
            ),
            ExpertEntity(
                id = "expert_010",
                name = "Dr. Lý Thị Kim",
                specialization = "Circular Economy",
                rating = 4.7f,
                hourlyRate = 420,
                experience = "13 years of experience",
                isAvailable = true,
                description = "Expert in circular economy and resource management. Experience in building circular business models for enterprises.",
                education = "Ph.D. in Environmental Economics - University of Cambridge, Master of Economics - National Economics University",
                certifications = "Cradle to Cradle, Ellen MacArthur Foundation, Circular Economy",
                languages = "Vietnamese, English, French",
                responseTime = "Within 3 hours",
                completedProjects = 86
            )
        )
    }
}
