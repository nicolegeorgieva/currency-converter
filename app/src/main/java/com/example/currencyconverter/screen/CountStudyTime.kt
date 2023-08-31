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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.edit
import com.example.currencyconverter.Screen
import com.example.currencyconverter.component.BackButton
import com.example.currencyconverter.data.CUT_MINS_KEY
import com.example.currencyconverter.data.START_HOUR_KEY
import com.example.currencyconverter.data.START_MINS_KEY
import com.example.currencyconverter.data.TOTAL_STUDY_TIME_KEY
import com.example.currencyconverter.data.dataStore
import com.example.currencyconverter.domain.studytime.convertStringTotalTimeToInt
import com.example.currencyconverter.domain.studytime.totalStudyTimeRes
import com.example.currencyconverter.screenState
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountStudyTime() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        val endHourInput = rememberSaveable { mutableStateOf("") }
        val endMinsInput = rememberSaveable { mutableStateOf("") }

        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()

        val startHourInputState = remember {
            context.dataStore.data.map {
                it[START_HOUR_KEY]
            }
        }.collectAsState(initial = "")

        val startMinsInputState = remember {
            context.dataStore.data.map {
                it[START_MINS_KEY]
            }
        }.collectAsState(initial = "")

        val cutMinsState = remember {
            context.dataStore.data.map {
                it[CUT_MINS_KEY]
            }
        }.collectAsState(initial = "")

        val totalStudyTimeState = remember {
            context.dataStore.data.map { it[TOTAL_STUDY_TIME_KEY] }
        }.collectAsState(initial = null)

        val errorOccuredState = remember { mutableStateOf(false) }

        BackButton {
            screenState.value = Screen.MenuScreen
        }

        Spacer(modifier = Modifier.height(12.dp))

        TimeInputRow(
            hours = startHourInputState.value ?: "",
            onHoursChange = { newHours ->
                coroutineScope.launch {
                    context.dataStore.edit {
                        it[START_HOUR_KEY] = newHours
                    }
                }
            },
            mins = startMinsInputState.value ?: "",
            onMinsChange = { newMins ->
                coroutineScope.launch {
                    context.dataStore.edit {
                        it[START_MINS_KEY] = newMins
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        TimeInputRow(
            hours = endHourInput.value,
            onHoursChange = { endHourInput.value = it },
            mins = endMinsInput.value,
            onMinsChange = { endMinsInput.value = it }
        )

        Spacer(modifier = Modifier.height(24.dp))

        TextField(
            modifier = Modifier.width(124.dp),
            value = cutMinsState.value ?: "",
            onValueChange = { newCutMinsInput ->
                coroutineScope.launch {
                    context.dataStore.edit {
                        it[CUT_MINS_KEY] = newCutMinsInput
                    }
                }
            },
            label = { Text("Cut mins*") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            val totalStudy = totalStudyTimeRes(
                (startHourInputState.value ?: "").toString(),
                (startMinsInputState.value ?: "").toString(),
                endHourInput.value,
                endMinsInput.value,
                cutMinsState.value ?: "",
                convertStringTotalTimeToInt(totalStudyTimeState.value ?: "")
            )

            if (totalStudy != totalStudyTimeState.value) {
                errorOccuredState.value = false

                coroutineScope.launch {
                    context.dataStore.edit {
                        it[TOTAL_STUDY_TIME_KEY] = totalStudy
                        it[START_HOUR_KEY] = ""
                        it[START_MINS_KEY] = ""
                        it[CUT_MINS_KEY] = ""
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
                    contentDescription = "Menu"
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