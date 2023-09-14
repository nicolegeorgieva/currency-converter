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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.currencyconverter.MyPreview
import com.example.currencyconverter.Screen
import com.example.currencyconverter.component.BackButton
import com.example.currencyconverter.screenState

@Composable
fun ApartmentInfoScreen() {
    val viewModel: ApartmentInfoViewModel = viewModel()
    val uiState = viewModel.uiState()

    ApartmentInfoUi(
        uiState = uiState,
        onEvent = {
            viewModel.onEvent(it)
        }
    )
}

@Composable
private fun ApartmentInfoUi(
    uiState: ApartmentInfoState,
    onEvent: (ApartmentInfoEvent) -> Unit
) {
    LaunchedEffect(Unit) {
        onEvent(ApartmentInfoEvent.OnStart)
    }

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
            onValueChange = {
                onEvent(ApartmentInfoEvent.OnM2PriceEurChange(it))
            },
            label = "EUR/m2"
        )

        Spacer(modifier = Modifier.height(12.dp))

        InputRow(
            value = uiState.totalM2,
            onValueChange = {
                onEvent(ApartmentInfoEvent.OnTotalM2Change(it))
            },
            label = "total m2"
        )

        Spacer(modifier = Modifier.height(12.dp))

        InputRow(
            value = uiState.realM2,
            onValueChange = {
                onEvent(ApartmentInfoEvent.OnRealM2Change(it))
            },
            label = "real m2"
        )

        Spacer(modifier = Modifier.height(12.dp))

        Divider()

        Spacer(modifier = Modifier.height(12.dp))

        TotalM2PriceRow(
            label = "Real m2 price: ",
            price = uiState.realM2Price,
            currencyValue = uiState.realM2PriceCurrency,
            onCurrencyValueSelected = {
                onEvent(ApartmentInfoEvent.OnRealPriceCurrencySet(it))
            },
            isExpanded = uiState.isRealM2PriceCurrencyExpanded,
            onExpandedChange = {
                onEvent(ApartmentInfoEvent.OnRealM2ExpandedChange(it))
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        TotalM2PriceRow(
            label = "Total m2 price: ",
            price = uiState.totalM2Price,
            currencyValue = uiState.totalM2PriceCurrency,
            onCurrencyValueSelected = {
                onEvent(ApartmentInfoEvent.OnTotalPriceCurrencySet(it))
            },
            isExpanded = uiState.isTotalM2PriceCurrencyExpanded,
            onExpandedChange = {
                onEvent(ApartmentInfoEvent.OnTotalM2ExpandedChange(it))
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InputRow(
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
private fun TotalM2PriceRow(
    label: String,
    price: String,
    isExpanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    currencyValue: ApartmentInfoCurrency,
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
                onExpandedChange = {
                    onExpandedChange(it)
                }
            ) {
                TextField(
                    modifier = Modifier.menuAnchor(),
                    value = "$currencyValue",
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors()
                )

                ExposedDropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = {
                        onExpandedChange(false)
                    }
                ) {
                    DropdownMenuItem(
                        text = { Text("${ApartmentInfoCurrency.EUR}") },
                        onClick = {
                            onCurrencyValueSelected(ApartmentInfoCurrency.EUR)
                            onExpandedChange(false)
                        }
                    )

                    DropdownMenuItem(
                        text = { Text("${ApartmentInfoCurrency.BGN}") },
                        onClick = {
                            onCurrencyValueSelected(ApartmentInfoCurrency.BGN)
                            onExpandedChange(false)
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ApartmentInfoPreview() {
    MyPreview {
        ApartmentInfoUi(
            uiState = ApartmentInfoState(
                m2PriceEur = "2000",
                totalM2 = "70",
                realM2 = "50",
                realM2Price = "2800.0",
                realM2PriceCurrency = ApartmentInfoCurrency.EUR,
                isRealM2PriceCurrencyExpanded = false,
                totalM2Price = "140,000.0",
                totalM2PriceCurrency = ApartmentInfoCurrency.EUR,
                isTotalM2PriceCurrencyExpanded = false
            ),
            onEvent = {}
        )
    }
}