package com.example.currencyconverter.screen.demo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.currencyconverter.Screen
import com.example.currencyconverter.component.BackButton
import com.example.currencyconverter.screenState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DemoScreen() {
    val viewModel: DemoViewModel = viewModel()
    val uiState = viewModel.uiState()

    Column {
        LaunchedEffect(Unit) {
            viewModel.onEvent(DemoEvent.OnStart)
        }

        Row {
            BackButton {
                screenState.value = Screen.MenuScreen
            }

            Spacer(modifier = Modifier.width(12.dp))

            Button(onClick = {
                screenState.value = Screen.AgeScreen
            }) {
                Text(text = "Set age")
            }
        }

        Text(text = uiState.greeting, fontSize = 48.sp)

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = uiState.name,
            onValueChange = {
                viewModel.onEvent(DemoEvent.SetName(it))
            })

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            viewModel.onEvent(DemoEvent.ClearName)
        }) {
            Text(text = "Clear name")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Age: ${uiState.age}", fontSize = 48.sp)
    }
}