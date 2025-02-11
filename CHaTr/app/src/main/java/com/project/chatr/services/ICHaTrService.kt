package com.project.chatr.services

import com.project.chatr.database.dao.HabitDao
import com.project.chatr.domain.Habit

interface ICHaTrService {
    suspend fun saveHabit(game: Habit): Long
    suspend fun getAllHabits(): List<Habit>
    suspend fun deleteHabitById(habitId: Long)
}
