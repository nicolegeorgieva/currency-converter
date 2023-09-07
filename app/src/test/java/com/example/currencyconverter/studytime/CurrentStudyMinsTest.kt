package com.example.currencyconverter.studytime

import com.example.currencyconverter.domain.studytime.currentStudyMins
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class CurrentStudyMinsTest : FreeSpec({
    "without cut mins" - {
        "10:00 - 11:00 -> 60m" {
            currentStudyMins(
                startHour = "10",
                startMin = "00",
                endHour = "11",
                endMin = "00",
                cutMins = "00"
            ) shouldBe 60
        }

        "10:00 - 12:30 -> 150m" {
            currentStudyMins(
                startHour = "10",
                startMin = "00",
                endHour = "12",
                endMin = "30",
                cutMins = "00"
            ) shouldBe 150
        }
    }

    "with cut mins" - {
        "10:00 - 11:00, cut: 10m -> 50m" {
            currentStudyMins(
                startHour = "10",
                startMin = "00",
                endHour = "11",
                endMin = "00",
                cutMins = "10"
            ) shouldBe 50
        }
    }
})