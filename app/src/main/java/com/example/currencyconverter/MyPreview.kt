package com.example.currencyconverter

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.currencyconverter.ui.theme.CurrencyConverterTheme

@Composable
fun MyPreview(content: @Composable () -> Unit) {
    CurrencyConverterTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            content()
        }
    }
}