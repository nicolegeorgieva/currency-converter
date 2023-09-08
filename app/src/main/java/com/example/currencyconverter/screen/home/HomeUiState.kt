package com.example.currencyconverter.screen.home

data class HomeUiState(
    val date: String?,
    val hourlyRateUsd: String,
    val taxPercentage: String,
    val socialSecurityAmount: String,
    val companyExpensesAmount: String,
    val monthlyGrossSalary: String,
    val monthlyNetSalary: String,
    val yearlyNetSalary: String
)