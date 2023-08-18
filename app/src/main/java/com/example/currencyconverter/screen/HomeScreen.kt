package com.example.currencyconverter.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.currencyconverter.request.ExchangeRatesResponse
import com.example.currencyconverter.request.fetchExchangeRates
import com.example.currencyconverter.utility.calculateMonthlySalary
import com.example.currencyconverter.utility.monthlyHours
import java.text.DecimalFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Text(text = "Hello, User!", fontWeight = FontWeight.Bold, fontSize = 32.sp)

        Spacer(modifier = Modifier.height(24.dp))

        var hourlyRateInUsd by rememberSaveable { mutableStateOf("") }
        var monthlySalaryInBgn by rememberSaveable { mutableStateOf("") }
        var exchangeRates by remember { mutableStateOf<ExchangeRatesResponse?>(null) }

        LaunchedEffect(Unit) {
            exchangeRates = fetchExchangeRates()
        }

        val eurToUsd = exchangeRates?.eur?.usd ?: 1.0
        val eurToBgn = exchangeRates?.eur?.bgn ?: 1.0
        val usdHourlyRateToEur = (hourlyRateInUsd.toDoubleOrNull() ?: 1.0) / eurToUsd
        val eurToBgnHourlyRate = usdHourlyRateToEur * eurToBgn

        monthlySalaryInBgn = calculateMonthlySalary(eurToBgnHourlyRate, monthlyHours).toString()

        Text(text = "Hourly rate:")

        Spacer(modifier = Modifier.height(24.dp))

        Row(verticalAlignment = Alignment.Bottom) {
            TextField(
                value = hourlyRateInUsd,
                onValueChange = { hourlyRateInUsd = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(text = "$", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(text = "=", fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(6.dp))

        if (hourlyRateInUsd.isNotBlank()) {
            Row(verticalAlignment = Alignment.Bottom) {
                val formatter = DecimalFormat("###,###.00")
                val formattedSalary = formatter.format(monthlySalaryInBgn.toDoubleOrNull() ?: 1.0)

                Text(text = formattedSalary)

                Spacer(modifier = Modifier.width(8.dp))

                Text(text = "BGN", fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(text = "monthly salary", fontStyle = FontStyle.Italic)
    }
}