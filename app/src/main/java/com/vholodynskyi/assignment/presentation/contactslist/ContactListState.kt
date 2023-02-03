package com.vholodynskyi.assignment.presentation.contactslist

import com.vholodynskyi.assignment.domain.model.Contact

data class ContactListState(
    val contact: List<Contact> = emptyList(),
)