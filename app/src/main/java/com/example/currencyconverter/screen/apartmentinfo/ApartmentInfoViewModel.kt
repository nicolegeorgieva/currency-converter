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
    private val totalM2Price = mutableStateOf("")
    private val totalM2PriceCurrency = mutableStateOf("")
    private val realM2Price = mutableStateOf("")
    private val totalRealM2PriceCurrency = mutableStateOf("")

    @Composable
    fun uiState(): ApartmentInfoUi {
        return ApartmentInfoUi(
            m2PriceEur = getM2PriceEur(),
            totalM2 = getTotalM2(),
            realM2 = getRealM2(),
            totalM2Price = getTotalM2Price(),
            totalM2PriceCurrency = getTotalM2PriceCurrency(),
            realM2Price = getRealM2Price(),
            realM2PriceCurrency = getRealM2PriceCurrency()
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
    private fun getTotalM2Price(): String {
        return totalM2Price.value
    }

    @Composable
    private fun getTotalM2PriceCurrency(): String {
        return totalM2PriceCurrency.value
    }

    @Composable
    private fun getRealM2Price(): String {
        return realM2Price.value
    }

    @Composable
    private fun getRealM2PriceCurrency(): String {
        return totalRealM2PriceCurrency.value
    }

    fun onM2PriceEurChange(newM2PriceEur: String) {
        m2PriceEur.value = newM2PriceEur
    }

    fun onTotalM2Change(newTotalM2: String) {
        m2PriceEur.value = newTotalM2
    }

    fun onRealM2Change(newRealM2: String) {
        m2PriceEur.value = newRealM2
    }
}