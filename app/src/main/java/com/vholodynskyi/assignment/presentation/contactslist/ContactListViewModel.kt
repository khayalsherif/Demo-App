package com.vholodynskyi.assignment.presentation.contactslist

import androidx.lifecycle.viewModelScope
import com.vholodynskyi.assignment.base.BaseViewModel
import com.vholodynskyi.assignment.common.NetworkResult
import com.vholodynskyi.assignment.data.local.contact.ContactLocalDto
import com.vholodynskyi.assignment.data.repository.ContactRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ContactListViewModel(private val repository: ContactRepository) : BaseViewModel() {

    private var _contactResponse = MutableStateFlow(emptyList<ContactLocalDto>())
    val contactResponse: StateFlow<List<ContactLocalDto>>
        get() = _contactResponse.asStateFlow()

    private val _syncStatus = MutableStateFlow<NetworkResult>(NetworkResult.Loading())
    val syncStatus: StateFlow<NetworkResult> get() = _syncStatus.asStateFlow()

    init {
        syncData()
        getContacts()
    }

    fun syncData() = viewModelScope.launch {
        repository.sync().collect {
            _syncStatus.emit(it)
        }
    }

    private fun getContacts() = viewModelScope.launch {
        repository.observeContact().collect {
            _contactResponse.emit(it)
        }
    }

    fun deleteById(id: Int) = viewModelScope.launch {
        repository.deleteById(id)
    }
}
