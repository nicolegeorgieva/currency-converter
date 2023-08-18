package com.example.currencyconverter.utility

const val HOURSPERWEEK = 40
const val WEEKSPERYEAR = 52
const val MONTHSPERYEAR = 12

fun calculateMonthlyHours(hoursPerWeek: Int, weeksPerYear: Int, monthsPerYear: Int): Double {
    return ((hoursPerWeek * weeksPerYear) / monthsPerYear).toDouble()
}