package com.example.currencyconverter.component

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun MenuItem(name: String, onClick: () -> Unit) {
    Button(onClick = {
        onClick()
    }) {
        Text(text = name)
    }
}
