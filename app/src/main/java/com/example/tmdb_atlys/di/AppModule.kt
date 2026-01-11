package com.example.tmdb_atlys.di

import com.example.tmdb_atlys.data.local.dao.MovieDao
import com.example.tmdb_atlys.data.local.dao.SearchHistoryDao
import com.example.tmdb_atlys.data.remote.api.TmdbApi
import com.example.tmdb_atlys.data.repository.MovieRepositoryImpl
import com.example.tmdb_atlys.data.repository.SearchHistoryRepositoryImpl
import com.example.tmdb_atlys.domain.repository.MovieRepository
import com.example.tmdb_atlys.domain.repository.SearchHistoryRepository
import com.example.tmdb_atlys.util.NetworkMonitor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for providing application-level dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    /**
     * Provide MovieRepository implementation.
     */
    @Provides
    @Singleton
    fun provideMovieRepository(
        api: TmdbApi,
        movieDao: MovieDao,
        networkMonitor: NetworkMonitor
    ): MovieRepository {
        return MovieRepositoryImpl(api, movieDao, networkMonitor)
    }
    
    /**
     * Provide SearchHistoryRepository implementation.
     */
    @Provides
    @Singleton
    fun provideSearchHistoryRepository(
        searchHistoryDao: SearchHistoryDao
    ): SearchHistoryRepository {
        return SearchHistoryRepositoryImpl(searchHistoryDao)
    }
}
