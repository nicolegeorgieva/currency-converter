package com.example.currencyconverter.screen.apartmentinfo

data class ApartmentInfoUi(
    val m2PriceEur: String,
    val totalM2: String,
    val realM2: String,
    val realM2Price: String,
    val realM2PriceCurrency: String,
    val isRealM2PriceCurrencyExpanded: Boolean,
    val totalM2Price: String,
    val totalM2PriceCurrency: String,
    val isTotalM2PriceCurrencyExpanded: Boolean
)
