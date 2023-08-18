package com.example.currencyconverter.request

import kotlinx.serialization.Serializable

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