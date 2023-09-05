package com.example.currencyconverter.screen.demo

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.example.currencyconverter.data.FIRST_NAME_KEY
import com.example.currencyconverter.data.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class Greeter @Inject constructor(
    @ApplicationContext
    private val context: Context,
) {
    private val dataStore = context.dataStore

    fun greet(): Flow<String> = dataStore.data.map { it[FIRST_NAME_KEY] ?: "" }

    suspend fun updateName() {
        dataStore.edit {
            it[FIRST_NAME_KEY] = "Nicole"
        }
    }

    suspend fun clearName() {
        dataStore.edit {
            it[FIRST_NAME_KEY] = ""
        }
    }
}