package com.example.currencyconverter.screen.age

sealed interface AgeEvent {
    object OnStart : AgeEvent
    data class OnChangeAge(val newAge: String) : AgeEvent
}