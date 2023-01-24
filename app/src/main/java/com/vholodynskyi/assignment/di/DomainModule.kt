package com.vholodynskyi.assignment.di

import com.vholodynskyi.assignment.domain.exceptions.ErrorConverter
import com.vholodynskyi.assignment.domain.exceptions.ErrorConverterImpl
import com.vholodynskyi.assignment.domain.useCase.contact.ContactObserveUseCase
import com.vholodynskyi.assignment.domain.useCase.contact.ContactSyncUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val ERROR_MAPPER_NETWORK = "ERROR_MAPPER_NETWORK"
const val IO_CONTEXT = "IO_CONTEXT"

val domainModule = module {

    factory {
        ContactSyncUseCase(context =  get(named(IO_CONTEXT)), converter = get(), repository = get())
    }
    factory {
        ContactObserveUseCase(context =  get(named(IO_CONTEXT)), converter = get(), repository = get())
    }

    single<ErrorConverter> {
        ErrorConverterImpl(
            setOf(
                get(named(ERROR_MAPPER_NETWORK))
            )
        )
    }
}