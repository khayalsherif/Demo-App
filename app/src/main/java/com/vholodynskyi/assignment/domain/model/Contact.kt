package com.vholodynskyi.assignment.domain.model

data class Contact(
    val id: Int,
    val firstName: String?,
    val lastName: String?,
    val email: String?,
    val photo: String?,
)