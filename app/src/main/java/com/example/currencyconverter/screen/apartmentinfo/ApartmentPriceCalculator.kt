package com.example.currencyconverter.screen.apartmentinfo

import javax.inject.Inject

class ApartmentPriceCalculator @Inject constructor() {
    fun calculateRealM2Price(totalM2Price: String, realM2: String): Double {
        return (totalM2Price.toDoubleOrZero()) / (realM2.toDoubleOrZero())
    }

    fun calculateTotalM2Price(eurPerM2: String, totalM2: String): Double {
        return eurPerM2.toDoubleOrZero() * totalM2.toDoubleOrZero()
    }

    fun String.toDoubleOrZero(): Double {
        return this.toDoubleOrNull() ?: 0.0
    }
}