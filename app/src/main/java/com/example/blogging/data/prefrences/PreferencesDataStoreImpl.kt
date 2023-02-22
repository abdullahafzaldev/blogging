package com.example.blogging.data.prefrences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.blogging.models.users.AuthUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferencesDataStoreImpl(
    private val context: Context
) : PreferencesDataStore {

    companion object {
        private const val KEY_ID_USER = "KEY_ID_USER"
        private const val KEY_NAME_USER = "KEY_NAME_USER"
        private const val KEY_TOKEN_USER = "KEY_TOKEN_USER"
        private const val KEY_IMG_USER = "KEY_IMG_USER"

        private const val NAME_PREF_USER = "NAME_PREF_USER"
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = NAME_PREF_USER)

    private val keyId = stringPreferencesKey(KEY_ID_USER)
    private val keyImg = stringPreferencesKey(KEY_IMG_USER)
    private val keyName = stringPreferencesKey(KEY_NAME_USER)
    private val keyToken = stringPreferencesKey(KEY_TOKEN_USER)


    override fun getUser(): Flow<AuthUser> = context.dataStore.data.map { preferences ->
        AuthUser(
            id = preferences[keyId] ?: "",
            name = preferences[keyName] ?: "",
            urlImg = preferences[keyImg] ?: "",
            token = preferences[keyToken] ?: ""
        )
    }


}