package com.example.blogging.data.remote.auth

import com.example.blogging.models.users.AuthUser
import com.google.firebase.auth.AuthCredential

interface AuthDataSource {
    suspend fun authWithCredential(authCredential: AuthCredential): AuthUser
    suspend fun addingTokenUser(
        newToken: String? = null,
        uuidUser: String? = null,
        oldToken: String = ""
    )
}