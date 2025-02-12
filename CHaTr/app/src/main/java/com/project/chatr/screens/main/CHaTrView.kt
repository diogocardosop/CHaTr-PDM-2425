package com.project.chatr.screens.main

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.chatr.R
import com.project.chatr.domain.Habit
import com.project.chatr.utils.ViewModelDefinition
import com.project.chatr.utils.ViewModelState

class ButtonActions(
    val summaryHabits: (() -> Unit)
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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
    val habits = viewModel.todayHabits.collectAsState().value

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = stringResource(R.string.dailyTracker),
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 24.dp) // Increased padding
        )

        if (habits.isEmpty()) {
            // Display a message when there are no habits
            Text(
                text = stringResource(R.string.noResults),
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(16.dp)
            )
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(habits) { habit ->
                    HabitItem(habit = habit, viewModel)
                }
            }
        }

        DailyTrackerButtons(btnActions, viewModel)
    }
}

@Composable
fun CHaTrLandscapeView(
    modifier: Modifier = Modifier,
    btnActions: ButtonActions,
    viewModel: CHaTrViewModel
) {
    val habits = viewModel.todayHabits.collectAsState().value

    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background),
    ) {
        // Left Column for Title and Habit List
        Column(
            modifier = Modifier
                .weight(2f) // Takes up 2/3 of the space
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Top,
        ) {
            if (habits.isEmpty()) {
                Text(
                    text = stringResource(R.string.noResults),
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(habits) { habit ->
                        HabitItem(habit = habit, viewModel)
                    }
                }
            }
        }

        // Right Column for Buttons
        Column(
            modifier = Modifier
                .weight(0.5f) // Takes up 1/3 of the space
                .fillMaxHeight()
                .testTag("DailyTrackerButtons")
                .padding(start = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.dailyTracker),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            DailyTrackerButtons(btnActions, viewModel)
        }
    }
}

@Composable
fun DailyTrackerButtons(btnActions: ButtonActions, viewModel: CHaTrViewModel) {
    Spacer(modifier = Modifier.height(16.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = { btnActions.summaryHabits() },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer), // Styled button
            shape = RoundedCornerShape(8.dp) // Rounded corners
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_data_usage_24),
                contentDescription = "Habit Resume",
                tint = MaterialTheme.colorScheme.onSecondaryContainer // Styled icon
            )
        }

        Button(
            onClick = { viewModel.updateViewModelScreen(ViewModelDefinition.Adding) },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer), // Styled button
            shape = RoundedCornerShape(8.dp) // Rounded corners
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_add_circle_outline_24),
                contentDescription = "Add Habit",
                tint = MaterialTheme.colorScheme.onPrimaryContainer // Styled icon
            )
        }
    }
}

@Composable
fun HabitItem(habit: Habit, viewModel: CHaTrViewModel) {
    var showDialog by rememberSaveable { mutableStateOf(false) }
    val state = viewModel.state.collectAsState().value
    val screen = viewModel.screen.collectAsState().value
    var dialogType by rememberSaveable { mutableStateOf<ViewModelState?>(null) }

    LaunchedEffect(state, screen) {
        if (screen is ViewModelDefinition.Home && (state is ViewModelState.Error || state is ViewModelState.Success && !showDialog)) {
            showDialog = true
            dialogType = state
        }
    }

    if (showDialog) {
        when (dialogType) {
            is ViewModelState.Error -> {
                AlertDialog(
                    onDismissRequest = {
                        showDialog = false
                        viewModel.resetState()
                    },
                    title = { Text(text = stringResource(R.string.error), color = MaterialTheme.colorScheme.primary) },
                    text = { Text(text = (dialogType as ViewModelState.Error).message) },
                    confirmButton = {
                        Button(onClick = {
                            showDialog = false
                            viewModel.resetState()
                        })
                        {
                            Text(text = stringResource(R.string.ok))
                        }
                    })
            }
            else -> {}
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), // Added elevation
        shape = RoundedCornerShape(8.dp) // Rounded corners
    ) {
        Row(
            modifier = Modifier
                .background(
                    color = when {
                        habit.countTimes >= habit.timesPerDay -> Color(0xB58EF38E)
                        habit.countTimes > 0 -> Color(0xFFF6E98A)
                        else -> Color(0xB5FC8585)
                    }
                )
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = habit.name,
                    style = MaterialTheme.typography.bodyLarge, // Use typography
                    color = MaterialTheme.colorScheme.onSurface // Use color scheme
                )
                Spacer(modifier = Modifier.padding(8.dp))
                if (habit.timesPerDay > 0) {
                    Text(
                        text = "(${habit.timesPerDay})",
                        style = MaterialTheme.typography.bodyMedium, // Use typography
                        color = MaterialTheme.colorScheme.onSurfaceVariant // Use color scheme
                    )
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
                        contentDescription = "Decrease counter",
                        tint = MaterialTheme.colorScheme.primary // Styled icon
                    )
                }
                Text(
                    text = habit.countTimes.toString(),
                    style = MaterialTheme.typography.bodyLarge, // Use typography
                    color = MaterialTheme.colorScheme.onSurface // Use color scheme
                )
                IconButton(onClick = {
                    val updatedHabit = habit.copy(countTimes = habit.countTimes + 1)
                    viewModel.updateHabit(updatedHabit)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_upward_24),
                        contentDescription = "Increase counter",
                        tint = MaterialTheme.colorScheme.primary // Styled icon
                    )
                }
                IconButton(onClick = {
                    viewModel.removeHabit(habit)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_delete_24),
                        contentDescription = "Remove",
                        tint = MaterialTheme.colorScheme.error // Styled icon
                    )
                }
            }
        }
        if (habit.description.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = habit.description,
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}