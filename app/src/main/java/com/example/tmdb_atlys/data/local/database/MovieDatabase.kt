package com.example.tmdb_atlys.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tmdb_atlys.data.local.dao.MovieDao
import com.example.tmdb_atlys.data.local.dao.SearchHistoryDao
import com.example.tmdb_atlys.data.local.entity.MovieEntity
import com.example.tmdb_atlys.data.local.entity.SearchHistoryEntity

/**
 * Room database for the TMDB app.
 * Contains tables for movies and search history.
 */
@Database(
    entities = [
        MovieEntity::class,
        SearchHistoryEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    
    /**
     * Get the MovieDao for movie operations.
     */
    abstract fun movieDao(): MovieDao
    
    /**
     * Get the SearchHistoryDao for search history operations.
     */
    abstract fun searchHistoryDao(): SearchHistoryDao
}
