package com.example.contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor (
    private val dao: ContactDao
): ViewModel() {

    private val _state = MutableStateFlow(ContactState())
    private val _contacts = dao.getContacts().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList<Contacts>())
    val state = combine(_state,_contacts) { state, contacts ->
        state.copy(
            contacts = contacts,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ContactState())

    fun onEvent(event: ContactEvent) {
        when (event) {
            is ContactEvent.DeleteContact -> {
                viewModelScope.launch {
                    dao.deleteContact(contact = event.contact)
                }
            }
            ContactEvent.HideDialog -> {
                _state.update { it.copy(
                        isAddingContact = false
                    )
                }
            }
            ContactEvent.SaveContact -> {
                val name = state.value.name
                val phoneNumber = state.value.phoneNumber
                if (name.isBlank() || phoneNumber.isBlank()) {
                    return
                }
                val contact = Contacts(name = name, contactNumber = phoneNumber)
                viewModelScope.launch {
                    dao.upsertContact(contact)
                    }
                _state.update { it.copy(
                    isAddingContact = false,
                    name = "",
                    phoneNumber = ""
                ) }
            }
            is ContactEvent.SetContactNumber -> {
                _state.update { it.copy(
                    phoneNumber = event.number
                    )
                }
            }
            is ContactEvent.SetName -> {
                _state.update { it.copy(
                    name = event.name
                    )
                }
            }
            ContactEvent.ShoeDialog -> {
                _state.update { it.copy(
                    isAddingContact = true
                )
                }
            }
        }
    }
}