package com.example.currencyconverter.screen.age

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.example.currencyconverter.data.SAVED_AGE_KEY
import com.example.currencyconverter.data.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AgeDataStore @Inject constructor(
    @ApplicationContext
    private val context: Context
) {
    fun getAge(): Flow<Double?> {
        return context.dataStore.data.map { it[SAVED_AGE_KEY] }
    }

    suspend fun setAge(newAge: Double) {
        context.dataStore.edit { it[SAVED_AGE_KEY] = newAge }
    }
}