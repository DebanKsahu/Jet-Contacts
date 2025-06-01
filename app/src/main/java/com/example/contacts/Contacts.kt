package com.example.contacts

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Contacts(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val name: String,
    val contactNumber: String
)
