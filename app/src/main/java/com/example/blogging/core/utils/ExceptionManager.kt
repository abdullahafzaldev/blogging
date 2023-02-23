package com.example.blogging.core.utils

import com.example.blogging.R
import com.example.blogging.models.customSnack.MessageSnack
import kotlinx.coroutines.channels.Channel
import timber.log.Timber

object ExceptionManager {
    const val NO_INTERNET_CONNECTION = "NO_INTERNET_NETWORK"
    const val SERVER_TIME_OUT = "SERVER_TIME_OUT"

    private fun getMessageForMsgException(exception: Throwable, message: String?): String {
        Timber.e("${message}: $exception")
        return when (exception.message) {
            NO_INTERNET_CONNECTION -> MessageSnack.createInfoMessageEncode(R.string.message_error_internet_checker)
            SERVER_TIME_OUT -> MessageSnack.createRetryMessageEncode(R.string.error_time_out)
            else -> {
//                crash.recordException(exception)
                MessageSnack.createErrorMessageEncode(R.string.message_error_unknown)
            }
        }
    }


    fun sendMessageErrorToException(
        exception: Throwable,
        message: String,
        channel: Channel<String>
    ) {
        val messageUser = getMessageForMsgException(exception, message)
        channel.trySend(messageUser)
    }
}