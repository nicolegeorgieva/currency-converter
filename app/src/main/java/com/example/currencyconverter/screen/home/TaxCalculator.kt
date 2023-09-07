package com.example.currencyconverter.screen.home

import javax.inject.Inject

class TaxCalculator @Inject constructor() {
    fun calculateTaxAmount(
        income: Double,
        socialSecurityAmount: Double,
        companyExpenses: Double,
        taxPercentage: Double
    ): Double {
        return (income - socialSecurityAmount - companyExpenses) * taxPercentage
    }
}