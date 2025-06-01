package com.example.contacts

import android.content.Context
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Room
import androidx.room.Upsert
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton


@Dao
interface ContactDao {

    @Upsert
    suspend fun upsertContact(contact: Contacts)

    @Delete
    suspend fun deleteContact(contact: Contacts)

    @Query("SELECT * FROM contacts")
    fun getContacts(): Flow<List<Contacts>>
}

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ContactDatabase {
        val db = Room.databaseBuilder(
            context,
            ContactDatabase::class.java, "ContactDB"
        ).build()
        return db
    }

    @Provides
    @Singleton
    fun provideDao(db: ContactDatabase): ContactDao {
        return db.dao
    }
}