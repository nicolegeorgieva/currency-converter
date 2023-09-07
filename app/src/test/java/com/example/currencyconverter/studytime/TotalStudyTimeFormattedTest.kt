package com.example.currencyconverter.studytime

import com.example.currencyconverter.domain.studytime.totalStudyTimeFormatted
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class TotalStudyTimeFormattedTest : FreeSpec({
    "150m -> \"2h 30m\"" {
        totalStudyTimeFormatted(150) shouldBe "2h 30m"
    }

    "0m -> \"0h 00m\"" {
        totalStudyTimeFormatted(0) shouldBe "0h 00m"
    }

    "1m -> \"0h 01m\"" {
        totalStudyTimeFormatted(1) shouldBe "0h 01m"
    }

    "10m -> \"0h 10m\"" {
        totalStudyTimeFormatted(10) shouldBe "0h 10m"
    }

    "60m -> \"1h 00m\"" {
        totalStudyTimeFormatted(60) shouldBe "1h 00m"
    }

    "61m -> \"1h 01m\"" {
        totalStudyTimeFormatted(61) shouldBe "1h 01m"
    }

    "119m -> \"1h 59m\"" {
        totalStudyTimeFormatted(119) shouldBe "1h 59m"
    }
})