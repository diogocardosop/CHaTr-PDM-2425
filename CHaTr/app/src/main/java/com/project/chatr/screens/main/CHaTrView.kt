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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.chatr.R

data class Habit(
    val name: String,
    var isCompleted: Boolean = false,
    var count: Int = 1,
    var final: Int = 10
)

class ButtonActions(
    val habitAdd: (() -> Unit),
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
        Configuration.ORIENTATION_LANDSCAPE -> CHaTrLandscapeView(modifier, btnActions)
        else -> CHaTrPortraitView(modifier, btnActions)
    }
}

@Composable
fun CHaTrPortraitView(
    modifier: Modifier = Modifier,
    btnActions: ButtonActions
) {
    DailyTrackerList(modifier, btnActions)
}

@Composable
fun CHaTrLandscapeView(
    modifier: Modifier = Modifier,
    btnActions: ButtonActions
) {
    DailyTrackerList(modifier, btnActions)
}

@Composable
fun DailyTrackerList(modifier: Modifier = Modifier, btnActions: ButtonActions) {
    val habits = remember {
        mutableStateListOf(
            Habit("Drink Water"),
            Habit("Exercise"),
            Habit("Meditate"),
            Habit("Read"),
            Habit("Eat Healthy")
        )
    }

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
                HabitItem(habit = habit)
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
                onClick = { btnActions.habitAdd() }
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
fun HabitItem(habit: Habit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)

    ) {
        Row(
            modifier = Modifier
                .background(color =
                if (habit.isCompleted) Color(0xB58EF38E) else if (habit.count > 0) Color(0xFFF6E98A) else Color(
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
                if (habit.final > 0) {
                    Text(text = "(${habit.final})")
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { if (habit.count > 0) habit.count-- }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_downward_24),
                        contentDescription = "Less"
                    )
                }
                Text(
                    text = habit.count.toString()
                )
                IconButton(onClick = { habit.count++ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_upward_24),
                        contentDescription = "More"
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DailyTrackerListPreview() {
    DailyTrackerList(btnActions = ButtonActions(summaryHabits = {}, habitAdd = {}))
}