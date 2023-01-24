package com.vholodynskyi.assignment.domain.useCase.contact

import com.vholodynskyi.assignment.base.BaseObserveUseCase
import com.vholodynskyi.assignment.domain.exceptions.ErrorConverter
import com.vholodynskyi.assignment.domain.model.Contact
import com.vholodynskyi.assignment.domain.repository.ContactRepository
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

class ContactObserveUseCase(
    context: CoroutineContext,
    converter: ErrorConverter,
    private val repository: ContactRepository
) : BaseObserveUseCase<Unit, List<Contact>>(context, converter) {

    override fun createFlow(params: Unit): Flow<List<Contact>> {
        return repository.observeContact()
    }

}