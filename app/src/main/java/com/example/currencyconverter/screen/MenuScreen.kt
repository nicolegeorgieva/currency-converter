package com.example.currencyconverter.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.currencyconverter.MyPreview
import com.example.currencyconverter.Screen
import com.example.currencyconverter.component.MenuItem

@Composable
fun MenuScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        menuItem("Home", Screen.HomeScreen)
        menuItem("Apartment", Screen.ApartmentInfoScreen)
        menuItem("Study Time", Screen.CountStudyTime)
        menuItem("Tasks", Screen.RequestPlaygroundScreen)
        menuItem("Demo", Screen.DemoScreen)
        menuItem("Contact", Screen.ContactScreen)
        menuItem("Charts", Screen.ChartsScreen)
    }
}

private fun LazyListScope.menuItem(name: String, screen: Screen) {
    item(key = name) {
        MenuItem(name = name, screen = screen)
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Preview
@Composable
private fun MenuScreenPreview() {
    MyPreview {
        MenuScreen()
    }
}
