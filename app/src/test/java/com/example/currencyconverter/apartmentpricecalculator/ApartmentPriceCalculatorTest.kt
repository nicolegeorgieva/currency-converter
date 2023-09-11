package com.example.currencyconverter.apartmentpricecalculator

import com.example.currencyconverter.screen.apartmentinfo.ApartmentPriceCalculator
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class ApartmentPriceCalculatorTest : FreeSpec({
    val apartmentPriceCalculator = ApartmentPriceCalculator()

    "Real m2 price should be 2800.0 for 140000 total price and 50m2 real area" {
        apartmentPriceCalculator.calculateRealM2Price("140000", "50") shouldBe
                2800.0
    }

    "Should return 0.0 for invalid total price and invalid real area" {
        apartmentPriceCalculator.calculateRealM2Price("total", "real") shouldBe
                0.0
    }

    "Total m2 price should be 140000.0 for 2000EUR per m2 and 70m2 area" {
        apartmentPriceCalculator.calculateTotalM2Price("2000", "70") shouldBe
                140000.0
    }

    "Should return 0.0 for invalid eur per m2 and valid total m2" {
        apartmentPriceCalculator.calculateTotalM2Price("eur per m2", "70") shouldBe
                0.0
    }
})