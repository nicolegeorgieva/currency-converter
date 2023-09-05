package com.example.currencyconverter.screen.demo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DemoViewModel @Inject constructor(
    private val nameDataStore: NameDataStore,
    private val greeter: Greeter
) : ViewModel() {

    @Composable
    fun greeting(): String {
        val name = remember { nameDataStore.name() }
            .collectAsState(initial = "").value
        val greeting = remember { greeter.randomGreeting() }

        return if (name.isNotBlank()) "$greeting $name!" else ""
    }

    fun setName(newName: String) {
        viewModelScope.launch {
            nameDataStore.updateName(newName)
        }
    }

    fun clearName() {
        viewModelScope.launch {
            nameDataStore.clearName()
        }
    }
}