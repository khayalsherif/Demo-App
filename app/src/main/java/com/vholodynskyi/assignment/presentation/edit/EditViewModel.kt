package com.vholodynskyi.assignment.presentation.edit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.vholodynskyi.assignment.base.BaseViewModel
import com.vholodynskyi.assignment.domain.model.Contact
import com.vholodynskyi.assignment.domain.useCase.contact.ContactObserveUseCase
import com.vholodynskyi.assignment.domain.useCase.contact.ContactSyncUseCase
import com.vholodynskyi.assignment.presentation.details.ContactDetailState
import kotlinx.coroutines.launch

class EditViewModel(
    private val syncUseCase: ContactSyncUseCase,
    private val observeUseCase: ContactObserveUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _state = mutableStateOf(ContactDetailState())
    val state: State<ContactDetailState> = _state

    init {
        savedStateHandle.get<String>("contactId")?.let { coinId ->
            getContactItemById(coinId)
        }
    }

    private fun getContactItemById(selectedId: String) = viewModelScope.launch {
        observeUseCase.execute(Unit).collect { list ->
            list.forEach {
                if (it.id.toString() == selectedId) _state.value = ContactDetailState(contact = it)
            }
        }
    }

    fun updateContact(contact: Contact) = viewModelScope.launch {
        syncUseCase.updateContact(contact = contact)
    }
}