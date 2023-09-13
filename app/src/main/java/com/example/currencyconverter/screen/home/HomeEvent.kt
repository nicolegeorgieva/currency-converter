package com.example.currencyconverter.screen.home

sealed interface HomeEvent {
    object OnStart : HomeEvent
    data class OnChangeHourlyRateInUsd(val newRate: String) : HomeEvent
    data class OnChangeTaxPercentage(val newTaxPercentage: String) : HomeEvent
    data class OnChangeSocialSecurityAmount(val newSocialSecurityAmount: String) : HomeEvent
    data class OnChangeCompanyExpensesAmount(val newCompanyExpensesAmount: String) : HomeEvent
}