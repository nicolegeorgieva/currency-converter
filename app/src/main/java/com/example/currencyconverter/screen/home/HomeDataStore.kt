package com.example.currencyconverter.screen.home

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.example.currencyconverter.data.COMPANY_EXPENSES_AMOUNT_KEY
import com.example.currencyconverter.data.HOURLY_RATE_KEY
import com.example.currencyconverter.data.MONTHLY_GROSS_SALARY_KEY
import com.example.currencyconverter.data.MONTHLY_NET_SALARY_KEY
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

    fun getHourlyRate(): Flow<String> {
        return dataStore.data.map { it[HOURLY_RATE_KEY] ?: "" }
    }

    suspend fun editHourlyRate(newHourlyRate: String) {
        dataStore.edit {
            it[HOURLY_RATE_KEY] = newHourlyRate
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

    fun getMonthlyGrossSalary(): Flow<String> {
        return dataStore.data.map { it[MONTHLY_GROSS_SALARY_KEY] ?: "" }
    }

    suspend fun editMonthlyGrossSalary(newMonthlyGrossSalary: String) {
        dataStore.edit {
            it[MONTHLY_GROSS_SALARY_KEY] = newMonthlyGrossSalary
        }
    }

    fun getMonthlyNetSalary(): Flow<Double> {
        return dataStore.data.map { it[MONTHLY_NET_SALARY_KEY] ?: 0.0 }
    }

    suspend fun editMonthlyNetSalary(newMonthlyNetSalary: Double) {
        dataStore.edit {
            it[MONTHLY_NET_SALARY_KEY] = newMonthlyNetSalary
        }
    }
}