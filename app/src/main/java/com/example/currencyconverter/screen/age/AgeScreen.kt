package com.example.currencyconverter.screen.age

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.currencyconverter.Screen
import com.example.currencyconverter.component.BackButton
import com.example.currencyconverter.screenState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgeScreen() {
    Column {
        val viewModel: AgeViewModel = viewModel()
        var ageState = remember { mutableStateOf("") }

        BackButton {
            screenState.value = Screen.DemoScreen
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextField(
            value = ageState.value,
            onValueChange = { ageState.value = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = {
            viewModel.setAge(ageState.value)
        }) {
            Text(text = "Set age")
        }
    }
}