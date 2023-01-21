package com.vholodynskyi.assignment.data.repository

import com.vholodynskyi.assignment.common.NetworkResult
import com.vholodynskyi.assignment.data.local.contact.ContactLocalDto
import com.vholodynskyi.assignment.data.local.contact.LocalDataSource
import com.vholodynskyi.assignment.data.mapper.ContactMapper
import com.vholodynskyi.assignment.data.remote.contact.ContactService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class ContactRepositoryImpl(
    private val service: ContactService,
    private val localDataSource: LocalDataSource,
    private val mapper: ContactMapper
) : ContactRepository {
    override fun observeContact(): Flow<List<ContactLocalDto>> {
        return localDataSource.observeContact()
    }

    override suspend fun deleteById(id: Int) {
        localDataSource.deleteContact(id)
    }

    override suspend fun sync(): Flow<NetworkResult> {
        val networkResult = MutableStateFlow<NetworkResult>(NetworkResult.Loading())
        try {
            val response = service.getContacts()
            if (response.isSuccessful) {
                val remoteData = response.body()!!.results
                val localData = remoteData.map { mapper.fromRemoteToLocal(it) }
                localDataSource.insertContact(localData)
                networkResult.emit(NetworkResult.Success())
            } else {
                networkResult.emit(NetworkResult.Error(response.errorBody().toString()))
            }
        } catch (e: Exception) {
            networkResult.emit(NetworkResult.Error(e.message))
        }
        return networkResult
    }
}