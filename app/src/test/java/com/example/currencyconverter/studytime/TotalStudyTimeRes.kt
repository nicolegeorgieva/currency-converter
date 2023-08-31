package com.example.currencyconverter.studytime

import com.example.currencyconverter.domain.studytime.totalStudyTimeRes
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class TotalStudyTimeRes : FreeSpec({
    "without cut mins" - {
        "10:00 - 10:08, 0m, 0m -> 0h 08m" {
            totalStudyTimeRes(
                startHour = "10",
                startMin = "00",
                endHour = "10",
                endMin = "08",
                cutMins = "0",
                existingStudyTimeMins = 0
            ) shouldBe "0h 08m"
        }
    }

    "with cut mins" - {
        "10:00 - 11:00, 10m, 60m -> 1h 50m" {
            totalStudyTimeRes(
                startHour = "10",
                startMin = "00",
                endHour = "11",
                endMin = "00",
                cutMins = "10",
                existingStudyTimeMins = 60
            ) shouldBe "1h 50m"
        }

        "10:00 - 11:30, 15m, 15m -> 1h 30m" {
            totalStudyTimeRes(
                startHour = "10",
                startMin = "00",
                endHour = "11",
                endMin = "30",
                cutMins = "15",
                existingStudyTimeMins = 15
            ) shouldBe "1h 30m"
        }
    }
})