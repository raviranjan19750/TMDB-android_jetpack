package com.example.tmdb_atlys.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tmdb_atlys.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for movie operations.
 * Provides methods for CRUD operations on the movies table.
 */
@Dao
interface MovieDao {
    
    /**
     * Get all trending movies from cache.
     * @return Flow of list of trending movies
     */
    @Query("SELECT * FROM movies WHERE isTrending = 1 ORDER BY popularity DESC")
    fun getTrendingMovies(): Flow<List<MovieEntity>>
    
    /**
     * Get all trending movies synchronously.
     * @return List of trending movies
     */
    @Query("SELECT * FROM movies WHERE isTrending = 1 ORDER BY popularity DESC")
    suspend fun getTrendingMoviesSync(): List<MovieEntity>
    
    /**
     * Get a single movie by ID.
     * @param movieId The ID of the movie
     * @return The movie entity or null if not found
     */
    @Query("SELECT * FROM movies WHERE id = :movieId")
    suspend fun getMovieById(movieId: Int): MovieEntity?
    
    /**
     * Get a single movie by ID as Flow.
     * @param movieId The ID of the movie
     * @return Flow of the movie entity
     */
    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun getMovieByIdFlow(movieId: Int): Flow<MovieEntity?>
    
    /**
     * Search movies by title (local search).
     * @param query Search query
     * @return List of matching movies
     */
    @Query("SELECT * FROM movies WHERE title LIKE '%' || :query || '%' ORDER BY popularity DESC")
    suspend fun searchMovies(query: String): List<MovieEntity>
    
    /**
     * Insert or update a single movie.
     * @param movie The movie entity to insert/update
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)
    
    /**
     * Insert or update multiple movies.
     * @param movies List of movie entities to insert/update
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)
    
    /**
     * Clear all trending movies from cache.
     */
    @Query("DELETE FROM movies WHERE isTrending = 1")
    suspend fun clearTrendingMovies()
    
    /**
     * Clear all movies from cache.
     */
    @Query("DELETE FROM movies")
    suspend fun clearAllMovies()
    
    /**
     * Delete old cached movies (older than specified time).
     * @param timestamp Threshold timestamp
     */
    @Query("DELETE FROM movies WHERE cachedAt < :timestamp")
    suspend fun deleteOldMovies(timestamp: Long)
}
