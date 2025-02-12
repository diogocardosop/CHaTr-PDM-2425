package com.project.chatr.domain

import java.time.LocalDateTime
import java.util.Date

data class Habit (
    val id: Long,
    val name: String,
    val description: String,
    val timesPerDay: Int,
    var countTimes: Int,
    val dateCreated: LocalDateTime,
)