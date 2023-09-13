package com.example.currencyconverter.component

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.currencyconverter.Screen
import com.example.currencyconverter.screenState

@Composable
fun MenuItem(name: String, screen: Screen) {
    Button(onClick = {
        screenState.value = screen
    }) {
        Text(text = name)
    }
}
