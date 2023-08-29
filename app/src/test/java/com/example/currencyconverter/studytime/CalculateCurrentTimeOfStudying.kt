package com.example.currencyconverter.studytime

import com.example.currencyconverter.domain.calculateCurrentTimeOfStudying
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class CalculateCurrentTimeOfStudying : FreeSpec({
    "Without cut mins argument" - {
        "Should return 1.2 for input 80" {
            calculateCurrentTimeOfStudying(80) shouldBe 1.2
        }
    }
})