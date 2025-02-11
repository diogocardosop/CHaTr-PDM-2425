package com.project.chatr

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.room.Room
import com.project.chatr.database.AppDatabase
import com.project.chatr.screens.habitAdd.HabitAddActivity
import com.project.chatr.screens.helpers.viewModelInit
import com.project.chatr.screens.main.CHaTrScreen
import com.project.chatr.screens.main.CHaTrViewModel
import com.project.chatr.screens.summaryHabits.SummaryHabitActivity
import com.project.chatr.services.CHaTrService
import com.project.chatr.ui.theme.CHaTrTheme



class MainActivity : ComponentActivity() {


    private val cHaTrViewModel by viewModels<CHaTrViewModel> {
        viewModelInit {
            CHaTrViewModel(CHaTrService((application as DependencyContainer).database))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CHaTrTheme {
                CHaTrScreen(
                    SummaryHabits = {
                        SummaryHabitActivity.navigate(this)
                    },
                    HabitAdd = {
                        HabitAddActivity.navigate(this)
                    },
                    viewModel = cHaTrViewModel
                )

            }
        }
    }
}