package com.vholodynskyi.assignment.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vholodynskyi.assignment.R
import com.vholodynskyi.assignment.domain.exceptions.NetworkError
import com.vholodynskyi.assignment.domain.exceptions.ServerError
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseViewModel : ViewModel() {

    private val _commonEffect = Channel<BaseEffect>()
    val commonEffect: Flow<BaseEffect>
        get() = _commonEffect.receiveAsFlow()

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
        viewModelScope.launch {
            when (t) {
                is ServerError.ServerIsDown -> _commonEffect.send(BackEndError())
                is ServerError.Unexpected -> _commonEffect.send(MessageError(R.string.error_unexpected))
                is NetworkError -> _commonEffect.send(NoInternet())
                else -> _commonEffect.send(UnknownError(cause = t))
            }
        }
    }

}