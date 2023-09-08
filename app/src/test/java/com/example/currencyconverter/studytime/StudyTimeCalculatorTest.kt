package com.example.currencyconverter.studytime

import com.example.currencyconverter.screen.studytime.StudyTimeCalculator
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class StudyTimeCalculatorTest : FreeSpec({
    val studyTimeCalculator = StudyTimeCalculator()

    "CurrentStudyMinsTest" - {
        "without cut mins" - {
            "10:00 - 11:00 -> 60m" {
                studyTimeCalculator.currentStudyMins(
                    startHour = "10",
                    startMin = "00",
                    endHour = "11",
                    endMin = "00",
                    cutMins = "00"
                ) shouldBe 60
            }

            "10:00 - 12:30 -> 150m" {
                studyTimeCalculator.currentStudyMins(
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
                studyTimeCalculator.currentStudyMins(
                    startHour = "10",
                    startMin = "00",
                    endHour = "11",
                    endMin = "00",
                    cutMins = "10"
                ) shouldBe 50
            }
        }
    }

    "TotalStudyTimeFormattedTest" - {
        "150m -> \"2h 30m\"" {
            studyTimeCalculator.totalStudyTimeFormatted(150) shouldBe "2h 30m"
        }

        "0m -> \"0h 00m\"" {
            studyTimeCalculator.totalStudyTimeFormatted(0) shouldBe "0h 00m"
        }

        "1m -> \"0h 01m\"" {
            studyTimeCalculator.totalStudyTimeFormatted(1) shouldBe "0h 01m"
        }

        "10m -> \"0h 10m\"" {
            studyTimeCalculator.totalStudyTimeFormatted(10) shouldBe "0h 10m"
        }

        "60m -> \"1h 00m\"" {
            studyTimeCalculator.totalStudyTimeFormatted(60) shouldBe "1h 00m"
        }

        "61m -> \"1h 01m\"" {
            studyTimeCalculator.totalStudyTimeFormatted(61) shouldBe "1h 01m"
        }

        "119m -> \"1h 59m\"" {
            studyTimeCalculator.totalStudyTimeFormatted(119) shouldBe "1h 59m"
        }
    }

    "TotalStudyTimeMinsTest" - {
        "60m, 20m -> 80m" {
            studyTimeCalculator.totalStudyTimeMins(60, 20) shouldBe 80
        }

        "60m, 35m -> 95m" {
            studyTimeCalculator.totalStudyTimeMins(60, 35) shouldBe 95
        }

        "150m, 35m -> 185m" {
            studyTimeCalculator.totalStudyTimeMins(150, 35) shouldBe 185
        }
    }

    "TotalStudyTimeResTest" - {
        "without cut mins" - {
            "10:00 - 10:08, 0m, 0m -> 0h 08m" {
                studyTimeCalculator.totalStudyTimeRes(
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
                studyTimeCalculator.totalStudyTimeRes(
                    startHour = "10",
                    startMin = "00",
                    endHour = "11",
                    endMin = "00",
                    cutMins = "10",
                    existingStudyTimeMins = 60
                ) shouldBe "1h 50m"
            }

            "10:00 - 11:30, 15m, 15m -> 1h 30m" {
                studyTimeCalculator.totalStudyTimeRes(
                    startHour = "10",
                    startMin = "00",
                    endHour = "11",
                    endMin = "30",
                    cutMins = "15",
                    existingStudyTimeMins = 15
                ) shouldBe "1h 30m"
            }
        }
    }
}
)