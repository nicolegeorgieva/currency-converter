package com.example.currencyconverter

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel

abstract class ComposeViewModel<S, E> : ViewModel() {
    @Composable
    abstract fun uiState(): S

    abstract fun onEvent(event: E)
}