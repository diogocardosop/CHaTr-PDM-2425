package com.project.chatr.screens.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.project.chatr.screens.components.AppTopBar
import com.project.chatr.screens.components.NavigationActions

@Composable
fun CHaTrScreen(
    modifier: Modifier = Modifier,
    SummaryHabits: () -> Unit,
    HabitAdd: () -> Unit,
    viewModel: CHaTrViewModel,

) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            AppTopBar(
                navActions = NavigationActions(
                )
            )
        }
    ) { innerPadding ->
        CHaTrView(
            modifier = modifier
                .padding(innerPadding),
            btnActions = ButtonActions(
                summaryHabits = SummaryHabits,
                habitAdd = HabitAdd,
            ),
            viewModel = viewModel
        )
    }
}