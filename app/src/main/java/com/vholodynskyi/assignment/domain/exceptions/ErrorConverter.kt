package com.vholodynskyi.assignment.domain.exceptions

interface ErrorConverter {
    fun convert(throwable: Throwable): Throwable
}