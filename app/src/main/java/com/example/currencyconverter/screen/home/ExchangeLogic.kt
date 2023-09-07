package com.example.currencyconverter.screen.home

import javax.inject.Inject

class ExchangeLogic @Inject constructor(
) {
    fun exchangeUsdToBgn(
        exchangeRatesResponse: ExchangeRatesDataSource.ExchangeRatesResponse?,
        hourlyRateInUsd: String
    ): Double {
        val eurToUsd = exchangeRatesResponse?.eur?.usd ?: 1.0
        val eurToBgn = exchangeRatesResponse?.eur?.bgn ?: 1.0
        val usdHourlyRateToEur = (hourlyRateInUsd.toDoubleOrNull() ?: 1.0) / eurToUsd

        return usdHourlyRateToEur * eurToBgn
    }
}