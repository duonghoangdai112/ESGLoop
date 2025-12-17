package com.ignitech.esgcompanion

import android.app.Application
import androidx.room.Room
import com.ignitech.esgcompanion.data.local.ESGDatabase
import com.ignitech.esgcompanion.data.seed.DatabaseSeeder
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class ESGApplication : Application() {
    
    @Inject
    lateinit var databaseSeeder: DatabaseSeeder
    
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    
    override fun onCreate() {
        super.onCreate()
        
        // Seed database khi app khởi động
        applicationScope.launch {
            databaseSeeder.seedDatabase()
        }
    }
}
