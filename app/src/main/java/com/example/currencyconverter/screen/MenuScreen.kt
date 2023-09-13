package com.example.currencyconverter.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.currencyconverter.Screen
import com.example.currencyconverter.component.MenuItem
import com.example.currencyconverter.screenState

@Composable
fun MenuScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        MenuItem(name = "Home") {
            screenState.value = Screen.HomeScreen
        }

        Spacer(modifier = Modifier.weight(1f))

        MenuItem(name = "Apartment Info") {
            screenState.value = Screen.ApartmentInfoScreen
        }

        Spacer(modifier = Modifier.weight(1f))

        MenuItem(name = "Count Study Time") {
            screenState.value = Screen.CountStudyTime
        }

        Spacer(modifier = Modifier.weight(1f))

        MenuItem(name = "Tasks") {
            screenState.value = Screen.RequestPlaygroundScreen
        }

        Spacer(modifier = Modifier.weight(1f))

        MenuItem(name = "Demo") {
            screenState.value = Screen.DemoScreen
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}