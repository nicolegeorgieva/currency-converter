package com.example.currencyconverter.screen.age

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.currencyconverter.MyPreview
import com.example.currencyconverter.Screen
import com.example.currencyconverter.component.BackButton
import com.example.currencyconverter.screenState
import com.example.currencyconverter.ui.theme.CurrencyConverterTheme

@Composable
fun AgeScreen() {
    val viewModel: AgeViewModel = viewModel()
    val uiState = viewModel.uiState()

    UI(
        state = uiState,
        onEvent = {
            viewModel.onEvent(it)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UI(
    state: AgeUiState,
    onEvent: (AgeEvent) -> Unit
) {
    Column {
        LaunchedEffect(Unit) {
            onEvent(AgeEvent.OnStart)
        }

        BackButton {
            screenState.value = Screen.DemoScreen
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextField(
            value = state.age,
            onValueChange = {
                onEvent(AgeEvent.OnChangeAge(it))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}

@Preview
@Composable
private fun AgeUiPreview() {
    CurrencyConverterTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            UI(
                state = AgeUiState(""),
                onEvent = {}
            )
        }
    }
}

@Preview
@Composable
private fun AgeUiPreview2() {
    MyPreview {
        UI(
            state = AgeUiState("24"),
            onEvent = {}
        )
    }
}