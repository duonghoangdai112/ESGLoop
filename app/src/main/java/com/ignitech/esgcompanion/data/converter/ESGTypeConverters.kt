package com.ignitech.esgcompanion.data.converter

import androidx.room.TypeConverter
import com.ignitech.esgcompanion.data.entity.AssessmentAnswer
import com.ignitech.esgcompanion.domain.entity.UserRole
import com.ignitech.esgcompanion.data.entity.LearningResourceType
import com.ignitech.esgcompanion.data.entity.LearningLevel
import com.ignitech.esgcompanion.data.entity.QuizQuestionType
import com.ignitech.esgcompanion.data.entity.ESGTrackerType
import com.ignitech.esgcompanion.data.entity.TrackerStatus
import com.ignitech.esgcompanion.data.entity.TrackerPriority
import com.ignitech.esgcompanion.data.entity.RecurringType
import com.ignitech.esgcompanion.data.entity.AttachmentType
import com.ignitech.esgcompanion.data.entity.MeasurementFrequency
import com.ignitech.esgcompanion.data.entity.TimelineEventType
import com.ignitech.esgcompanion.domain.entity.AssessmentStatus
import com.ignitech.esgcompanion.domain.entity.ESGPillar
import com.ignitech.esgcompanion.domain.entity.ReportStandard
import com.ignitech.esgcompanion.domain.entity.ReportStatus

class ESGTypeConverters {
    
    @TypeConverter
    fun fromESGPillar(pillar: ESGPillar): String {
        return pillar.name
    }
    
    @TypeConverter
    fun toESGPillar(pillar: String): ESGPillar {
        return ESGPillar.valueOf(pillar)
    }
    
    @TypeConverter
    fun fromAssessmentStatus(status: AssessmentStatus): String {
        return status.name
    }
    
    @TypeConverter
    fun toAssessmentStatus(status: String): AssessmentStatus {
        return AssessmentStatus.valueOf(status)
    }
    
    @TypeConverter
    fun fromAssessmentAnswer(answer: AssessmentAnswer): String {
        return answer.name
    }
    
    @TypeConverter
    fun toAssessmentAnswer(answer: String): AssessmentAnswer {
        return AssessmentAnswer.valueOf(answer)
    }
    
    @TypeConverter
    fun fromUserRole(role: UserRole): String {
        return role.name
    }
    
    @TypeConverter
    fun toUserRole(role: String): UserRole {
        return UserRole.valueOf(role)
    }
    
    // Learning Resource Type converters
    @TypeConverter
    fun fromLearningResourceType(type: LearningResourceType): String {
        return type.name
    }
    
    @TypeConverter
    fun toLearningResourceType(type: String): LearningResourceType {
        return LearningResourceType.valueOf(type)
    }
    
    // Learning Level converters
    @TypeConverter
    fun fromLearningLevel(level: LearningLevel): String {
        return level.name
    }
    
    @TypeConverter
    fun toLearningLevel(level: String): LearningLevel {
        return LearningLevel.valueOf(level)
    }
    
    // Quiz Question Type converters
    @TypeConverter
    fun fromQuizQuestionType(type: QuizQuestionType): String {
        return type.name
    }
    
    @TypeConverter
    fun toQuizQuestionType(type: String): QuizQuestionType {
        return QuizQuestionType.valueOf(type)
    }
    
    // ESGTrackerType converters
    @TypeConverter
    fun fromESGTrackerType(type: ESGTrackerType): String {
        return type.name
    }
    
    @TypeConverter
    fun toESGTrackerType(type: String): ESGTrackerType {
        return ESGTrackerType.valueOf(type)
    }
    
    // TrackerStatus converters
    @TypeConverter
    fun fromTrackerStatus(status: TrackerStatus): String {
        return status.name
    }
    
    @TypeConverter
    fun toTrackerStatus(status: String): TrackerStatus {
        return TrackerStatus.valueOf(status)
    }
    
    // TrackerPriority converters
    @TypeConverter
    fun fromTrackerPriority(priority: TrackerPriority): String {
        return priority.name
    }
    
    @TypeConverter
    fun toTrackerPriority(priority: String): TrackerPriority {
        return TrackerPriority.valueOf(priority)
    }
    
    // RecurringType converters
    @TypeConverter
    fun fromRecurringType(type: RecurringType): String {
        return type.name
    }
    
    @TypeConverter
    fun toRecurringType(type: String): RecurringType {
        return RecurringType.valueOf(type)
    }
    
    // AttachmentType converters
    @TypeConverter
    fun fromAttachmentType(type: AttachmentType): String {
        return type.name
    }
    
    @TypeConverter
    fun toAttachmentType(type: String): AttachmentType {
        return AttachmentType.valueOf(type)
    }
    
    // MeasurementFrequency converters
    @TypeConverter
    fun fromMeasurementFrequency(frequency: MeasurementFrequency): String {
        return frequency.name
    }
    
    @TypeConverter
    fun toMeasurementFrequency(frequency: String): MeasurementFrequency {
        return MeasurementFrequency.valueOf(frequency)
    }
    
    // TimelineEventType converters
    @TypeConverter
    fun fromTimelineEventType(type: TimelineEventType): String {
        return type.name
    }
    
    @TypeConverter
    fun toTimelineEventType(type: String): TimelineEventType {
        return TimelineEventType.valueOf(type)
    }
    
    // ReportStandard converters
    @TypeConverter
    fun fromReportStandard(standard: ReportStandard): String {
        return standard.name
    }
    
    @TypeConverter
    fun toReportStandard(standard: String): ReportStandard {
        return ReportStandard.valueOf(standard)
    }
    
    // ReportStatus converters
    @TypeConverter
    fun fromReportStatus(status: ReportStatus): String {
        return status.name
    }
    
    @TypeConverter
    fun toReportStatus(status: String): ReportStatus {
        return ReportStatus.valueOf(status)
    }
}
