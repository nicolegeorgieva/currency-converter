package com.example.currencyconverter.studytime

import com.example.currencyconverter.domain.totalStudyTime
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class TotalStudyTime : FreeSpec({
    "Should return 2.45 for input 10:00 - 11:00, cutMins: 0, calculated time: 1.45" {
        totalStudyTime(
            "10", "00", "11", "00", "0", 1.45
        ) shouldBe 2.45
    }

    "Should return 3.3 for input 10:00 - 11:45, cutMins: 0, calculated time: 1.45" {
        totalStudyTime(
            "10", "00", "11", "45", "0", 1.45
        ) shouldBe 3.3
    }
})