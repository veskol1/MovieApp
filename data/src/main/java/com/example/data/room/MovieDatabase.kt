package com.example.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.models.Movie


@Database(entities = [Movie::class], version = 2)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
}