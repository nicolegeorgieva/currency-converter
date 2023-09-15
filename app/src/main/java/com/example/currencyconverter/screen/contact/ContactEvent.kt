package com.example.currencyconverter.screen.contact

import com.example.currencyconverter.database.contact.ContactEntity

sealed interface ContactEvent {
    data class OnDismissContactDialog(val toDismiss: Boolean) : ContactEvent
    data class OnFirstNameChange(val firstName: String) : ContactEvent
    data class OnLastNameChange(val lastName: String) : ContactEvent
    data class OnPhoneNumberChange(val phoneNumber: String) : ContactEvent
    object OnAddContact : ContactEvent
    data class OnDeleteContact(val contact: ContactEntity) : ContactEvent
}