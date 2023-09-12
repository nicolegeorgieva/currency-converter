package com.example.currencyconverter.screen.apartmentinfo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
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
            isExpanded = uiState.isRealM2PriceCurrencyExpanded,
            onExpandedChange = viewModel.onRealM2PriceCurrencyExpandedStateChange(),
            dropDownOption2 = uiState.realM2PriceCurrency,
            dropDownOption3 = uiState.realM2PriceCurrency
        )

        Spacer(modifier = Modifier.height(12.dp))

        TotalM2PriceRow(
            label = "Total m2 price: ",
            price = uiState.totalM2Price,
            isExpanded = uiState.isTotalM2PriceCurrencyExpanded,
            onExpandedChange = viewModel.onTotalM2PriceCurrencyExpandedStateChange(),
            dropDownOption2 = uiState.totalM2PriceCurrency,
            dropDownOption3 = uiState.totalM2PriceCurrency
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

@Composable
fun TotalM2PriceRow(
    label: String,
    price: String,
    isExpanded: Boolean,
    onExpandedChange: Unit,
    dropDownOption2: String,
    dropDownOption3: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label)
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = price)
        Spacer(modifier = Modifier.width(4.dp))
        DropDownCurrencyMenu(
            isExpanded = isExpanded,
            onExpandedChange = onExpandedChange,
            dropDownOption2 = dropDownOption2,
            dropDownOption3 = dropDownOption3
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownCurrencyMenu(
    isExpanded: Boolean,
    onExpandedChange: Unit,
    dropDownOption2: String,
    dropDownOption3: String
) {
    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { onExpandedChange }
    ) {
        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { onExpandedChange }) {
            CustomDropDownCurrencyItem(
                text = dropDownOption2,
                onClick = onExpandedChange
            )
            CustomDropDownCurrencyItem(
                text = dropDownOption3,
                onClick = onExpandedChange
            )
        }
    }
}

@Composable
fun CustomDropDownCurrencyItem(
    text: String,
    onClick: Unit
) {
    DropdownMenuItem(text = { text }, onClick = { onClick })
}