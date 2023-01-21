package com.vholodynskyi.assignment.data.local.contact

import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun observeContact(): Flow<List<ContactLocalDto>>
    suspend fun insertContact(contactList: List<ContactLocalDto>)
    suspend fun deleteContact(id: Int)
}