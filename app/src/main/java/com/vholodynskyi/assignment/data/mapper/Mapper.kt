package com.vholodynskyi.assignment.data.mapper

interface Mapper<REMOTE, LOCAL> {
    fun fromRemoteToLocal(remote: REMOTE): LOCAL
}