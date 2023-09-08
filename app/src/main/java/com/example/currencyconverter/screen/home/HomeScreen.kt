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
import java.text.DecimalFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = viewModel()

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
            viewModel.onStart()
        }

        if (viewModel.getDate() != null) {
            Text(text = "Conversions are up to this date: ${viewModel.getDate()}")

            Spacer(modifier = Modifier.height(24.dp))
        }

        Row(verticalAlignment = Alignment.Bottom) {
            TextField(
                value = viewModel.getHourlyRateInUsd() ?: "",
                onValueChange = {
                    viewModel.onChangeHourlyRateInUsd(it)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(text = "$ (hourly rate)", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(verticalAlignment = Alignment.Bottom) {
            TextField(
                value = viewModel.getTaxPercentage().toString(),
                onValueChange = {
                    viewModel.onChangeTaxPercentage(
                        it.toDoubleOrNull() ?: 0.0
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(text = "% (tax)", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(verticalAlignment = Alignment.Bottom) {
            TextField(
                value = viewModel.getSocialSecurityAmount().toString(),
                onValueChange = {
                    viewModel.onChangeSocialSecurityAmount(
                        it.toDoubleOrNull() ?: 0.0
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(text = "BGN (social security)", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(verticalAlignment = Alignment.Bottom) {
            TextField(
                value = viewModel.getCompanyExpensesAmount().toString(),
                onValueChange = {
                    viewModel.onChangeCompanyExpensesAmount(
                        it.toDoubleOrNull() ?: 0.0
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(text = "BGN (company expenses)", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(text = "=", fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(12.dp))

        if (viewModel.getHourlyRateInUsd()?.isNotBlank() == true) {
            Row(verticalAlignment = Alignment.Bottom) {
                val formatter = DecimalFormat("###,###.00")
                val formattedSalary = formatter.format(
                    viewModel.getMonthlyBgnGrossSalary().toString()
                )

                Text(text = formattedSalary)

                Spacer(modifier = Modifier.width(8.dp))

                Text(text = "BGN", fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(text = "monthly gross salary", fontStyle = FontStyle.Italic)

        Spacer(modifier = Modifier.height(12.dp))

        Text(text = "=", fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(12.dp))

        if (viewModel.getHourlyRateInUsd()?.isNotBlank() == true) {
            Row(verticalAlignment = Alignment.Bottom) {
                val formatter = DecimalFormat("###,###.00")
                val formattedSalary = formatter.format(
                    viewModel.getMonthlyBgnNetSalary().toString()
                )

                Text(text = formattedSalary, color = Color(0xFF3B6909))

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
    }
}