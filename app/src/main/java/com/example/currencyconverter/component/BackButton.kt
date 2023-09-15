package com.example.currencyconverter.component

import androidx.activity.compose.BackHandler
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.currencyconverter.Screen
import com.example.currencyconverter.screenState

@Composable
fun BackButton(screen: Screen = Screen.MenuScreen) {
    Button(onClick = {
        screenState.value = screen
    }) {
        Text(text = "<")
    }

    BackHandler {
        screenState.value = screen
    }
}