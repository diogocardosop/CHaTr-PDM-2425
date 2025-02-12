package com.project.chatr.services

import com.project.chatr.database.AppDatabase
import com.project.chatr.database.dao.HabitDao
import com.project.chatr.database.model.HabitEntity
import com.project.chatr.domain.Habit
import java.time.LocalDate
import java.time.LocalDateTime

class CHaTrService (private val db: AppDatabase
) : ICHaTrService{
    override suspend fun saveHabit(name: String, description: String, timesPerDay: Int) {
        db.habitDao().insertHabit(HabitEntity(
            name = name,
            description = description,
            timesPerDay = timesPerDay,
            countTimes = 0,
            dateCreated = LocalDateTime.now()
        ))
    }

    override suspend fun getAllHabits(): List<Habit> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteHabitById(habitId: Long) {
        return db.habitDao().deleteHabitById(habitId)
    }

    override suspend fun getHabitsOfDay(date: LocalDateTime): List<Habit> {
        return db.habitDao().getHabitsOfDay(date).map {
            Habit(
                id = it.id,
                name = it.name,
                description = it.description,
                timesPerDay = it.timesPerDay,
                dateCreated = it.dateCreated,
                countTimes = it.countTimes
            )
        }
    }

    override suspend fun updateHabit(habit: Habit) {
        db.habitDao().updateHabit(habit.countTimes, habit.id)
    }


}