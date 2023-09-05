package com.example.currencyconverter.screen.age

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.example.currencyconverter.data.AGE_KEY
import com.example.currencyconverter.data.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AgeDataStore @Inject constructor(
    @ApplicationContext
    private val context: Context
) {
    fun getAge(): Flow<String> {
        return context.dataStore.data.map { it[AGE_KEY] ?: "" }
    }

    suspend fun setAge(newAge: String) {
        context.dataStore.edit { it[AGE_KEY] = newAge }
    }
}