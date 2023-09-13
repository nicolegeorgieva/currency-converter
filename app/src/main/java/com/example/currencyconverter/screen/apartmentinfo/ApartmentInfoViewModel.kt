package com.example.currencyconverter.screen.apartmentinfo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.screen.home.CurrencyConverter
import com.example.currencyconverter.screen.home.ExchangeRatesDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import javax.inject.Inject

@HiltViewModel
class ApartmentInfoViewModel @Inject constructor(
    private val currencyRequest: ExchangeRatesDataSource,
    private val currencyConverter: CurrencyConverter,
    private val apartmentPriceCalculator: ApartmentPriceCalculator
) : ViewModel() {
    private val exchangeRatesResponse =
        mutableStateOf<ExchangeRatesDataSource.ExchangeRatesResponse?>(null)
    private val m2PriceEur = mutableStateOf("")
    private val totalM2 = mutableStateOf("")
    private val realM2 = mutableStateOf("")
    val realM2PriceCurrency =
        mutableStateOf<ApartmentInfoCurrency>(ApartmentInfoCurrency.EUR)
    val totalM2PriceCurrency =
        mutableStateOf<ApartmentInfoCurrency>(ApartmentInfoCurrency.EUR)
    val realM2PriceCurrencyExpanded = mutableStateOf(false)
    val totalM2PriceCurrencyExpanded = mutableStateOf(false)

    fun onStart() {
        viewModelScope.launch {
            exchangeRatesResponse.value = currencyRequest.fetchExchangeRates()
        }
    }

    @Composable
    fun uiState(): ApartmentInfoUi {
        return ApartmentInfoUi(
            m2PriceEur = getM2PriceEur(),
            totalM2 = getTotalM2(),
            realM2 = getRealM2(),
            realM2Price = getRealM2Price(),
            realM2PriceCurrency = getRealM2PriceCurrency(),
            totalM2Price = getTotalM2Price(),
            totalM2PriceCurrency = getTotalM2PriceCurrency()
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
        val price = apartmentPriceCalculator.calculateRealM2Price(
            totalM2Price = getTotalM2Price(),
            realM2 = getRealM2()
        )

        val formatter = DecimalFormat("###,###.##")

        val res = when (realM2PriceCurrency.value) {
            ApartmentInfoCurrency.EUR -> when (totalM2PriceCurrency.value) {
                ApartmentInfoCurrency.EUR -> price
                ApartmentInfoCurrency.BGN -> currencyConverter.exchangeBgnToEur(
                    exchangeRatesResponse = exchangeRatesResponse.value,
                    amount = price
                )
            }

            ApartmentInfoCurrency.BGN -> when (totalM2PriceCurrency.value) {
                ApartmentInfoCurrency.EUR -> currencyConverter.exchangeEurToBgn(
                    exchangeRatesResponse = exchangeRatesResponse.value,
                    amount = price
                )

                ApartmentInfoCurrency.BGN -> price
            }
        } ?: 0.0

        return formatter.format(res)
    }

    @Composable
    fun getRealM2PriceCurrency(): ApartmentInfoCurrency {
        return realM2PriceCurrency.value
    }

    @Composable
    fun getTotalM2Price(): String {
        val price = apartmentPriceCalculator.calculateTotalM2Price(
            eurPerM2 = getM2PriceEur(),
            totalM2 = getTotalM2()
        )

        val formatter = DecimalFormat("###,###.##")

        val res = when (totalM2PriceCurrency.value) {
            ApartmentInfoCurrency.EUR -> price
            ApartmentInfoCurrency.BGN -> currencyConverter.exchangeEurToBgn(
                exchangeRatesResponse = exchangeRatesResponse.value,
                amount = price
            )
        } ?: 0.0

        return formatter.format(res)
    }

    @Composable
    private fun getTotalM2PriceCurrency(): ApartmentInfoCurrency {
        return totalM2PriceCurrency.value
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