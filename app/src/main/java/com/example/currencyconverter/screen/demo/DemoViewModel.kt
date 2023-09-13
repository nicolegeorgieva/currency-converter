package com.example.currencyconverter.screen.demo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.ComposeViewModel
import com.example.currencyconverter.screen.age.AgeDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DemoViewModel @Inject constructor(
    private val nameDataStore: NameDataStore,
    private val greeter: Greeter,
    private val ageDataStore: AgeDataStore
) : ComposeViewModel<DemoUiState, DemoEvent>() {
    private val name = mutableStateOf<String?>(null)
    private val greeting = mutableStateOf<String?>(null)
    private val age = mutableStateOf<Int?>(null)

    @Composable
    override fun uiState(): DemoUiState {
        return DemoUiState(
            greeting = getGreeting(),
            name = getName(),
            age = getAge()
        )
    }

    @Composable
    private fun getGreeting(): String {
        return if (name.value?.isNotBlank() == true) "${greeting.value} ${name.value}!" else ""
    }

    @Composable
    private fun getName(): String {
        return name.value ?: ""
    }

    @Composable
    private fun getAge(): String {
        return age.value?.takeIf { it != 0 }?.toString() ?: ""
    }

    override fun onEvent(event: DemoEvent) {
        when (event) {
            DemoEvent.ClearName -> clearName()
            DemoEvent.OnStart -> onStart()
            is DemoEvent.SetName -> setName(event.newName)
        }
    }

    private fun onStart() {
        greeting.value = greeter.randomGreeting()

        viewModelScope.launch {
            name.value = nameDataStore.name().firstOrNull() ?: ""
            age.value = ageDataStore.getAge().firstOrNull() ?: 0
        }
    }

    private fun setName(newName: String) {
        name.value = newName

        viewModelScope.launch {
            nameDataStore.updateName(newName)
        }
    }

    private fun clearName() {
        name.value = ""

        viewModelScope.launch {
            nameDataStore.clearName()
        }
    }
}