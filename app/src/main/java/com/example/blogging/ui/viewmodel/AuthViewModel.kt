package com.example.blogging.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blogging.core.states.LoginStatus
import com.example.blogging.domain.auth.AuthRepository
import com.example.blogging.models.users.AuthUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

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