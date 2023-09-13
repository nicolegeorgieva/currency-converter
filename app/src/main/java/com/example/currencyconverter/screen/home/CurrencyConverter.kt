package com.example.currencyconverter.screen.home

import javax.inject.Inject

class CurrencyConverter @Inject constructor(
) {
    fun exchangeUsdToBgn(
        exchangeRatesResponse: ExchangeRatesDataSource.ExchangeRatesResponse?,
        amount: Double?
    ): Double? {
        if (amount == null) return null
        val eurToUsd = exchangeRatesResponse?.eur?.usd ?: return null

        val eurToBgn = exchangeRatesResponse.eur.bgn
        val usdHourlyRateToEur = amount / eurToUsd

        return usdHourlyRateToEur * eurToBgn
    }

    fun exchangeEurToBgn(
        exchangeRatesResponse: ExchangeRatesDataSource.ExchangeRatesResponse?,
        amount: Double?
    ): Double? {
        if (amount == null || exchangeRatesResponse == null) return null
        val eurToBgn = exchangeRatesResponse.eur.bgn

        return amount * eurToBgn
    }
}