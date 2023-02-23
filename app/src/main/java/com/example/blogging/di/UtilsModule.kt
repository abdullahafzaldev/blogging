package com.example.blogging.di

import android.content.Context
import com.example.blogging.domain.compress.CompressRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UtilsModule {


    @Provides
    @Singleton
    fun provideCompressRepository(
        @ApplicationContext context: Context
    ) : CompressRepoImpl = CompressRepoImpl(context)
}