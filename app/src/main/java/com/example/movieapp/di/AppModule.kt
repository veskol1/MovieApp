package com.example.movieapp.di

import android.content.Context
import androidx.room.Room
import com.example.movieapp.api.ApiService
import com.example.movieapp.constans.Constants.API_BASE_URL
import com.example.movieapp.repository.MovieRepository
import com.example.movieapp.room.MovieDao
import com.example.movieapp.room.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun getApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext appContext: Context): MovieDatabase {
        return Room.databaseBuilder(appContext,
            MovieDatabase::class.java, "database name"
        ).build()
    }

    @Provides
    fun provideDao(movieDatabase: MovieDatabase): MovieDao = movieDatabase.movieDao()

    @Provides
    @Singleton
    fun movieRepositoryProvide(movieApiService: ApiService, movieDao: MovieDao): MovieRepository {
        return MovieRepository(movieApiService, movieDao)
    }

}