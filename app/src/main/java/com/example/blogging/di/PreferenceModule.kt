package com.example.blogging.di

import android.content.Context
import com.example.blogging.data.prefrences.PreferencesDataStore
import com.example.blogging.data.prefrences.PreferencesDataStoreImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object PreferenceModule {

    @Provides
    @Singleton
    fun providePreferencesDataSource(
        @ApplicationContext context: Context,
    ): PreferencesDataStore = PreferencesDataStoreImpl(context)
}