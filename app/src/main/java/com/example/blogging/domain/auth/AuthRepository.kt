package com.example.blogging.domain.auth

import com.example.blogging.models.users.AuthUser
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
 val myUser : Flow<AuthUser>
 suspend fun authWithCredential(authCredential: AuthCredential)
}