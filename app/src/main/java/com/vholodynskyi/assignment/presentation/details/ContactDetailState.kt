package com.vholodynskyi.assignment.presentation.details

import com.vholodynskyi.assignment.domain.model.Contact

data class ContactDetailState(
    val contact: Contact? = null,
    val error: String = ""
)