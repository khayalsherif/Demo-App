package com.vholodynskyi.assignment.presentation.edit

import com.vholodynskyi.assignment.domain.model.Contact

class ContactEditState(
    val contact: Contact? = null,
    val error: String = ""
) {
}