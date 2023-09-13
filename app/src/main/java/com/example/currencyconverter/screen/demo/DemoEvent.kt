package com.example.currencyconverter.screen.demo

sealed interface DemoEvent {
    object OnStart : DemoEvent
    data class SetName(val newName: String) : DemoEvent
    object ClearName : DemoEvent
}