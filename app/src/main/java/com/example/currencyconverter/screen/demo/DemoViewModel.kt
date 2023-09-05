package com.example.currencyconverter.screen.demo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DemoViewModel @Inject constructor(
    private val greeter: Greeter
) : ViewModel() {

    @Composable
    fun greeting(): String {
        val name = greeter.greet().collectAsState(initial = "").value
        return if (name.isNotBlank()) "Hi, $name!" else ""
    }

    fun setName() {
        viewModelScope.launch {
            greeter.updateName()
        }
    }

    fun clearName() {
        viewModelScope.launch {
            greeter.clearName()
        }
    }
}