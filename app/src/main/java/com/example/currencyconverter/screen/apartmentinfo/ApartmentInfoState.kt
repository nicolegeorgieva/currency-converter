package com.example.currencyconverter.screen.apartmentinfo

data class ApartmentInfoState(
    val m2PriceEur: String,
    val totalM2: String,
    val realM2: String,
    val realM2Price: String,
    val realM2PriceCurrency: ApartmentInfoCurrency,
    val isRealM2PriceCurrencyExpanded: Boolean,
    val totalM2Price: String,
    val totalM2PriceCurrency: ApartmentInfoCurrency,
    val isTotalM2PriceCurrencyExpanded: Boolean
)

enum class ApartmentInfoCurrency {
    EUR,
    BGN
}
