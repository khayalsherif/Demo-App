package com.vholodynskyi.assignment.di

import com.vholodynskyi.assignment.presentation.contactslist.ContactListViewModel
import com.vholodynskyi.assignment.presentation.details.DetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { ContactListViewModel(syncUseCase = get(), observeUseCase = get()) }
    viewModel { DetailsViewModel(syncUseCase = get(), observeUseCase = get(), get()) }
}