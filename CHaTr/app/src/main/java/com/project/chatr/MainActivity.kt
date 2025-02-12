package com.project.chatr

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.room.Room
import com.project.chatr.database.AppDatabase
import com.project.chatr.screens.components.AppTopBar
import com.project.chatr.screens.components.NavigationActions
import com.project.chatr.screens.habitAdd.HabitAddScreen
import com.project.chatr.screens.helpers.viewModelInit
import com.project.chatr.screens.main.CHaTrScreen
import com.project.chatr.screens.main.CHaTrViewModel
import com.project.chatr.screens.summaryHabits.SummaryHabitActivity
import com.project.chatr.services.CHaTrService
import com.project.chatr.ui.theme.CHaTrTheme
import com.project.chatr.utils.ViewModelDefinition


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
            val screen = cHaTrViewModel.screen.collectAsState().value

            when (screen) {
                ViewModelDefinition.Home -> {
                    cHaTrViewModel.resetState()
                    CHaTrTheme {
                        CHaTrScreen(
                            SummaryHabits = {
                                SummaryHabitActivity.navigate(this)
                            },
                            viewModel = cHaTrViewModel
                        )
                    }
                }
                ViewModelDefinition.Adding -> {
                    cHaTrViewModel.resetState()
                    CHaTrTheme {
                        Scaffold(modifier = Modifier.fillMaxSize(),
                            topBar = {
                                AppTopBar(
                                    navActions = NavigationActions(
                                        onBackAction = {
                                            cHaTrViewModel.updateViewModelScreen(ViewModelDefinition.Home);
                                            cHaTrViewModel.getHabitsOfToday();
                                       },
                                    )
                                )
                            }
                        ) { innerPadding ->
                            HabitAddScreen(
                                modifier = Modifier.padding(innerPadding),
                                viewModel = cHaTrViewModel,
                            )
                        }
                    }
                }
            }
        }
    }
}