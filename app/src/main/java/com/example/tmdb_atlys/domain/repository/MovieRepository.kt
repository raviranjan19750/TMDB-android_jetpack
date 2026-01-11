package com.example.tmdb_atlys.domain.repository

import com.example.tmdb_atlys.domain.model.Movie
import com.example.tmdb_atlys.util.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for movie operations.
 * Defines the contract for fetching and caching movies.
 */
interface MovieRepository {
    
    /**
     * Get trending movies with offline-first strategy.
     * Returns cached data first, then fetches from network and updates cache.
     * @param forceRefresh Force fetch from network even if cache is available
     * @return Flow of Resource containing list of movies
     */
    fun getTrendingMovies(forceRefresh: Boolean = false): Flow<Resource<List<Movie>>>
    
    /**
     * Search for movies by query.
     * Searches locally first, then from network if online.
     * @param query Search query string
     * @return Flow of Resource containing list of matching movies
     */
    fun searchMovies(query: String): Flow<Resource<List<Movie>>>
    
    /**
     * Get a single movie by ID.
     * @param movieId The ID of the movie
     * @return The movie or null if not found
     */
    suspend fun getMovieById(movieId: Int): Movie?
    
    /**
     * Get a single movie by ID as Flow.
     * @param movieId The ID of the movie
     * @return Flow of the movie
     */
    fun getMovieByIdFlow(movieId: Int): Flow<Movie?>
}
