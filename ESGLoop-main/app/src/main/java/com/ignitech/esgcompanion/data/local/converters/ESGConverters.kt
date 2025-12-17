package com.ignitech.esgcompanion.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ignitech.esgcompanion.domain.entity.*
import com.ignitech.esgcompanion.domain.entity.ESGPillar

class ESGConverters {
    private val gson = Gson()
    
    // ESGPillar converters
    @TypeConverter
    fun fromESGPillar(pillar: ESGPillar): String = pillar.name
    
    @TypeConverter
    fun toESGPillar(pillar: String): ESGPillar = ESGPillar.valueOf(pillar)
    
    // UserRole converters
    @TypeConverter
    fun fromUserRole(role: UserRole): String = role.name
    
    @TypeConverter
    fun toUserRole(role: String): UserRole = UserRole.valueOf(role)
    
    // SubscriptionPlan converters
    @TypeConverter
    fun fromSubscriptionPlan(plan: SubscriptionPlan): String = plan.name
    
    @TypeConverter
    fun toSubscriptionPlan(plan: String): SubscriptionPlan = SubscriptionPlan.valueOf(plan)
    
    // AssessmentStatus converters
    @TypeConverter
    fun fromAssessmentStatus(status: AssessmentStatus): String = status.name
    
    @TypeConverter
    fun toAssessmentStatus(status: String): AssessmentStatus = AssessmentStatus.valueOf(status)
    
    // List<ESGQuestionOption> converters
    @TypeConverter
    fun fromQuestionOptionsList(options: List<ESGQuestionOption>): String {
        return gson.toJson(options)
    }
    
    @TypeConverter
    fun toQuestionOptionsList(optionsJson: String): List<ESGQuestionOption> {
        val listType = object : TypeToken<List<ESGQuestionOption>>() {}.type
        return gson.fromJson(optionsJson, listType) ?: emptyList()
    }
    
    // List<ESGAnswer> converters
    @TypeConverter
    fun fromAnswersList(answers: List<ESGAnswer>): String {
        return gson.toJson(answers)
    }
    
    @TypeConverter
    fun toAnswersList(answersJson: String): List<ESGAnswer> {
        val listType = object : TypeToken<List<ESGAnswer>>() {}.type
        return gson.fromJson(answersJson, listType) ?: emptyList()
    }
    
    
    // List<String> converters
    @TypeConverter
    fun fromStringList(list: List<String>): String {
        return gson.toJson(list)
    }
    
    @TypeConverter
    fun toStringList(listJson: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(listJson, listType) ?: emptyList()
    }
    
    // NotificationType converters
    @TypeConverter
    fun fromNotificationType(type: com.ignitech.esgcompanion.domain.entity.NotificationType): String = type.name
    
    @TypeConverter
    fun toNotificationType(type: String): com.ignitech.esgcompanion.domain.entity.NotificationType = 
        com.ignitech.esgcompanion.domain.entity.NotificationType.valueOf(type)
    
    // NotificationPriority converters
    @TypeConverter
    fun fromNotificationPriority(priority: com.ignitech.esgcompanion.domain.entity.NotificationPriority): String = priority.name
    
    @TypeConverter
    fun toNotificationPriority(priority: String): com.ignitech.esgcompanion.domain.entity.NotificationPriority = 
        com.ignitech.esgcompanion.domain.entity.NotificationPriority.valueOf(priority)
    
    // Map<String, String> converters for metadata
    @TypeConverter
    fun fromStringMap(map: Map<String, String>): String {
        return gson.toJson(map)
    }
    
    @TypeConverter
    fun toStringMap(mapJson: String): Map<String, String> {
        val mapType = object : TypeToken<Map<String, String>>() {}.type
        return gson.fromJson(mapJson, mapType) ?: emptyMap()
    }
}

