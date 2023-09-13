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

@Composable
fun MenuScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        MenuItem(name = "Home", screen = Screen.HomeScreen)

        Spacer(modifier = Modifier.weight(1f))

        MenuItem(name = "Apartment Info", screen = Screen.ApartmentInfoScreen)

        Spacer(modifier = Modifier.weight(1f))

        MenuItem(name = "Count Study Time", screen = Screen.CountStudyTime)

        Spacer(modifier = Modifier.weight(1f))

        MenuItem(name = "Tasks", screen = Screen.RequestPlaygroundScreen)

        Spacer(modifier = Modifier.weight(1f))

        MenuItem(name = "Demo", screen = Screen.DemoScreen)

        Spacer(modifier = Modifier.weight(1f))
    }
}