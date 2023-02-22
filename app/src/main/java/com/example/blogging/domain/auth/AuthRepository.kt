package com.example.blogging.domain.auth

import com.example.blogging.models.users.AuthUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
 val myUser : Flow<AuthUser>
}