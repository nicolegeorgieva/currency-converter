package com.example.currencyconverter.screen.requestplayground

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.currencyconverter.Screen
import com.example.currencyconverter.component.BackButton
import com.example.currencyconverter.screenState

@Composable
fun RequestPlaygroundScreen() {
    val viewModel: RequestPlaygroundViewModel = viewModel()
    val uiState = viewModel.uiState()

    RequestPlaygroundUi(
        uiState = uiState,
        onEvent = {
            viewModel.onEvent(it)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RequestPlaygroundUi(
    uiState: RequestUi,
    onEvent: (RequestPlaygroundEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        LaunchedEffect(Unit) {
            onEvent(RequestPlaygroundEvent.OnStart)
        }

        BackButton {
            screenState.value = Screen.MenuScreen
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row {
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = {
                    onEvent(RequestPlaygroundEvent.OnStart)
                }
            )
            {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = "Refresh"
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Crossfade(
            targetState = uiState.tasks,
            label = "crossfade content"
        ) { state ->
            Column {
                when (state) {
                    is RequestPlayground.Request.Success ->
                        for (i in state.data.indices) {
                            TaskCard(task = state.data[i]) {
                                onEvent(RequestPlaygroundEvent.OnDelete(i))
                            }
                        }

                    RequestPlayground.Request.Error -> {
                        Text(text = "Error!", color = Color.Red)
                    }

                    RequestPlayground.Request.Loading -> Text(text = "Loading...")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        TextField(
            value = uiState.currentTask,
            onValueChange = {
                onEvent(RequestPlaygroundEvent.OnToDoWriting(it))
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = {
            onEvent(RequestPlaygroundEvent.OnAddToDo)
        }) {
            Text(text = "Add")
        }
    }
}

@Composable
private fun TaskCard(task: String, onDelete: () -> Unit) {
    Card {
        Row {
            Text(text = task)

            Spacer(modifier = Modifier.weight(1f))

            Button(onClick = {
                onDelete()
            }) {
                Text(text = "Delete")
            }
        }
    }

    Spacer(modifier = Modifier.height(12.dp))
}

@Preview
@Composable
private fun RequestPlaygroundPreview() {
    RequestPlaygroundUi(
        uiState = RequestUi(
            tasks = RequestPlayground.Request.Success(
                listOf("task1", "task2", "task3")
            ),
            currentTask = "task4"
        ),
        onEvent = {}
    )
}