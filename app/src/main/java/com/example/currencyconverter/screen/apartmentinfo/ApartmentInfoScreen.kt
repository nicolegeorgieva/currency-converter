package com.example.currencyconverter.screen.apartmentinfo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
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

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputRow(
    value: String,
    onValueChange: (String) -> Unit,
    label: String
) {
    TextField(value = value, onValueChange = onValueChange)
    Spacer(modifier = Modifier.width(4.dp))
    Text(text = label)
}