package com.example.currencyconverter.home

import com.example.currencyconverter.screen.home.CurrencyConverter
import com.example.currencyconverter.screen.home.ExchangeRatesDataSource
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class CurrencyConverterTest : FreeSpec({
    val currencyConverter = CurrencyConverter()

    "100$ should be ~182.0 BGN" {
        currencyConverter.exchangeUsdToBgn(
            ExchangeRatesDataSource.ExchangeRatesResponse(
                date = "06.09.2023",
                eur = ExchangeRatesDataSource.Rates(
                    1.95,
                    1.07
                )
            ),
            100.0
        ) shouldBe 182.24299065420558
    }

    "null arguments" - {
        "Should return null for 2 null arguments" {
            currencyConverter.exchangeUsdToBgn(
                null,
                null
            ) shouldBe null
        }

        "Should return null for null exchangeRatesResponse" {
            currencyConverter.exchangeUsdToBgn(
                null,
                50.0
            ) shouldBe null
        }

        "Should return null for null hourlyRateInUsd" {
            currencyConverter.exchangeUsdToBgn(
                ExchangeRatesDataSource.ExchangeRatesResponse(
                    date = "06.09.2023",
                    eur = ExchangeRatesDataSource.Rates(
                        1.95,
                        1.07
                    )
                ),
                null
            ) shouldBe null
        }
    }

})