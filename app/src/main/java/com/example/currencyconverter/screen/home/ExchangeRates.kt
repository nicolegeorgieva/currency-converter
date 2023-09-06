package com.example.currencyconverter.screen.home

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.Serializable
import javax.inject.Inject

class ExchangeRates @Inject constructor(
    private val client: HttpClient
) {
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

        return response.body<ExchangeRatesResponse>()
    }
}
