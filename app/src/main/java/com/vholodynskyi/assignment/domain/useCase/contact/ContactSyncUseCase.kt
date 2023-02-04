package com.vholodynskyi.assignment.domain.useCase.contact

import com.vholodynskyi.assignment.base.BaseSyncUseCase
import com.vholodynskyi.assignment.domain.exceptions.ErrorConverter
import com.vholodynskyi.assignment.domain.model.Contact
import com.vholodynskyi.assignment.domain.repository.ContactRepository
import kotlin.coroutines.CoroutineContext

class ContactSyncUseCase(
    context: CoroutineContext,
    converter: ErrorConverter,
    private val repository: ContactRepository
) : BaseSyncUseCase<Unit, Unit>(context, converter) {

    override suspend fun executeOnBackground(params: Unit) {
        repository.sync()
    }

    suspend fun deleteItemById(id: Int) {
        repository.deleteById(id)
    }

    suspend fun updateContact(contact: Contact) {
        repository.updateContact(contact = contact)
    }

}