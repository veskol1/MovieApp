package com.example.data.modules


import com.example.data.interfaces.LocalMovieRepository
import com.example.data.interfaces.MovieRepository
import com.example.data.repository.LocalMovieRepositoryImpl
import com.example.data.repository.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BindRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMovieRepository(impl: MovieRepositoryImpl): MovieRepository

    @Binds
    @Singleton
    abstract fun bindLocalMovieRepository(impl: LocalMovieRepositoryImpl): LocalMovieRepository

}