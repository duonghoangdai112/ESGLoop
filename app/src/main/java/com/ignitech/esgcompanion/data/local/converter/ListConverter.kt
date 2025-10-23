package com.ignitech.esgcompanion.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ignitech.esgcompanion.data.entity.AssignmentResource

class ListConverter {
    
    private val gson = Gson()
    
    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        return value?.let { gson.toJson(it) }
    }
    
    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        return value?.let {
            val listType = object : TypeToken<List<String>>() {}.type
            gson.fromJson(it, listType)
        }
    }
    
    @TypeConverter
    fun fromAssignmentResourceList(value: List<AssignmentResource>?): String? {
        return value?.let { gson.toJson(it) }
    }
    
    @TypeConverter
    fun toAssignmentResourceList(value: String?): List<AssignmentResource>? {
        return value?.let {
            val listType = object : TypeToken<List<AssignmentResource>>() {}.type
            gson.fromJson(it, listType)
        }
    }
}
