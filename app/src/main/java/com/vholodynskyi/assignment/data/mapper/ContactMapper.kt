package com.vholodynskyi.assignment.data.mapper

import com.vholodynskyi.assignment.data.local.contact.ContactLocalDto
import com.vholodynskyi.assignment.data.remote.contact.ContactRemoteDto

class ContactMapper : Mapper<ContactRemoteDto.Result, ContactLocalDto> {
    override fun fromRemoteToLocal(remote: ContactRemoteDto.Result): ContactLocalDto {
        return ContactLocalDto(
            firstName = remote.name.first,
            lastName = remote.name.last,
            email = remote.email,
            photo = remote.picture.medium
        )
    }

}