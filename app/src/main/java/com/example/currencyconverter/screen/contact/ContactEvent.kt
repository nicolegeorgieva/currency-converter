package com.example.currencyconverter.screen.contact

import com.example.currencyconverter.database.contact.ContactEntity

sealed interface ContactEvent {
    data class OnShowContactDialog(val show: Boolean) : ContactEvent
    data class OnFirstNameChange(val firstName: String) : ContactEvent
    data class OnLastNameChange(val lastName: String) : ContactEvent
    data class OnPhoneNumberChange(val phoneNumber: String) : ContactEvent
    object OnAddContact : ContactEvent
    data class OnDeleteContact(val contact: ContactEntity) : ContactEvent
    object OnAddWithBlankFields : ContactEvent
}