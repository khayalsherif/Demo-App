package com.vholodynskyi.assignment.domain.exceptions

fun interface ErrorMapper {
    fun mapError(throwable: Throwable): Throwable
}