package com.example.currencyconverter.home

import com.example.currencyconverter.screen.home.MonthlyGrossSalaryCalculator
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class MonthlySalaryCalculatorTest : FreeSpec({
    val monthlySalaryCalculator = MonthlyGrossSalaryCalculator()

    "monthly hours should be ~173.0 for 40h per week, 52 weeks per year and 12mo per year" {
        monthlySalaryCalculator.monthlyHours shouldBe 173.33333333333334
    }

    "calculate monthly salary" - {
        "should return ~8666.0 for 50.0 hourly rate and 173.33333333333334 monthly hours" {
            monthlySalaryCalculator.calculateMonthlyGrossSalary(
                50.0,
                173.33333333333334
            ) shouldBe 8666.666666666668
        }

        "should return 10400.0 for 60.0 hourly rate and 173.33333333333334 monthly hours" {
            monthlySalaryCalculator.calculateMonthlyGrossSalary(
                60.0,
                173.33333333333334
            ) shouldBe 10400.0
        }
    }
})