package com.vholodynskyi.assignment.presentation.details

import androidx.lifecycle.viewModelScope
import com.vholodynskyi.assignment.base.BaseViewModel
import com.vholodynskyi.assignment.data.repository.ContactRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class DetailsViewModel(private val repository: ContactRepository) : BaseViewModel() {

    fun getContactItemById(selectedId: String) = flow {
        repository.observeContact().collect { list ->
            list.forEach {
                if (it.id.toString() == selectedId) emit(it)
            }
        }
    }

    fun deleteById(id: Int) = viewModelScope.launch {
        repository.deleteById(id)
    }
}
