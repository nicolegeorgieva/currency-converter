package com.example.currencyconverter.screen.apartmentinfo

import javax.inject.Inject

class ApartmentPriceCalculator @Inject constructor() {
    fun calculateTotalM2Price(eurPerM2: String, totalM2: String): Double {
        return eurPerM2.toDoubleOrZero() * totalM2.toDoubleOrZero()
    }

    fun String.toDoubleOrZero(): Double {
        return this.toDoubleOrNull() ?: 0.0
    }

    fun calculateTotalRealM2Price(eurPerM2: String, realM2: String): Double {
        return eurPerM2.toDoubleOrZero() * realM2.toDoubleOrZero()
    }
}