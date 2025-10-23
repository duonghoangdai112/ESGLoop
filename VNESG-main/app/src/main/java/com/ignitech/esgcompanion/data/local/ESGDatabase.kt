package com.ignitech.esgcompanion.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.ignitech.esgcompanion.data.entity.AssignmentEntity
import com.ignitech.esgcompanion.data.entity.ESGCategoryEntity
import com.ignitech.esgcompanion.data.entity.LearningResourceEntity
import com.ignitech.esgcompanion.data.entity.LearningCategoryEntity
import com.ignitech.esgcompanion.data.entity.LearningProgressEntity
import com.ignitech.esgcompanion.data.entity.LearningCertificateEntity
import com.ignitech.esgcompanion.data.entity.LearningQuizEntity
import com.ignitech.esgcompanion.data.entity.LearningQuizAttemptEntity
import com.ignitech.esgcompanion.data.entity.AssignmentSubmissionEntity
import com.ignitech.esgcompanion.data.entity.QuestionAttemptEntity
import com.ignitech.esgcompanion.data.entity.ESGTrackerEntity
import com.ignitech.esgcompanion.data.entity.ESGTrackerAttachmentEntity
import com.ignitech.esgcompanion.data.entity.ESGTrackerKPIEntity
import com.ignitech.esgcompanion.data.entity.ESGTrackerKPIValueEntity
import com.ignitech.esgcompanion.data.entity.ESGTrackerTimelineEntity
import com.ignitech.esgcompanion.data.entity.ReportEntity
import com.ignitech.esgcompanion.data.entity.ReportSectionEntity
import com.ignitech.esgcompanion.data.entity.ReportFieldEntity
import com.ignitech.esgcompanion.data.local.entity.NotificationEntity
import com.ignitech.esgcompanion.data.local.entity.UserEntity
import com.ignitech.esgcompanion.data.local.entity.ESGAssessmentEntity
import com.ignitech.esgcompanion.data.local.entity.ESGAnswerEntity
import com.ignitech.esgcompanion.data.local.entity.ESGQuestionEntity
import com.ignitech.esgcompanion.data.local.converter.DateConverter
import com.ignitech.esgcompanion.data.local.converter.ListConverter
import com.ignitech.esgcompanion.data.local.converter.EnumConverter
import com.ignitech.esgcompanion.data.local.AssignmentSubmissionDao
import com.ignitech.esgcompanion.data.local.QuestionAttemptDao
import com.ignitech.esgcompanion.data.local.dao.ESGQuestionDao
import com.ignitech.esgcompanion.data.local.AssignmentDao
import com.ignitech.esgcompanion.data.local.QuestionDao
import com.ignitech.esgcompanion.data.dao.ESGAssessmentDao
import com.ignitech.esgcompanion.data.dao.LearningHubDao
import com.ignitech.esgcompanion.data.dao.ESGTrackerDao
import com.ignitech.esgcompanion.data.dao.ReportDao
import com.ignitech.esgcompanion.data.local.dao.ESGScoresDao
import com.ignitech.esgcompanion.data.local.dao.NotificationDao
import com.ignitech.esgcompanion.data.local.entity.ExpertEntity
import com.ignitech.esgcompanion.data.local.dao.ExpertDao
import com.ignitech.esgcompanion.data.local.entity.EnterpriseExpertConnectionEntity
import com.ignitech.esgcompanion.data.local.dao.EnterpriseExpertConnectionDao
import com.ignitech.esgcompanion.data.local.entity.ClassEntity
import com.ignitech.esgcompanion.data.local.dao.ClassDao
import com.ignitech.esgcompanion.data.local.entity.StudentEntity
import com.ignitech.esgcompanion.data.local.dao.StudentDao
import com.ignitech.esgcompanion.data.local.entity.EnterpriseEntity
import com.ignitech.esgcompanion.data.local.dao.EnterpriseDao

@Database(
    entities = [
        AssignmentEntity::class,
        com.ignitech.esgcompanion.data.entity.QuestionEntity::class,
        com.ignitech.esgcompanion.data.local.entity.QuestionEntity::class,
        UserEntity::class,
        ESGAssessmentEntity::class,
        ESGAnswerEntity::class,
        ESGQuestionEntity::class,
        ESGCategoryEntity::class,
        LearningResourceEntity::class,
        LearningCategoryEntity::class,
        LearningProgressEntity::class,
        LearningCertificateEntity::class,
        LearningQuizEntity::class,
        LearningQuizAttemptEntity::class,
        AssignmentSubmissionEntity::class,
        QuestionAttemptEntity::class,
        ESGTrackerEntity::class,
        ESGTrackerAttachmentEntity::class,
        ESGTrackerKPIEntity::class,
        ESGTrackerKPIValueEntity::class,
        ESGTrackerTimelineEntity::class,
        ReportEntity::class,
        ReportSectionEntity::class,
        ReportFieldEntity::class,
        NotificationEntity::class,
        ExpertEntity::class,
        EnterpriseExpertConnectionEntity::class,
        ClassEntity::class,
        StudentEntity::class,
        EnterpriseEntity::class
    ],
                version = 7,
    exportSchema = false
)
@TypeConverters(
    DateConverter::class,
    ListConverter::class,
    EnumConverter::class
)
abstract class ESGDatabase : RoomDatabase() {
    
    abstract fun assignmentDao(): AssignmentDao
    abstract fun assignmentQuestionDao(): com.ignitech.esgcompanion.data.local.QuestionDao
    abstract fun questionLibraryDao(): com.ignitech.esgcompanion.data.local.dao.QuestionDao
    abstract fun userDao(): com.ignitech.esgcompanion.data.local.dao.UserDao
    abstract fun esgAssessmentDao(): ESGAssessmentDao
    abstract fun learningHubDao(): LearningHubDao
    abstract fun esgTrackerDao(): ESGTrackerDao
    abstract fun assignmentSubmissionDao(): AssignmentSubmissionDao
    abstract fun questionAttemptDao(): QuestionAttemptDao
    abstract fun esgScoresDao(): ESGScoresDao
    abstract fun esgQuestionDao(): ESGQuestionDao
    abstract fun reportDao(): ReportDao
    abstract fun notificationDao(): NotificationDao
    abstract fun expertDao(): ExpertDao
    abstract fun enterpriseExpertConnectionDao(): EnterpriseExpertConnectionDao
    abstract fun classDao(): ClassDao
    abstract fun studentDao(): StudentDao
    abstract fun enterpriseDao(): EnterpriseDao
    
    companion object {
        @Volatile
        private var INSTANCE: ESGDatabase? = null
        
        fun getDatabase(context: Context): ESGDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ESGDatabase::class.java,
                    "esg_database"
                )
                .fallbackToDestructiveMigration() // Temporary for development
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
