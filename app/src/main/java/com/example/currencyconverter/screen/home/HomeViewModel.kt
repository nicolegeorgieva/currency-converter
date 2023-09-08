package com.example.currencyconverter.screen.home

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
    private val monthlyGrossSalaryInBgn = mutableStateOf<String?>("")
    private val monthlyNetSalaryInBgn = mutableStateOf<Double?>(null)
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
            socialSecurityAmount.value = homeDataStore.getSocialSecurityAmount().firstOrNull()
            companyExpensesAmount.value = homeDataStore.getCompanyExpensesAmount().firstOrNull()
            monthlyGrossSalaryInBgn.value = homeDataStore.getMonthlyGrossSalary().firstOrNull()
            monthlyNetSalaryInBgn.value = homeDataStore.getMonthlyNetSalary().firstOrNull()
        }
    }

    fun getDate(): String? {
        return exchangeRatesResponse.value?.date
    }

    fun getHourlyRateInUsd(): String? {
        return hourlyRateInUsd.value
    }

    fun getTaxPercentage(): Double? {
        return taxPercentage.value
    }

    fun getSocialSecurityAmount(): Double? {
        return socialSecurityAmount.value
    }

    fun getCompanyExpensesAmount(): Double? {
        return companyExpensesAmount.value
    }

    fun getMonthlyBgnGrossSalary(): Double? {
        return monthlyGrossSalaryInBgn.value?.toDoubleOrNull()
    }

    fun getMonthlyBgnNetSalary(): Double? {
        return monthlyNetSalaryInBgn.value
    }

    fun onChangeHourlyRateInUsd(newRate: String) {
        hourlyRateInUsd.value = newRate
        val usdToBgn = currencyConverter.exchangeUsdToBgn(
            exchangeRatesResponse.value,
            (hourlyRateInUsd.value ?: "").toDoubleOrNull()
        )

        monthlyGrossSalaryInBgn.value = monthlyGrossSalaryCalculator.calculateMonthlyGrossSalary(
            usdToBgn,
            monthlyGrossSalaryCalculator.monthlyHours
        ).toString()

        val monthlyNetSalary = monthlyNetSalaryCalculator.calculateMonthlyNetSalary(
            income = (monthlyGrossSalaryInBgn.value ?: "").toDoubleOrNull() ?: 0.0,
            taxAmount = taxCalculator.calculateTaxAmount(
                income = (monthlyGrossSalaryInBgn.value ?: "").toDoubleOrNull() ?: 0.0,
                socialSecurityAmount = socialSecurityAmount.value ?: 0.0,
                companyExpenses = companyExpensesAmount.value ?: 0.0,
                taxPercentage = taxPercentage.value ?: 0.0
            ),
            companyExpensesAmount = companyExpensesAmount.value ?: 0.0,
            socialSecurityAmount = socialSecurityAmount.value ?: 0.0
        )

        viewModelScope.launch {
            homeDataStore.editHourlyRate(newRate)
            homeDataStore.editMonthlyGrossSalary(
                monthlyGrossSalaryInBgn.toString()
            )
            homeDataStore.editMonthlyNetSalary(monthlyNetSalary)
        }

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
            homeDataStore.editMonthlyNetSalary(
                monthlyNetSalaryCalculator.calculateMonthlyNetSalary(
                    monthlyGrossSalaryInBgn.value?.toDoubleOrNull() ?: 0.0,
                    taxPercentage.value ?: 0.0,
                    companyExpensesAmount.value ?: 0.0,
                    socialSecurityAmount.value ?: 0.0
                )
            )
            monthlyNetSalaryInBgn.value = homeDataStore.getMonthlyNetSalary().firstOrNull()
        }
    }

    fun onChangeSocialSecurityAmount(newSocialSecurityAmount: Double) {
        socialSecurityAmount.value = newSocialSecurityAmount

        viewModelScope.launch {
            homeDataStore.editSocialSecurityAmount(newSocialSecurityAmount)
            homeDataStore.editMonthlyNetSalary(
                monthlyNetSalaryCalculator.calculateMonthlyNetSalary(
                    monthlyGrossSalaryInBgn.value?.toDoubleOrNull() ?: 0.0,
                    taxPercentage.value ?: 0.0,
                    companyExpensesAmount.value ?: 0.0,
                    socialSecurityAmount.value ?: 0.0
                )
            )
            monthlyNetSalaryInBgn.value = homeDataStore.getMonthlyNetSalary().firstOrNull()
        }
    }

    fun onChangeCompanyExpensesAmount(newCompanyExpensesAmount: Double) {
        companyExpensesAmount.value = newCompanyExpensesAmount

        viewModelScope.launch {
            homeDataStore.editCompanyExpensesAmount(newCompanyExpensesAmount)
            homeDataStore.editMonthlyNetSalary(
                monthlyNetSalaryCalculator.calculateMonthlyNetSalary(
                    monthlyGrossSalaryInBgn.value?.toDoubleOrNull() ?: 0.0,
                    taxPercentage.value ?: 0.0,
                    companyExpensesAmount.value ?: 0.0,
                    socialSecurityAmount.value ?: 0.0
                )
            )
            monthlyNetSalaryInBgn.value = homeDataStore.getMonthlyNetSalary().firstOrNull()
        }
    }
}