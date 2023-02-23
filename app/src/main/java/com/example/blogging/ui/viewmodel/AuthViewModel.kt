package com.example.blogging.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blogging.core.states.LoginStatus
import com.example.blogging.core.utils.launchSafeIO
import com.example.blogging.domain.auth.AuthRepository
import com.example.blogging.models.users.AuthUser
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject
import com.example.blogging.R

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val AuthUser.isUserAuth get() = id.isNotEmpty()
    private val AuthUser.isDataComplete get() = name.isNotEmpty() && urlImg.isNotEmpty()


    private val _messageAuth = Channel<Int>()
    val messageAuth = _messageAuth.receiveAsFlow()

    var isProcessing by mutableStateOf(false)
        private set



    fun authWithCredential(
        authCredential: AuthCredential
    ) = launchSafeIO(
        blockBefore = { isProcessing = true },
        blockAfter = { isProcessing = false },
        blockException = {
            Timber.e("Error al auth $it")
            _messageAuth.trySend(R.string.message_error_auth)
        },
        blockIO = { authRepository.authWithCredential(authCredential) }
    )


    val stateUser = authRepository.myUser.transform { user ->
        val stateUser = when {
            !user.isUserAuth -> LoginStatus.Unauthenticated
            user.isDataComplete -> LoginStatus.Authenticated.CompleteData
            else -> LoginStatus.Authenticated.CompletingData
        }
        emit(stateUser)


    }.catch {
        emit(LoginStatus.Unauthenticated)
    }.flowOn(
        Dispatchers.IO
    ).stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), LoginStatus.Authenticating
    )


}