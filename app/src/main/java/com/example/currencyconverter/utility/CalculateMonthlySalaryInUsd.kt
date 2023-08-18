package com.example.currencyconverter.utility

fun calculateMonthlySalaryInUsd(hourlyRate: Double, monthlyHours: Double): Double {
    return hourlyRate * monthlyHours
}