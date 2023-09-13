package com.example.currencyconverter.screen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.currencyconverter.Screen
import com.example.currencyconverter.component.MenuItem

@Composable
fun MenuScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        item(key = "Home") {
            MenuItem(name = "Home", screen = Screen.HomeScreen)
            Spacer(modifier = Modifier.height(8.dp))
        }

        item(key = "Apartment Info") {
            MenuItem(name = "Apartment Info", screen = Screen.ApartmentInfoScreen)
            Spacer(modifier = Modifier.height(8.dp))
        }

        item(key = "Count Study Time") {
            MenuItem(name = "Count Study Time", screen = Screen.CountStudyTime)
            Spacer(modifier = Modifier.height(8.dp))
        }

        item(key = "Tasks") {
            MenuItem(name = "Tasks", screen = Screen.RequestPlaygroundScreen)
            Spacer(modifier = Modifier.height(8.dp))
        }

        item(key = "Demo") {
            MenuItem(name = "Demo", screen = Screen.DemoScreen)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}