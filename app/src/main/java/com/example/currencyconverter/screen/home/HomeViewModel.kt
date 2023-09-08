package com.example.currencyconverter.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val monthlyGrossSalaryCalculator: MonthlyGrossSalaryCalculator,
    private val monthlyNetSalaryCalculator: MonthlyNetSalaryCalculator,
    private val exchangeRatesDataSource: ExchangeRatesDataSource,
    private val currencyConverter: CurrencyConverter,
    private val homeDataStore: HomeDataStore,
    private val taxCalculator: TaxCalculator
) : ViewModel() {
    private val hourlyRateInUsd = mutableStateOf<String?>("")
    private val exchangeRatesResponse =
        mutableStateOf<ExchangeRatesDataSource.ExchangeRatesResponse?>(null)
    private val taxPercentage = mutableStateOf<Double?>(null)
    private val socialSecurityAmount = mutableStateOf<Double?>(null)
    private val companyExpensesAmount = mutableStateOf<Double?>(null)

    fun onStart() {
        viewModelScope.launch {
            exchangeRatesResponse.value = exchangeRatesDataSource.fetchExchangeRates()
            hourlyRateInUsd.value = homeDataStore.getHourlyRate().firstOrNull()
            taxPercentage.value = homeDataStore.getTaxPercentage().firstOrNull()
            socialSecurityAmount.value =
                homeDataStore.getSocialSecurityAmount().firstOrNull()
            companyExpensesAmount.value =
                homeDataStore.getCompanyExpensesAmount().firstOrNull()
        }
    }

    @Composable
    fun getDate(): String? {
        return exchangeRatesResponse.value?.date
    }

    @Composable
    fun getHourlyRateInUsd(): String? {
        return hourlyRateInUsd.value
    }

    @Composable
    fun getTaxPercentage(): Double? {
        return taxPercentage.value
    }

    @Composable
    fun getSocialSecurityAmount(): Double? {
        return socialSecurityAmount.value
    }

    @Composable
    fun getCompanyExpensesAmount(): Double? {
        return companyExpensesAmount.value
    }

    @Composable
    fun getMonthlyBgnGrossSalary(): Double? {
        val usdToBgn = currencyConverter.exchangeUsdToBgn(
            exchangeRatesResponse.value,
            (hourlyRateInUsd.value ?: "").toDoubleOrNull()
        ) ?: 0.0

        val monthlyGrossSalaryInBgn = monthlyGrossSalaryCalculator.calculateMonthlyGrossSalary(
            hourlyRate = usdToBgn,
            monthlyHours = monthlyGrossSalaryCalculator.monthlyHours
        ).toString()

        return monthlyGrossSalaryInBgn.toDoubleOrNull()
    }

    @Composable
    fun getMonthlyBgnNetSalary(): Double {
        val income = getMonthlyBgnGrossSalary()

        return monthlyNetSalaryCalculator.calculateMonthlyNetSalary(
            income = income ?: 0.0,
            taxAmount = taxCalculator.calculateTaxAmount(
                income = income ?: 0.0,
                socialSecurityAmount = socialSecurityAmount.value,
                companyExpenses = companyExpensesAmount.value,
                taxPercentage = taxPercentage.value
            ),
            companyExpensesAmount = companyExpensesAmount.value ?: 0.0,
            socialSecurityAmount = socialSecurityAmount.value ?: 0.0
        )
    }

    @Composable
    fun getYearlyNetSalary(): Double {
        return getMonthlyBgnNetSalary() * 12
    }

    fun onChangeHourlyRateInUsd(newRate: String) {
        hourlyRateInUsd.value = newRate

        viewModelScope.launch {
            homeDataStore.editHourlyRate(newRate)
        }
    }

    fun onChangeTaxPercentage(newTaxPercentage: Double) {
        taxPercentage.value = newTaxPercentage

        viewModelScope.launch {
            homeDataStore.editTaxPercentage(newTaxPercentage)
        }
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
}