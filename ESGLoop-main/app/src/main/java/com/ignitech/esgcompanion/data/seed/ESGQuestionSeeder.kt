package com.ignitech.esgcompanion.data.seed

import com.ignitech.esgcompanion.data.entity.*
import com.ignitech.esgcompanion.data.local.entity.ESGQuestionEntity
import com.ignitech.esgcompanion.domain.entity.ESGPillar
import com.ignitech.esgcompanion.domain.entity.UserRole

object ESGQuestionSeeder {
    
    fun getStandardQuestions(): List<ESGQuestionEntity> {
        return listOf(
            // ENVIRONMENTAL PILLAR
            // Climate Change & Energy
            ESGQuestionEntity(
                id = "env_climate_001",
                pillar = ESGPillar.ENVIRONMENTAL,
                category = "Climate Change & Energy",
                question = "Does the enterprise assess and report carbon emissions?",
                description = "Assessing the measurement, reporting, and management of greenhouse gas (GHG) emissions",
                optionsJson = """[
                    {"id": "opt1", "text": "Fully implemented", "score": 4, "description": "Comprehensive measurement and reporting system"},
                    {"id": "opt2", "text": "In progress", "score": 3, "description": "Building measurement system"},
                    {"id": "opt3", "text": "Not yet", "score": 2, "description": "No measurement system"},
                    {"id": "opt4", "text": "Not applicable", "score": 1, "description": "Not applicable to the enterprise"}
                ]""",
                weight = 5,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 1
            ),
            ESGQuestionEntity(
                id = "env_climate_002",
                pillar = ESGPillar.ENVIRONMENTAL,
                category = "Climate Change & Energy",
                question = "Does the enterprise have a carbon reduction plan?",
                description = "Assessing the development and implementation of carbon emission reduction plans",
                optionsJson = """[
                    {"id": "opt1", "text": "Fully implemented", "score": 4, "description": "Detailed plan in implementation"},
                    {"id": "opt2", "text": "In progress", "score": 3, "description": "Developing the plan"},
                    {"id": "opt3", "text": "Not yet", "score": 2, "description": "No specific plan"},
                    {"id": "opt4", "text": "Not applicable", "score": 1, "description": "Not applicable to the enterprise"}
                ]""",
                weight = 4,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 2
            ),
            
            // Waste Management
            ESGQuestionEntity(
                id = "env_waste_001",
                pillar = ESGPillar.ENVIRONMENTAL,
                category = "Waste Management",
                question = "Does the enterprise have a waste management system?",
                description = "Assessing waste classification, treatment, and recycling",
                optionsJson = """[
                    {"id": "opt1", "text": "Fully implemented", "score": 4, "description": "Complete waste management system"},
                    {"id": "opt2", "text": "In progress", "score": 3, "description": "Building the system"},
                    {"id": "opt3", "text": "Not yet", "score": 2, "description": "No management system"},
                    {"id": "opt4", "text": "Not applicable", "score": 1, "description": "Not applicable to the enterprise"}
                ]""",
                weight = 3,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 1
            ),
            
            // SOCIAL PILLAR
            // Labor Rights
            ESGQuestionEntity(
                id = "soc_labor_001",
                pillar = ESGPillar.SOCIAL,
                category = "Labor Rights",
                question = "Does the enterprise comply with labor rights?",
                description = "Assessing compliance with basic labor rights",
                optionsJson = """[
                    {"id": "opt1", "text": "Fully implemented", "score": 4, "description": "Full compliance with labor rights"},
                    {"id": "opt2", "text": "In progress", "score": 3, "description": "Improving compliance"},
                    {"id": "opt3", "text": "Not yet", "score": 2, "description": "Not fully compliant"},
                    {"id": "opt4", "text": "Not applicable", "score": 1, "description": "Not applicable to the enterprise"}
                ]""",
                weight = 4,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 1
            ),
            
            // Community Engagement
            ESGQuestionEntity(
                id = "soc_community_001",
                pillar = ESGPillar.SOCIAL,
                category = "Community Engagement",
                question = "Does the enterprise participate in community activities?",
                description = "Assessing participation and contribution to community",
                optionsJson = """[
                    {"id": "opt1", "text": "Fully implemented", "score": 4, "description": "Active participation in community activities"},
                    {"id": "opt2", "text": "In progress", "score": 3, "description": "Expanding community activities"},
                    {"id": "opt3", "text": "Not yet", "score": 2, "description": "Limited participation in activities"},
                    {"id": "opt4", "text": "Not applicable", "score": 1, "description": "Not applicable to the enterprise"}
                ]""",
                weight = 3,
                isRequired = false,
                userRole = UserRole.ENTERPRISE,
                order = 1
            ),
            
            // GOVERNANCE PILLAR
            // Ethics & Compliance
            ESGQuestionEntity(
                id = "gov_ethics_001",
                pillar = ESGPillar.GOVERNANCE,
                category = "Ethics & Compliance",
                question = "Does the enterprise have a code of ethics?",
                description = "Assessing the development and implementation of code of ethics",
                optionsJson = """[
                    {"id": "opt1", "text": "Fully implemented", "score": 4, "description": "Complete code of ethics in place"},
                    {"id": "opt2", "text": "In progress", "score": 3, "description": "Developing the code"},
                    {"id": "opt3", "text": "Not yet", "score": 2, "description": "No code of ethics yet"},
                    {"id": "opt4", "text": "Not applicable", "score": 1, "description": "Not applicable to the enterprise"}
                ]""",
                weight = 4,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 1
            ),
            
            // Transparency
            ESGQuestionEntity(
                id = "gov_transparency_001",
                pillar = ESGPillar.GOVERNANCE,
                category = "Transparency",
                question = "Is the enterprise transparent in reporting?",
                description = "Assessing transparency in financial and operational reporting",
                optionsJson = """[
                    {"id": "opt1", "text": "Fully implemented", "score": 4, "description": "Very transparent and detailed reporting"},
                    {"id": "opt2", "text": "In progress", "score": 3, "description": "Improving transparency"},
                    {"id": "opt3", "text": "Not yet", "score": 2, "description": "Not fully transparent"},
                    {"id": "opt4", "text": "Not applicable", "score": 1, "description": "Not applicable to the enterprise"}
                ]""",
                weight = 3,
                isRequired = true,
                userRole = UserRole.ENTERPRISE,
                order = 1
            )
        )
    }
    
    fun getStandardCategories(): List<ESGCategoryEntity> {
        return listOf(
            // Environmental Categories
            ESGCategoryEntity(
                id = "env_climate",
                name = "Climate Change & Energy",
                description = "Carbon emissions management and renewable energy",
                pillar = ESGPillar.ENVIRONMENTAL,
                order = 1
            ),
            ESGCategoryEntity(
                id = "env_waste",
                name = "Waste Management",
                description = "Waste management and recycling",
                pillar = ESGPillar.ENVIRONMENTAL,
                order = 2
            ),
            ESGCategoryEntity(
                id = "env_water",
                name = "Water Management",
                description = "Water management and source protection",
                pillar = ESGPillar.ENVIRONMENTAL,
                order = 3
            ),
            ESGCategoryEntity(
                id = "env_biodiversity",
                name = "Biodiversity",
                description = "Biodiversity protection",
                pillar = ESGPillar.ENVIRONMENTAL,
                order = 4
            ),
            
            // Social Categories
            ESGCategoryEntity(
                id = "soc_labor",
                name = "Labor Rights",
                description = "Labor rights and working conditions",
                pillar = ESGPillar.SOCIAL,
                order = 1
            ),
            ESGCategoryEntity(
                id = "soc_community",
                name = "Community Engagement",
                description = "Community engagement and social responsibility",
                pillar = ESGPillar.SOCIAL,
                order = 2
            ),
            ESGCategoryEntity(
                id = "soc_diversity",
                name = "Diversity & Inclusion",
                description = "Diversity and inclusion",
                pillar = ESGPillar.SOCIAL,
                order = 3
            ),
            ESGCategoryEntity(
                id = "soc_human_rights",
                name = "Human Rights",
                description = "Human rights",
                pillar = ESGPillar.SOCIAL,
                order = 4
            ),
            
            // Governance Categories
            ESGCategoryEntity(
                id = "gov_ethics",
                name = "Ethics & Compliance",
                description = "Ethics and compliance",
                pillar = ESGPillar.GOVERNANCE,
                order = 1
            ),
            ESGCategoryEntity(
                id = "gov_transparency",
                name = "Transparency",
                description = "Transparency and reporting",
                pillar = ESGPillar.GOVERNANCE,
                order = 2
            ),
            ESGCategoryEntity(
                id = "gov_risk",
                name = "Risk Management",
                description = "Risk management",
                pillar = ESGPillar.GOVERNANCE,
                order = 3
            ),
            ESGCategoryEntity(
                id = "gov_stakeholder",
                name = "Stakeholder Engagement",
                description = "Stakeholder engagement",
                pillar = ESGPillar.GOVERNANCE,
                order = 4
            )
        )
    }
}