package com.example.currencyconverter.screen.home

import android.util.Log
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
            socialSecurityAmount.value =
                homeDataStore.getSocialSecurityAmount().firstOrNull()
            companyExpensesAmount.value =
                homeDataStore.getCompanyExpensesAmount().firstOrNull()
            monthlyGrossSalaryInBgn.value =
                homeDataStore.getMonthlyGrossSalary().firstOrNull()
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
        ) ?: 0.0

        monthlyGrossSalaryInBgn.value = monthlyGrossSalaryCalculator.calculateMonthlyGrossSalary(
            hourlyRate = usdToBgn,
            monthlyHours = monthlyGrossSalaryCalculator.monthlyHours
        ).toString()

        val income = monthlyGrossSalaryInBgn.value?.toDoubleOrNull()

        monthlyNetSalaryInBgn.value = monthlyNetSalaryCalculator.calculateMonthlyNetSalary(
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

        viewModelScope.launch {
            homeDataStore.editHourlyRate(newRate)
            homeDataStore.editMonthlyGrossSalary(
                monthlyGrossSalaryInBgn.value.toString()
            )
            homeDataStore.editMonthlyNetSalary(
                monthlyNetSalaryInBgn.value ?: 0.0
            )
        }
    }

    fun onChangeTaxPercentage(newTaxPercentage: Double) {
        taxPercentage.value = newTaxPercentage

        val income = monthlyGrossSalaryInBgn.value?.toDoubleOrNull()

        monthlyNetSalaryInBgn.value = monthlyNetSalaryCalculator.calculateMonthlyNetSalary(
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

        viewModelScope.launch {
            homeDataStore.editTaxPercentage(newTaxPercentage)
            homeDataStore.editMonthlyNetSalary(
                monthlyNetSalaryInBgn.value ?: 0.0
            )
        }
    }

    fun onChangeSocialSecurityAmount(newSocialSecurityAmount: Double) {
        socialSecurityAmount.value = newSocialSecurityAmount

        val income = monthlyGrossSalaryInBgn.value?.toDoubleOrNull()
        val taxAmount = taxCalculator.calculateTaxAmount(
            income = income ?: 0.0,
            socialSecurityAmount = socialSecurityAmount.value,
            companyExpenses = companyExpensesAmount.value,
            taxPercentage = taxPercentage.value
        )

        monthlyNetSalaryInBgn.value = monthlyNetSalaryCalculator.calculateMonthlyNetSalary(
            income = income ?: 0.0,
            taxAmount = taxAmount,
            companyExpensesAmount = companyExpensesAmount.value ?: 0.0,
            socialSecurityAmount = socialSecurityAmount.value ?: 0.0
        )

        Log.i("netSalary", "Income: $income")
        Log.i("netSalary", "Tax: $taxAmount")
        Log.i("netSalary", "Comp.exp.amount: ${companyExpensesAmount.value}")
        Log.i("netSalary", "SocialSecurityAmount: ${socialSecurityAmount.value}")
        Log.i("netSalary", "M.net.s.in bgn: ${monthlyNetSalaryInBgn.value}")

        viewModelScope.launch {
            homeDataStore.editSocialSecurityAmount(newSocialSecurityAmount)
            homeDataStore.editMonthlyNetSalary(
                monthlyNetSalaryInBgn.value ?: 0.0
            )
        }
    }

    fun onChangeCompanyExpensesAmount(newCompanyExpensesAmount: Double) {
        companyExpensesAmount.value = newCompanyExpensesAmount

        val income = monthlyGrossSalaryInBgn.value?.toDoubleOrNull()

        monthlyNetSalaryInBgn.value = monthlyNetSalaryCalculator.calculateMonthlyNetSalary(
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

        viewModelScope.launch {
            homeDataStore.editCompanyExpensesAmount(newCompanyExpensesAmount)
            homeDataStore.editMonthlyNetSalary(
                monthlyNetSalaryInBgn.value ?: 0.0
            )
        }
    }
}