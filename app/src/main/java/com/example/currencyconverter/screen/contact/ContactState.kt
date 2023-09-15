package com.example.currencyconverter.screen.contact

import com.example.currencyconverter.database.contact.ContactEntity

data class ContactState(
    val contacts: List<ContactEntity>,
    val showContactDialog: Boolean,
    val sortedBy: SortedBy,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val addWithBlankFields: Boolean
)