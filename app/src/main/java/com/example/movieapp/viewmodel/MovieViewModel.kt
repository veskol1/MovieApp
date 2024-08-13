package com.example.movieapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {


    init {
        viewModelScope.launch {
            val movies = repository.fetchUpcomingMovies()
            Log.d("haha","movies="+movies)
        }
    }

}