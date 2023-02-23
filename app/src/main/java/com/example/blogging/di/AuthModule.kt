package com.example.blogging.di

import com.example.blogging.data.local.prefrences.PreferencesDataStore
import com.example.blogging.data.remote.auth.AuthDataSource
import com.example.blogging.data.remote.auth.AuthDataStoreImpl
import com.example.blogging.domain.auth.AuthRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthDataSource(): AuthDataSource =
        AuthDataStoreImpl()

    @Provides
    @Singleton
    fun provideAuthRepository (
        preferencesDataStore: PreferencesDataStore,
        authDataSource: AuthDataSource
    ) : AuthRepoImpl = AuthRepoImpl(prefDataSource = preferencesDataStore , authDataSource = authDataSource)

}