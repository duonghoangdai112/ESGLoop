package com.ignitech.esgcompanion.domain.entity

data class ReportSummary(
    val id: String,
    val title: String,
    val description: String,
    val status: ReportStatus,
    val standard: ReportStandard,
    val createdAt: Long
)

data class ExpertReportSummary(
    val id: String,
    val title: String,
    val description: String,
    val companyName: String,
    val status: ReportStatus,
    val standard: ReportStandard,
    val submittedAt: Long
)
