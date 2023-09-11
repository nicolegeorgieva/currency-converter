package com.example.currencyconverter.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "myapp-data")

val START_HOUR_KEY = stringPreferencesKey("start-hour")
val START_MINS_KEY = stringPreferencesKey("start-min")
val CUT_MINS_KEY = stringPreferencesKey("cut-mins")

val TOTAL_STUDY_TIME_KEY = stringPreferencesKey("total-study-time")

val FIRST_NAME_KEY = stringPreferencesKey("first-name")
val AGE_KEY = intPreferencesKey("age")

val RATE_PER_HOUR_KEY = doublePreferencesKey("rate-per-hour")
val TAX_PERCENTAGE_KEY = doublePreferencesKey("tax-percentage")
val SOCIAL_SECURITY_AMOUNT_KEY = doublePreferencesKey("social-security-amount")
val COMPANY_EXPENSES_AMOUNT_KEY = doublePreferencesKey("company-expenses-amount")