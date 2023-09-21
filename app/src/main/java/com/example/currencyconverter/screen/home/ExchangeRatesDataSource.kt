package com.example.currencyconverter.screen.home

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import javax.inject.Inject

class ExchangeRatesDataSource @Inject constructor(
    private val client: HttpClient
) {
    private val apiUrl =
        "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/eur.json"

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
        return try {
            withContext(Dispatchers.IO) {
                val response = client.get(apiUrl)

                response.body<ExchangeRatesResponse>()
            }
        } catch (e: Exception) {
            // TODO - Handle that case
            ExchangeRatesResponse(date = "", eur = Rates(bgn = 1.0, usd = 1.0))
        }
    }
}
