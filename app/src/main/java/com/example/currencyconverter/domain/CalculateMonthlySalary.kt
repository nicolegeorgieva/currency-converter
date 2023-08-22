package com.example.currencyconverter.domain

fun calculateMonthlySalary(hourlyRate: Double, monthlyHours: Double): Double {
    return hourlyRate * monthlyHours
}