package com.example.blogging.core.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blogging.core.utils.ExceptionManager.NO_INTERNET_CONNECTION
import com.example.blogging.core.utils.ExceptionManager.SERVER_TIME_OUT
import kotlinx.coroutines.*


suspend fun <T> callApiTimeOut(
    timeOut: Long = 5000,
    callApi: suspend () -> T
): T {
    if (!InternetCheck.isNetworkAvailable()) throw Exception(NO_INTERNET_CONNECTION)
    return try {
        withTimeout(timeOut) {
            callApi()
        }
    } catch (e: TimeoutCancellationException) {
        throw Exception(SERVER_TIME_OUT)
    }
}



fun ViewModel.launchSafeIO(
    isEnabled: Boolean = true,
    blockBefore: suspend CoroutineScope.() -> Unit = {},
    blockAfter: suspend CoroutineScope.(Boolean) -> Unit = {},
    blockException: suspend CoroutineScope.(Exception) -> Unit = {},
    blockIO: suspend CoroutineScope.() -> Unit,
): Job? {
    return if (isEnabled) {
        var isForCancelled = false
        viewModelScope.launch {
            try {
                blockBefore()
                withContext(Dispatchers.IO) { blockIO() }
            } catch (e: Exception) {
                when (e) {
                    is CancellationException -> {
                        isForCancelled = true
                        throw e
                    }
                    else -> blockException(e)
                }
            } finally {
                blockAfter(isForCancelled)
            }
        }
    } else {
        null
    }
}