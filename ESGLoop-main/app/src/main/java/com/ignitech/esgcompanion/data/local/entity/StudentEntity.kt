package com.ignitech.esgcompanion.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class StudentEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val email: String,
    val studentId: String, // Mã số sinh viên
    val classId: String, // ID của lớp học
    val averageScore: Int,
    val completedAssignments: Int,
    val totalAssignments: Int,
    val status: String, // ACTIVE, INACTIVE, GRADUATED
    val enrollmentDate: Long = System.currentTimeMillis(),
    val lastActivityDate: Long = System.currentTimeMillis()
)
