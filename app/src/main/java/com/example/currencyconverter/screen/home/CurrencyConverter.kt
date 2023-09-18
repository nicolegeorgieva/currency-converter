package com.example.currencyconverter.screen.home

import javax.inject.Inject

class CurrencyConverter @Inject constructor(
) {
    fun exchangeCurrencies(
        exchangeRatesResponse: ExchangeRatesDataSource.ExchangeRatesResponse?,
        from: CurrencyOptions,
        to: CurrencyOptions,
        amount: Double
    ): Double? {
        if (exchangeRatesResponse == null) return null

        var fromTo = 0.0

        when (from) {
            CurrencyOptions.EUR -> fromTo = when (to) {
                CurrencyOptions.EUR -> amount
                CurrencyOptions.BGN -> exchangeRatesResponse.eur.bgn
                CurrencyOptions.USD -> exchangeRatesResponse.eur.usd
            }

            CurrencyOptions.BGN -> fromTo = when (to) {
                CurrencyOptions.EUR -> 1 / exchangeRatesResponse.eur.bgn
                CurrencyOptions.BGN -> amount
                CurrencyOptions.USD -> (1 / exchangeRatesResponse.eur.bgn) * exchangeRatesResponse.eur.usd
            }

            CurrencyOptions.USD -> fromTo = when (to) {
                CurrencyOptions.EUR -> 1 / exchangeRatesResponse.eur.usd
                CurrencyOptions.BGN -> (1 / exchangeRatesResponse.eur.usd) * exchangeRatesResponse.eur.bgn
                CurrencyOptions.USD -> amount
            }
        }

        return fromTo * amount
    }
}