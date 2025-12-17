# ESG Auto-Assessment System - Technical Documentation

**Version:** 1.0  
**Date:** October 2025  
**Author:** ESG Companion Team

---

## 📋 Table of Contents

1. [Overview](#overview)
2. [System Architecture](#system-architecture)
3. [Database Design](#database-design)
4. [Data Flow](#data-flow)
5. [Implementation Guide](#implementation-guide)
6. [API Specifications](#api-specifications)
7. [Use Cases](#use-cases)
8. [Standards & Compliance](#standards--compliance)

---

## 1. Overview

### 1.1 Purpose

The ESG Auto-Assessment System automatically calculates ESG assessment scores based on daily tracker activities, providing:
- **Automation**: Reduce 80% manual assessment time
- **Objectivity**: Scores based on actual data, not self-reporting
- **Transparency**: Every score backed by evidence
- **Real-time**: See impact immediately
- **AI-driven**: Intelligent analysis and scoring
- **Audit-ready**: Complete trail for expert verification

### 1.2 Core Concept

```
Daily Tracker Activities (Continuous)
        ↓
   Accumulate over Quarter (3 months)
        ↓
   AI Analysis & Calculation
        ↓
   Suggest Scores for 60 Assessment Questions
        ↓
   Enterprise Review & Confirm
        ↓
   Generate Quarterly ESG Report
```

### 1.3 Key Features

- **Automatic Mapping**: AI links activities to assessment questions
- **Quantifiable Metrics**: Structured data for calculations
- **Multi-level Scoring**: Direct, indirect, and cumulative contributions
- **Evidence-based**: Photos, documents, KPIs as proof
- **Expert Verification**: Review and override workflow
- **Trend Analysis**: Compare quarters and predict future scores
- **Benchmark**: Compare with industry standards

---

## 2. System Architecture

### 2.1 High-Level Architecture

```
┌─────────────────────────────────────────────────────┐
│                 Presentation Layer                   │
│  - Tracker Screen (Daily data entry)                │
│  - Assessment Review Screen (Quarterly)             │
│  - AI Assistant (Insights & recommendations)        │
└────────────────┬────────────────────────────────────┘
                 │
┌────────────────┴────────────────────────────────────┐
│                  Business Logic Layer                │
│  - Activity Manager                                  │
│  - Metrics Calculator                                │
│  - Contribution Mapper (AI)                         │
│  - Score Aggregator                                  │
│  - Verification Workflow                             │
└────────────────┬────────────────────────────────────┘
                 │
┌────────────────┴────────────────────────────────────┐
│                    Data Layer                        │
│  - ESGTrackerEntity (Activities)                    │
│  - ESGActivityMetricEntity (Metrics)                │
│  - AssessmentContributionEntity (Mapping)           │
│  - AssessmentAggregationEntity (Final Scores)       │
└────────────────┬────────────────────────────────────┘
                 │
┌────────────────┴────────────────────────────────────┐
│                 External Services                    │
│  - OpenAI API (AI Analysis)                         │
│  - Cloud Storage (Evidence files)                   │
│  - Reporting APIs (Export to GRI, SASB, TCFD)      │
└─────────────────────────────────────────────────────┘
```

### 2.2 Component Relationships

```
ESGTrackerEntity (1) ──────────> (N) ESGActivityMetricEntity
       │                                    │
       │                                    │
       └──────────> AssessmentContributionEntity <─┘
                            │
                            │
                            ↓
                  AssessmentAggregationEntity
                            │
                            ↓
                    ESGAssessmentEntity
                      (Final Report)
```

---

## 3. Database Design

### 3.1 Enhanced ESGTrackerEntity

**Purpose**: Core activity tracking with minimal enhancements

**Table**: `esg_tracker_activities`

```kotlin
@Entity(tableName = "esg_tracker_activities")
data class ESGTrackerEntity(
    @PrimaryKey 
    val id: String,
    
    // Identity
    val userId: String,
    
    // Classification
    val activityType: ESGTrackerType,
    val pillar: ESGPillar,
    val title: String,
    val description: String,
    
    // Status
    val status: TrackerStatus,
    val priority: TrackerPriority = TrackerPriority.MEDIUM,
    
    // Dates
    val plannedDate: Long,
    val actualDate: Long? = null,
    val completedAt: Long? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    
    // Organization
    val createdBy: String = "",
    val assignedTo: String? = null,
    val department: String? = null,
    val location: String? = null,
    
    // Financial
    val budget: Double? = null,
    val actualCost: Double? = null,
    
    // Metadata
    val tags: String = "",
    val notes: String = "",
    
    // Recurring
    val isRecurring: Boolean = false,
    val recurringType: RecurringType? = null,
    val parentActivityId: String? = null,
    
    // Links
    val assessmentId: String? = null,
    val kpiId: String? = null,
    
    // Verification
    val isPublic: Boolean = true,
    val isVerified: Boolean = false,
    val verifiedBy: String? = null,
    val verifiedAt: Long? = null,
    
    // ✨ NEW FIELDS FOR AUTO-ASSESSMENT
    val fiscalYear: Int? = null,
    val fiscalQuarter: Int? = null,
    val assessmentCategory: String? = null
)
```

**New Fields Explained**:
- `fiscalYear`: Year for assessment aggregation (2025)
- `fiscalQuarter`: Quarter 1-4 for assessment period
- `assessmentCategory`: Category to help AI mapping ("Financial Inclusion", "Climate Change", etc.)

**Indexes**:
```sql
CREATE INDEX idx_tracker_user_fiscal ON esg_tracker_activities(userId, fiscalYear, fiscalQuarter);
CREATE INDEX idx_tracker_category ON esg_tracker_activities(assessmentCategory);
CREATE INDEX idx_tracker_completed ON esg_tracker_activities(completedAt);
```

---

### 3.2 ESGActivityMetricEntity (NEW)

**Purpose**: Store quantifiable metrics for each activity

**Table**: `esg_activity_metrics`

```kotlin
@Entity(tableName = "esg_activity_metrics")
data class ESGActivityMetricEntity(
    @PrimaryKey 
    val id: String,
    
    // Links
    @ColumnInfo(index = true)
    val activityId: String,
    val userId: String,
    
    // Metric Definition
    val metricType: MetricType,
    val metricName: String,
    val metricUnit: String,
    val metricDescription: String = "",
    
    // Values
    val baselineValue: Double? = null,
    val targetValue: Double? = null,
    val actualValue: Double,
    val achievementRate: Double? = null,
    
    // Context
    val measurementDate: Long,
    val fiscalPeriod: String,
    
    // Data Quality
    val dataSource: String? = null,
    val collectionMethod: String? = null,
    val accuracy: Double? = null,
    
    // Verification
    val verifiedBy: String? = null,
    val verifiedAt: Long? = null,
    val isOfficial: Boolean = false,
    
    // Evidence
    val evidenceAttachmentIds: String = "",
    
    // Metadata
    val notes: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

enum class MetricType {
    QUANTITY,           // Count: branches, employees, projects
    PERCENTAGE,         // Ratio: coverage, efficiency
    CARBON_EMISSION,    // CO2e in tons
    ENERGY,             // kWh, MWh
    WATER,              // m³, liters
    WASTE,              // kg, tons
    COST,               // VND, USD
    PEOPLE,             // People impacted
    COVERAGE,           // Geographic/demographic coverage
    DURATION,           // Hours, days
    FREQUENCY,          // Times per period
    CUSTOM              // Other metrics
}
```

**Example Data**:

```kotlin
// Activity: "Open 7 rural branches"
// Metric 1: Quantity
ESGActivityMetricEntity(
    id = "metric_001",
    activityId = "act_rural_001",
    metricType = QUANTITY,
    metricName = "rural_branches_opened",
    metricUnit = "branches",
    baselineValue = 3.0,
    targetValue = 10.0,
    actualValue = 10.0,
    achievementRate = 100.0,
    fiscalPeriod = "2025-Q3"
)

// Metric 2: Coverage
ESGActivityMetricEntity(
    id = "metric_002",
    activityId = "act_rural_001",
    metricType = PERCENTAGE,
    metricName = "rural_coverage_rate",
    metricUnit = "percent",
    baselineValue = 6.0,
    targetValue = 20.0,
    actualValue = 20.0,
    achievementRate = 100.0,
    fiscalPeriod = "2025-Q3"
)
```

**Indexes**:
```sql
CREATE INDEX idx_metric_activity ON esg_activity_metrics(activityId);
CREATE INDEX idx_metric_fiscal ON esg_activity_metrics(fiscalPeriod);
CREATE INDEX idx_metric_type ON esg_activity_metrics(metricType);
```

---

### 3.3 AssessmentContributionEntity (NEW)

**Purpose**: Link activities to assessment questions with scoring

**Table**: `esg_assessment_contributions`

```kotlin
@Entity(tableName = "esg_assessment_contributions")
data class AssessmentContributionEntity(
    @PrimaryKey 
    val id: String,
    
    // Links
    @ColumnInfo(index = true)
    val activityId: String,
    @ColumnInfo(index = true)
    val questionId: String,
    val userId: String,
    
    // Contribution Details
    val contributionType: ContributionType,
    val contributionWeight: Double,
    val contributionScore: Double,
    
    // Scoring
    val suggestedScore: Int,
    val finalScore: Int? = null,
    val scoreRationale: String = "",
    val confidenceLevel: Double,
    
    // Evidence
    val supportingMetricIds: String = "",
    val supportingAttachmentIds: String = "",
    
    // Fiscal Context
    val fiscalPeriod: String,
    val assessmentYear: Int,
    val assessmentQuarter: Int,
    
    // Calculation Metadata
    val calculationMethod: CalculationMethod,
    val calculatedBy: String,
    val calculatedAt: Long = System.currentTimeMillis(),
    val lastReviewedBy: String? = null,
    val lastReviewedAt: Long? = null,
    
    // Verification
    val isVerified: Boolean = false,
    val verifiedBy: String? = null,
    val verifiedAt: Long? = null,
    
    // Status
    val status: ContributionStatus = ContributionStatus.DRAFT,
    
    // Timestamps
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

enum class ContributionType {
    DIRECT,         // 1:1 mapping to question
    INDIRECT,       // Partial contribution
    SUPPORTING,     // Evidence for another activity
    CUMULATIVE      // Multiple activities aggregate
}

enum class CalculationMethod {
    RULE_BASED,     // Pre-defined rules
    AI_MODEL,       // Machine learning
    MANUAL,         // User input
    HYBRID          // Combination
}

enum class ContributionStatus {
    DRAFT,          // Being calculated
    PENDING_REVIEW, // Awaiting review
    APPROVED,       // Accepted
    REJECTED,       // Declined
    ARCHIVED        // Historical
}
```

**Example Data**:

```kotlin
AssessmentContributionEntity(
    id = "contrib_001",
    activityId = "act_rural_001",
    questionId = "soc_002", // "Has branches in rural areas?"
    
    contributionType = DIRECT,
    contributionWeight = 1.0,
    contributionScore = 4.0,
    
    suggestedScore = 4,
    finalScore = 4,
    scoreRationale = """
        Activity: Opened 7 rural branches
        Baseline: 3 branches (6% coverage)
        Target: 10 branches (20% coverage)
        Actual: 10 branches (20% coverage)
        Achievement: 100%
        
        Assessment: soc_002 "Has branches in rural areas"
        Threshold: >= 20% for "Fully implemented"
        Result: 20% coverage → Fully implemented (4 points)
        
        Evidence:
        - 7 branch opening certificates
        - 50 photos of new branches
        - 10 verified locations on map
        
        Confidence: 95% (high data quality)
    """,
    confidenceLevel = 0.95,
    
    supportingMetricIds = """["metric_001", "metric_002"]""",
    
    fiscalPeriod = "2025-Q3",
    assessmentYear = 2025,
    assessmentQuarter = 3,
    
    calculationMethod = HYBRID,
    calculatedBy = "AI_OpenAI_GPT4",
    
    isVerified = true,
    verifiedBy = "expert_001",
    
    status = APPROVED
)
```

**Indexes**:
```sql
CREATE INDEX idx_contribution_activity ON esg_assessment_contributions(activityId);
CREATE INDEX idx_contribution_question ON esg_assessment_contributions(questionId);
CREATE INDEX idx_contribution_fiscal ON esg_assessment_contributions(fiscalPeriod);
CREATE INDEX idx_contribution_status ON esg_assessment_contributions(status);
```

---

### 3.4 AssessmentAggregationEntity (NEW)

**Purpose**: Aggregate final scores for each question per quarter

**Table**: `esg_assessment_aggregation`

```kotlin
@Entity(tableName = "esg_assessment_aggregation")
data class AssessmentAggregationEntity(
    @PrimaryKey 
    val id: String,
    
    // Identity
    val userId: String,
    @ColumnInfo(index = true)
    val questionId: String,
    
    // Period
    val fiscalPeriod: String,
    val assessmentYear: Int,
    val assessmentQuarter: Int,
    
    // Aggregated Score
    val totalContributions: Int,
    val aiSuggestedScore: Int,
    val userOverrideScore: Int? = null,
    val finalScore: Int,
    
    // Score Breakdown
    val scoreBreakdownJson: String = "",
    
    // Evidence Summary
    val totalMetrics: Int = 0,
    val totalAttachments: Int = 0,
    val contributionIds: String = "",
    
    // Quality Metrics
    val confidenceLevel: Double,
    val completenessScore: Double,
    val consistencyScore: Double,
    
    // Comparison
    val previousPeriodScore: Int? = null,
    val changeFromPrevious: Int? = null,
    val trendDirection: TrendDirection? = null,
    val industryBenchmark: Double? = null,
    val benchmarkComparison: BenchmarkStatus? = null,
    
    // Verification
    val isVerified: Boolean = false,
    val verifiedBy: String? = null,
    val verifiedAt: Long? = null,
    val verificationNotes: String = "",
    
    // Metadata
    val calculatedAt: Long = System.currentTimeMillis(),
    val lastUpdatedAt: Long = System.currentTimeMillis()
)

enum class TrendDirection {
    IMPROVING,      // Score increasing
    STABLE,         // No significant change
    DECLINING       // Score decreasing
}

enum class BenchmarkStatus {
    ABOVE,          // Above industry average
    AT,             // At industry average
    BELOW           // Below industry average
}
```

**Score Breakdown JSON Format**:

```json
{
  "question_id": "soc_002",
  "question_text": "Has branches in rural areas?",
  "final_score": 4,
  "max_score": 4,
  "percentage": 100,
  "contributions": [
    {
      "activity_id": "act_rural_001",
      "activity_title": "Open 7 rural branches",
      "contribution_score": 4,
      "weight": 1.0,
      "evidence_count": 57
    }
  ],
  "metrics_summary": {
    "rural_branches": {
      "baseline": 3,
      "target": 10,
      "actual": 10,
      "achievement_rate": 100
    },
    "coverage_rate": {
      "baseline": 6,
      "target": 20,
      "actual": 20,
      "unit": "percent"
    }
  },
  "calculation_logic": "Direct mapping: Rural coverage 20% exceeds threshold of 20% for 'Fully implemented'",
  "confidence": 0.95
}
```

**Indexes**:
```sql
CREATE INDEX idx_aggregation_user_question ON esg_assessment_aggregation(userId, questionId);
CREATE INDEX idx_aggregation_fiscal ON esg_assessment_aggregation(fiscalPeriod);
CREATE INDEX idx_aggregation_year_quarter ON esg_assessment_aggregation(assessmentYear, assessmentQuarter);
```

---

## 4. Data Flow

### 4.1 Complete Workflow

```
┌─────────────────────────────────────────────────────┐
│ PHASE 1: Daily Activity Tracking (Continuous)       │
└─────────────────────────────────────────────────────┘

Day 1: User creates activity
  ↓
  ESGTrackerEntity created
    - title: "Open rural branch in Ha Giang"
    - activityType: LOCAL_DEVELOPMENT
    - fiscalQuarter: 3
    - assessmentCategory: "Financial Inclusion"
  ↓
  User adds metrics
  ↓
  ESGActivityMetricEntity created
    - metricType: QUANTITY
    - metricName: "rural_branches_opened"
    - baselineValue: 3, targetValue: 10, actualValue: 4

Day 15: Activity in progress, more metrics added
  ↓
  ESGActivityMetricEntity updated
    - actualValue: 7 (progress update)

Day 30: Activity completed
  ↓
  ESGTrackerEntity updated
    - status: COMPLETED
    - completedAt: timestamp
  ↓
  Trigger AI Analysis (async)

┌─────────────────────────────────────────────────────┐
│ PHASE 2: AI Analysis & Mapping (Background)         │
└─────────────────────────────────────────────────────┘

AI Service receives completed activity
  ↓
  Step 1: Analyze activity context
    - activityType: LOCAL_DEVELOPMENT
    - pillar: SOCIAL
    - assessmentCategory: "Financial Inclusion"
    - metrics: rural_branches_opened
  ↓
  Step 2: Find related assessment questions
    - Query ESGQuestionEntity
    - Filter by pillar: SOCIAL
    - Filter by category: "Financial Inclusion"
    - AI semantic matching on question text
    → Result: ["soc_002", "soc_004"]
  ↓
  Step 3: Calculate contribution scores
    For each question:
      - Load question thresholds
      - Analyze metrics
      - Calculate score (0, 2, or 4)
      - Generate rationale
      - Calculate confidence
  ↓
  Step 4: Create contributions
    AssessmentContributionEntity created
      - questionId: "soc_002"
      - suggestedScore: 4
      - confidenceLevel: 0.95
      - scoreRationale: "..."

┌─────────────────────────────────────────────────────┐
│ PHASE 3: Quarterly Aggregation (End of Quarter)     │
└─────────────────────────────────────────────────────┘

September 30, 2025: End of Q3
  ↓
  Trigger aggregation job
  ↓
  For each assessment question (60 questions):
    Step 1: Load all contributions for Q3
      - Query by fiscalPeriod = "2025-Q3"
      - Filter by status = APPROVED
    ↓
    Step 2: Aggregate scores
      - If single contribution: use its score
      - If multiple contributions: weighted average
      - Apply business rules
    ↓
    Step 3: Calculate quality metrics
      - Confidence: average of all contributions
      - Completeness: % of required evidence
      - Consistency: variance check
    ↓
    Step 4: Compare with previous quarter
      - Load Q2 aggregation
      - Calculate change
      - Determine trend
    ↓
    Step 5: Benchmark comparison
      - Load industry data
      - Compare scores
    ↓
    Step 6: Create aggregation
      AssessmentAggregationEntity created
        - questionId: "soc_002"
        - aiSuggestedScore: 4
        - totalContributions: 1
        - confidenceLevel: 0.95
        - previousPeriodScore: 2
        - changeFromPrevious: +2

┌─────────────────────────────────────────────────────┐
│ PHASE 4: User Review & Verification (UI)            │
└─────────────────────────────────────────────────────┘

User opens Assessment Review screen
  ↓
  Load all aggregations for Q3
  ↓
  For each question, display:
    - Question text
    - AI suggested score
    - Confidence level
    - Evidence summary
    - Contributing activities
    - Comparison with Q2
  ↓
  User can:
    - Accept AI suggestion
    - Override with different score
    - Add notes
    - Request expert review
  ↓
  If overridden:
    AssessmentAggregationEntity updated
      - userOverrideScore: 2
      - finalScore: 2 (override takes precedence)
  ↓
  Submit for expert verification
  ↓
  Expert reviews:
    - Check evidence
    - Verify metrics
    - Approve or reject
  ↓
  If approved:
    AssessmentAggregationEntity updated
      - isVerified: true
      - verifiedBy: "expert_001"
      - verifiedAt: timestamp

┌─────────────────────────────────────────────────────┐
│ PHASE 5: Report Generation                          │
└─────────────────────────────────────────────────────┘

All questions verified
  ↓
  Generate ESGAssessmentEntity
    - assessmentId: "assessment_2025_Q3"
    - userId: "user_3"
    - pillar: ENVIRONMENTAL / SOCIAL / GOVERNANCE
    - totalScore: sum of all finalScores
    - completedAt: timestamp
  ↓
  Export to reporting formats:
    - PDF: Visual report with charts
    - GRI: Global Reporting Initiative format
    - SASB: Sustainability Accounting Standards
    - TCFD: Task Force on Climate-related Disclosures
  ↓
  Store in assessment history
  ↓
  Notify stakeholders
```

---

## 5. Implementation Guide

### 5.1 Phase 1: Database Setup

**Step 1: Create new entities**

File: `app/src/main/java/com/ignitech/esgcompanion/data/entity/ESGActivityMetricEntity.kt`

```kotlin
package com.ignitech.esgcompanion.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "esg_activity_metrics")
data class ESGActivityMetricEntity(
    @PrimaryKey 
    val id: String,
    
    @ColumnInfo(index = true)
    val activityId: String,
    val userId: String,
    
    val metricType: MetricType,
    val metricName: String,
    val metricUnit: String,
    val metricDescription: String = "",
    
    val baselineValue: Double? = null,
    val targetValue: Double? = null,
    val actualValue: Double,
    val achievementRate: Double? = null,
    
    val measurementDate: Long,
    val fiscalPeriod: String,
    
    val dataSource: String? = null,
    val collectionMethod: String? = null,
    val accuracy: Double? = null,
    
    val verifiedBy: String? = null,
    val verifiedAt: Long? = null,
    val isOfficial: Boolean = false,
    
    val evidenceAttachmentIds: String = "",
    
    val notes: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

enum class MetricType {
    QUANTITY, PERCENTAGE, CARBON_EMISSION, ENERGY,
    WATER, WASTE, COST, PEOPLE, COVERAGE,
    DURATION, FREQUENCY, CUSTOM
}
```

**Step 2: Create DAOs**

File: `app/src/main/java/com/ignitech/esgcompanion/data/local/dao/ESGActivityMetricDao.kt`

```kotlin
package com.ignitech.esgcompanion.data.local.dao

import androidx.room.*
import com.ignitech.esgcompanion.data.entity.ESGActivityMetricEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ESGActivityMetricDao {
    
    @Query("SELECT * FROM esg_activity_metrics WHERE activityId = :activityId")
    fun getMetricsByActivity(activityId: String): Flow<List<ESGActivityMetricEntity>>
    
    @Query("SELECT * FROM esg_activity_metrics WHERE userId = :userId AND fiscalPeriod = :period")
    fun getMetricsByPeriod(userId: String, period: String): Flow<List<ESGActivityMetricEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMetric(metric: ESGActivityMetricEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMetrics(metrics: List<ESGActivityMetricEntity>)
    
    @Update
    suspend fun updateMetric(metric: ESGActivityMetricEntity)
    
    @Delete
    suspend fun deleteMetric(metric: ESGActivityMetricEntity)
    
    @Query("DELETE FROM esg_activity_metrics WHERE activityId = :activityId")
    suspend fun deleteMetricsByActivity(activityId: String)
}
```

**Step 3: Update database**

File: `app/src/main/java/com/ignitech/esgcompanion/data/local/ESGDatabase.kt`

```kotlin
@Database(
    entities = [
        // ... existing entities ...
        ESGActivityMetricEntity::class,
        AssessmentContributionEntity::class,
        AssessmentAggregationEntity::class
    ],
    version = 2, // Increment version
    exportSchema = true
)
abstract class ESGDatabase : RoomDatabase() {
    // ... existing DAOs ...
    abstract fun activityMetricDao(): ESGActivityMetricDao
    abstract fun assessmentContributionDao(): AssessmentContributionDao
    abstract fun assessmentAggregationDao(): AssessmentAggregationDao
}
```

**Step 4: Create migration**

```kotlin
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Add new fields to tracker
        database.execSQL("""
            ALTER TABLE esg_tracker_activities 
            ADD COLUMN fiscalYear INTEGER
        """)
        database.execSQL("""
            ALTER TABLE esg_tracker_activities 
            ADD COLUMN fiscalQuarter INTEGER
        """)
        database.execSQL("""
            ALTER TABLE esg_tracker_activities 
            ADD COLUMN assessmentCategory TEXT
        """)
        
        // Create metrics table
        database.execSQL("""
            CREATE TABLE IF NOT EXISTS esg_activity_metrics (
                id TEXT PRIMARY KEY NOT NULL,
                activityId TEXT NOT NULL,
                userId TEXT NOT NULL,
                metricType TEXT NOT NULL,
                metricName TEXT NOT NULL,
                metricUnit TEXT NOT NULL,
                metricDescription TEXT NOT NULL,
                baselineValue REAL,
                targetValue REAL,
                actualValue REAL NOT NULL,
                achievementRate REAL,
                measurementDate INTEGER NOT NULL,
                fiscalPeriod TEXT NOT NULL,
                dataSource TEXT,
                collectionMethod TEXT,
                accuracy REAL,
                verifiedBy TEXT,
                verifiedAt INTEGER,
                isOfficial INTEGER NOT NULL DEFAULT 0,
                evidenceAttachmentIds TEXT NOT NULL,
                notes TEXT NOT NULL,
                createdAt INTEGER NOT NULL,
                updatedAt INTEGER NOT NULL
            )
        """)
        database.execSQL("CREATE INDEX idx_metric_activity ON esg_activity_metrics(activityId)")
        
        // Create contributions table
        // ... similar SQL for AssessmentContributionEntity ...
        
        // Create aggregation table  
        // ... similar SQL for AssessmentAggregationEntity ...
    }
}
```

---

### 5.2 Phase 2: Repository Layer

File: `app/src/main/java/com/ignitech/esgcompanion/data/repository/AutoAssessmentRepository.kt`

```kotlin
package com.ignitech.esgcompanion.data.repository

import com.ignitech.esgcompanion.data.local.dao.*
import com.ignitech.esgcompanion.data.entity.*
import com.ignitech.esgcompanion.data.remote.OpenAIService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AutoAssessmentRepository @Inject constructor(
    private val activityDao: ESGTrackerDao,
    private val metricDao: ESGActivityMetricDao,
    private val contributionDao: AssessmentContributionDao,
    private val aggregationDao: AssessmentAggregationDao,
    private val questionDao: ESGQuestionDao,
    private val openAIService: OpenAIService
) {
    
    // === METRICS ===
    
    fun getMetricsByActivity(activityId: String): Flow<List<ESGActivityMetricEntity>> {
        return metricDao.getMetricsByActivity(activityId)
    }
    
    suspend fun addMetric(metric: ESGActivityMetricEntity) {
        metricDao.insertMetric(metric)
    }
    
    // === AI ANALYSIS ===
    
    suspend fun analyzeActivityForContributions(activityId: String) {
        val activity = activityDao.getActivityById(activityId).first()
        val metrics = metricDao.getMetricsByActivity(activityId).first()
        
        // Find related questions
        val relatedQuestions = findRelatedQuestions(activity, metrics)
        
        // Calculate contributions
        relatedQuestions.forEach { question ->
            val contribution = calculateContribution(activity, metrics, question)
            contributionDao.insertContribution(contribution)
        }
    }
    
    private suspend fun findRelatedQuestions(
        activity: ESGTrackerEntity,
        metrics: List<ESGActivityMetricEntity>
    ): List<ESGQuestionEntity> {
        // Step 1: Filter by pillar and category
        val candidateQuestions = questionDao.getQuestionsByPillarAndCategory(
            pillar = activity.pillar,
            category = activity.assessmentCategory ?: ""
        ).first()
        
        // Step 2: AI semantic matching
        val prompt = buildAIPrompt(activity, metrics, candidateQuestions)
        val aiResponse = openAIService.generateAIResponse(OpenAIRequest(
            model = "gpt-4",
            messages = listOf(
                OpenAIMessage(role = "system", content = "You are an ESG assessment expert..."),
                OpenAIMessage(role = "user", content = prompt)
            )
        ))
        
        // Step 3: Parse AI response to get question IDs
        val questionIds = parseQuestionIds(aiResponse.choices.first().message.content)
        
        return candidateQuestions.filter { it.id in questionIds }
    }
    
    private suspend fun calculateContribution(
        activity: ESGTrackerEntity,
        metrics: List<ESGActivityMetricEntity>,
        question: ESGQuestionEntity
    ): AssessmentContributionEntity {
        // Use AI to calculate score
        val prompt = buildScoringPrompt(activity, metrics, question)
        val aiResponse = openAIService.generateAIResponse(OpenAIRequest(
            model = "gpt-4",
            messages = listOf(
                OpenAIMessage(role = "system", content = "Calculate ESG assessment score..."),
                OpenAIMessage(role = "user", content = prompt)
            )
        ))
        
        val scoringResult = parseScoring(aiResponse.choices.first().message.content)
        
        return AssessmentContributionEntity(
            id = generateId(),
            activityId = activity.id,
            questionId = question.id,
            userId = activity.userId,
            contributionType = scoringResult.type,
            contributionWeight = scoringResult.weight,
            contributionScore = scoringResult.score.toDouble(),
            suggestedScore = scoringResult.score,
            scoreRationale = scoringResult.rationale,
            confidenceLevel = scoringResult.confidence,
            supportingMetricIds = metrics.map { it.id }.toString(),
            fiscalPeriod = "${activity.fiscalYear}-Q${activity.fiscalQuarter}",
            assessmentYear = activity.fiscalYear ?: 0,
            assessmentQuarter = activity.fiscalQuarter ?: 0,
            calculationMethod = CalculationMethod.AI_MODEL,
            calculatedBy = "AI_GPT4"
        )
    }
    
    // === AGGREGATION ===
    
    suspend fun aggregateQuarterlyScores(
        userId: String,
        year: Int,
        quarter: Int
    ) {
        val fiscalPeriod = "$year-Q$quarter"
        val allQuestions = questionDao.getAllQuestions().first()
        
        allQuestions.forEach { question ->
            val contributions = contributionDao.getContributionsByQuestionAndPeriod(
                questionId = question.id,
                fiscalPeriod = fiscalPeriod,
                status = ContributionStatus.APPROVED
            ).first()
            
            if (contributions.isEmpty()) {
                // No data for this question
                return@forEach
            }
            
            // Calculate aggregated score
            val aggregation = calculateAggregation(question, contributions, userId, year, quarter)
            aggregationDao.insertAggregation(aggregation)
        }
    }
    
    private fun calculateAggregation(
        question: ESGQuestionEntity,
        contributions: List<AssessmentContributionEntity>,
        userId: String,
        year: Int,
        quarter: Int
    ): AssessmentAggregationEntity {
        // Logic to aggregate multiple contributions
        val finalScore = if (contributions.size == 1) {
            contributions.first().suggestedScore
        } else {
            // Weighted average or max, depending on question type
            contributions.maxOf { it.suggestedScore }
        }
        
        return AssessmentAggregationEntity(
            id = generateId(),
            userId = userId,
            questionId = question.id,
            fiscalPeriod = "$year-Q$quarter",
            assessmentYear = year,
            assessmentQuarter = quarter,
            totalContributions = contributions.size,
            aiSuggestedScore = finalScore,
            finalScore = finalScore,
            confidenceLevel = contributions.map { it.confidenceLevel }.average(),
            completenessScore = calculateCompleteness(contributions),
            consistencyScore = calculateConsistency(contributions),
            contributionIds = contributions.map { it.id }.toString()
        )
    }
}
```

---

**(Continued in next section due to length...)**

---

## 6. API Specifications

### 6.1 Add Activity with Metrics

**Endpoint**: Internal API (ViewModel → Repository)

```kotlin
// ViewModel
viewModelScope.launch {
    // 1. Create activity
    val activity = ESGTrackerEntity(
        id = UUID.randomUUID().toString(),
        userId = currentUserId,
        title = "Open 7 rural branches",
        activityType = ESGTrackerType.LOCAL_DEVELOPMENT,
        pillar = ESGPillar.SOCIAL,
        fiscalYear = 2025,
        fiscalQuarter = 3,
        assessmentCategory = "Financial Inclusion",
        // ... other fields
    )
    repository.addActivity(activity)
    
    // 2. Add metrics
    val metric1 = ESGActivityMetricEntity(
        id = UUID.randomUUID().toString(),
        activityId = activity.id,
        userId = currentUserId,
        metricType = MetricType.QUANTITY,
        metricName = "rural_branches_opened",
        metricUnit = "branches",
        baselineValue = 3.0,
        targetValue = 10.0,
        actualValue = 7.0, // Progress
        measurementDate = System.currentTimeMillis(),
        fiscalPeriod = "2025-Q3"
    )
    repository.addMetric(metric1)
    
    // 3. When completed
    repository.updateActivityStatus(activity.id, TrackerStatus.COMPLETED)
    
    // 4. Trigger AI analysis (async)
    repository.analyzeActivityForContributions(activity.id)
}
```

### 6.2 Quarterly Aggregation

```kotlin
// Background job (WorkManager)
class QuarterlyAggregationWorker @Inject constructor(
    private val repository: AutoAssessmentRepository
) : CoroutineWorker() {
    
    override suspend fun doWork(): Result {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val currentQuarter = (Calendar.getInstance().get(Calendar.MONTH) / 3) + 1
        
        try {
            repository.aggregateQuarterlyScores(
                userId = getUserId(),
                year = currentYear,
                quarter = currentQuarter
            )
            return Result.success()
        } catch (e: Exception) {
            return Result.retry()
        }
    }
}
```

---

## 7. Use Cases

### 7.1 Complete Example: Rural Branches

**Scenario**: Bank opens 7 rural branches in Q3/2025

**Timeline**:

**July 1, 2025**: Create activity
```kotlin
val activity = ESGTrackerEntity(
    id = "act_001",
    userId = "bank_vietcombank",
    title = "Open 7 rural branches in northern provinces",
    description = "Expand banking services to underserved rural areas",
    activityType = LOCAL_DEVELOPMENT,
    pillar = SOCIAL,
    status = PLANNED,
    plannedDate = parseDate("2025-09-30"),
    fiscalYear = 2025,
    fiscalQuarter = 3,
    assessmentCategory = "Financial Inclusion"
)
```

**August 15, 2025**: Progress update
```kotlin
val metric = ESGActivityMetricEntity(
    id = "metric_001",
    activityId = "act_001",
    metricType = QUANTITY,
    metricName = "rural_branches_opened",
    metricUnit = "branches",
    baselineValue = 3.0,
    targetValue = 10.0,
    actualValue = 5.0, // 5 branches opened so far
    achievementRate = 50.0,
    measurementDate = parseDate("2025-08-15"),
    fiscalPeriod = "2025-Q3"
)
```

**September 30, 2025**: Activity completed
```kotlin
// Update activity
activity.copy(
    status = COMPLETED,
    completedAt = parseDate("2025-09-30")
)

// Update metrics
metric.copy(
    actualValue = 10.0, // All 10 branches opened
    achievementRate = 100.0
)

// Add coverage metric
val coverageMetric = ESGActivityMetricEntity(
    id = "metric_002",
    activityId = "act_001",
    metricType = PERCENTAGE,
    metricName = "rural_coverage_rate",
    metricUnit = "percent",
    baselineValue = 6.0,
    targetValue = 20.0,
    actualValue = 20.0,
    fiscalPeriod = "2025-Q3"
)

// Trigger AI analysis
repository.analyzeActivityForContributions("act_001")
```

**AI Analysis Output**:
```kotlin
// Contribution 1: Direct mapping to soc_002
AssessmentContributionEntity(
    id = "contrib_001",
    activityId = "act_001",
    questionId = "soc_002", // "Has branches in rural areas"
    contributionType = DIRECT,
    suggestedScore = 4, // Fully implemented
    scoreRationale = """
        Question: "The financial institution has branches in rural areas"
        
        Threshold Analysis:
        - Fully implemented (4): >= 20% rural branches
        - Partially (2): 10-19% rural branches
        - Not implemented (0): < 10% rural branches
        
        Current Data:
        - Total branches: 50
        - Rural branches: 10 (increased from 3)
        - Rural coverage: 20%
        
        Assessment: 20% meets threshold for "Fully implemented"
        Score: 4 points
        
        Evidence:
        - 7 branch opening ceremonies documented
        - 50 photos of new branches
        - 10 official operating licenses
        - Government approval documents
        
        Confidence: 95% (high-quality evidence)
    """,
    confidenceLevel = 0.95,
    fiscalPeriod = "2025-Q3"
)

// Contribution 2: Indirect mapping to soc_004
AssessmentContributionEntity(
    id = "contrib_002",
    activityId = "act_001",
    questionId = "soc_004", // "Share of loans to MSMEs above 10%"
    contributionType = INDIRECT,
    contributionWeight = 0.3,
    suggestedScore = 2,
    scoreRationale = """
        Rural branches typically serve MSMEs.
        Opening 7 rural branches likely increases MSME access.
        However, need actual loan data to confirm full impact.
        Partial contribution to this question.
    """,
    confidenceLevel = 0.70
)
```

**October 5, 2025**: End-of-quarter aggregation
```kotlin
// Aggregate for soc_002
AssessmentAggregationEntity(
    id = "agg_001",
    userId = "bank_vietcombank",
    questionId = "soc_002",
    fiscalPeriod = "2025-Q3",
    totalContributions = 1,
    aiSuggestedScore = 4,
    finalScore = 4,
    scoreBreakdownJson = """{
        "question": "soc_002",
        "contributions": [
            {"activity": "act_001", "score": 4, "weight": 1.0}
        ],
        "logic": "Direct mapping with 100% achievement",
        "evidence_summary": {
            "branches_opened": 7,
            "coverage": "20%",
            "photos": 50,
            "documents": 10
        }
    }""",
    confidenceLevel = 0.95,
    previousPeriodScore = 2, // Q2 was "Partially implemented"
    changeFromPrevious = +2,
    trendDirection = IMPROVING
)
```

**October 10, 2025**: User review
```
User opens Assessment Review screen:

┌─────────────────────────────────────────┐
│ Q3/2025 Assessment Review                │
├─────────────────────────────────────────┤
│                                          │
│ Question soc_002: ⭐⭐⭐⭐ (4/4)         │
│ "Has branches in rural areas"           │
│                                          │
│ 🤖 AI Suggested Score: 4 (Fully)        │
│ 📊 Confidence: 95%                       │
│ 📈 Trend: +2 from Q2 (IMPROVING)        │
│                                          │
│ 📋 Contributing Activities:              │
│ ├─ "Open 7 rural branches" ✅           │
│ │  Score: 4/4                            │
│ │  Evidence: 67 items                    │
│ │  Verified: Yes                         │
│                                          │
│ 📊 Key Metrics:                          │
│ ├─ Rural branches: 3 → 10 (+233%)       │
│ ├─ Coverage: 6% → 20% (Target: 20%)    │
│ └─ Achievement: 100%                     │
│                                          │
│ [✓ Accept] [✏ Override] [👁 Details]    │
└─────────────────────────────────────────┘

User clicks [✓ Accept]
```

**October 12, 2025**: Expert verification
```
Expert "Nguyen Van A" reviews:
- Checks photos of branches
- Verifies operating licenses
- Confirms coverage calculation
- Approves score

AssessmentAggregationEntity updated:
  isVerified = true
  verifiedBy = "expert_001"
  verifiedAt = parseDate("2025-10-12")
```

**October 15, 2025**: Final report generation
```kotlin
// Generate final assessment
ESGAssessmentEntity(
    id = "assessment_2025_Q3",
    userId = "bank_vietcombank",
    pillar = SOCIAL,
    totalScore = 168, // Sum of all social questions
    maxScore = 240,
    percentageScore = 70.0,
    completedAt = parseDate("2025-10-15")
)

// Export reports
exportToGRI("assessment_2025_Q3")
exportToSASB("assessment_2025_Q3")
exportToPDF("assessment_2025_Q3")
```

---

## 8. Standards & Compliance

### 8.1 ESG Reporting Frameworks

**GRI (Global Reporting Initiative)**
- Metrics align with GRI 201-419 standards
- Automatic mapping to GRI disclosure requirements
- Export format compliant with GRI Standards

**SASB (Sustainability Accounting Standards Board)**
- Industry-specific metrics (Financial Services)
- Quantitative and qualitative disclosures
- Materiality assessment integration

**TCFD (Task Force on Climate-related Disclosures)**
- Governance metrics
- Strategy assessment
- Risk management
- Metrics and targets

### 8.2 Data Quality Standards

**ISO 14064** (GHG Accounting)
- Carbon emission calculations
- Verification requirements
- Baseline establishment

**AA1000** (Assurance Standard)
- Stakeholder engagement
- Materiality determination
- Completeness assessment

### 8.3 Audit Trail Requirements

Every contribution must have:
- **Who**: calculatedBy, verifiedBy
- **When**: calculatedAt, verifiedAt
- **What**: scoreRationale, evidence
- **How**: calculationMethod
- **Why**: contributionType

---

## 9. Security & Privacy

### 9.1 Data Access Control

```kotlin
enum class AccessLevel {
    OWNER,      // Full CRUD
    EDITOR,     // Can add/edit activities
    VIEWER,     // Read-only
    EXPERT,     // Can verify
    AUDITOR     // Can audit, read-only
}
```

### 9.2 Sensitive Data

- Financial metrics encrypted at rest
- Personal data (verifiedBy) pseudonymized
- Audit logs immutable
- GDPR compliance for EU operations

---

## 10. Performance Optimization

### 10.1 Database Indexing

```sql
-- Critical indexes
CREATE INDEX idx_tracker_user_fiscal ON esg_tracker_activities(userId, fiscalYear, fiscalQuarter);
CREATE INDEX idx_metric_activity ON esg_activity_metrics(activityId);
CREATE INDEX idx_contribution_question_period ON esg_assessment_contributions(questionId, fiscalPeriod);
CREATE INDEX idx_aggregation_user_period ON esg_assessment_aggregation(userId, fiscalPeriod);
```

### 10.2 Caching Strategy

- Cache aggregation results (invalidate on new contribution)
- Cache AI responses (reuse similar analyses)
- Preload assessment questions
- Lazy load evidence attachments

### 10.3 Background Processing

- AI analysis runs async (WorkManager)
- Quarterly aggregation scheduled job
- Evidence upload in background
- Report generation queued

---

## 11. Testing Strategy

### 11.1 Unit Tests

```kotlin
@Test
fun `calculateContribution should return score 4 for 20% rural coverage`() {
    val metric = ESGActivityMetricEntity(
        metricName = "rural_coverage",
        actualValue = 20.0,
        targetValue = 20.0
    )
    
    val score = calculator.calculateScore(
        questionId = "soc_002",
        metrics = listOf(metric)
    )
    
    assertEquals(4, score)
}
```

### 11.2 Integration Tests

```kotlin
@Test
fun `end-to-end workflow from activity to aggregation`() = runTest {
    // 1. Create activity
    val activityId = repository.addActivity(testActivity)
    
    // 2. Add metrics
    repository.addMetric(testMetric)
    
    // 3. Complete activity
    repository.completeActivity(activityId)
    
    // 4. Analyze
    repository.analyzeActivityForContributions(activityId)
    
    // 5. Aggregate
    repository.aggregateQuarterlyScores("user", 2025, 3)
    
    // 6. Verify
    val aggregation = repository.getAggregation("soc_002", "2025-Q3").first()
    assertEquals(4, aggregation.finalScore)
}
```

---

## 12. Monitoring & Analytics

### 12.1 Key Metrics

- **Automation Rate**: % of questions auto-scored
- **Confidence Distribution**: Average confidence levels
- **Override Rate**: How often users change AI suggestions
- **Verification Time**: Time from suggestion to approval
- **Data Completeness**: % of activities with full metrics

### 12.2 Dashboards

```
Admin Dashboard:
├─ Activities per quarter
├─ Metrics quality score
├─ AI accuracy (compared to expert reviews)
├─ Top contributing activities
└─ Missing data alerts
```

---

## 13. Future Enhancements

### 13.1 Phase 2 Features

- **Predictive Scoring**: Forecast Q4 scores based on Q1-Q3 trends
- **Benchmark Integration**: Real-time comparison with industry peers
- **Mobile Evidence Capture**: Photo/video upload from field
- **Collaboration**: Multi-user workflow for large enterprises
- **Automated Reminders**: Notify when activities due or overdue

### 13.2 Phase 3 Features

- **Machine Learning**: Custom models trained on enterprise data
- **Natural Language**: Chat-based activity logging
- **IoT Integration**: Automatic metrics from sensors (energy, water)
- **Blockchain**: Immutable audit trail on distributed ledger
- **External Audit API**: Direct access for auditors

---

## 14. Conclusion

The ESG Auto-Assessment System transforms manual quarterly assessments into an automated, data-driven process. By tracking daily activities and their quantifiable impacts, enterprises can:

✅ **Save Time**: 80% reduction in assessment effort  
✅ **Improve Accuracy**: Data-driven vs subjective scoring  
✅ **Increase Transparency**: Every score has evidence  
✅ **Enable Continuous Improvement**: Real-time feedback  
✅ **Meet Compliance**: Audit-ready documentation  
✅ **Drive Action**: Clear link between activities and scores  

This system sets a new standard for ESG management in the financial sector and beyond.

---

**Document Version History**

| Version | Date | Changes | Author |
|---------|------|---------|--------|
| 1.0 | Oct 2025 | Initial release | ESG Team |

---

**Contact**

For technical questions: tech@esgcompanion.com  
For implementation support: support@esgcompanion.com

---

*End of Document*

