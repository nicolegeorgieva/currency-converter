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
    private val monthlyHoursProvider: MonthlyHoursProvider,
    private val exchangeRatesDataSource: ExchangeRatesDataSource,
    private val exchangeLogic: ExchangeLogic
) : ViewModel() {
    private val monthlySalaryInBgn = mutableStateOf("")
    val hourlyRateInUsd = mutableStateOf("")
    private val exchangeRatesResponse =
        mutableStateOf<ExchangeRatesDataSource.ExchangeRatesResponse?>(null)

    fun onStart() {
        viewModelScope.launch {
            exchangeRatesResponse.value = exchangeRatesDataSource.fetchExchangeRates()
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

        monthlySalaryInBgn.value =
            calculateMonthlySalary(
                exchangeLogic.exchangeUsdToBgn(
                    exchangeRatesResponse.value,
                    hourlyRateInUsd.value
                ), monthlyHoursProvider.monthlyHours
            ).toString()
    }

    fun getMontlySalary(): Double {
        return monthlySalaryInBgn.value.toDoubleOrNull() ?: 1.0
    }
}