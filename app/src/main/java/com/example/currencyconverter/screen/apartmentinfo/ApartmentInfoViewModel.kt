package com.example.currencyconverter.screen.apartmentinfo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ApartmentInfoViewModel @Inject constructor(
    private val apartmentPriceCalculator: ApartmentPriceCalculator
) : ViewModel() {
    private val m2PriceEur = mutableStateOf("")
    private val totalM2 = mutableStateOf("")
    private val realM2 = mutableStateOf("")
    val realM2PriceCurrency =
        mutableStateOf<ApartmentInfoCurrency>(ApartmentInfoCurrency.EUR)
    val totalM2PriceCurrency =
        mutableStateOf<ApartmentInfoCurrency>(ApartmentInfoCurrency.EUR)
    private val realM2PriceCurrencyExpanded = mutableStateOf(false)
    private val totalM2PriceCurrencyExpanded = mutableStateOf(false)

    @Composable
    fun uiState(): ApartmentInfoUi {
        return ApartmentInfoUi(
            m2PriceEur = getM2PriceEur(),
            totalM2 = getTotalM2(),
            realM2 = getRealM2(),
            realM2Price = getRealM2Price(),
            realM2PriceCurrency = getRealM2PriceCurrency(),
            isRealM2PriceCurrencyExpanded = false,
            totalM2Price = getTotalM2Price(),
            totalM2PriceCurrency = getTotalM2PriceCurrency(),
            isTotalM2PriceCurrencyExpanded = false
        )
    }

    @Composable
    private fun getM2PriceEur(): String {
        return m2PriceEur.value
    }

    @Composable
    private fun getTotalM2(): String {
        return totalM2.value
    }

    @Composable
    private fun getRealM2(): String {
        return realM2.value
    }

    @Composable
    private fun getRealM2Price(): String {
        return apartmentPriceCalculator.calculateRealM2Price(
            totalM2Price = getTotalM2Price(),
            realM2 = getRealM2()
        ).toString()
    }

    @Composable
    fun getRealM2PriceCurrency(): ApartmentInfoCurrency {
        return realM2PriceCurrency.value
    }

    @Composable
    private fun getRealM2PriceCurrencyExpandedState(): Boolean {
        return realM2PriceCurrencyExpanded.value
    }

    @Composable
    fun getTotalM2Price(): String {
        return apartmentPriceCalculator.calculateTotalM2Price(
            eurPerM2 = getM2PriceEur(),
            totalM2 = getTotalM2()
        ).toString()
    }

    @Composable
    private fun getTotalM2PriceCurrency(): ApartmentInfoCurrency {
        return totalM2PriceCurrency.value
    }

    @Composable
    private fun getTotalM2PriceCurrencyExpandedState(): Boolean {
        return totalM2PriceCurrencyExpanded.value
    }

    fun onM2PriceEurChange(newM2PriceEur: String) {
        m2PriceEur.value = newM2PriceEur
    }

    fun onTotalM2Change(newTotalM2: String) {
        totalM2.value = newTotalM2
    }

    fun onRealM2Change(newRealM2: String) {
        realM2.value = newRealM2
    }

    fun onRealM2PriceCurrencyExpandedStateChange() {
        realM2PriceCurrencyExpanded.value = true
    }

    fun onTotalM2PriceCurrencyExpandedStateChange() {
        totalM2PriceCurrencyExpanded.value = true
    }

    fun onRealPriceCurrencySet(currency: ApartmentInfoCurrency) {
        realM2PriceCurrency.value = when (currency) {
            ApartmentInfoCurrency.EUR -> ApartmentInfoCurrency.EUR
            ApartmentInfoCurrency.BGN -> ApartmentInfoCurrency.BGN
        }
    }

    fun onTotalPriceCurrencySet(currency: ApartmentInfoCurrency) {
        totalM2PriceCurrency.value = when (currency) {
            ApartmentInfoCurrency.EUR -> ApartmentInfoCurrency.EUR
            ApartmentInfoCurrency.BGN -> ApartmentInfoCurrency.BGN
        }
    }
}