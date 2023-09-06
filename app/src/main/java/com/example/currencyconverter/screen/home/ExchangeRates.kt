package com.example.currencyconverter.screen.home

import com.example.currencyconverter.request.client
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.date
import kotlinx.serialization.Serializable
import java.util.Date
import javax.inject.Inject

class ExchangeRates @Inject constructor() {
    @Serializable
    data class ExchangeRatesResponse(
        val date: String,
        val eur: Rates
    )

    @Serializable
    data class Rates(
        val bgn: Double,
        val usd: Double
    )

    suspend fun fetchExchangeRates(): ExchangeRatesResponse {
        val response =
            client.get("https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/eur.json")

        val rates = response.body<ExchangeRatesResponse>()

        return rates
    }

    suspend fun getDate(): Date? {
        val response =
            client.get("https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/eur.json")

        val date = response.date()

        return date
    }
}
