package com.vholodynskyi.assignment.presentation.details

import com.vholodynskyi.assignment.base.BaseViewModel
import com.vholodynskyi.assignment.data.repository.ContactRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class DetailsViewModel(private val repository: ContactRepository) : BaseViewModel() {

    fun getContactItemById(selectedId: String) = flow {
        repository.observeContact().collect { list ->
            list.forEach {
                if (it.id.toString() == selectedId) emit(it)
            }
        }
    }
}
