package com.vholodynskyi.assignment.presentation.contactslist

import androidx.lifecycle.viewModelScope
import com.vholodynskyi.assignment.base.BaseViewModel
import com.vholodynskyi.assignment.domain.model.Contact
import com.vholodynskyi.assignment.domain.useCase.contact.ContactObserveUseCase
import com.vholodynskyi.assignment.domain.useCase.contact.ContactSyncUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ContactListViewModel(
    private val syncUseCase: ContactSyncUseCase,
    private val observeUseCase: ContactObserveUseCase
) : BaseViewModel() {

    private var _contactResponse = MutableStateFlow(emptyList<Contact>())
    val contactResponse: StateFlow<List<Contact>>
        get() = _contactResponse.asStateFlow()

    init {
        syncData()
        getContacts()
    }

    fun syncData() = viewModelScope.launch {
        syncUseCase.launch(Unit)
    }

    private fun getContacts() = viewModelScope.launch {
        observeUseCase.execute(Unit).collect {
            _contactResponse.emit(it)
        }
    }

    fun deleteById(id: Int) = viewModelScope.launch {
        syncUseCase.deleteItemById(id)
    }
}
