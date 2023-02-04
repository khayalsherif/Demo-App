package com.vholodynskyi.assignment.data.mapper

import com.vholodynskyi.assignment.data.local.contact.ContactLocalDto
import com.vholodynskyi.assignment.data.remote.contact.ContactRemoteDto
import com.vholodynskyi.assignment.domain.model.Contact

class ContactMapper : Mapper<ContactRemoteDto.Result, ContactLocalDto, Contact> {
    override fun fromRemoteToLocal(remote: ContactRemoteDto.Result): ContactLocalDto {
        return ContactLocalDto(
            firstName = remote.name.first,
            lastName = remote.name.last,
            email = remote.email,
            photo = remote.picture.large
        )
    }

    override fun fromLocalToDomain(local: ContactLocalDto): Contact {
        return Contact(local.id, local.firstName, local.lastName, local.email, local.photo)
    }

    override fun fromDomainToLocal(domain: Contact): ContactLocalDto {
        return ContactLocalDto(domain.id, domain.firstName, domain.lastName, domain.email, domain.photo)
    }

}