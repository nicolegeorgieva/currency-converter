package com.example.currencyconverter.studytime.oldfunctions

import com.example.currencyconverter.domain.oldfunctions.calculateTotalTimeOfStudying
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class CalculateTotalTimeOfStudying : FreeSpec({
    "Should return 3.2 for input 1.0, 2.2" {
        calculateTotalTimeOfStudying(1.0, 2.2) shouldBe 3.2
    }

    "Should return 0.2 for input 0.0, 0.2" {
        calculateTotalTimeOfStudying(0.0, 0.2) shouldBe 0.2
    }
})