package com.image.viewer.demo.di

import android.content.Context
import com.image.viewer.demo.api.ApiClient
import com.image.viewer.demo.api.ApiService
import com.image.viewer.demo.utilities.PreferenceHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    operator fun invoke(): ApiService {
        return ApiClient.create()
    }

    @Provides
    @Singleton
    fun provideSharedPreference(@ApplicationContext context: Context): PreferenceHelper {
        return PreferenceHelper(context)
    }

}