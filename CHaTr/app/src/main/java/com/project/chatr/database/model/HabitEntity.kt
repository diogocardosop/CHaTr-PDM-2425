package com.project.chatr.database.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDateTime


@Entity(tableName = "habits")
data class HabitEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val description: String,
    val timesPerDay: Int,
    val countTimes: Int,
    val dateCreated: LocalDateTime,
    val isActive: Boolean
)