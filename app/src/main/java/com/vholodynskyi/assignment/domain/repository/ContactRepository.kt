package com.vholodynskyi.assignment.domain.repository

import com.vholodynskyi.assignment.data.local.contact.ContactLocalDto
import com.vholodynskyi.assignment.domain.model.Contact
import kotlinx.coroutines.flow.Flow

interface ContactRepository {
    fun observeContact(): Flow<List<Contact>>
    suspend fun deleteById(id: Int)
    suspend fun sync()
    suspend fun updateContact(contact: Contact)
}