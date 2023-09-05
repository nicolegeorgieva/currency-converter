package com.example.currencyconverter.screen.age

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AgeViewModel @Inject constructor(
    private val ageDataStore: AgeDataStore
) : ViewModel() {
    fun setAge(age: String) {
        viewModelScope.launch {
            ageDataStore.setAge(age)
        }
    }
}