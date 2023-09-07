package com.example.currencyconverter.screen.home

import javax.inject.Inject

class MonthlyNetSalaryCalculator @Inject constructor(
    private val taxCalculator: TaxCalculator
) {
    fun calculateMonthlyNetSalary(
        income: Double,
        taxAmount: Double,
        companyExpensesAmount: Double,
        socialSecurityAmount: Double
    ): Double {
        return income - taxAmount - companyExpensesAmount - socialSecurityAmount
    }
}