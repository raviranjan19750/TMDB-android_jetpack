package com.example.tmdb_atlys.presentation.screens.movielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb_atlys.domain.usecase.GetTrendingMoviesUseCase
import com.example.tmdb_atlys.domain.usecase.SearchHistoryUseCase
import com.example.tmdb_atlys.domain.usecase.SearchMoviesUseCase
import com.example.tmdb_atlys.util.Constants
import com.example.tmdb_atlys.util.NetworkMonitor
import com.example.tmdb_atlys.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Movie List screen.
 * Handles trending movies, search, and search history.
 */
@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val searchHistoryUseCase: SearchHistoryUseCase,
    private val networkMonitor: NetworkMonitor
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(MovieListUiState())
    val uiState: StateFlow<MovieListUiState> = _uiState.asStateFlow()
    
    private val _searchQuery = MutableStateFlow("")
    
    private var searchJob: Job? = null
    
    init {
        observeNetworkStatus()
        observeSearchQuery()
        observeSearchHistory()
        loadTrendingMovies()
    }
    
    /**
     * Observe network connectivity status.
     */
    private fun observeNetworkStatus() {
        networkMonitor.isOnline
            .onEach { isOnline ->
                _uiState.update { it.copy(isOffline = !isOnline) }
            }
            .launchIn(viewModelScope)
    }
    
    /**
     * Observe search query changes with debounce.
     */
    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        _searchQuery
            .debounce(Constants.SEARCH_DEBOUNCE_MILLIS)
            .distinctUntilChanged()
            .onEach { query ->
                if (query.isBlank()) {
                    loadTrendingMovies()
                } else {
                    searchMovies(query)
                }
            }
            .launchIn(viewModelScope)
    }
    
    /**
     * Observe search history.
     */
    private fun observeSearchHistory() {
        searchHistoryUseCase.getRecentSearches()
            .onEach { history ->
                _uiState.update { it.copy(searchHistory = history) }
            }
            .launchIn(viewModelScope)
    }
    
    /**
     * Load trending movies.
     */
    fun loadTrendingMovies(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            getTrendingMoviesUseCase(forceRefresh).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update { 
                            it.copy(
                                isLoading = !forceRefresh && it.movies.isEmpty(),
                                isRefreshing = forceRefresh,
                                error = null
                            )
                        }
                    }
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                movies = result.data,
                                isLoading = false,
                                isRefreshing = false,
                                isSearching = false,
                                error = null
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                isRefreshing = false,
                                isSearching = false,
                                error = result.message
                            )
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Search movies by query.
     */
    private fun searchMovies(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            searchMoviesUseCase(query).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(
                                isSearching = true,
                                error = null
                            )
                        }
                    }
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                movies = result.data,
                                isLoading = false,
                                isSearching = false,
                                error = null
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                isSearching = false,
                                error = result.message
                            )
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Update search query.
     */
    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        _searchQuery.value = query
    }
    
    /**
     * Execute search and save to history.
     */
    fun onSearch(query: String) {
        if (query.isNotBlank()) {
            viewModelScope.launch {
                searchHistoryUseCase.saveSearch(query)
            }
            _uiState.update { it.copy(showSearchHistory = false) }
        }
    }
    
    /**
     * Show/hide search history dropdown.
     */
    fun onSearchFocusChange(hasFocus: Boolean) {
        _uiState.update { 
            it.copy(showSearchHistory = hasFocus && it.searchQuery.isEmpty())
        }
    }
    
    /**
     * Select item from search history.
     */
    fun onSearchHistoryItemClick(query: String) {
        onSearchQueryChange(query)
        onSearch(query)
    }
    
    /**
     * Delete item from search history.
     */
    fun onDeleteSearchHistoryItem(query: String) {
        viewModelScope.launch {
            searchHistoryUseCase.deleteSearch(query)
        }
    }
    
    /**
     * Clear all search history.
     */
    fun onClearSearchHistory() {
        viewModelScope.launch {
            searchHistoryUseCase.clearHistory()
        }
    }
    
    /**
     * Refresh movies (pull-to-refresh).
     */
    fun onRefresh() {
        if (_uiState.value.isSearchActive) {
            searchMovies(_uiState.value.searchQuery)
        } else {
            loadTrendingMovies(forceRefresh = true)
        }
    }
    
    /**
     * Retry after error.
     */
    fun onRetry() {
        _uiState.update { it.copy(error = null) }
        if (_uiState.value.isSearchActive) {
            searchMovies(_uiState.value.searchQuery)
        } else {
            loadTrendingMovies()
        }
    }
}
