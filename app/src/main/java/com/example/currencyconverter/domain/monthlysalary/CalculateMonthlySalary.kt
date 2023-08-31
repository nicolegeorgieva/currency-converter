package com.example.currencyconverter.domain.monthlysalary

fun calculateMonthlySalary(hourlyRate: Double, monthlyHours: Double): Double {
    return hourlyRate * monthlyHours
}