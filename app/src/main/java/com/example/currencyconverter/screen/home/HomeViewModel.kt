package com.example.currencyconverter.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    private val monthlyGrossSalaryInBgn = mutableStateOf("")
    private val monthlyNetSalaryInBgn = mutableStateOf<Double?>(null)
    val hourlyRateInUsd = mutableStateOf("")
    private val exchangeRatesResponse =
        mutableStateOf<ExchangeRatesDataSource.ExchangeRatesResponse?>(null)
    private val taxPercentage = mutableStateOf<Double?>(null)
    private val socialSecurityAmount = mutableStateOf<Double?>(null)
    private val companyExpensesAmount = mutableStateOf<Double?>(null)

    private fun fetchExchangeRates() {
        viewModelScope.launch {
            exchangeRatesResponse.value = exchangeRatesDataSource.fetchExchangeRates()
        }
    }

    @Composable
    fun OnStart() {
        fetchExchangeRates()

        hourlyRateInUsd.value = remember { homeDataStore.getHourlyRate() }
            .collectAsState(initial = "").value

        taxPercentage.value = remember { homeDataStore.getTaxPercentage() }
            .collectAsState(initial = 0.0).value

        socialSecurityAmount.value = remember { homeDataStore.getSocialSecurityAmount() }
            .collectAsState(initial = 0.0).value

        companyExpensesAmount.value = remember { homeDataStore.getCompanyExpensesAmount() }
            .collectAsState(initial = 0.0).value

        monthlyGrossSalaryInBgn.value = remember { homeDataStore.getMonthlyGrossSalary() }
            .collectAsState(initial = "").value

        monthlyNetSalaryInBgn.value = remember { homeDataStore.getMonthlyNetSalary() }
            .collectAsState(initial = 0.0).value
    }

    fun getDate(): String? {
        return exchangeRatesResponse.value?.date
    }

    fun getHourlyRateInUsd(): String {
        return hourlyRateInUsd.value
    }

    fun onChangeHourlyRateInUsd(newRate: String) {
        hourlyRateInUsd.value = newRate
        val monthlyGrossSalary = monthlyGrossSalaryCalculator.calculateMonthlyGrossSalary(
            hourlyRateInUsd.value.toDoubleOrNull(),
            monthlyGrossSalaryCalculator.monthlyHours
        )

        val monthlyNetSalary = monthlyNetSalaryCalculator.calculateMonthlyNetSalary(
            income = monthlyGrossSalary,
            taxAmount = taxCalculator.calculateTaxAmount(
                income = monthlyGrossSalary,
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
                monthlyGrossSalary.toString()
            )
            homeDataStore.editMonthlyNetSalary(monthlyNetSalary)
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

    fun getTaxPercentage(): Double? {
        return taxPercentage.value
    }

    fun getSocialSecurity(): Double? {
        return socialSecurityAmount.value
    }

    fun getCompanyExpensesAmount(): Double? {
        return companyExpensesAmount.value
    }

    fun onChangeTaxPercentage(newTaxPercentage: Double) {
        taxPercentage.value = newTaxPercentage

        viewModelScope.launch {
            homeDataStore.editTaxPercentage(newTaxPercentage)
            homeDataStore.editMonthlyNetSalary(
                monthlyNetSalaryCalculator.calculateMonthlyNetSalary(
                    monthlyGrossSalaryInBgn.value.toDoubleOrNull() ?: 0.0,
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
                    monthlyGrossSalaryInBgn.value.toDoubleOrNull() ?: 0.0,
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
                    monthlyGrossSalaryInBgn.value.toDoubleOrNull() ?: 0.0,
                    taxPercentage.value ?: 0.0,
                    companyExpensesAmount.value ?: 0.0,
                    socialSecurityAmount.value ?: 0.0
                )
            )
            monthlyNetSalaryInBgn.value = homeDataStore.getMonthlyNetSalary().firstOrNull()
        }
    }

    fun getMontlyBgnGrossSalary(): Double {
        return monthlyGrossSalaryInBgn.value.toDoubleOrNull() ?: 1.0
    }

    fun getMonthlyBgnNetSalary(): Double {
        return monthlyNetSalaryInBgn.value ?: 1.0
    }
}