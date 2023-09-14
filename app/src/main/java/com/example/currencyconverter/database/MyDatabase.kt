package com.example.currencyconverter.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.currencyconverter.database.contact.ContactDao
import com.example.currencyconverter.database.contact.ContactEntity

@Database(
    entities = [ContactEntity::class],
    version = 1
)
abstract class MyDatabase : RoomDatabase() {
    abstract val dao: ContactDao
}