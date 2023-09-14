package com.example.currencyconverter.screen.apartmentinfo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.ComposeViewModel
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
) : ComposeViewModel<ApartmentInfoState, ApartmentInfoEvent>() {
    private val exchangeRatesResponse =
        mutableStateOf<ExchangeRatesDataSource.ExchangeRatesResponse?>(null)
    private val m2PriceEur = mutableStateOf("")
    private val totalM2 = mutableStateOf("")
    private val realM2 = mutableStateOf("")
    private val realM2PriceCurrency =
        mutableStateOf<ApartmentInfoCurrency>(ApartmentInfoCurrency.EUR)
    private val totalM2PriceCurrency =
        mutableStateOf<ApartmentInfoCurrency>(ApartmentInfoCurrency.EUR)
    private val realM2PriceCurrencyExpanded = mutableStateOf(false)
    private val totalM2PriceCurrencyExpanded = mutableStateOf(false)

    @Composable
    override fun uiState(): ApartmentInfoState {
        return ApartmentInfoState(
            m2PriceEur = getM2PriceEur(),
            totalM2 = getTotalM2(),
            realM2 = getRealM2(),
            realM2Price = getRealM2Price(),
            realM2PriceCurrency = getRealM2PriceCurrency(),
            isRealM2PriceCurrencyExpanded = getRealPriceCurrencyExpanded()
                    totalM2Price = getTotalM2Price (),
            totalM2PriceCurrency = getTotalM2PriceCurrency(),
            isTotalM2PriceCurrencyExpanded = getTotalPriceCurrencyExpanded()
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
    private fun getRealPriceCurrencyExpanded(): Boolean {
        return realM2PriceCurrencyExpanded.value
    }

    @Composable
    private fun getTotalPriceCurrencyExpanded(): Boolean {
        return totalM2PriceCurrencyExpanded.value
    }

    @Composable
    private fun getRealM2Price(): String {
        val totalM2Price = getTotalM2Price().replace(",", "")

        val price = apartmentPriceCalculator.calculateRealM2Price(
            totalM2Price = totalM2Price,
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

        if (res == 0.0) return ""
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

        if (res == 0.0) return ""
        return formatter.format(res)
    }

    @Composable
    private fun getTotalM2PriceCurrency(): ApartmentInfoCurrency {
        return totalM2PriceCurrency.value
    }

    override fun onEvent(event: ApartmentInfoEvent) {
        when (event) {
            is ApartmentInfoEvent.OnM2PriceEurChange -> onM2PriceEurChange(event.newM2PriceEur)
            is ApartmentInfoEvent.OnRealM2Change -> onRealM2Change(event.newRealM2)
            is ApartmentInfoEvent.OnRealPriceCurrencySet -> onRealPriceCurrencySet(event.currency)
            ApartmentInfoEvent.OnStart -> onStart()
            is ApartmentInfoEvent.OnTotalM2Change -> onTotalM2Change(event.newTotalM2)
            is ApartmentInfoEvent.OnTotalPriceCurrencySet -> onTotalPriceCurrencySet(event.currency)
            is ApartmentInfoEvent.OnRealM2ExpandedChange -> onRealPriceCurrencyExpandedChange(event.expanded)
            is ApartmentInfoEvent.OnTotalM2ExpandedChange -> onTotalPriceCurrencyExpandedChange(
                event.expanded
            )
        }
    }

    private fun onStart() {
        viewModelScope.launch {
            exchangeRatesResponse.value = currencyRequest.fetchExchangeRates()
        }
    }

    private fun onM2PriceEurChange(newM2PriceEur: String) {
        m2PriceEur.value = newM2PriceEur
    }

    private fun onTotalM2Change(newTotalM2: String) {
        totalM2.value = newTotalM2
    }

    private fun onRealPriceCurrencyExpandedChange(isExpanded: Boolean) {
        realM2PriceCurrencyExpanded.value = isExpanded
    }

    private fun onTotalPriceCurrencyExpandedChange(isExpanded: Boolean) {
        totalM2PriceCurrencyExpanded.value = isExpanded
    }

    private fun onRealM2Change(newRealM2: String) {
        realM2.value = newRealM2
    }

    private fun onRealPriceCurrencySet(currency: ApartmentInfoCurrency) {
        realM2PriceCurrency.value = when (currency) {
            ApartmentInfoCurrency.EUR -> ApartmentInfoCurrency.EUR
            ApartmentInfoCurrency.BGN -> ApartmentInfoCurrency.BGN
        }
    }

    private fun onTotalPriceCurrencySet(currency: ApartmentInfoCurrency) {
        totalM2PriceCurrency.value = when (currency) {
            ApartmentInfoCurrency.EUR -> ApartmentInfoCurrency.EUR
            ApartmentInfoCurrency.BGN -> ApartmentInfoCurrency.BGN
        }
    }
}