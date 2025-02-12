package com.project.chatr.services

import com.project.chatr.database.AppDatabase
import com.project.chatr.database.dao.HabitDao
import com.project.chatr.database.model.HabitEntity
import com.project.chatr.domain.Habit
import com.project.chatr.domain.HabitSummary
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.DayOfWeek
import java.time.temporal.TemporalAdjusters

class CHaTrService (private val db: AppDatabase
) : ICHaTrService{
    override suspend fun saveHabit(name: String, description: String, timesPerDay: Int) {
        db.habitDao().insertHabit(HabitEntity(
            name = name,
            description = description,
            timesPerDay = timesPerDay,
            countTimes = 0,
            dateCreated = LocalDateTime.now(),
            isActive = true
        ))
    }


    override suspend fun deleteHabitById(habitId: Long) {
        return db.habitDao().deleteHabitById(habitId)
    }


    override suspend fun getHabitsOfDay(date: LocalDateTime): List<Habit> {
        val todayHabits = db.habitDao().getHabitsOfDay(date).map {
            Habit(
                id = it.id,
                name = it.name,
                description = it.description,
                timesPerDay = it.timesPerDay,
                dateCreated = it.dateCreated,
                countTimes = it.countTimes,
                isActive = it.isActive,
            )
        }

        val activeHabits = db.habitDao().getActiveHabits().map {
            Habit(
                id = it.id,
                name = it.name,
                description = it.description,
                timesPerDay = it.timesPerDay,
                dateCreated = it.dateCreated,
                countTimes = it.countTimes,
                isActive = it.isActive,
            )
        }


        val activeHabitsNotDoneToday = activeHabits.filter { activeHabit -> !todayHabits.any { it.name == activeHabit.name && it.description == activeHabit.description } }

        //ID TAB is active but not today, create a new habit with countTimes = 0
        activeHabitsNotDoneToday.forEach {
            db.habitDao().insertHabit(
                HabitEntity(
                    name = it.name,
                    description = it.description,
                    timesPerDay = it.timesPerDay,
                    dateCreated = LocalDateTime.now(),
                    countTimes = 0,
                    isActive = it.isActive
                )
            )
        }

        val totalHabits = todayHabits.plus(activeHabitsNotDoneToday)

        return totalHabits;

    }

    override suspend fun updateHabit(habit: Habit) {
        db.habitDao().updateHabit(habit.countTimes, habit.id)
    }

    override suspend fun updateHabitByName(habit: Habit) {
        return db.habitDao().updateHabitByName(habit.name, habit.isActive)
    }

    override suspend fun getHabitsOfWeek(): List<HabitSummary> {
        val today = LocalDate.now()
        val startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).atStartOfDay()
        val endOfWeek = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).atTime(23,59,59)

        val habits = db.habitDao().getHabitsByDateRange(startOfWeek, endOfWeek).map {
            Habit(
                id = it.id,
                name = it.name,
                description = it.description,
                timesPerDay = it.timesPerDay,
                dateCreated = it.dateCreated,
                countTimes = it.countTimes,
                isActive = it.isActive
            )
        }

        val completedHabits = habits.filter { it.countTimes >= it.timesPerDay }



        return emptyList();
    }


}