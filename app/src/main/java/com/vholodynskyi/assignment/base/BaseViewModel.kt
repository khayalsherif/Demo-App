package com.vholodynskyi.assignment.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vholodynskyi.assignment.R
import com.vholodynskyi.assignment.common.SingleLiveEvent
import com.vholodynskyi.assignment.domain.exceptions.NetworkError
import com.vholodynskyi.assignment.domain.exceptions.ServerError
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseViewModel : ViewModel() {

    private val _commonEffect = SingleLiveEvent<BaseEffect>()
    val commonEffect: LiveData<BaseEffect>
        get() = _commonEffect

    protected fun <P, R, U : BaseSyncUseCase<P, R>> U.launch(
        param: P,
        block: Block<R> = {},
    ) {
        viewModelScope.launch {
            val request = BaseSyncUseCase.Request<R>().apply(block)

            val proxy: Block<R> = {
                onStart = {
                    request.onStart?.invoke()
                }
                onSuccess = {
                    request.onSuccess(it)
                }
                onCancel = {
                    request.onCancel?.invoke(it)
                }
                onTerminate = {
                    request.onTerminate
                }
                onError = {
                    request.onError?.invoke(it) ?: handleError(it)
                }
            }
            execute(param, proxy)
        }
    }

    private fun handleError(t: Throwable) {
        Timber.e(t)
        when (t) {
            is ServerError.ServerIsDown -> _commonEffect.postValue(BackEndError())
            is ServerError.Unexpected -> _commonEffect.postValue(MessageError(R.string.error_unexpected))
            is NetworkError -> _commonEffect.postValue(NoInternet())
            else -> _commonEffect.postValue(UnknownError(cause = t))
        }
    }

}