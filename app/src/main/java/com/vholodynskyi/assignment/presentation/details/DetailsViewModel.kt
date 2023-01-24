package com.vholodynskyi.assignment.presentation.details

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

    fun getContactItemById(selectedId: String) = flow {
        observeUseCase.execute(Unit).collect { list ->
            list.forEach {
                if (it.id.toString() == selectedId) emit(it)
            }
        }
    }

    fun deleteById(id: Int) = viewModelScope.launch {
        syncUseCase.deleteItemById(id)
    }
}
