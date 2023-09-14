package com.example.currencyconverter.screen.contact

sealed interface ContactEvent {
    object OnDismissContactDialog : ContactEvent
    data class OnFirstNameChange(val firstName: String) : ContactEvent
    data class OnLastNameChange(val lastName: String) : ContactEvent
    data class OnPhoneNumberChange(val phoneNumber: String) : ContactEvent
}