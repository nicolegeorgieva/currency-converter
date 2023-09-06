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
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountStudyTimeViewModel @Inject constructor(
    private val studyTimeDataStore: CountStudyTimeDataStore
) : ViewModel() {
    private val startHourInputState = mutableStateOf("")
    private val startMinsInputState = mutableStateOf("")
    private val endHourInputState = mutableStateOf("")
    private val endMinsInputState = mutableStateOf("")
    private val cutMinsState = mutableStateOf("")
    private val totalStudyTimeState = mutableStateOf<String?>(null)
    private val errorOccurredState = mutableStateOf(false)

    fun onStart() {
        viewModelScope.launch {
            startHourInputState.value = studyTimeDataStore.startHourInput.firstOrNull() ?: ""
            startMinsInputState.value = studyTimeDataStore.startMinsInput.firstOrNull() ?: ""
            cutMinsState.value = studyTimeDataStore.cutMins.firstOrNull() ?: ""
            totalStudyTimeState.value = studyTimeDataStore.totalStudyTime.firstOrNull() ?: "0h 00m"
        }
    }

    @Composable
    fun getStartHour(): String {
        return startHourInputState.value
    }

    @Composable
    fun getStartMins(): String {
        return startMinsInputState.value
    }

    @Composable
    fun getEndHour(): String {
        return endHourInputState.value
    }

    @Composable
    fun getEndMins(): String {
        return endMinsInputState.value
    }

    @Composable
    fun getErrorOccuredState(): Boolean {
        return errorOccurredState.value
    }

    @Composable
    fun getTotalStudyTimeState(): String {
        return totalStudyTimeState.value ?: "0h 00m"
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

    fun editEndHour(newHour: String) {
        endHourInputState.value = newHour
    }

    fun editEndMins(newMins: String) {
        endMinsInputState.value = newMins
    }

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

    private fun totalStudy(
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

    fun totalTimeReset() {
        totalStudyTimeState.value = "0h 00m"

        viewModelScope.launch {
            studyTimeDataStore.totalTimeReset()
        }
    }
}