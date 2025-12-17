package com.ignitech.esgcompanion.data.di

import com.ignitech.esgcompanion.data.repository.UserRepositoryImpl
import com.ignitech.esgcompanion.data.repository.NotificationRepositoryImpl
import com.ignitech.esgcompanion.data.repository.LearningHubRepository
import com.ignitech.esgcompanion.domain.repository.UserRepository
import com.ignitech.esgcompanion.domain.repository.NotificationRepository
import com.ignitech.esgcompanion.data.local.PreferencesManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    
    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository
    
    @Binds
    @Singleton
    abstract fun bindNotificationRepository(
        notificationRepositoryImpl: NotificationRepositoryImpl
    ): NotificationRepository
}

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {
    
    @Provides
    @Singleton
    fun providePreferencesManager(
        @dagger.hilt.android.qualifiers.ApplicationContext context: android.content.Context
    ): PreferencesManager = PreferencesManager(context)
}

