package com.project.chatr.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime


@Entity(tableName = "habits")
data class HabitEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val timesPerDay: Int,
    val countTimes: Int,
    val DateCreated: LocalDateTime,
)