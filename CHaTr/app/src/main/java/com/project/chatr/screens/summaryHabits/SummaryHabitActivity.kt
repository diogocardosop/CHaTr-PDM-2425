package com.project.chatr.screens.summaryHabits

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.project.chatr.DependencyContainer
import com.project.chatr.screens.components.AppTopBar
import com.project.chatr.screens.components.NavigationActions
import com.project.chatr.screens.helpers.viewModelInit
import com.project.chatr.screens.main.CHaTrViewModel
import com.project.chatr.services.CHaTrService
import com.project.chatr.ui.theme.CHaTrTheme

class SummaryHabitActivity : ComponentActivity(){
    private val summaryHabbitsViewModel by viewModels<SummaryHabitViewModel> {
        viewModelInit {
            SummaryHabitViewModel(CHaTrService((application as DependencyContainer).database))
        }
    }

    companion object {
        fun navigate(ctx: ComponentActivity) {
            val intent = Intent(ctx, SummaryHabitActivity::class.java)

            ctx.startActivity(intent)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CHaTrTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        AppTopBar(
                            navActions = NavigationActions(
                                onBackAction = { finish() },
                            )
                        )
                    }
                ) { innerPadding ->
                    SummaryHabitScreen(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = summaryHabbitsViewModel,
                    )
                }
            }
        }
    }
}