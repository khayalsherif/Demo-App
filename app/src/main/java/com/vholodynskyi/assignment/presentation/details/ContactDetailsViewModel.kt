package com.vholodynskyi.assignment.presentation.details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.vholodynskyi.assignment.base.BaseViewModel
import com.vholodynskyi.assignment.domain.useCase.contact.ContactObserveUseCase
import com.vholodynskyi.assignment.domain.useCase.contact.ContactSyncUseCase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val syncUseCase: ContactSyncUseCase,
    private val observeUseCase: ContactObserveUseCase
) : BaseViewModel() {

    private val _state = mutableStateOf(ContactDetailState())
    val state: State<ContactDetailState> = _state

    fun getContactItemById(selectedId: String) = viewModelScope.launch {
        observeUseCase.execute(Unit).collect { list ->
            list.forEach {
                if (it.id.toString() == selectedId) _state.value = ContactDetailState(contact = it)
            }
        }
    }

    fun deleteById(id: Int) = viewModelScope.launch {
        syncUseCase.deleteItemById(id)
    }
}
