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

    "Total m2 price should be 140000.0 for 2000EUR per m2 and 70m2 area" {
        apartmentPriceCalculator.calculateTotalM2Price("2000", "70") shouldBe
                140000.0
    }
})