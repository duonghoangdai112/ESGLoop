package com.ignitech.esgcompanion.di

import android.content.Context
import androidx.room.Room
import com.ignitech.esgcompanion.data.local.ESGDatabase
import com.ignitech.esgcompanion.data.local.AssignmentDao
import com.ignitech.esgcompanion.data.local.dao.QuestionDao
import com.ignitech.esgcompanion.data.local.AssignmentSubmissionDao
import com.ignitech.esgcompanion.data.local.QuestionAttemptDao
import com.ignitech.esgcompanion.data.dao.ESGAssessmentDao
import com.ignitech.esgcompanion.data.dao.LearningHubDao
import com.ignitech.esgcompanion.data.dao.ESGTrackerDao
import com.ignitech.esgcompanion.data.dao.ReportDao
import com.ignitech.esgcompanion.data.local.dao.UserDao
import com.ignitech.esgcompanion.data.local.dao.NotificationDao
import com.ignitech.esgcompanion.data.local.dao.ESGScoresDao
import com.ignitech.esgcompanion.data.local.dao.ESGQuestionDao
import com.ignitech.esgcompanion.data.local.dao.ExpertDao
import com.ignitech.esgcompanion.data.local.dao.EnterpriseExpertConnectionDao
import com.ignitech.esgcompanion.data.local.dao.ClassDao
import com.ignitech.esgcompanion.data.local.dao.StudentDao
import com.ignitech.esgcompanion.data.local.dao.EnterpriseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideESGDatabase(@ApplicationContext context: Context): ESGDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ESGDatabase::class.java,
            "esg_database"
        ).build()
    }
    
    @Provides
    fun provideAssignmentDao(database: ESGDatabase): AssignmentDao {
        return database.assignmentDao()
    }
    
    @Provides
    fun provideAssignmentQuestionDao(database: ESGDatabase): com.ignitech.esgcompanion.data.local.QuestionDao {
        return database.assignmentQuestionDao()
    }
    
    @Provides
    fun provideQuestionLibraryDao(database: ESGDatabase): com.ignitech.esgcompanion.data.local.dao.QuestionDao {
        return database.questionLibraryDao()
    }
    
    @Provides
    fun provideAssignmentSubmissionDao(database: ESGDatabase): AssignmentSubmissionDao {
        return database.assignmentSubmissionDao()
    }
    
    @Provides
    fun provideQuestionAttemptDao(database: ESGDatabase): QuestionAttemptDao {
        return database.questionAttemptDao()
    }
    
    // Temporary stub DAOs to fix Hilt injection errors
    // TODO: Implement proper database entities and DAOs for these
    @Provides
    fun provideESGAssessmentDao(database: ESGDatabase): ESGAssessmentDao {
        return database.esgAssessmentDao()
    }
    
    @Provides
    fun provideLearningHubDao(database: ESGDatabase): LearningHubDao {
        return database.learningHubDao()
    }
    
    @Provides
    fun provideESGTrackerDao(database: ESGDatabase): ESGTrackerDao {
        return database.esgTrackerDao()
    }
    
    @Provides
    fun provideReportDao(database: ESGDatabase): ReportDao {
        return database.reportDao()
    }
    
    @Provides
    fun provideUserDao(database: ESGDatabase): UserDao {
        return database.userDao()
    }
    
    @Provides
    fun provideNotificationDao(database: ESGDatabase): NotificationDao {
        return database.notificationDao()
    }
    
    @Provides
    fun provideESGScoresDao(database: ESGDatabase): ESGScoresDao {
        return database.esgScoresDao()
    }
    
    @Provides
    fun provideESGQuestionDao(database: ESGDatabase): ESGQuestionDao {
        return database.esgQuestionDao()
    }
    
    @Provides
    fun provideExpertDao(database: ESGDatabase): ExpertDao {
        return database.expertDao()
    }
    
    @Provides
    fun provideEnterpriseExpertConnectionDao(database: ESGDatabase): EnterpriseExpertConnectionDao {
        return database.enterpriseExpertConnectionDao()
    }
    
    @Provides
    fun provideClassDao(database: ESGDatabase): ClassDao {
        return database.classDao()
    }
    
    @Provides
    fun provideStudentDao(database: ESGDatabase): StudentDao {
        return database.studentDao()
    }
    
    @Provides
    fun provideEnterpriseDao(database: ESGDatabase): EnterpriseDao {
        return database.enterpriseDao()
    }
}