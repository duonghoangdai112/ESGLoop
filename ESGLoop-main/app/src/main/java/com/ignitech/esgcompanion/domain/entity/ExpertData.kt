package com.ignitech.esgcompanion.domain.entity

data class ExpertData(
    val id: String,
    val name: String,
    val specialization: String,
    val rating: Float,
    val hourlyRate: Int,
    val experience: String,
    val isAvailable: Boolean = true
)
