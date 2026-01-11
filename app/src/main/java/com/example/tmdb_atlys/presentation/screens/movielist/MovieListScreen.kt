package com.example.tmdb_atlys.presentation.screens.movielist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PullRefreshIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pullRefresh
import androidx.compose.material3.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tmdb_atlys.presentation.components.ConnectivityBanner
import com.example.tmdb_atlys.presentation.components.EmptyState
import com.example.tmdb_atlys.presentation.components.ErrorState
import com.example.tmdb_atlys.presentation.components.MovieCard
import com.example.tmdb_atlys.presentation.components.SearchBar
import com.example.tmdb_atlys.presentation.components.SearchEmptyState
import com.example.tmdb_atlys.presentation.components.SearchHistoryItem
import com.example.tmdb_atlys.presentation.components.ShimmerMovieGrid

/**
 * Movie List screen displaying trending movies with search functionality.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen(
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MovieListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.isRefreshing,
        onRefresh = viewModel::onRefresh
    )
    
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Connectivity Banner
            ConnectivityBanner(isOffline = uiState.isOffline)
            
            // Search Bar
            SearchBar(
                query = uiState.searchQuery,
                onQueryChange = viewModel::onSearchQueryChange,
                onSearch = viewModel::onSearch,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            )
            
            // Search History Dropdown
            AnimatedVisibility(
                visible = uiState.showSearchHistory && uiState.searchHistory.isNotEmpty(),
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                SearchHistoryDropdown(
                    searchHistory = uiState.searchHistory,
                    onItemClick = viewModel::onSearchHistoryItemClick,
                    onDeleteItem = viewModel::onDeleteSearchHistoryItem,
                    onClearAll = viewModel::onClearSearchHistory
                )
            }
            
            // Content
            Box(modifier = Modifier.fillMaxSize()) {
                when {
                    // Loading state (shimmer)
                    uiState.isLoading -> {
                        ShimmerMovieGrid()
                    }
                    
                    // Error state
                    uiState.shouldShowError -> {
                        ErrorState(
                            message = uiState.error ?: "Unknown error",
                            onRetry = viewModel::onRetry
                        )
                    }
                    
                    // Empty state
                    uiState.shouldShowEmptyState -> {
                        if (uiState.isSearchActive) {
                            SearchEmptyState(query = uiState.searchQuery)
                        } else {
                            EmptyState(
                                title = "No movies available",
                                message = "Pull down to refresh"
                            )
                        }
                    }
                    
                    // Success state with movies
                    else -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .pullRefresh(pullRefreshState)
                        ) {
                            MovieGrid(
                                movies = uiState.movies,
                                onMovieClick = onMovieClick,
                                isSearching = uiState.isSearching
                            )
                            
                            PullRefreshIndicator(
                                refreshing = uiState.isRefreshing,
                                state = pullRefreshState,
                                modifier = Modifier.align(Alignment.TopCenter)
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Grid of movie cards.
 */
@Composable
private fun MovieGrid(
    movies: List<com.example.tmdb_atlys.domain.model.Movie>,
    onMovieClick: (Int) -> Unit,
    isSearching: Boolean,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                items = movies,
                key = { it.id }
            ) { movie ->
                MovieCard(
                    movie = movie,
                    onClick = { onMovieClick(movie.id) }
                )
            }
        }
        
        // Searching indicator overlay
        if (isSearching) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0.7f)),
                contentAlignment = Alignment.Center
            ) {
                // Could add a loading indicator here
            }
        }
    }
}

/**
 * Search history dropdown component.
 */
@Composable
private fun SearchHistoryDropdown(
    searchHistory: List<String>,
    onItemClick: (String) -> Unit,
    onDeleteItem: (String) -> Unit,
    onClearAll: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = "Recent Searches",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.align(Alignment.CenterStart)
            )
            
            TextButton(
                onClick = onClearAll,
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Text(
                    text = "Clear All",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        
        HorizontalDivider()
        
        // History items
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(searchHistory) { query ->
                SearchHistoryItem(
                    query = query,
                    onClick = { onItemClick(query) },
                    onDelete = { onDeleteItem(query) }
                )
            }
        }
        
        HorizontalDivider()
    }
}
