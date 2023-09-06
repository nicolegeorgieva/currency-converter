package com.example.currencyconverter.screen.studytime

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.domain.studytime.convertTotalTimeToMins
import com.example.currencyconverter.domain.studytime.currentStudyMins
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
    var totalStudyTimeState = mutableStateOf<String?>(null)
    var errorOccurredState = mutableStateOf(false)

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
        totalStudyTimeState: String?
    ): String {
        return totalStudyTimeRes(
            startHourInputState,
            startMinsInputState,
            endHourInputState,
            endMinsInputState,
            cutMinsState,
            convertTotalTimeToMins(totalStudyTimeState)
        )
    }

    fun addCurrentStudyToTotal() {
        val currentStudyMins = currentStudyMins(
            startHourInputState.value,
            startMinsInputState.value,
            endHourInputState.value,
            endMinsInputState.value,
            cutMinsState.value
        )

        val totalStudyTime = totalStudy(
            startHourInputState.value,
            startMinsInputState.value,
            endHourInputState.value,
            endMinsInputState.value,
            cutMinsState.value,
            totalStudyTimeState.value
        )

        if (currentStudyMins < 0) {
            errorOccurredState.value = true
        } else if (totalStudyTime != totalStudyTimeState.value) {
            errorOccurredState.value = false

            viewModelScope.launch {
                studyTimeDataStore.addTotalStudyTime(totalStudyTime)
            }

            startHourInputState.value = ""
            startMinsInputState.value = ""
            cutMinsState.value = ""
            endHourInputState.value = ""
            endMinsInputState.value = ""
            totalStudyTimeState.value = totalStudyTime
        } else {
            errorOccurredState.value = true
        }
    }

    fun totalTimeReset() {
        totalStudyTimeState.value = "0h 00m"

        viewModelScope.launch {
            studyTimeDataStore.totalTimeReset()
        }
    }
}