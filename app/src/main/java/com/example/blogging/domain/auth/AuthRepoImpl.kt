package com.example.blogging.domain.auth

import com.example.blogging.data.prefrences.PreferencesDataStore
import com.example.blogging.models.users.AuthUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepoImpl @Inject constructor(
    private val prefDataSource: PreferencesDataStore
) : AuthRepository {

    override val myUser: Flow<AuthUser> = prefDataSource.getUser()

}