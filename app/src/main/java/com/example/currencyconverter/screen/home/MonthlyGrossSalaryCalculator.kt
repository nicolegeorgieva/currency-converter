package com.example.currencyconverter.screen.home

import javax.inject.Inject

class MonthlyGrossSalaryCalculator @Inject constructor() {
    private val hoursPerWeek = 40.0
    private val weeksPerYear = 52
    private val monthsPerYear = 12

    val monthlyHours = (hoursPerWeek * weeksPerYear) / monthsPerYear

    fun calculateMonthlyGrossSalary(hourlyRate: Double?, monthlyHours: Double): Double {
        return (hourlyRate ?: 1.0) * monthlyHours
    }
}