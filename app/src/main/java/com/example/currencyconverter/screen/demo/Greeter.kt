package com.example.currencyconverter.screen.demo

import javax.inject.Inject

class Greeter @Inject constructor() {
    private val greetingsList = listOf("Hi,", "Hello there,", "What's up", "Good day,")

    fun randomGreeting(): String {
        return greetingsList.random()
    }
}