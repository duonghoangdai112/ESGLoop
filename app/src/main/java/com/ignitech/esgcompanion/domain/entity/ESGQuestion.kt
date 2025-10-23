package com.ignitech.esgcompanion.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ESGQuestion(
    val id: String,
    val pillar: ESGPillar,
    val category: String,
    val question: String,
    val description: String? = null,
    val options: List<ESGQuestionOption>,
    val weight: Int = 1, // Trọng số câu hỏi
    val isRequired: Boolean = true
) : Parcelable

@Parcelize
data class ESGQuestionOption(
    val id: String,
    val text: String,
    val score: Int, // Điểm số từ 0-100
    val description: String? = null
) : Parcelable
