package com.example.currencyconverter.utility

fun calculateMonthlySalaryInUsd(hourlyRate: Int, monthlyHours: Double): Double {
    return hourlyRate * monthlyHours
}