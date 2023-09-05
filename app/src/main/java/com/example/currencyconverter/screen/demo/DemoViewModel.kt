package com.example.currencyconverter.screen.demo

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DemoViewModel @Inject constructor() : ViewModel() {
    val greeting = "Hello"
}