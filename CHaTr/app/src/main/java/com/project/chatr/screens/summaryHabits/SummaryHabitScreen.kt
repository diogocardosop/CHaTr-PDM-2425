package com.project.chatr.screens.summaryHabits

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.project.chatr.R
import com.project.chatr.domain.HabitSummary
import java.time.DayOfWeek

@Composable
fun SummaryHabitScreen(
    modifier: Modifier = Modifier,
    viewModel: SummaryHabitViewModel
) {
    // Dummy data for demonstration
    val habitSummaries = viewModel.weekHabits.collectAsState().value

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.title_summary_habit_activity),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(habitSummaries) { summary ->
                HabitSummaryCard(summary = summary)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun HabitSummaryCard(summary: HabitSummary) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
           Text(
               text = summary.habitName,
               style = MaterialTheme.typography.titleMedium,
               fontWeight = FontWeight.Bold
           )
           Spacer(modifier = Modifier.height(8.dp))

           Row(modifier = Modifier.fillMaxWidth()) {
               summary.daysCompleted.forEach { day ->
                   DayIndicator(day)
                   Spacer(modifier = Modifier.width(8.dp))
               }
           }
        }
    }
}
@Composable
fun DayIndicator(day: DayOfWeek) {
    Column {
        Text(
            text = day.name.substring(0, 3),
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(4.dp))
        Divider(modifier = Modifier
            .height(4.dp).width(30.dp))
    }
}