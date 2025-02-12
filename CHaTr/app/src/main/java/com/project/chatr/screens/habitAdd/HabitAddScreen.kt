package com.project.chatr.screens.habitAdd

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.project.chatr.R
import com.project.chatr.screens.main.CHaTrViewModel
import com.project.chatr.utils.ViewModelState
import kotlinx.coroutines.flow.MutableStateFlow
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
    var showDialog by rememberSaveable { mutableStateOf(false) }
    var dialogType by rememberSaveable { mutableStateOf<ViewModelState?>(null) }

    LaunchedEffect(state) {
        if (state is ViewModelState.Error || state is ViewModelState.Success) {
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
                    title = { Text(text = "ERROR") },
                    text = { Text(text = (dialogType as ViewModelState.Error).message) },
                    confirmButton = {
                        Button(onClick = {
                            showDialog = false
                            viewModel.resetState()
                        })
                        {
                            Text(text = "OK")
                        }
                    })
            }

            ViewModelState.Success -> {
                AlertDialog(
                    onDismissRequest = {
                        showDialog = false
                        viewModel.resetState()
                    },
                    title = { Text(text = "SUCESSO") },
                    text = { Text(text = "Adicionado com Sucesso") },
                    confirmButton = {
                        Button(onClick = {
                            showDialog = false
                            viewModel.resetState()
                        })
                        {
                            Text(text = "OK")
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
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = timesPerDay,
            onValueChange = {
                timesPerDay = it
                isError = it.toIntOrNull() == null
            },
            label = { Text("How Many Times a Day") },
            modifier = Modifier.fillMaxWidth(),
            isError = isError
        )
        if (isError) {
            Text(text = "Invalid number", color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (name.isNotBlank() && description.isNotBlank() && timesPerDay.isNotBlank() && !isError) {
                    viewModel.addHabit(name, description, timesPerDay.toInt())
                    name = ""
                    description = ""
                    timesPerDay = ""
                    isError = false
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Item")
        }
    }
}