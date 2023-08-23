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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.edit
import com.example.currencyconverter.data.TOTAL_STUDY_TIME_KEY
import com.example.currencyconverter.data.dataStore
import com.example.currencyconverter.domain.totalStudyTime
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

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

        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()

        val totalStudyTimeState = remember {
            context.dataStore.data.map { it[TOTAL_STUDY_TIME_KEY] }
        }.collectAsState(initial = null)

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
            val totalStudy = totalStudyTime(
                startHoursInput.value,
                startMinsInput.value,
                endHoursInput.value,
                endMinsInput.value,
                totalStudyTimeState.value ?: 0.0
            )

            coroutineScope.launch {
                context.dataStore.edit { it[TOTAL_STUDY_TIME_KEY] = totalStudy }
            }

            startHoursInput.value = ""
            startMinsInput.value = ""
            endHoursInput.value = ""
            endMinsInput.value = ""
        }) {
            Text(text = "Add")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Total time of studying: ")

            Spacer(modifier = Modifier.weight(1f))

            Text(text = "${totalStudyTimeState.value}", fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = {
                    coroutineScope.launch {
                        context.dataStore.edit { it[TOTAL_STUDY_TIME_KEY] = 0.0 }
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