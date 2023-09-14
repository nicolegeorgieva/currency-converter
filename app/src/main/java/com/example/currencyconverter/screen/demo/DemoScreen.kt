package com.example.currencyconverter.screen.demo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.currencyconverter.Screen
import com.example.currencyconverter.component.BackButton
import com.example.currencyconverter.screenState
import com.example.currencyconverter.ui.theme.CurrencyConverterTheme

@Composable
fun DemoScreen() {
    val viewModel: DemoViewModel = viewModel()
    val uiState = viewModel.uiState()

    DemoUi(
        state = uiState,
        onEvent = {
            viewModel.onEvent(it)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DemoUi(
    state: DemoUiState,
    onEvent: (DemoEvent) -> Unit
) {
    Column {
        LaunchedEffect(Unit) {
            onEvent(DemoEvent.OnStart)
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

        Text(text = state.greeting, fontSize = 48.sp)

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = state.name,
            onValueChange = {
                onEvent(DemoEvent.SetName(it))
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            onEvent(DemoEvent.ClearName)
        }) {
            Text(text = "Clear name")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Age: ${state.age}", fontSize = 48.sp)
    }
}

@Preview
@Composable
private fun DemoScreenPreview() {
    CurrencyConverterTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            DemoUi(
                state = DemoUiState(
                    greeting = "Hello, Iliyan!",
                    name = "Iliyan",
                    age = "26"
                ),
                onEvent = {}
            )
        }
    }
}

@Preview
@Composable
private fun DemoScreenPreview2() {
    CurrencyConverterTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            DemoUi(
                state = DemoUiState(
                    greeting = "Hi, Amy!",
                    name = "Amy",
                    age = ""
                ),
                onEvent = {}
            )
        }
    }
}