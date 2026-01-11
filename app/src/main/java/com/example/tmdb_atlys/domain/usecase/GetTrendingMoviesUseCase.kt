package com.example.tmdb_atlys.domain.usecase

import com.example.tmdb_atlys.domain.model.Movie
import com.example.tmdb_atlys.domain.repository.MovieRepository
import com.example.tmdb_atlys.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for fetching trending movies.
 * Encapsulates the business logic for getting trending movies.
 */
class GetTrendingMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    /**
     * Execute the use case to get trending movies.
     * @param forceRefresh Force fetch from network
     * @return Flow of Resource containing list of movies
     */
    operator fun invoke(forceRefresh: Boolean = false): Flow<Resource<List<Movie>>> {
        return movieRepository.getTrendingMovies(forceRefresh)
    }
}
