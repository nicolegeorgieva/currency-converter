package com.example.currencyconverter.screen.apartmentinfo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.currencyconverter.Screen
import com.example.currencyconverter.component.BackButton
import com.example.currencyconverter.screenState

@Composable
fun ApartmentInfoScreen() {
    val viewModel: ApartmentInfoViewModel = viewModel()
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

        InputRow(
            value = uiState.m2PriceEur,
            onValueChange = viewModel::onM2PriceEurChange,
            label = "eur/m2"
        )

        Spacer(modifier = Modifier.height(12.dp))

        InputRow(
            value = uiState.totalM2,
            onValueChange = viewModel::onTotalM2Change,
            label = "total m2"
        )

        Spacer(modifier = Modifier.height(12.dp))

        InputRow(
            value = uiState.realM2,
            onValueChange = viewModel::onRealM2Change,
            label = "real m2"
        )

        Spacer(modifier = Modifier.height(12.dp))

        Divider()

        Spacer(modifier = Modifier.height(12.dp))

        TotalM2PriceRow(
            label = "Real m2 price: ",
            price = uiState.realM2Price,
            currencyValue = viewModel.realM2PriceCurrency,
            onCurrencyValueSelected = { viewModel.onRealPriceCurrencySet(viewModel.realM2PriceCurrency.value) },
            isExpanded = uiState.isRealM2PriceCurrencyExpanded
        )

        Spacer(modifier = Modifier.height(12.dp))

        TotalM2PriceRow(
            label = "Total m2 price: ",
            price = uiState.totalM2Price,
            currencyValue = viewModel.totalM2PriceCurrency,
            onCurrencyValueSelected = { viewModel.onTotalPriceCurrencySet(viewModel.totalM2PriceCurrency.value) },
            isExpanded = uiState.isTotalM2PriceCurrencyExpanded
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputRow(
    value: String,
    onValueChange: (String) -> Unit,
    label: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = label)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TotalM2PriceRow(
    label: String,
    price: String,
    isExpanded: Boolean,
    currencyValue: MutableState<ApartmentInfoCurrency>,
    onCurrencyValueSelected: (ApartmentInfoCurrency) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label)
        Spacer(modifier = Modifier.width(4.dp))

        Text(text = price)
        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier.size(128.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            ExposedDropdownMenuBox(
                expanded = isExpanded,
                onExpandedChange = {}) {
                TextField(
                    value = "",
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors()
                )

                ExposedDropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = {}
                ) {
                    DropdownMenuItem(
                        text = { Text("${ApartmentInfoCurrency.EUR}") },
                        onClick = {
                            currencyValue.value = ApartmentInfoCurrency.EUR
                            onCurrencyValueSelected(currencyValue.value)
                        }
                    )

                    DropdownMenuItem(
                        text = { Text("${ApartmentInfoCurrency.BGN}") },
                        onClick = {
                            currencyValue.value = ApartmentInfoCurrency.BGN
                            onCurrencyValueSelected(currencyValue.value)
                        }
                    )
                }
            }
        }
    }
}