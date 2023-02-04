package com.vholodynskyi.assignment.data.local.contact

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class LocalDataSourceImpl(private val dao: ContactDao) : LocalDataSource {
    override fun observeContact(): Flow<List<ContactLocalDto>> {
        return dao.getContacts()
    }

    override suspend fun insertContact(contactList: List<ContactLocalDto>) {
        withContext(Dispatchers.IO) {
            dao.deleteAll()
            dao.addAll(contactList)
        }
    }

    override suspend fun deleteContact(id: Int) {
        dao.deleteById(id)
    }

    override suspend fun updateContact(contact: ContactLocalDto) {
        dao.updateContact(contact)
    }
}