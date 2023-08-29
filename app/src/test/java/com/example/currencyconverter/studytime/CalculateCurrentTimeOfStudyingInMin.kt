package com.example.currencyconverter.studytime

import com.example.currencyconverter.domain.calculateCurrentTimeOfStudyingInMin
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class CalculateCurrentTimeOfStudyingInMin : FreeSpec({
    "Should return 60 for input 11, 00, 12, 00" {
        val res = calculateCurrentTimeOfStudyingInMin(
            "11", "00",
            "12", "00"
        )
        res shouldBe 60
    }

    "Should return 42 for input 10, 00, 10, 42" {
        val res = calculateCurrentTimeOfStudyingInMin(
            "10", "00",
            "10", "42"
        )
        res shouldBe 42
    }

    "Should return 90 for input 10, 00, 11, 30" {
        val res = calculateCurrentTimeOfStudyingInMin(
            "10", "00",
            "11", "30"
        )
        res shouldBe 90
    }

    "Should return 2 for input 10, 00, 10, 02" {
        val res = calculateCurrentTimeOfStudyingInMin(
            "10", "00",
            "10", "02"
        )
        res shouldBe 2
    }
})