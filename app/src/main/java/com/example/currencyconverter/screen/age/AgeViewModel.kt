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
    private val ageState = mutableStateOf<Int?>(null)

    fun onStart() {
        viewModelScope.launch {
            ageState.value = ageDataStore.getAge().firstOrNull() ?: 0
        }
    }

    @Composable
    fun getAgeUi(): AgeUiState {
        return AgeUiState(getAge().toCustomString())
    }

    private fun Int?.toCustomString(): String {
        return if (this == null || this == 0) "" else this.toString()
    }

    @Composable
    private fun getAge(): Int {
        return ageState.value ?: 0
    }

    fun onChangeAge(age: String) {
        ageState.value = age.toIntOrNull() ?: 0

        viewModelScope.launch {
            ageDataStore.setAge(age.toIntOrNull() ?: 0)
        }
    }
}