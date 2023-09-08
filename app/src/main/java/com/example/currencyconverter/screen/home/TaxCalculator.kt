package com.example.currencyconverter.screen.home

import javax.inject.Inject

class TaxCalculator @Inject constructor() {
    fun calculateTaxAmount(
        income: Double,
        socialSecurityAmount: Double?,
        companyExpenses: Double?,
        taxPercentage: Double?
    ): Double {
        val percentageParser = if (taxPercentage != null) {
            1 - (taxPercentage / 100)
        } else 1.0

        return (income - (socialSecurityAmount ?: 0.0) - (companyExpenses
            ?: 0.0) * percentageParser)
    }
}