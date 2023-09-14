package com.example.currencyconverter

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "contacts")
data class ContactEntity(
    @PrimaryKey
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String
)