package com.example.currencyconverter.studytime.oldfunctions

import com.example.currencyconverter.domain.oldfunctions.totalStudyTime
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

    "Should return 24.58 for input 00:00 - 23:59, cutMins: 0, calculated time: 0.59" {
        totalStudyTime(
            "00", "00", "23", "59", "0", 0.59
        ) shouldBe 24.58
    }

    "Should return 00.00 for input 00:00 - 00:00, cutMins: 0, calculated time: 0.00" {
        totalStudyTime(
            "00", "00", "00", "00", "0", 0.00
        ) shouldBe 00.00
    }

    "Should return 24.47 for input 00:00 - 23:59, cutMins: 11, calculated time: 0.59" {
        totalStudyTime(
            "00", "00", "23", "59", "11", 0.59
        ) shouldBe 24.47
    }

    "Should return 2.28 for input 12:10 - 13:45, cutMins: 20, calculated time: 1.13" {
        totalStudyTime(
            "12", "10", "13", "45", "20", 1.13
        ) shouldBe 2.28
    }
})