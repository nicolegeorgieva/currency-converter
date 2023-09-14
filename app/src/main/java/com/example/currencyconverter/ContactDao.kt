package com.example.currencyconverter

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Upsert
    suspend fun upsertContact(contact: ContactEntity)

    @Delete
    suspend fun deleteContact(contact: ContactEntity)

    @Query("SELECT * FROM contacts ORDER BY firstName ASC")
    fun getContactsOrderedByFirstName(): Flow<List<ContactEntity>>

    @Query("SELECT * FROM contacts ORDER BY lastName ASC")
    fun getContactsOrderedByLastName(): Flow<List<ContactEntity>>

    @Query("SELECT * FROM contacts ORDER BY phoneNumber ASC")
    fun getContactsOrderedByPhoneNumber(): Flow<List<ContactEntity>>
}