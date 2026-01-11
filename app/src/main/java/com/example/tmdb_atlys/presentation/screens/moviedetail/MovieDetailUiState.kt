package com.example.tmdb_atlys.presentation.screens.moviedetail

import com.example.tmdb_atlys.domain.model.Movie

/**
 * UI state for the Movie Detail screen.
 */
data class MovieDetailUiState(
    val movie: Movie? = null,
    val isLoading: Boolean = true,
    val error: String? = null
) {
    val hasMovie: Boolean
        get() = movie != null
}
