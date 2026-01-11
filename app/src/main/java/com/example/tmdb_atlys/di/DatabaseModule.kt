package com.example.tmdb_atlys.di

import android.content.Context
import androidx.room.Room
import com.example.tmdb_atlys.data.local.dao.MovieDao
import com.example.tmdb_atlys.data.local.dao.SearchHistoryDao
import com.example.tmdb_atlys.data.local.database.MovieDatabase
import com.example.tmdb_atlys.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for providing database-related dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    /**
     * Provide Room database instance.
     */
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            Constants.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }
    
    /**
     * Provide MovieDao from database.
     */
    @Provides
    @Singleton
    fun provideMovieDao(database: MovieDatabase): MovieDao {
        return database.movieDao()
    }
    
    /**
     * Provide SearchHistoryDao from database.
     */
    @Provides
    @Singleton
    fun provideSearchHistoryDao(database: MovieDatabase): SearchHistoryDao {
        return database.searchHistoryDao()
    }
}
