package com.example.tmdb_atlys.domain.usecase

import com.example.tmdb_atlys.domain.model.Movie
import com.example.tmdb_atlys.domain.repository.MovieRepository
import com.example.tmdb_atlys.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for searching movies.
 * Encapsulates the business logic for movie search.
 */
class SearchMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    /**
     * Execute the use case to search movies.
     * @param query Search query string
     * @return Flow of Resource containing list of matching movies
     */
    operator fun invoke(query: String): Flow<Resource<List<Movie>>> {
        return movieRepository.searchMovies(query)
    }
}
