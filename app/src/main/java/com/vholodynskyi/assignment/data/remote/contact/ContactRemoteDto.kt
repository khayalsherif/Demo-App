package com.vholodynskyi.assignment.data.remote.contact

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ContactRemoteDto(
    val results: List<Result>
) {
    @JsonClass(generateAdapter = true)
    data class Result(
        val cell: String,
        val email: String,
        val gender: String,
        val name: Name,
        val nat: String,
        val phone: String,
        val picture: Picture,
    )

    @JsonClass(generateAdapter = true)
    data class Name(
        val first: String,
        val last: String,
        val title: String
    )

    @JsonClass(generateAdapter = true)
    data class Picture(
        val large: String,
        val medium: String,
        val thumbnail: String
    )
}