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

    fun onStart() {
        viewModelScope.launch {
            exchangeRatesResponse.value = exchangeRatesDataSource.fetchExchangeRates()
        }
    }

    fun getDate(): String? {
        return exchangeRatesResponse.value?.date
    }

    @Composable
    fun getHourlyRateInUsd(): String {
        hourlyRateInUsd.value = remember { homeDataStore.getHourlyRate() }
            .collectAsState(initial = "").value
        return hourlyRateInUsd.value
    }

    fun onChangeHourlyRateInUsd(newRate: String) {
        hourlyRateInUsd.value = newRate
        val usdToBgn = currencyConverter.exchangeUsdToBgn(
            exchangeRatesResponse.value,
            hourlyRateInUsd.value.toDoubleOrNull()
        )

        monthlyGrossSalaryInBgn.value = monthlyGrossSalaryCalculator.calculateMonthlyGrossSalary(
            usdToBgn,
            monthlyGrossSalaryCalculator.monthlyHours
        ).toString()

        val monthlyNetSalary = monthlyNetSalaryCalculator.calculateMonthlyNetSalary(
            income = monthlyGrossSalaryInBgn.value.toDoubleOrNull() ?: 0.0,
            taxAmount = taxCalculator.calculateTaxAmount(
                income = monthlyGrossSalaryInBgn.value.toDoubleOrNull() ?: 0.0,
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

    @Composable
    fun getTaxPercentage(): Double? {
        taxPercentage.value = remember { homeDataStore.getTaxPercentage() }
            .collectAsState(initial = 0.0).value

        return taxPercentage.value
    }

    @Composable
    fun getSocialSecurityAmount(): Double? {
        socialSecurityAmount.value = remember { homeDataStore.getSocialSecurityAmount() }
            .collectAsState(initial = 0.0).value
        return socialSecurityAmount.value
    }

    @Composable
    fun getCompanyExpensesAmount(): Double? {
        companyExpensesAmount.value = remember { homeDataStore.getCompanyExpensesAmount() }
            .collectAsState(initial = 0.0).value
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

    @Composable
    fun getMontlyBgnGrossSalary(): Double {
        monthlyGrossSalaryInBgn.value = remember { homeDataStore.getMonthlyGrossSalary() }
            .collectAsState(initial = "").value
        return monthlyGrossSalaryInBgn.value.toDoubleOrNull() ?: 1.0
    }

    @Composable
    fun getMonthlyBgnNetSalary(): Double {
        monthlyNetSalaryInBgn.value = remember { homeDataStore.getMonthlyNetSalary() }
            .collectAsState(initial = 0.0).value
        return monthlyNetSalaryInBgn.value ?: 1.0
    }
}