package com.ignitech.esgcompanion.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ignitech.esgcompanion.domain.entity.UserRole

@Entity(tableName = "learning_resources")
data class LearningResourceEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val content: String,
    val type: LearningResourceType,
    val category: String,
    val subcategory: String,
    val level: LearningLevel,
    val userRole: UserRole,
    val industry: String? = null, // Ngành nghề cụ thể
    val duration: Int = 0, // Thời gian học (phút)
    val difficulty: Int = 1, // 1-5
    val rating: Float = 0f, // 0-5
    val viewCount: Int = 0,
    val isPremium: Boolean = false,
    val isFeatured: Boolean = false,
    val isPublished: Boolean = false,
    val thumbnailUrl: String = "",
    val videoUrl: String = "",
    val audioUrl: String = "",
    val documentUrl: String = "",
    val attachments: String = "", // JSON string of attachment URLs
    val tags: String = "", // JSON string of tags
    val prerequisites: String = "", // JSON string of prerequisite IDs
    val learningObjectives: String = "", // JSON string of objectives
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val publishedAt: Long? = null,
    val authorId: String = "",
    val authorName: String = "",
    val language: String = "vi",
    // Additional fields for assignments
    val dueDate: Long? = null, // Hạn nộp cho bài tập
    val maxScore: Int? = null, // Điểm tối đa
    val timeLimit: Int? = null, // Thời gian làm bài (phút)
    val attempts: Int = 1, // Số lần được phép làm lại
    val instructions: String = "", // Hướng dẫn chi tiết
    val requirements: String = "", // JSON string of requirements
    val isGraded: Boolean = false, // Có chấm điểm không
    // Additional fields for questions
    val questionType: QuizQuestionType? = null, // Loại câu hỏi
    val options: String = "", // JSON string of options
    val correctAnswer: String = "", // JSON string of correct answers
    val explanation: String = "", // Giải thích đáp án
    val points: Int = 1, // Điểm số cho câu hỏi
    val hint: String = "", // Gợi ý
    // Additional fields for lessons
    val lessonDuration: Int? = null, // Thời lượng bài giảng (phút)
    val lessonObjectives: String = "", // Mục tiêu bài học
    val lessonMaterials: String = "", // Tài liệu bài giảng
    val classId: String = "", // ID lớp học
    val className: String = "" // Tên lớp học
)

@Entity(tableName = "learning_categories")
data class LearningCategoryEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val icon: String,
    val color: String,
    val userRole: UserRole,
    val order: Int = 0,
    val isActive: Boolean = true
)

@Entity(tableName = "learning_progress")
data class LearningProgressEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val resourceId: String,
    val progress: Float = 0f, // 0-1
    val completedAt: Long? = null,
    val startedAt: Long = System.currentTimeMillis(),
    val lastAccessedAt: Long = System.currentTimeMillis(),
    val timeSpent: Int = 0, // Tổng thời gian học (phút)
    val notes: String = "",
    val rating: Int? = null, // 1-5
    val isBookmarked: Boolean = false
)

@Entity(tableName = "learning_certificates")
data class LearningCertificateEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val resourceId: String,
    val certificateName: String,
    val issuedAt: Long = System.currentTimeMillis(),
    val validUntil: Long? = null,
    val certificateUrl: String = "",
    val verificationCode: String = "",
    val isVerified: Boolean = false
)

@Entity(tableName = "learning_quizzes")
data class LearningQuizEntity(
    @PrimaryKey
    val id: String,
    val resourceId: String,
    val question: String,
    val questionType: QuizQuestionType,
    val options: String, // JSON string of options
    val correctAnswer: String,
    val explanation: String = "",
    val points: Int = 1,
    val order: Int = 0,
    val difficulty: Int = 1
)

@Entity(tableName = "learning_quiz_attempts")
data class LearningQuizAttemptEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val quizId: String,
    val resourceId: String,
    val userAnswer: String,
    val isCorrect: Boolean,
    val pointsEarned: Int = 0,
    val attemptedAt: Long = System.currentTimeMillis(),
    val timeSpent: Int = 0 // Thời gian trả lời (giây)
)

@Entity(tableName = "assignment_submissions")
data class AssignmentSubmissionEntity(
    @PrimaryKey
    val id: String,
    val assignmentId: String,
    val studentId: String,
    val studentName: String,
    val content: String,
    val attachments: String = "", // JSON string of attachment URLs
    val submittedAt: Long = System.currentTimeMillis(),
    val score: Int? = null,
    val feedback: String = "",
    val gradedAt: Long? = null,
    val gradedBy: String? = null,
    val isLate: Boolean = false,
    val attemptNumber: Int = 1
)

@Entity(tableName = "question_attempts")
data class QuestionAttemptEntity(
    @PrimaryKey
    val id: String,
    val questionId: String,
    val studentId: String,
    val studentAnswer: String, // JSON string of answers
    val isCorrect: Boolean,
    val pointsEarned: Int = 0,
    val attemptedAt: Long = System.currentTimeMillis(),
    val timeSpent: Int = 0 // Thời gian trả lời (giây)
)

enum class LearningResourceType {
    VIDEO,           // Video bài giảng
    ARTICLE,         // Bài viết, blog
    PODCAST,         // Podcast, audio
    COURSE,          // Khóa học hoàn chỉnh
    WEBINAR,         // Webinar, hội thảo trực tuyến
    DOCUMENT,        // Tài liệu PDF, Word
    TEMPLATE,        // Mẫu, template
    TOOL,            // Công cụ, tool
    CASE_STUDY,      // Case study, nghiên cứu tình huống
    GUIDELINE,       // Hướng dẫn, guideline
    CHECKLIST,       // Checklist, danh sách kiểm tra
    PRESENTATION,    // Slide, presentation
    SPREADSHEET,     // Excel, Google Sheets
    INTERACTIVE,     // Nội dung tương tác
    GUIDE,           // Hướng dẫn
    WORKSHOP,        // Workshop
    RESEARCH,        // Nghiên cứu
    LESSON,          // Bài giảng
    ASSIGNMENT,      // Bài tập
    QUIZ,            // Câu hỏi trắc nghiệm
    EXAM             // Bài thi
}

enum class LearningLevel {
    BEGINNER,        // Cơ bản
    INTERMEDIATE,    // Trung cấp
    ADVANCED,        // Nâng cao
    EXPERT          // Chuyên gia
}

enum class QuizQuestionType {
    MULTIPLE_CHOICE, // Trắc nghiệm
    TRUE_FALSE,      // Đúng/Sai
    FILL_BLANK,      // Điền vào chỗ trống
    ESSAY,           // Tự luận
    MATCHING,        // Ghép cặp
    ORDERING         // Sắp xếp thứ tự
}
