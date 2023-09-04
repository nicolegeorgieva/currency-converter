package com.example.currencyconverter.screen

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.currencyconverter.Screen
import com.example.currencyconverter.component.BackButton
import com.example.currencyconverter.request.Request
import com.example.currencyconverter.request.createToDo
import com.example.currencyconverter.request.getToDos
import com.example.currencyconverter.request.removeToDo
import com.example.currencyconverter.screenState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestPlaygroundScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        var toDosState by remember { mutableStateOf<Request>(Request.Loading) }

        val coroutineScope = rememberCoroutineScope()

        LaunchedEffect(Unit) {
            toDosState = getToDos()
        }

        BackButton {
            screenState.value = Screen.MenuScreen
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row {
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        toDosState = Request.Loading
                        toDosState = getToDos()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = "Refresh"
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Crossfade(
            targetState = toDosState,
            label = "crossfade content"
        ) { state ->
            Column {
                when (state) {
                    is Request.Success ->
                        for (i in state.data.indices) {
                            TaskCard(task = state.data[i]) {
                                coroutineScope.launch {
                                    toDosState = Request.Loading
                                    toDosState = removeToDo(i)
                                }
                            }
                        }

                    Request.Error -> {
                        Text(text = "Error!", color = Color.Red)
                    }

                    Request.Loading -> Text(text = "Loading...")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        var newToDo by rememberSaveable { mutableStateOf("") }

        TextField(value = newToDo, onValueChange = { newToDo = it })

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = {
            coroutineScope.launch {
                toDosState = Request.Loading
                toDosState = createToDo(newToDo)
                newToDo = ""
            }
        }) {
            Text(text = "Add")
        }
    }
}

@Composable
fun TaskCard(task: String, onDelete: () -> Unit) {
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