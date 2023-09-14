package com.example.currencyconverter.database.contact

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class ContactEntity(
    @PrimaryKey
    val id: String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String
)