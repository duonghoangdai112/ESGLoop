package com.ignitech.esgcompanion.domain.entity

enum class ESGPillar {
    ENVIRONMENTAL,
    SOCIAL,
    GOVERNANCE
}

data class ESGPillarInfo(
    val pillar: ESGPillar,
    val name: String,
    val description: String,
    val icon: String,
    val color: String
) {
    companion object {
        val ENVIRONMENTAL_INFO = ESGPillarInfo(
            pillar = ESGPillar.ENVIRONMENTAL,
            name = "Environmental",
            description = "Measures the enterprise's impact on the natural environment",
            icon = "E",
            color = "#4CAF50"
        )
        
        val SOCIAL_INFO = ESGPillarInfo(
            pillar = ESGPillar.SOCIAL,
            name = "Social", 
            description = "Reflects how the enterprise treats people",
            icon = "S",
            color = "#81C784"
        )
        
        val GOVERNANCE_INFO = ESGPillarInfo(
            pillar = ESGPillar.GOVERNANCE,
            name = "Governance",
            description = "Evaluates management structure and financial transparency",
            icon = "G",
            color = "#8BC34A"
        )
        
        val ALL_PILLARS = listOf(ENVIRONMENTAL_INFO, SOCIAL_INFO, GOVERNANCE_INFO)
    }
}

