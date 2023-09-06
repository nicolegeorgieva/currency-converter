package com.example.currencyconverter.screen.studytime

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.example.currencyconverter.data.CUT_MINS_KEY
import com.example.currencyconverter.data.START_HOUR_KEY
import com.example.currencyconverter.data.START_MINS_KEY
import com.example.currencyconverter.data.TOTAL_STUDY_TIME_KEY
import com.example.currencyconverter.data.dataStore
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CountStudyTimeDataStore @Inject constructor(
    private val context: Context
) {
    val startHourInput = context.dataStore.data.map {
        it[START_HOUR_KEY]
    }

    val startMinsInput = context.dataStore.data.map {
        it[START_MINS_KEY]
    }

    val cutMins = context.dataStore.data.map {
        it[CUT_MINS_KEY]
    }

    val totalStudyTime = context.dataStore.data.map {
        it[TOTAL_STUDY_TIME_KEY]
    }

    suspend fun editStartHour(newHour: String) {
        context.dataStore.edit {
            it[START_HOUR_KEY] = newHour
        }
    }

    suspend fun editStartMins(newMins: String) {
        context.dataStore.edit {
            it[START_MINS_KEY] = newMins
        }
    }

    suspend fun editCutMins(newCutMins: String) {
        context.dataStore.edit {
            it[CUT_MINS_KEY] = newCutMins
        }
    }

    suspend fun addTotalStudyTime(totalStudy: String) {
        context.dataStore.edit {
            it[TOTAL_STUDY_TIME_KEY] = totalStudy
            it[START_HOUR_KEY] = ""
            it[START_MINS_KEY] = ""
            it[CUT_MINS_KEY] = ""
        }
    }

    suspend fun totalTimeReset() {
        context.dataStore.edit {
            it[TOTAL_STUDY_TIME_KEY] = "0h 00m"
        }
    }
}