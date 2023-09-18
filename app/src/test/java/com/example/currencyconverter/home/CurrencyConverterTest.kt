package com.example.currencyconverter.home

import com.example.currencyconverter.screen.home.CurrencyConverter
import com.example.currencyconverter.screen.home.CurrencyOptions
import com.example.currencyconverter.screen.home.ExchangeRatesDataSource
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class CurrencyConverterTest : FreeSpec({
    val currencyConverter = CurrencyConverter()
    val exchangeRatesResponse = ExchangeRatesDataSource.ExchangeRatesResponse(
        date = "06.09.2023",
        eur = ExchangeRatesDataSource.Rates(
            1.95,
            1.07
        )
    )

    "100$ should be ~182.0 BGN" {
        currencyConverter.exchangeCurrencies(
            exchangeRatesResponse = exchangeRatesResponse,
            from = CurrencyOptions.USD,
            to = CurrencyOptions.BGN,
            amount = 100.0
        ) shouldBe 182.24299065420558
    }

    "Should return null for exchangeRatesResponse null" {
        currencyConverter.exchangeCurrencies(
            exchangeRatesResponse = null,
            from = CurrencyOptions.BGN,
            to = CurrencyOptions.USD,
            amount = 100.0
        ) shouldBe null
    }
})