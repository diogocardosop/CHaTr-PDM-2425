package com.project.chatr.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.chatr.domain.Habit
import com.project.chatr.services.CHaTrService
import com.project.chatr.utils.ViewModelDefinition
import com.project.chatr.utils.ViewModelState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime

class CHaTrViewModel(
    private val cHaTrService: CHaTrService
) : ViewModel() {
    private val viewModelState = MutableStateFlow<ViewModelState>(ViewModelState.Loaded)
    val state: StateFlow<ViewModelState> get() = viewModelState


    private val viewModelScreen = MutableStateFlow<ViewModelDefinition>(ViewModelDefinition.Home)
    val screen: StateFlow<ViewModelDefinition> get() = viewModelScreen

    private val _todayHabits = MutableStateFlow<List<Habit>>(emptyList())
    val todayHabits: StateFlow<List<Habit>> get() = _todayHabits

    init {
        getHabitsOfToday()
    }

    fun resetState() {
        viewModelState.value = ViewModelState.Loaded
    }

    fun addHabit(name: String, description: String, timesPerDay: Int) {
        viewModelScope.launch {
            try {
                cHaTrService.saveHabit(name, description, timesPerDay)
                viewModelState.value = ViewModelState.Success
            } catch (e: Exception) {
                viewModelState.value = ViewModelState.Error(
                    e.message ?: "Something went wrong while adding Habit!"
                )
            }
        }
    }

    fun updateViewModelScreen(screen :ViewModelDefinition){
        viewModelScreen.value = screen
    }

    fun getHabitsOfToday() {
        viewModelScope.launch {
            try {
                _todayHabits.value = cHaTrService.getHabitsOfDay(LocalDate.now().atStartOfDay())
                viewModelState.value = ViewModelState.Loading
            } catch (e: Exception) {
                viewModelState.value = ViewModelState.Error(
                    e.message ?: "Something went wrong loading habits of today!"
                )
            }
        }
    }

    fun updateHabit(habit: Habit) {
        viewModelScope.launch {
            try {
                cHaTrService.updateHabit(habit)
                val currentHabits = _todayHabits.value.toMutableList()
                val index = currentHabits.indexOfFirst { it.id == habit.id }
                if (index != -1) {
                    currentHabits[index] = habit.copy()
                    _todayHabits.value = currentHabits
                }
                viewModelState.value = ViewModelState.Success
                }
            catch (e: Exception) {
                viewModelState.value = ViewModelState.Error(
                    e.message ?: "Something went wrong while updating Habit!"
                )
            }

        }
    }

    fun removeHabit(habit: Habit) {
        viewModelScope.launch {
            try {
                cHaTrService.deleteHabitById(habit.id)
                val currentHabits = _todayHabits.value.toMutableList()
                currentHabits.remove(habit)
                _todayHabits.value = currentHabits.toList()
                viewModelState.value = ViewModelState.Success
            } catch (e: Exception) {
                viewModelState.value = ViewModelState.Error(
                    e.message ?: "Something went wrong while deleting Habit!"
                )
            }
        }
    }

}