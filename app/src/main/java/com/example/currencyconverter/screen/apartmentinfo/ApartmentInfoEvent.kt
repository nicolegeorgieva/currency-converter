package com.example.currencyconverter.screen.apartmentinfo

sealed interface ApartmentInfoEvent {
    object OnStart : ApartmentInfoEvent
    data class OnM2PriceEurChange(val newM2PriceEur: String) : ApartmentInfoEvent
    data class OnTotalM2Change(val newTotalM2: String) : ApartmentInfoEvent
    data class OnRealM2Change(val newRealM2: String) : ApartmentInfoEvent
    data class OnRealM2ExpandedChange(val expanded: Boolean) : ApartmentInfoEvent
    data class OnRealPriceCurrencySet(val currency: ApartmentInfoCurrency) : ApartmentInfoEvent
    data class OnTotalPriceCurrencySet(val currency: ApartmentInfoCurrency) : ApartmentInfoEvent
    data class OnTotalM2ExpandedChange(val expanded: Boolean) : ApartmentInfoEvent
}