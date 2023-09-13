package com.example.currencyconverter.screen.requestplayground

sealed interface RequestPlaygroundEvent {
    object OnStart : RequestPlaygroundEvent
    data class OnToDoWriting(val task: String) : RequestPlaygroundEvent
    data class OnDelete(val item: Int) : RequestPlaygroundEvent
    object OnAddToDo : RequestPlaygroundEvent
}