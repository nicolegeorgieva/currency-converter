package com.example.currencyconverter.screen.studytime

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.domain.studytime.convertTotalTimeToMins
import com.example.currencyconverter.domain.studytime.totalStudyTimeRes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountStudyTimeViewModel @Inject constructor(
    private val studyTimeDataStore: CountStudyTimeDataStore
) : ViewModel() {
    private var startHourInputState = mutableStateOf("")

    @Composable
    fun getStartHour(): String {
        return studyTimeDataStore.startHourInput.collectAsState(initial = "").value ?: ""
    }

    private var startMinsInputState = mutableStateOf("")

    @Composable
    fun getStartMins(): String {
        return studyTimeDataStore.startMinsInput.collectAsState(initial = "").value ?: ""
    }

    fun editStartHour(newHour: String) {
        startHourInputState.value = newHour

        viewModelScope.launch {
            studyTimeDataStore.editStartHour(newHour)
        }
    }

    fun editStartMins(newMins: String) {
        startMinsInputState.value = newMins

        viewModelScope.launch {
            studyTimeDataStore.editStartMins(newMins)
        }
    }

    var endHourInputState = mutableStateOf("")
    var endMinsInputState = mutableStateOf("")
    private var cutMinsState = mutableStateOf("")
    var totalStudyTimeState = mutableStateOf("")
    var errorOccuredState = mutableStateOf(false)

    @Composable
    fun getCutMins(): String {
        return studyTimeDataStore.cutMins.collectAsState(initial = "").value ?: ""
    }

    fun editCutMins(newCutMins: String) {
        cutMinsState.value = newCutMins

        viewModelScope.launch {
            studyTimeDataStore.editCutMins(newCutMins)
        }
    }

    fun totalStudy(
        startHourInputState: String,
        startMinsInputState: String,
        endHourInputState: String,
        endMinsInputState: String,
        cutMinsState: String,
        totalStudyTimeState: String
    ) {
        totalStudyTimeRes(
            startHourInputState,
            startMinsInputState,
            endHourInputState,
            endMinsInputState,
            cutMinsState,
            convertTotalTimeToMins(totalStudyTimeState)
        )
    }
}