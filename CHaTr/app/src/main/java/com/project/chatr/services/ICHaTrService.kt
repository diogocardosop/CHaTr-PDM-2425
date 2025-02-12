package com.project.chatr.services

import com.project.chatr.database.dao.HabitDao
import com.project.chatr.domain.Habit
import java.time.LocalDate
import java.time.LocalDateTime

interface ICHaTrService {
    suspend fun saveHabit(name: String, description: String, timesPerDay: Int)
    suspend fun getAllHabits(): List<Habit>
    suspend fun deleteHabitById(habitId: Long)
    suspend fun getHabitsOfDay(date: LocalDateTime): List<Habit>
    suspend fun updateHabit(habit: Habit)
}
