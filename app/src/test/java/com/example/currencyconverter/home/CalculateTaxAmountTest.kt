package com.example.currencyconverter.home

import com.example.currencyconverter.screen.home.TaxCalculator
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class CalculateTaxAmountTest : FreeSpec({
    val taxCalculator = TaxCalculator()

    "should return 3000.0 for income 30000 and no other expenses" {
        taxCalculator.calculateTaxAmount(
            income = 30000.0,
            socialSecurityAmount = null,
            companyExpenses = null,
            taxPercentage = 10.0
        ) shouldBe 3000.0
    }
})