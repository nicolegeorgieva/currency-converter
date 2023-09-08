package com.example.currencyconverter.screen.age

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AgeViewModel @Inject constructor(
    private val ageDataStore: AgeDataStore
) : ViewModel() {
    private val ageState = mutableStateOf<Double?>(0.0)

    fun onStart() {
        viewModelScope.launch {
            ageState.value = ageDataStore.getAge().firstOrNull() ?: 0.0
        }
    }

    private fun Double?.toCustomString(): String {
        return if (this == null || this == 0.0) "" else this.toString()
    }

    @Composable
    fun getAgeUi(): AgeUiState {
        return AgeUiState(getAge().toCustomString())
    }

    private fun getAge(): Double {
        return ageState.value ?: 0.0
    }

    fun onChangeAge(age: String) {
        ageState.value = age.toDoubleOrNull() ?: 0.0

        viewModelScope.launch {
            ageDataStore.setAge(age.toDoubleOrNull() ?: 0.0)
        }
    }
}