package com.example.currencyconverter.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.ComposeViewModel
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
) : ComposeViewModel<HomeUiState, HomeEvent>() {
    private val hourlyRateInUsd = mutableStateOf<String?>(null)
    private val exchangeRatesResponse =
        mutableStateOf<ExchangeRatesDataSource.ExchangeRatesResponse?>(null)
    private val taxPercentage = mutableStateOf<String?>(null)
    private val socialSecurityAmount = mutableStateOf<String?>(null)
    private val companyExpensesAmount = mutableStateOf<String?>(null)

    @Composable
    override fun uiState(): HomeUiState {
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
            hourlyRateInUsd.value.toDoubleOrZero()
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
                socialSecurityAmount = socialSecurityAmount.value.toDoubleOrZero(),
                companyExpenses = companyExpensesAmount.value.toDoubleOrZero(),
                taxPercentage = taxPercentage.value.toDoubleOrZero()
            ),
            companyExpensesAmount = companyExpensesAmount.value.toDoubleOrZero(),
            socialSecurityAmount = socialSecurityAmount.value.toDoubleOrZero()
        )
    }

    @Composable
    private fun getYearlyNetSalary(): Double {
        return getMonthlyBgnNetSalary() * 12
    }

    override fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnChangeCompanyExpensesAmount ->
                onChangeCompanyExpensesAmount(event.newCompanyExpensesAmount)

            is HomeEvent.OnChangeHourlyRateInUsd -> onChangeHourlyRateInUsd(event.newRate)
            is HomeEvent.OnChangeSocialSecurityAmount ->
                onChangeSocialSecurityAmount(event.newSocialSecurityAmount)

            is HomeEvent.OnChangeTaxPercentage -> onChangeTaxPercentage(event.newTaxPercentage)
            HomeEvent.OnStart -> onStart()
        }
    }

    private fun onStart() {
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

    private fun onChangeHourlyRateInUsd(newRate: String) {
        hourlyRateInUsd.value = newRate

        viewModelScope.launch {
            homeDataStore.editHourlyRate(newRate.toDoubleOrZero())
        }
    }

    private fun onChangeTaxPercentage(newTaxPercentage: String) {
        taxPercentage.value = newTaxPercentage

        viewModelScope.launch {
            homeDataStore.editTaxPercentage(newTaxPercentage.toDoubleOrZero())
        }
    }

    private fun onChangeSocialSecurityAmount(newSocialSecurityAmount: String) {
        socialSecurityAmount.value = newSocialSecurityAmount

        viewModelScope.launch {
            homeDataStore.editSocialSecurityAmount(newSocialSecurityAmount.toDoubleOrZero())
        }
    }

    private fun onChangeCompanyExpensesAmount(newCompanyExpensesAmount: String) {
        companyExpensesAmount.value = newCompanyExpensesAmount

        viewModelScope.launch {
            homeDataStore.editCompanyExpensesAmount(newCompanyExpensesAmount.toDoubleOrZero())
        }
    }

    private fun Double?.formatAmount(): String {
        val formatter = DecimalFormat("###,###.00")

        return if (this == null || this == 0.0) "" else formatter.format(this)
    }

    private fun String?.toDoubleOrZero(): Double {
        return this?.toDoubleOrNull() ?: 0.0
    }
}