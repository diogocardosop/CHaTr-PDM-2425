package com.project.chatr.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.project.chatr.database.dao.HabitDao
import com.project.chatr.database.model.HabitEntity
import com.project.chatr.utils.Converters


@TypeConverters(Converters::class)
    @Database(
        version = 3,
        entities = [HabitEntity::class],
        exportSchema = false
    )

abstract class AppDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
}
