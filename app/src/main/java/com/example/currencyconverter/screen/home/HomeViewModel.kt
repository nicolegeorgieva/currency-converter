package com.example.currencyconverter.screen.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val monthlyGrossSalaryCalculator: MonthlyGrossSalaryCalculator,
    private val monthlyNetSalaryCalculator: MonthlyNetSalaryCalculator,
    private val exchangeRatesDataSource: ExchangeRatesDataSource,
    private val currencyConverter: CurrencyConverter,
    private val homeDataStore: HomeDataStore
) : ViewModel() {
    private val monthlyGrossSalaryInBgn = mutableStateOf("")
    private val monthlyNetSalaryInBgn = mutableStateOf<Double?>(null)
    val hourlyRateInUsd = mutableStateOf("")
    private val exchangeRatesResponse =
        mutableStateOf<ExchangeRatesDataSource.ExchangeRatesResponse?>(null)
    private val taxPercentage = mutableStateOf<Double?>(null)
    private val socialSecurityAmount = mutableStateOf<Double?>(null)
    private val companyExpensesAmount = mutableStateOf<Double?>(null)

    fun onStart() {
        viewModelScope.launch {
            exchangeRatesResponse.value = exchangeRatesDataSource.fetchExchangeRates()
            hourlyRateInUsd.value = homeDataStore.getHourlyRate()
            taxPercentage.value = homeDataStore.getTaxPercentage()
            socialSecurityAmount.value = homeDataStore.getSocialSecurityAmount()
            companyExpensesAmount.value = homeDataStore.getCompanyExpensesAmount()
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

        viewModelScope.launch {
            homeDataStore.editHourlyRate(newRate)
        }

        val usdToBgn = currencyConverter.exchangeUsdToBgn(
            exchangeRatesResponse.value,
            hourlyRateInUsd.value.toDoubleOrNull()
        )

        monthlyGrossSalaryInBgn.value =
            monthlyGrossSalaryCalculator.calculateMonthlyGrossSalary(
                usdToBgn ?: 1.0,
                monthlyGrossSalaryCalculator.monthlyHours
            ).toString()
    }

    fun onChangeTaxPercentage(newTaxPercentage: Double) {
        taxPercentage.value = newTaxPercentage

        viewModelScope.launch {
            homeDataStore.editTaxPercentage(newTaxPercentage)
        }

        monthlyNetSalaryInBgn.value =
    }

    fun onChangeSocialSecurityAmount(newSocialSecurityAmount: Double) {
        socialSecurityAmount.value = newSocialSecurityAmount

        viewModelScope.launch {
            homeDataStore.editSocialSecurityAmount(newSocialSecurityAmount)
        }
    }

    fun onChangeCompanyExpensesAmount(newCompanyExpensesAmount: Double) {
        companyExpensesAmount.value = newCompanyExpensesAmount

        viewModelScope.launch {
            homeDataStore.editCompanyExpensesAmount(newCompanyExpensesAmount)
        }
    }

    fun getMontlyBgnGrossSalary(): Double {
        return monthlyGrossSalaryInBgn.value.toDoubleOrNull() ?: 1.0
    }

    fun getMonthlyBgnNetSalary(): Double {
        return monthlyNetSalaryInBgn.value ?: 1.0
    }
}