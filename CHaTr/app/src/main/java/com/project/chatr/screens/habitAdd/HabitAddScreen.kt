package com.project.chatr.screens.habitAdd

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.project.chatr.R
import com.project.chatr.screens.main.CHaTrViewModel
import com.project.chatr.utils.ViewModelDefinition
import com.project.chatr.utils.ViewModelState
import kotlin.text.isNotBlank
import kotlin.text.toIntOrNull

@Composable
fun HabitAddScreen(
    modifier: Modifier = Modifier,
    viewModel: CHaTrViewModel,

    ) {
    AddItem(
        viewModel,
        modifier = modifier
    )

}

@Composable
fun AddItem(viewModel: CHaTrViewModel, modifier: Modifier = Modifier) {
    var name by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var timesPerDay by rememberSaveable { mutableStateOf("") }

    val state = viewModel.state.collectAsState().value
    var isError by rememberSaveable { mutableStateOf(false) }
    val screen = viewModel.screen.collectAsState().value
    var showDialog by rememberSaveable { mutableStateOf(false) }
    var dialogType by rememberSaveable { mutableStateOf<ViewModelState?>(null) }

    LaunchedEffect(state) {
        if (screen is ViewModelDefinition.Adding && (state is ViewModelState.Error || state is ViewModelState.Success)) {
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

            ViewModelState.Success -> {
                AlertDialog(
                    onDismissRequest = {
                        showDialog = false
                        viewModel.resetState()
                    },
                    title = { Text(text = stringResource(R.string.success), color = MaterialTheme.colorScheme.primary) },
                    text = { Text(text = stringResource(R.string.addWithSucess)) },
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

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {

        Text(
            text = stringResource(R.string.title_habit_add_activity),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(stringResource(R.string.nameLabel)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    shape = RoundedCornerShape(8.dp)
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text(stringResource(R.string.descriptionLabel)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    shape = RoundedCornerShape(8.dp)
                )

                OutlinedTextField(
                    value = timesPerDay,
                    onValueChange = {
                        timesPerDay = it
                        isError = it.toIntOrNull() == null
                    },
                    label = { Text(stringResource(R.string.timesDay)) },
                    modifier = Modifier.fillMaxWidth(),
                    isError = isError,
                    shape = RoundedCornerShape(8.dp)
                )
                if (isError) {
                    Text(
                        text = stringResource(R.string.invalidNum),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (name.isNotBlank()  && timesPerDay.isNotBlank() && !isError) {
                    viewModel.addHabit(name, description, timesPerDay.toInt())
                    name = ""
                    description = ""
                    timesPerDay = ""
                    isError = false
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(stringResource(R.string.addItem), color = Color.White)
        }
    }
}