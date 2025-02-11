package com.project.chatr.services

import com.project.chatr.database.AppDatabase
import com.project.chatr.database.dao.HabitDao
import com.project.chatr.domain.Habit

class CHaTrService (private val db: AppDatabase
) : ICHaTrService{
    override suspend fun saveHabit(game: Habit): Long {
        TODO("Not yet implemented")
    }

    override suspend fun getAllHabits(): List<Habit> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteHabitById(habitId: Long) {
        TODO("Not yet implemented")
    }


}