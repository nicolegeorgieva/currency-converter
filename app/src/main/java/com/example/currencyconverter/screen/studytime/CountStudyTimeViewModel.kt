package com.example.currencyconverter.screen.studytime

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountStudyTimeViewModel @Inject constructor(
    private val studyTimeDataStore: CountStudyTimeDataStore,
    private val studyTimeCalculator: StudyTimeCalculator
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
            startHourInputState.value = studyTimeDataStore.startHourInput.firstOrEmptyString()
            startMinsInputState.value = studyTimeDataStore.startMinsInput.firstOrEmptyString()
            cutMinsState.value = studyTimeDataStore.cutMins.firstOrEmptyString()
            totalStudyTimeState.value = studyTimeDataStore.totalStudyTime.firstOrNull() ?: "0h 00m"
        }
    }

    private suspend fun Flow<String?>.firstOrEmptyString(): String {
        return this.firstOrNull() ?: ""
    }

    @Composable
    fun studyTimeUi(): StudyTimeUi {
        return StudyTimeUi(
            startHour = getStartHour(),
            startMins = getStartMins(),
            endHour = getEndHour(),
            endMins = getEndMins(),
            cutMins = getCutMins(),
            totalTimeOfStudying = getTotalStudyTimeState(),
            errorOccured = getErrorOccuredState()
        )
    }

    @Composable
    private fun getStartHour(): String {
        return startHourInputState.value
    }

    @Composable
    private fun getStartMins(): String {
        return startMinsInputState.value
    }

    @Composable
    private fun getEndHour(): String {
        return endHourInputState.value
    }

    @Composable
    private fun getEndMins(): String {
        return endMinsInputState.value
    }

    @Composable
    private fun getErrorOccuredState(): Boolean {
        return errorOccurredState.value
    }

    @Composable
    private fun getTotalStudyTimeState(): String {
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

    fun getCutMins(): String {
        return cutMinsState.value
    }

    fun editCutMins(newCutMins: String) {
        cutMinsState.value = newCutMins

        viewModelScope.launch {
            studyTimeDataStore.editCutMins(newCutMins)
        }
    }

    fun addCurrentStudyToTotal() {
        val currentStudyMins = studyTimeCalculator.currentStudyMins(
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
        return studyTimeCalculator.totalStudyTimeRes(
            startHourInputState,
            startMinsInputState,
            endHourInputState,
            endMinsInputState,
            cutMinsState,
            studyTimeCalculator.convertTotalTimeToMins(totalStudyTimeState)
        )
    }

    fun totalTimeReset() {
        totalStudyTimeState.value = "0h 00m"

        viewModelScope.launch {
            studyTimeDataStore.totalTimeReset()
        }
    }
}