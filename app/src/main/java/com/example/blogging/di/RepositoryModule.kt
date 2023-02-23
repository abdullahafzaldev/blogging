package com.example.blogging.di

import com.example.blogging.domain.auth.AuthRepoImpl
import com.example.blogging.domain.auth.AuthRepository
import com.example.blogging.domain.compress.CompressRepoImpl
import com.example.blogging.domain.compress.CompressRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideAuthRepository(
        authRepoImpl: AuthRepoImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun provideCompressRepository(
        compressRepoImpl: CompressRepoImpl
    ): CompressRepository

}