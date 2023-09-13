package com.example.currencyconverter.screen.home

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.currencyconverter.Screen
import com.example.currencyconverter.component.BackButton
import com.example.currencyconverter.screenState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = viewModel()
    val uiState = viewModel.uiState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        BackButton {
            screenState.value = Screen.MenuScreen
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(text = "Hello, User!", fontWeight = FontWeight.Bold, fontSize = 32.sp)

        Spacer(modifier = Modifier.height(24.dp))

        LaunchedEffect(Unit) {
            viewModel.onEvent(HomeEvent.OnStart)
        }

        if (uiState.date != null) {
            Text(text = "Conversions are up to this date: ${uiState.date}")

            Spacer(modifier = Modifier.height(24.dp))
        }

        Row(verticalAlignment = Alignment.Bottom) {
            TextField(
                value = uiState.hourlyRateUsd,
                onValueChange = {
                    viewModel.onEvent(HomeEvent.OnChangeHourlyRateInUsd(it))
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(text = "$ (hourly rate)", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(verticalAlignment = Alignment.Bottom) {
            TextField(
                value = uiState.taxPercentage,
                onValueChange = {
                    viewModel.onEvent(HomeEvent.OnChangeTaxPercentage(it))
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(text = "% (tax)", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(verticalAlignment = Alignment.Bottom) {
            TextField(
                value = uiState.socialSecurityAmount,
                onValueChange = {
                    viewModel.onEvent(HomeEvent.OnChangeSocialSecurityAmount(it))
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(text = "BGN (social security)", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(verticalAlignment = Alignment.Bottom) {
            TextField(
                value = uiState.companyExpensesAmount,
                onValueChange = {
                    viewModel.onEvent(HomeEvent.OnChangeCompanyExpensesAmount(it))
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(text = "BGN (company expenses)", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(text = "=", fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(12.dp))

        if (uiState.hourlyRateUsd.isNotBlank()) {
            Row(verticalAlignment = Alignment.Bottom) {
                Text(text = uiState.monthlyGrossSalary)

                Spacer(modifier = Modifier.width(8.dp))

                Text(text = "BGN", fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(text = "monthly gross salary", fontStyle = FontStyle.Italic)

        Spacer(modifier = Modifier.height(12.dp))

        Text(text = "=", fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(12.dp))

        if (uiState.hourlyRateUsd.isNotBlank()) {
            Row(verticalAlignment = Alignment.Bottom) {
                Text(text = uiState.monthlyNetSalary, color = Color(0xFF3B6909))

                Spacer(modifier = Modifier.width(8.dp))

                Text(text = "BGN", fontWeight = FontWeight.Bold, color = Color(0xFF3B6909))
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "monthly net salary",
            fontStyle = FontStyle.Italic,
            color = Color(0xFF3B6909)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(text = "=", fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(12.dp))

        if (uiState.hourlyRateUsd.isNotBlank()) {
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = uiState.yearlyNetSalary,
                    color = Color(0xFFF4511E)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(text = "BGN", fontWeight = FontWeight.Bold, color = Color(0xFFF4511E))
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "yearly net salary",
            fontStyle = FontStyle.Italic,
            color = Color(0xFFF4511E)
        )
    }
}