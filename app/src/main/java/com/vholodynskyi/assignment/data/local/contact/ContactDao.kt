package com.vholodynskyi.assignment.data.local.contact

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Query("SELECT * FROM Contact")
    fun getContacts(): Flow<List<ContactLocalDto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(contact: List<ContactLocalDto>)

    @Query("DELETE FROM Contact WHERE id = (:contactId)")
    suspend fun deleteById(contactId: Int)

    @Query("DELETE FROM Contact")
    suspend fun deleteAll()
}