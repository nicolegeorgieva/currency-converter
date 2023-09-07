package com.example.currencyconverter.screen.home

import javax.inject.Inject

class CurrencyConverter @Inject constructor(
) {
    fun exchangeUsdToBgn(
        exchangeRatesResponse: ExchangeRatesDataSource.ExchangeRatesResponse?,
        hourlyRateInUsd: Double?
    ): Double? {
        if (hourlyRateInUsd == null) return null
        val eurToUsd = exchangeRatesResponse?.eur?.usd ?: return null

        val eurToBgn = exchangeRatesResponse.eur.bgn
        val usdHourlyRateToEur = hourlyRateInUsd / eurToUsd

        return usdHourlyRateToEur * eurToBgn
    }
}