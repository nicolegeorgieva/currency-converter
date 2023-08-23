package com.example.currencyconverter.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.currencyconverter.Screen
import com.example.currencyconverter.screenState

@Composable
fun MenuScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {

        Button(onClick = {
            screenState.value = Screen.HomeScreen
        }) {
            Text(text = "Home")
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(onClick = {
            screenState.value = Screen.CountStudyTime
        }) {
            Text(text = "Count Study Time")
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(onClick = {
            screenState.value = Screen.RequestPlaygroundScreen
        }) {
            Text(text = "Tasks")
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}