package com.example.currencyconverter.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.text.DecimalFormat
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
    private val hourlyRateInUsd = mutableStateOf<String?>(null)
    private val exchangeRatesResponse =
        mutableStateOf<ExchangeRatesDataSource.ExchangeRatesResponse?>(null)
    private val taxPercentage = mutableStateOf<String?>(null)
    private val socialSecurityAmount = mutableStateOf<String?>(null)
    private val companyExpensesAmount = mutableStateOf<String?>(null)

    fun onStart() {
        viewModelScope.launch {
            exchangeRatesResponse.value = exchangeRatesDataSource.fetchExchangeRates()
            hourlyRateInUsd.value = homeDataStore.getHourlyRate().firstOrNull()?.toString()
            taxPercentage.value = homeDataStore.getTaxPercentage().firstOrNull()?.toString()
            socialSecurityAmount.value =
                homeDataStore.getSocialSecurityAmount().firstOrNull()?.toString()
            companyExpensesAmount.value =
                homeDataStore.getCompanyExpensesAmount().firstOrNull()?.toString()
        }
    }

    private fun Double?.formatAmount(): String {
        val formatter = DecimalFormat("###,###.00")

        return if (this == null || this == 0.0) "" else formatter.format(this)
    }

    private fun Double?.toCustomString(): String {
        return if (this == null || this == 0.0) {
            ""
        } else {
            this.toString()
        }
    }

    @Composable
    fun getUiState(): HomeUiState {
        return HomeUiState(
            date = getDate(),
            hourlyRateUsd = getHourlyRateInUsd() ?: "",
            taxPercentage = getTaxPercentage() ?: "",
            socialSecurityAmount = getSocialSecurityAmount() ?: "",
            companyExpensesAmount = getCompanyExpensesAmount() ?: "",
            monthlyGrossSalary = getMonthlyBgnGrossSalary().formatAmount(),
            monthlyNetSalary = getMonthlyBgnNetSalary().formatAmount(),
            yearlyNetSalary = getYearlyNetSalary().formatAmount()
        )
    }

    @Composable
    private fun getDate(): String? {
        return exchangeRatesResponse.value?.date
    }

    @Composable
    private fun getHourlyRateInUsd(): String? {
        return hourlyRateInUsd.value
    }

    @Composable
    private fun getTaxPercentage(): String? {
        return taxPercentage.value
    }

    @Composable
    private fun getSocialSecurityAmount(): String? {
        return socialSecurityAmount.value
    }

    @Composable
    private fun getCompanyExpensesAmount(): String? {
        return companyExpensesAmount.value
    }

    @Composable
    private fun getMonthlyBgnGrossSalary(): Double? {
        val usdToBgn = currencyConverter.exchangeUsdToBgn(
            exchangeRatesResponse.value,
            hourlyRateInUsd.value?.toDoubleOrNull() ?: 0.0
        ) ?: 0.0

        val monthlyGrossSalaryInBgn = monthlyGrossSalaryCalculator.calculateMonthlyGrossSalary(
            hourlyRate = usdToBgn,
            monthlyHours = monthlyGrossSalaryCalculator.monthlyHours
        ).toString()

        return monthlyGrossSalaryInBgn.toDoubleOrNull()
    }

    @Composable
    private fun getMonthlyBgnNetSalary(): Double {
        val income = getMonthlyBgnGrossSalary()

        return monthlyNetSalaryCalculator.calculateMonthlyNetSalary(
            income = income ?: 0.0,
            taxAmount = taxCalculator.calculateTaxAmount(
                income = income ?: 0.0,
                socialSecurityAmount = socialSecurityAmount.value?.toDoubleOrNull() ?: 0.0,
                companyExpenses = companyExpensesAmount.value?.toDoubleOrNull() ?: 0.0,
                taxPercentage = taxPercentage.value?.toDoubleOrNull() ?: 0.0
            ),
            companyExpensesAmount = companyExpensesAmount.value?.toDoubleOrNull() ?: 0.0,
            socialSecurityAmount = socialSecurityAmount.value?.toDoubleOrNull() ?: 0.0
        )
    }

    @Composable
    private fun getYearlyNetSalary(): Double {
        return getMonthlyBgnNetSalary() * 12
    }

    fun onChangeHourlyRateInUsd(newRate: String) {
        val newHourlyRate = newRate.toDoubleOrNull() ?: 0.0

        hourlyRateInUsd.value = newHourlyRate.toString()

        viewModelScope.launch {
            homeDataStore.editHourlyRate(newHourlyRate)
        }
    }

    fun onChangeTaxPercentage(newTaxPercentage: Double) {
        taxPercentage.value = newTaxPercentage.toString()

        viewModelScope.launch {
            homeDataStore.editTaxPercentage(newTaxPercentage)
        }
    }

    fun onChangeSocialSecurityAmount(newSocialSecurityAmount: Double) {
        socialSecurityAmount.value = newSocialSecurityAmount.toString()

        viewModelScope.launch {
            homeDataStore.editSocialSecurityAmount(newSocialSecurityAmount)
        }
    }

    fun onChangeCompanyExpensesAmount(newCompanyExpensesAmount: Double) {
        companyExpensesAmount.value = newCompanyExpensesAmount.toString()

        viewModelScope.launch {
            homeDataStore.editCompanyExpensesAmount(newCompanyExpensesAmount)
        }
    }
}