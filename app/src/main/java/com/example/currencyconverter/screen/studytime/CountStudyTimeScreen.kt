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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.currencyconverter.Screen
import com.example.currencyconverter.component.BackButton
import com.example.currencyconverter.screenState
import com.example.currencyconverter.ui.theme.CurrencyConverterTheme

@Composable
fun CountStudyTimeScreen() {
    val viewModel: CountStudyTimeViewModel = viewModel()
    val uiState = viewModel.uiState()


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CountStudyTimeUi(
    uiState: StudyTimeUi,
    onEvent: (StudyTimeEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        LaunchedEffect(Unit) {
            onEvent(StudyTimeEvent.OnStart)
        }

        BackButton {
            screenState.value = Screen.MenuScreen
        }

        Spacer(modifier = Modifier.height(12.dp))

        TimeInputRow(
            hours = uiState.startHour,
            onHoursChange = {
                onEvent(StudyTimeEvent.EditStartHour(it))
            },
            mins = uiState.startMins,
            onMinsChange = {
                onEvent(StudyTimeEvent.EditStartMins(it))
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        TimeInputRow(
            hours = uiState.endHour,
            onHoursChange = {
                onEvent(StudyTimeEvent.EditEndHour(it))
            },
            mins = uiState.endMins,
            onMinsChange = {
                onEvent(StudyTimeEvent.EditEndMins(it))
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        TextField(
            modifier = Modifier.width(124.dp),
            value = uiState.cutMins,
            onValueChange = {
                onEvent(StudyTimeEvent.EditCutMins(it))
            },
            label = { Text("Cut mins") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            onEvent(StudyTimeEvent.AddCurrentStudyToTotal)
        }) {
            Text(text = "Add")
        }

        if (uiState.errorOccured) {
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

            Text(
                text = uiState.totalTimeOfStudying,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = {
                    onEvent(StudyTimeEvent.TotalTimeReset)
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
private fun TimeInputRow(
    hours: String,
    onHoursChange: (String) -> Unit,
    mins: String,
    onMinsChange: (String) -> Unit
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

@Preview
@Composable
private fun StudyTimePreview() {
    CurrencyConverterTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            CountStudyTimeUi(
                uiState = StudyTimeUi(
                    startHour = "10",
                    startMins = "00",
                    endHour = "14",
                    endMins = "00",
                    cutMins = "10",
                    totalTimeOfStudying = "1h 00m",
                    errorOccured = false
                ),
                onEvent = {}
            )
        }
    }
}