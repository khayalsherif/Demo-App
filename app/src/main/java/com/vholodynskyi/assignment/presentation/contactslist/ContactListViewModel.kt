package com.vholodynskyi.assignment.presentation.contactslist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.vholodynskyi.assignment.base.BackEndError
import com.vholodynskyi.assignment.base.BaseViewModel
import com.vholodynskyi.assignment.base.MessageError
import com.vholodynskyi.assignment.base.NoInternet
import com.vholodynskyi.assignment.domain.useCase.contact.ContactObserveUseCase
import com.vholodynskyi.assignment.domain.useCase.contact.ContactSyncUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ContactListViewModel(
    private val syncUseCase: ContactSyncUseCase,
    private val observeUseCase: ContactObserveUseCase
) : BaseViewModel() {

    private var _contactResponse = mutableStateOf(ContactListState())
    val contactResponse: State<ContactListState>
        get() = _contactResponse

    init {
        syncData()
        getContacts()
        getError()
    }

    fun syncData() = viewModelScope.launch {
        syncUseCase.launch(Unit)
    }

    private fun getContacts() = viewModelScope.launch {
        observeUseCase.execute(Unit).collect {
            _contactResponse.value = ContactListState(contact = it)
        }
    }

    private fun getError() = viewModelScope.launch {
        commonEffect.collect {
            when(it){
                is NoInternet -> _contactResponse.value = _contactResponse.value.copy(error = "Internet")
                is BackEndError ->  _contactResponse.value = _contactResponse.value.copy(error = "Internet")
                is UnknownError ->  _contactResponse.value = _contactResponse.value.copy(error = "Internet")
                is MessageError ->  _contactResponse.value = _contactResponse.value.copy(error = "Internet")
                else -> _contactResponse.value = _contactResponse.value.copy(error = "Internet")
            }
        }
    }

    fun deleteById(id: Int) = viewModelScope.launch {
        syncUseCase.deleteItemById(id)
    }
}
