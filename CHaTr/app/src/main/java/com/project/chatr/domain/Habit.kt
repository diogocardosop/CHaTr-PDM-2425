package com.project.chatr.domain

import java.util.Date

data class Habit (
    val name: String,
  val timesPerDay: Int,
  val countTimes: Int,
  val DateCreated: Date,
  val isFinished: Boolean
)