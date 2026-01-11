package com.example.tmdb_atlys.domain.usecase

import com.example.tmdb_atlys.domain.model.Movie
import com.example.tmdb_atlys.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for getting a single movie by ID.
 * Encapsulates the business logic for fetching movie details.
 */
class GetMovieByIdUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    /**
     * Execute the use case to get a movie by ID.
     * @param movieId The ID of the movie
     * @return The movie or null if not found
     */
    suspend operator fun invoke(movieId: Int): Movie? {
        return movieRepository.getMovieById(movieId)
    }
    
    /**
     * Get movie by ID as Flow for observing changes.
     * @param movieId The ID of the movie
     * @return Flow of the movie
     */
    fun asFlow(movieId: Int): Flow<Movie?> {
        return movieRepository.getMovieByIdFlow(movieId)
    }
}
