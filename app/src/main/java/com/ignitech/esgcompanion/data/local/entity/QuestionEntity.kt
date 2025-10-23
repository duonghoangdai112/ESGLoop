package com.ignitech.esgcompanion.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class QuestionEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val type: String, // Trắc nghiệm, Tự luận, Đúng/Sai, Điền vào chỗ trống
    val difficulty: String, // Dễ, Trung bình, Khó, Rất khó
    val category: String, // Môi trường, Xã hội, Quản trị
    val content: String, // Nội dung câu hỏi chi tiết
    val options: String? = null, // JSON string cho các lựa chọn (trắc nghiệm)
    val correctAnswer: String? = null, // Đáp án đúng
    val explanation: String? = null, // Giải thích đáp án
    val points: Int = 1, // Điểm số
    val tags: String? = null, // JSON string cho các tags
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
