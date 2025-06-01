package com.example.contacts

sealed interface ContactEvent {
    object SaveContact: ContactEvent
    data class SetName(val name: String): ContactEvent
    data class SetContactNumber(val number: String): ContactEvent
    object ShoeDialog: ContactEvent
    object HideDialog: ContactEvent
    data class DeleteContact(val contact: Contacts): ContactEvent
}