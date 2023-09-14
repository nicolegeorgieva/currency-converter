package com.example.currencyconverter

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class Contact(
    @PrimaryKey
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String
)