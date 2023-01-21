package com.vholodynskyi.assignment.data.repository

import com.vholodynskyi.assignment.common.NetworkResult
import com.vholodynskyi.assignment.data.local.contact.ContactLocalDto
import kotlinx.coroutines.flow.Flow

interface ContactRepository {
    fun observeContact(): Flow<List<ContactLocalDto>>
    suspend fun deleteById(id: Int)
    suspend fun sync(): Flow<NetworkResult>
}