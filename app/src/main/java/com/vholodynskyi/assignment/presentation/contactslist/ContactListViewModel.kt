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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class ContactListViewModel(
    private val syncUseCase: ContactSyncUseCase, private val observeUseCase: ContactObserveUseCase
) : BaseViewModel() {

    private var _contactResponse = mutableStateOf(ContactListState())
    val contactResponse: State<ContactListState>
        get() = _contactResponse

    private var _baseEffect = MutableSharedFlow<String>()
    val baseEffect: SharedFlow<String>
        get() = _baseEffect.asSharedFlow()

    init {
        viewModelScope.launch {
            _baseEffect.emit("")
        }
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
            when (it) {
                is NoInternet -> _baseEffect.emit("Internet connection error")
                is BackEndError -> _baseEffect.emit("Backend error")
                is UnknownError -> _baseEffect.emit("Unknown error")
                is MessageError -> _baseEffect.emit("Error")
                else -> _baseEffect.emit("Error")
            }
        }
    }

    fun deleteById(id: Int) = viewModelScope.launch {
        syncUseCase.deleteItemById(id)
    }
}
