package com.project.chatr.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.project.chatr.database.model.HabitEntity
import java.time.LocalDate
import java.time.LocalDateTime

@Dao
interface HabitDao {

    @Query("Select * FROM habits WHERE dateCreated > :date and isActive")
    suspend fun getHabitsOfDay(date: LocalDateTime): List<HabitEntity>

    @Query("Select * FROM habits WHERE id = :id")
    suspend fun getHabitById(id: Long): HabitEntity

    @Insert
    suspend fun insertHabit(ent: HabitEntity): Long

    @Query("Delete FROM habits WHERE id = :id")
    suspend fun deleteHabitById(id: Long)

    @Query("Delete FROM habits WHERE name = :name")
    suspend fun deleteHabitByName(name: String)

    @Query("UPDATE habits SET countTimes = :countTimes WHERE id = :id")
    suspend fun updateHabit(countTimes: Int, id: Long)

    @Query("Select * FROM habits WHERE dateCreated > :startOfWeek and dateCreated < :endOfWeek")
    suspend fun getHabitsByDateRange(startOfWeek: LocalDateTime?, endOfWeek: LocalDateTime?): List<HabitEntity>

    @Query("Select * FROM habits WHERE isActive")
    suspend fun getActiveHabits(): List<HabitEntity>

    @Query("UPDATE habits SET isActive = :active WHERE name = :name")
    suspend fun updateHabitByName(name: String, active: Boolean)
}