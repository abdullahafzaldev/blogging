package com.example.blogging.data.prefrences

import com.example.blogging.models.users.AuthUser
import kotlinx.coroutines.flow.Flow

interface PreferencesDataStore {
    fun getUser(): Flow<AuthUser>
}