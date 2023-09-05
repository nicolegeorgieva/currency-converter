package com.example.currencyconverter.screen.demo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DemoScreen() {
    val viewModel: DemoViewModel = viewModel()

    Column {
        Text(text = "Demo")

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = viewModel.greeting(), fontSize = 48.sp)

        Spacer(modifier = Modifier.height(8.dp))

        var nameState by remember { mutableStateOf("") }

        TextField(value = nameState, onValueChange = { nameState = it })

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            viewModel.setName(nameState)
        }) {
            Text(text = "Set name")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            viewModel.clearName()
        }) {
            Text(text = "Clear name")
        }
    }
}