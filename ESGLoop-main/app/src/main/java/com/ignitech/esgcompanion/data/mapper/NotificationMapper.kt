package com.ignitech.esgcompanion.data.mapper

import com.ignitech.esgcompanion.data.local.entity.NotificationEntity
import com.ignitech.esgcompanion.domain.entity.Notification
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object NotificationMapper {
    
    private val gson = Gson()
    
    fun toDomain(entity: NotificationEntity): Notification {
        return Notification(
            id = entity.id,
            title = entity.title,
            message = entity.message,
            type = entity.type,
            userId = entity.userId,
            isRead = entity.isRead,
            createdAt = entity.createdAt,
            actionUrl = entity.actionUrl,
            priority = entity.priority,
            metadata = parseMetadata(entity.metadata)
        )
    }
    
    fun toEntity(domain: Notification): NotificationEntity {
        return NotificationEntity(
            id = domain.id,
            title = domain.title,
            message = domain.message,
            type = domain.type,
            userId = domain.userId,
            isRead = domain.isRead,
            createdAt = domain.createdAt,
            actionUrl = domain.actionUrl,
            priority = domain.priority,
            metadata = serializeMetadata(domain.metadata)
        )
    }
    
    private fun parseMetadata(metadataJson: String): Map<String, String> {
        return try {
            val mapType = object : TypeToken<Map<String, String>>() {}.type
            gson.fromJson(metadataJson, mapType) ?: emptyMap()
        } catch (e: Exception) {
            emptyMap()
        }
    }
    
    private fun serializeMetadata(metadata: Map<String, String>): String {
        return gson.toJson(metadata)
    }
}
