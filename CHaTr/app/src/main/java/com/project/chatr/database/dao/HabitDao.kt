package com.project.chatr.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.project.chatr.database.model.HabitEntity

@Dao
interface HabitDao {
    @Query("SELECT * FROM habits")
    suspend fun getAllHabits(): List<HabitEntity>

    @Insert
    suspend fun insertHabit(ent: HabitEntity): Long

    @Query("Delete FROM habits WHERE id = :id")
    suspend fun deleteHabitById(id: Long)
}