package com.example.currencyconverter.screen.demo

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DemoScreen() {
    val viewModel: DemoViewModel = viewModel()

    Column {
        Text(text = "Demo")
        Text(text = viewModel.greeting)
    }
}