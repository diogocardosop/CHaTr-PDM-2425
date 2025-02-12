package com.project.chatr.screens.summaryHabits

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.chatr.domain.HabitSummary
import com.project.chatr.services.CHaTrService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SummaryHabitViewModel (
    private val cHaTrService: CHaTrService
) : ViewModel() {


    private val _weekHabits = MutableStateFlow<List<HabitSummary>>(emptyList())
    val weekHabits: StateFlow<List<HabitSummary>> get() = _weekHabits

    init {
        getHabitsOfCurrentWeek()
    }


    fun getHabitsOfCurrentWeek() {
        viewModelScope.launch {
            _weekHabits.value = cHaTrService.getHabitsOfWeek()
        }
    }
}

