package com.example.tmdb_atlys.data.repository

import com.example.tmdb_atlys.data.local.dao.MovieDao
import com.example.tmdb_atlys.data.mapper.toDomain
import com.example.tmdb_atlys.data.mapper.toDomainList
import com.example.tmdb_atlys.data.mapper.toEntityList
import com.example.tmdb_atlys.data.remote.api.TmdbApi
import com.example.tmdb_atlys.domain.model.Movie
import com.example.tmdb_atlys.domain.repository.MovieRepository
import com.example.tmdb_atlys.util.NetworkMonitor
import com.example.tmdb_atlys.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of MovieRepository.
 * Implements offline-first strategy: cache → network → update cache.
 */
@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val api: TmdbApi,
    private val movieDao: MovieDao,
    private val networkMonitor: NetworkMonitor
) : MovieRepository {
    
    override fun getTrendingMovies(forceRefresh: Boolean): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading)
        
        // First, emit cached data if available
        val cachedMovies = movieDao.getTrendingMoviesSync()
        if (cachedMovies.isNotEmpty() && !forceRefresh) {
            emit(Resource.Success(cachedMovies.toDomainList()))
        }
        
        // Try to fetch from network if online
        if (networkMonitor.isCurrentlyConnected()) {
            try {
                val response = api.getTrendingMovies()
                val movies = response.results
                
                // Clear old trending movies and insert new ones
                movieDao.clearTrendingMovies()
                movieDao.insertMovies(movies.toEntityList(isTrending = true))
                
                // Emit fresh data
                emit(Resource.Success(movieDao.getTrendingMoviesSync().toDomainList()))
            } catch (e: Exception) {
                // If we already emitted cached data, don't emit error
                if (cachedMovies.isEmpty()) {
                    emit(Resource.Error(
                        message = e.message ?: "Failed to fetch trending movies",
                        throwable = e
                    ))
                }
            }
        } else {
            // Offline: if no cached data, emit error
            if (cachedMovies.isEmpty()) {
                emit(Resource.Error(message = "No internet connection and no cached data available"))
            }
        }
    }
    
    override fun searchMovies(query: String): Flow<Resource<List<Movie>>> = flow {
        if (query.isBlank()) {
            emit(Resource.Success(emptyList()))
            return@flow
        }
        
        emit(Resource.Loading)
        
        // Search from network if online
        if (networkMonitor.isCurrentlyConnected()) {
            try {
                val response = api.searchMovies(query = query)
                val movies = response.results
                
                // Cache search results
                movieDao.insertMovies(movies.toEntityList(isTrending = false))
                
                emit(Resource.Success(movies.map { it.toDomain() }))
            } catch (e: Exception) {
                // Fallback to local search
                val localResults = movieDao.searchMovies(query)
                if (localResults.isNotEmpty()) {
                    emit(Resource.Success(localResults.toDomainList()))
                } else {
                    emit(Resource.Error(
                        message = e.message ?: "Search failed",
                        throwable = e
                    ))
                }
            }
        } else {
            // Offline: search locally
            val localResults = movieDao.searchMovies(query)
            if (localResults.isNotEmpty()) {
                emit(Resource.Success(localResults.toDomainList()))
            } else {
                emit(Resource.Error(message = "No internet connection. Search results may be limited."))
            }
        }
    }
    
    override suspend fun getMovieById(movieId: Int): Movie? {
        return movieDao.getMovieById(movieId)?.toDomain()
    }
    
    override fun getMovieByIdFlow(movieId: Int): Flow<Movie?> {
        return movieDao.getMovieByIdFlow(movieId).map { it?.toDomain() }
    }
}
