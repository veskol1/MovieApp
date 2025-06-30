package com.example.movieapp.di

import android.content.Context
import com.example.movieapp.connectivity.NetworkConnectivityManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideConnectivity(@ApplicationContext appContext: Context): NetworkConnectivityManager {
        return NetworkConnectivityManager(appContext)
    }


}