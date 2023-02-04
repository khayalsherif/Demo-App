package com.vholodynskyi.assignment.data.repository

import com.vholodynskyi.assignment.data.local.contact.LocalDataSource
import com.vholodynskyi.assignment.data.mapper.ContactMapper
import com.vholodynskyi.assignment.data.remote.contact.ContactService
import com.vholodynskyi.assignment.domain.model.Contact
import com.vholodynskyi.assignment.domain.repository.ContactRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ContactRepositoryImpl(
    private val service: ContactService,
    private val localDataSource: LocalDataSource,
    private val mapper: ContactMapper
) : ContactRepository {
    override fun observeContact(): Flow<List<Contact>> {
        return localDataSource.observeContact()
            .map { list -> list.map { mapper.fromLocalToDomain(it) } }
    }

    override suspend fun deleteById(id: Int) {
        localDataSource.deleteContact(id)
    }

    override suspend fun sync() {
        val response = service.getContacts()
        val remoteData = response.body()!!.results
        val localData = remoteData.map { mapper.fromRemoteToLocal(it) }
        localDataSource.insertContact(localData)
    }

    override suspend fun updateContact(contact: Contact) {
        localDataSource.updateContact(contact = mapper.fromDomainToLocal(contact))
    }
}