package com.example.tmdb_atlys.presentation.screens.movielist

import com.example.tmdb_atlys.domain.model.Movie

/**
 * UI state for the Movie List screen.
 * Represents all possible states of the screen.
 */
data class MovieListUiState(
    val movies: List<Movie> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isSearching: Boolean = false,
    val error: String? = null,
    val isOffline: Boolean = false,
    val searchHistory: List<String> = emptyList(),
    val showSearchHistory: Boolean = false
) {
    val isEmpty: Boolean
        get() = movies.isEmpty() && !isLoading && error == null
    
    val isSearchActive: Boolean
        get() = searchQuery.isNotEmpty()
    
    val shouldShowEmptyState: Boolean
        get() = isEmpty && !isLoading && error == null
    
    val shouldShowError: Boolean
        get() = error != null && movies.isEmpty()
}
