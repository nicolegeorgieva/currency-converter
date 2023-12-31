package com.example.currencyconverter.screen.demo

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.example.currencyconverter.data.FIRST_NAME_KEY
import com.example.currencyconverter.data.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NameDataStore @Inject constructor(
    @ApplicationContext
    private val context: Context,
) {
    private val dataStore = context.dataStore

    fun name(): Flow<String> = dataStore.data.map { it[FIRST_NAME_KEY] ?: "" }

    suspend fun updateName(newName: String) {
        dataStore.edit {
            it[FIRST_NAME_KEY] = newName
        }
    }

    suspend fun clearName() {
        dataStore.edit {
            it[FIRST_NAME_KEY] = ""
        }
    }
}