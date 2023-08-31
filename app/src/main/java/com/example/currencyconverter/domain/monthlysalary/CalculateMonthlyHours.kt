package com.example.currencyconverter.domain.monthlysalary

const val HOURSPERWEEK = 40
const val WEEKSPERYEAR = 52
const val MONTHSPERYEAR = 12

val monthlyHours = calculateMonthlyHours(HOURSPERWEEK, WEEKSPERYEAR, MONTHSPERYEAR)

fun calculateMonthlyHours(hoursPerWeek: Int, weeksPerYear: Int, monthsPerYear: Int): Double {
    return ((hoursPerWeek.toDouble() * weeksPerYear) / monthsPerYear)
}