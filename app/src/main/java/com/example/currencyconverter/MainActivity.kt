package com.example.currencyconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import com.example.currencyconverter.screen.HomeScreen
import com.example.currencyconverter.screen.MenuScreen
import com.example.currencyconverter.screen.age.AgeScreen
import com.example.currencyconverter.screen.demo.DemoScreen
import com.example.currencyconverter.screen.requestplayground.RequestPlaygroundScreen
import com.example.currencyconverter.screen.studytime.CountStudyTimeScreen
import com.example.currencyconverter.ui.theme.CurrencyConverterTheme
import dagger.hilt.android.AndroidEntryPoint

var screenState = mutableStateOf<Screen>(Screen.MenuScreen)

sealed interface Screen {
    object MenuScreen : Screen
    object HomeScreen : Screen
    object CountStudyTime : Screen
    object RequestPlaygroundScreen : Screen
    object DemoScreen : Screen
    object AgeScreen : Screen
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrencyConverterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    when (screenState.value) {
                        Screen.MenuScreen -> MenuScreen()
                        Screen.CountStudyTime -> CountStudyTimeScreen()
                        Screen.HomeScreen -> HomeScreen()
                        Screen.RequestPlaygroundScreen -> RequestPlaygroundScreen()
                        Screen.DemoScreen -> DemoScreen()
                        Screen.AgeScreen -> AgeScreen()
                    }
                }
            }
        }
    }
}