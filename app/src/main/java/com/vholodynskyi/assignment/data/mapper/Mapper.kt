package com.vholodynskyi.assignment.data.mapper

interface Mapper<REMOTE, LOCAL, DOMAIN> {
    fun fromRemoteToLocal(remote: REMOTE): LOCAL
    fun fromLocalToDomain(local: LOCAL): DOMAIN
}