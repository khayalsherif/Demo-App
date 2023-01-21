package com.vholodynskyi.assignment.data.remote.contact

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ContactService {
    @GET("api/")
    suspend fun getContacts(@Query("results") limit: Int = 30): Response<ContactRemoteDto>
}