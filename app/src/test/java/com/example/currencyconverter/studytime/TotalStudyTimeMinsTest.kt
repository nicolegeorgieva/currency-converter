package com.example.currencyconverter.studytime

import com.example.currencyconverter.domain.studytime.totalStudyTimeMins
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class TotalStudyTimeMinsTest : FreeSpec({
    "60m, 20m -> 80m" {
        totalStudyTimeMins(60, 20) shouldBe 80
    }

    "60m, 35m -> 95m" {
        totalStudyTimeMins(60, 35) shouldBe 95
    }

    "150m, 35m -> 185m" {
        totalStudyTimeMins(150, 35) shouldBe 185
    }
})