package com.project.chatr

import android.app.Application
import androidx.room.Room
import com.project.chatr.database.AppDatabase

interface DependencyContainer {
    val database: AppDatabase
}

class MainApplication : Application(), DependencyContainer {

    override val database: AppDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "CHarT.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}