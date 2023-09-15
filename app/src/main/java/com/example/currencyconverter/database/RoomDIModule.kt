package com.example.currencyconverter.database

import android.content.Context
import androidx.room.Room
import com.example.currencyconverter.database.contact.ContactDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RoomDIModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext
        context: Context
    ): MyDatabase {
        return Room.databaseBuilder(
            context,
            MyDatabase::class.java, "my-database"
        ).build()
    }

    @Provides
    fun provideContactDao(database: MyDatabase): ContactDao {
        return database.dao
    }
}