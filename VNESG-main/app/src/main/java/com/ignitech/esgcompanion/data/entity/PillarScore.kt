package com.ignitech.esgcompanion.data.entity

import com.ignitech.esgcompanion.domain.entity.ESGPillar

data class PillarScore(
    val pillar: ESGPillar,
    val averageScore: Double
)
