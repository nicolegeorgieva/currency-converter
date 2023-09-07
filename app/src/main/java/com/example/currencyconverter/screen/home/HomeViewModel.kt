package com.example.currencyconverter.screen.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.domain.monthlysalary.calculateMonthlySalary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val exchangeRates: ExchangeRates,
    private val monthlyHoursProvider: MonthlyHoursProvider
) : ViewModel() {
    private val hourlyRateInUsd = mutableStateOf("")
    private val monthlySalaryInBgn = mutableStateOf("")
    private val exchangeRatesResponse = mutableStateOf<ExchangeRates.ExchangeRatesResponse?>(null)

    fun onStart() {
        viewModelScope.launch {
            exchangeRatesResponse.value = exchangeRates.fetchExchangeRates()
        }
    }

    fun getDate(): String? {
        return exchangeRatesResponse.value?.date
    }

    fun getHourlyRateInUsd(): String {
        return hourlyRateInUsd.value
    }

    fun onChangeHourlyRateInUsd(newRate: String) {
        hourlyRateInUsd.value = newRate

        val eurToUsd = exchangeRatesResponse.value?.eur?.usd ?: 1.0
        val eurToBgn = exchangeRatesResponse.value?.eur?.bgn ?: 1.0
        val usdHourlyRateToEur = (hourlyRateInUsd.value.toDoubleOrNull() ?: 1.0) / eurToUsd
        val eurToBgnHourlyRate = usdHourlyRateToEur * eurToBgn

        monthlySalaryInBgn.value =
            calculateMonthlySalary(eurToBgnHourlyRate, monthlyHoursProvider.monthlyHours).toString()
    }

    fun getMontlySalary(): Double {
        return monthlySalaryInBgn.value.toDoubleOrNull() ?: 1.0
    }
}