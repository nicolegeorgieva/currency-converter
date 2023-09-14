package com.example.currencyconverter.screen.contact

import com.example.currencyconverter.database.contact.ContactEntity

data class ContactState(
    val contacts: List<ContactEntity>,
    val alertDialogDismissed: Boolean,
    val sortedBy: ContactsSortedBy,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String
)