package com.example.currencyconverter.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "myapp-data")

val START_HOUR_KEY = stringPreferencesKey("start-hour")
val START_MINS_KEY = stringPreferencesKey("start-min")
val CUT_MINS_KEY = stringPreferencesKey("cut-mins")

val TOTAL_STUDY_TIME_KEY = doublePreferencesKey("total-study-time")