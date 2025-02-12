package com.project.chatr.screens.main

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.chatr.R
import com.project.chatr.domain.Habit
import com.project.chatr.utils.ViewModelDefinition

class ButtonActions(
    val summaryHabits: (() -> Unit)
)

@Composable
fun CHaTrView(
    modifier: Modifier = Modifier,
    btnActions: ButtonActions,
    viewModel: CHaTrViewModel
) {
    val screenOrientation = LocalConfiguration.current.orientation

    when (screenOrientation) {
        Configuration.ORIENTATION_LANDSCAPE -> CHaTrLandscapeView(modifier, btnActions, viewModel)
        else -> CHaTrPortraitView(modifier, btnActions, viewModel)
    }
}

@Composable
fun CHaTrPortraitView(
    modifier: Modifier = Modifier,
    btnActions: ButtonActions,
    viewModel: CHaTrViewModel
) {
    DailyTrackerList(modifier, btnActions, viewModel)
}

@Composable
fun CHaTrLandscapeView(
    modifier: Modifier = Modifier,
    btnActions: ButtonActions,
    viewModel: CHaTrViewModel
) {
    DailyTrackerList(modifier, btnActions, viewModel)
}

@Composable
fun DailyTrackerList(modifier: Modifier = Modifier, btnActions: ButtonActions, viewModel: CHaTrViewModel) {
    val habits = viewModel.todayHabits.collectAsState().value

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Text(
            text = "Daily Tracker",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(habits) { habit ->
                HabitItem(habit = habit, viewModel)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { btnActions.summaryHabits() }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_data_usage_24),
                    contentDescription = "Habit Resume"
                )
            }

            Button(
                onClick = { viewModel.updateViewModelScreen(ViewModelDefinition.Adding) }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_add_circle_outline_24),
                    contentDescription = "Add Habit"
                )
            }
        }
    }
}

@Composable
fun HabitItem(habit: Habit, viewModel: CHaTrViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)

    ) {
        Row(
            modifier = Modifier
                .background(color =
                if (habit.countTimes >= habit.timesPerDay) Color(0xB58EF38E) else if (habit.countTimes > 0) Color(0xFFF6E98A) else Color(
                    0xB5FC8585
                )
                )
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = habit.name
                )
                Spacer(modifier = Modifier.padding(8.dp))
                if (habit.timesPerDay > 0) {
                    Text(text = "(${habit.timesPerDay})")
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {
                    if (habit.countTimes > 0) {
                        val updatedHabit = habit.copy(countTimes = habit.countTimes - 1)
                        viewModel.updateHabit(updatedHabit)
                    }
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_downward_24),
                        contentDescription = "Less"
                    )
                }
                Text(
                    text = habit.countTimes.toString()
                )
                IconButton(onClick = {
                    val updatedHabit = habit.copy(countTimes = habit.countTimes + 1)
                    viewModel.updateHabit(updatedHabit)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_upward_24),
                        contentDescription = "More"
                    )
                }
                IconButton(onClick = {
                    viewModel.removeHabit(habit)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_delete_24),
                        contentDescription = "Remove"
                    )
                }
            }
        }
    }
}