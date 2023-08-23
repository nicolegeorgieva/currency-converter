package com.example.currencyconverter.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun CountStudyTime() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        val startHoursInput = rememberSaveable { mutableStateOf("") }
        val startMinsInput = rememberSaveable { mutableStateOf("") }
        val endHoursInput = rememberSaveable { mutableStateOf("") }
        val endMinsInput = rememberSaveable { mutableStateOf("") }

        TimeInputRow(
            hours = startHoursInput.value,
            onHoursChange = { startHoursInput.value = it },
            mins = startMinsInput.value,
            onMinsChange = { startMinsInput.value = it }
        )

        Spacer(modifier = Modifier.height(24.dp))

        TimeInputRow(
            hours = endHoursInput.value,
            onHoursChange = { endHoursInput.value = it },
            mins = endMinsInput.value,
            onMinsChange = { endMinsInput.value = it }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            startHoursInput.value = ""
            startMinsInput.value = ""
            endHoursInput.value = ""
            endMinsInput.value = ""
        }) {
            Text(text = "Add")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeInputRow(
    hours: String, onHoursChange: (String) -> Unit,
    mins: String, onMinsChange: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            modifier = Modifier.width(124.dp),
            value = hours,
            onValueChange = {
                onHoursChange(it)
            },
            label = { Text("Hour") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(text = ":", fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.width(8.dp))

        TextField(
            modifier = Modifier.width(124.dp),
            value = mins,
            onValueChange = {
                onMinsChange(it)
            },
            label = { Text("Mins") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )
    }
}