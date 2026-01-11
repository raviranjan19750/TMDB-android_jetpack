package com.example.tmdb_atlys.presentation.navigation

/**
 * Sealed class representing all screens in the app.
 * Used for type-safe navigation with Compose Navigation.
 */
sealed class Screen(val route: String) {
    
    /**
     * Movie list screen - displays trending movies with search.
     */
    data object MovieList : Screen("movie_list")
    
    /**
     * Movie detail screen - displays details of a selected movie.
     * @param movieId The ID of the movie to display
     */
    data object MovieDetail : Screen("movie_detail/{movieId}") {
        fun createRoute(movieId: Int): String = "movie_detail/$movieId"
        
        const val ARG_MOVIE_ID = "movieId"
    }
}
