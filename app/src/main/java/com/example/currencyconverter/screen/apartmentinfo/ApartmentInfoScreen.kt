package com.example.currencyconverter.screen.apartmentinfo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ApartmentInfoScreen() {
    val viewModel: ApartmentInfoViewModel = viewModel()
    val uiState = viewModel.uiState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
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
            label = "Total m2 price: ",
            price = uiState.totalM2Price,
            currency = uiState.totalM2PriceCurrency
        )

        Spacer(modifier = Modifier.height(12.dp))

        TotalM2PriceRow(
            label = "Total real m2 price: ",
            price = uiState.totalRealM2Price,
            currency = uiState.totalRealM2PriceCurrency
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
        TextField(value = value, onValueChange = onValueChange)
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = label)
    }
}

@Composable
fun TotalM2PriceRow(
    label: String,
    price: String,
    currency: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label)
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = price)
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = currency)
    }
}