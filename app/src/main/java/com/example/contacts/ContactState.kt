package com.example.contacts

data class ContactState (
    val contacts: List<Contacts> = emptyList<Contacts>(),
    val name: String = "",
    val phoneNumber: String = "",
    val isAddingContact: Boolean = false
)