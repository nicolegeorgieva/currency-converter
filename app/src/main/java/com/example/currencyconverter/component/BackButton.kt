package com.example.currencyconverter.component

import androidx.activity.compose.BackHandler
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun BackButton(onBack: () -> Unit) {
    Button(onClick = {
        onBack()
    }) {
        Text(text = "<")
    }

    BackHandler {
        onBack()
    }
}