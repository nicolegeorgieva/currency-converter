package com.example.currencyconverter.screen

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
import androidx.compose.ui.unit.dp
import com.example.currencyconverter.request.createToDo
import com.example.currencyconverter.request.getToDos
import com.example.currencyconverter.request.removeToDo
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestPlaygroundScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        var toDosState by remember { mutableStateOf<List<String>?>(null) }

        val coroutineScope = rememberCoroutineScope()

        LaunchedEffect(Unit) {
            toDosState = getToDos()
        }

        Row {
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        toDosState = getToDos()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = "Menu"
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        val toDos = toDosState

        if (toDos != null) {
            for (i in toDos.indices) {
                TaskCard(task = toDos[i]) {
                    coroutineScope.launch {
                        toDosState = removeToDo(i)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        var newToDo by rememberSaveable { mutableStateOf("") }

        TextField(value = newToDo, onValueChange = { newToDo = it })

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = {
            coroutineScope.launch {
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