package com.example.currencyconverter

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel

abstract class CustomViewModel<S, E> : ViewModel() {
    @Composable
    abstract fun uiState(): S

    abstract fun onEvent(event: E)
}