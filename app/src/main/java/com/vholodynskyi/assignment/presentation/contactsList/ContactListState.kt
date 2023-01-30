package com.vholodynskyi.assignment.presentation.contactsList

import com.vholodynskyi.assignment.domain.model.Contact

data class ContactListState(
    val contact: List<Contact> = emptyList(),
    val error: String = ""
)