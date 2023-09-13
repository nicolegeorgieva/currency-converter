package com.example.currencyconverter.screen.age

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.ComposeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AgeViewModel @Inject constructor(
    private val ageDataStore: AgeDataStore
) : ComposeViewModel<AgeUiState, AgeEvent>() {
    private val ageState = mutableStateOf<Int?>(null)

    @Composable
    override fun uiState(): AgeUiState {
        return AgeUiState(getAge().toCustomString())
    }

    private fun Int?.toCustomString(): String {
        return if (this == null || this == 0) "" else this.toString()
    }

    @Composable
    private fun getAge(): Int {
        return ageState.value ?: 0
    }

    override fun onEvent(event: AgeEvent) {
        when (event) {
            is AgeEvent.OnChangeAge -> onChangeAge(event.newAge)
            AgeEvent.OnStart -> onStart()
        }
    }

    private fun onStart() {
        viewModelScope.launch {
            ageState.value = ageDataStore.getAge().firstOrNull() ?: 0
        }
    }

    private fun onChangeAge(age: String) {
        ageState.value = age.toIntOrNull() ?: 0

        viewModelScope.launch {
            ageDataStore.setAge(age.toIntOrNull() ?: 0)
        }
    }
}