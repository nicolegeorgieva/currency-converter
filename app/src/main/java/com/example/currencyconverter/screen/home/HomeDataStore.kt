package com.example.currencyconverter.screen.home

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.example.currencyconverter.data.COMPANY_EXPENSES_AMOUNT_KEY
import com.example.currencyconverter.data.RATE_PER_HOUR_KEY
import com.example.currencyconverter.data.SOCIAL_SECURITY_AMOUNT_KEY
import com.example.currencyconverter.data.TAX_PERCENTAGE_KEY
import com.example.currencyconverter.data.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HomeDataStore @Inject constructor(
    @ApplicationContext
    private val context: Context
) {
    val dataStore = context.dataStore

    fun getHourlyRate(): Flow<Double> {
        return dataStore.data.map { it[RATE_PER_HOUR_KEY] ?: 0.0 }
    }

    suspend fun editHourlyRate(newHourlyRate: Double) {
        dataStore.edit {
            it[RATE_PER_HOUR_KEY] = newHourlyRate
        }
    }

    fun getTaxPercentage(): Flow<Double> {
        return dataStore.data.map { it[TAX_PERCENTAGE_KEY] ?: 0.0 }
    }

    suspend fun editTaxPercentage(newTaxPercentage: Double) {
        dataStore.edit {
            it[TAX_PERCENTAGE_KEY] = newTaxPercentage
        }
    }

    fun getSocialSecurityAmount(): Flow<Double> {
        return dataStore.data.map { it[SOCIAL_SECURITY_AMOUNT_KEY] ?: 0.0 }
    }

    suspend fun editSocialSecurityAmount(newSocialSecurityAmount: Double) {
        dataStore.edit {
            it[SOCIAL_SECURITY_AMOUNT_KEY] = newSocialSecurityAmount
        }
    }

    fun getCompanyExpensesAmount(): Flow<Double> {
        return dataStore.data.map { it[COMPANY_EXPENSES_AMOUNT_KEY] ?: 0.0 }
    }

    suspend fun editCompanyExpensesAmount(newCompanyExpensesAmount: Double) {
        dataStore.edit {
            it[COMPANY_EXPENSES_AMOUNT_KEY] = newCompanyExpensesAmount
        }
    }
}