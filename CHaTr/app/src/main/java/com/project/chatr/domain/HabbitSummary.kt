package com.project.chatr.domain

import java.time.DayOfWeek
import java.time.LocalDateTime
import java.util.Date

data class HabitSummary(
    val habitName: String,
    val daysCompleted: List<DayOfWeek>
)