package com.example.tmdb_atlys.presentation.screens.moviedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb_atlys.domain.usecase.GetMovieByIdUseCase
import com.example.tmdb_atlys.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Movie Detail screen.
 * Fetches and displays movie details from local cache.
 */
@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieByIdUseCase: GetMovieByIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    
    private val movieId: Int = checkNotNull(savedStateHandle[Screen.MovieDetail.ARG_MOVIE_ID])
    
    private val _uiState = MutableStateFlow(MovieDetailUiState())
    val uiState: StateFlow<MovieDetailUiState> = _uiState.asStateFlow()
    
    init {
        loadMovie()
    }
    
    /**
     * Load movie details from cache.
     */
    private fun loadMovie() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            try {
                val movie = getMovieByIdUseCase(movieId)
                if (movie != null) {
                    _uiState.update {
                        it.copy(
                            movie = movie,
                            isLoading = false,
                            error = null
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = "Movie not found"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Failed to load movie"
                    )
                }
            }
        }
    }
    
    /**
     * Retry loading movie.
     */
    fun onRetry() {
        loadMovie()
    }
}
