package com.example.blogging.domain.auth

import com.example.blogging.core.utils.callApiTimeOut
import com.example.blogging.data.local.prefrences.PreferencesDataStore
import com.example.blogging.data.remote.auth.AuthDataSource
import com.example.blogging.models.users.AuthUser
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepoImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val prefDataSource: PreferencesDataStore
) : AuthRepository {

    override val myUser: Flow<AuthUser> = prefDataSource.getUser()
    override suspend fun authWithCredential(authCredential: AuthCredential) {
        // * authenticate user and save data user
        val user = callApiTimeOut { authDataSource.authWithCredential(authCredential) }
        prefDataSource.updateUser(user)
    }

}