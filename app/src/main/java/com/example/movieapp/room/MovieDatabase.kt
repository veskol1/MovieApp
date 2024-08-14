package com.example.movieapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movieapp.model.Movie

@Database(entities = [Movie::class], version = 1)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
}