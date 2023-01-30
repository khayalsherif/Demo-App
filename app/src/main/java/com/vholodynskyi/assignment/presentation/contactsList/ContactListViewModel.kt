package com.vholodynskyi.assignment.presentation.contactsList

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.vholodynskyi.assignment.base.BackEndError
import com.vholodynskyi.assignment.base.BaseViewModel
import com.vholodynskyi.assignment.base.MessageError
import com.vholodynskyi.assignment.base.NoInternet
import com.vholodynskyi.assignment.domain.useCase.contact.ContactObserveUseCase
import com.vholodynskyi.assignment.domain.useCase.contact.ContactSyncUseCase
import kotlinx.coroutines.launch

class ContactListViewModel(
    private val syncUseCase: ContactSyncUseCase,
    private val observeUseCase: ContactObserveUseCase
) : BaseViewModel() {
    
}
