package com.example.currencyconverter.screen.home

import javax.inject.Inject

class MonthlyHoursProvider @Inject constructor() {
    private val hoursPerWeek = 40
    private val weeksPerYear = 52
    private val monthsPerYear = 12

    val monthlyHours = calculateMonthlyHours(hoursPerWeek, weeksPerYear, monthsPerYear)

    private fun calculateMonthlyHours(
        hoursPerWeek: Int,
        weeksPerYear: Int,
        monthsPerYear: Int
    ): Double {
        return ((hoursPerWeek.toDouble() * weeksPerYear) / monthsPerYear)
    }
}