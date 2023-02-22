package com.example.blogging.di

import com.example.blogging.data.prefrences.PreferencesDataStore
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
    fun provideAuthRepository (
        preferencesDataStore: PreferencesDataStore
    ) : AuthRepoImpl = AuthRepoImpl(prefDataSource = preferencesDataStore)

}