package com.example.currencyconverter.screen.studytime

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.currencyconverter.Screen
import com.example.currencyconverter.component.BackButton
import com.example.currencyconverter.data.TOTAL_STUDY_TIME_KEY
import com.example.currencyconverter.data.dataStore
import com.example.currencyconverter.domain.studytime.currentStudyMins
import com.example.currencyconverter.screenState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountStudyTimeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        val viewModel: CountStudyTimeViewModel = viewModel()

        BackButton {
            screenState.value = Screen.MenuScreen
        }

        Spacer(modifier = Modifier.height(12.dp))

        TimeInputRow(
            hours = viewModel.getStartHour(),
            onHoursChange = { newHour ->
                viewModel.editStartHour(newHour)
            },
            mins = viewModel.getStartMins(),
            onMinsChange = { newMins ->
                viewModel.editStartMins(newMins)
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        TimeInputRow(
            hours = viewModel.endHourInputState.value,
            onHoursChange = { viewModel.endHourInputState.value = it },
            mins = viewModel.endMinsInputState.value,
            onMinsChange = { viewModel.endMinsInputState.value = it }
        )

        Spacer(modifier = Modifier.height(24.dp))

        TextField(
            modifier = Modifier.width(124.dp),
            value = viewModel.getCutMins(),
            onValueChange = { newCutMinsInput ->
                viewModel.editCutMins(newCutMinsInput)
            },
            label = { Text("Cut mins*") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            if (currentStudyMins(
                    (startHourInputState.value ?: "").toString(),
                    (startMinsInputState.value ?: "").toString(),
                    endHourInput.value,
                    endMinsInput.value,
                    cutMinsState.value ?: ""
                ) < 0
            ) {
                errorOccuredState.value = true
            } else if (totalStudy != totalStudyTimeState.value) {
                errorOccuredState.value = false

                coroutineScope.launch {
                    context.dataStore.edit {

                    }
                }

                endHourInput.value = ""
                endMinsInput.value = ""
            } else {
                errorOccuredState.value = true
            }
        }) {
            Text(text = "Add")
        }

        if (errorOccuredState.value) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "Invalid input!", color = Color.Red)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Total time of studying: ")

            Spacer(modifier = Modifier.weight(1f))

            Text(text = totalStudyTimeState.value ?: "0h 00m", fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = {
                    coroutineScope.launch {
                        context.dataStore.edit { it[TOTAL_STUDY_TIME_KEY] = "0h 00m" }
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "Clear"
                )
            }
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