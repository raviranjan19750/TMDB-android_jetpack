package com.example.tmdb_atlys.domain.usecase

import com.example.tmdb_atlys.domain.repository.SearchHistoryRepository
import com.example.tmdb_atlys.util.Constants
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for managing search history.
 * Encapsulates the business logic for search history operations.
 */
class SearchHistoryUseCase @Inject constructor(
    private val searchHistoryRepository: SearchHistoryRepository
) {
    /**
     * Get recent search queries.
     * @param limit Maximum number of items (default: MAX_SEARCH_HISTORY_ITEMS)
     * @return Flow of list of recent search query strings
     */
    fun getRecentSearches(limit: Int = Constants.MAX_SEARCH_HISTORY_ITEMS): Flow<List<String>> {
        return searchHistoryRepository.getRecentSearches(limit)
    }
    
    /**
     * Save a search query to history.
     * @param query The search query to save
     */
    suspend fun saveSearch(query: String) {
        searchHistoryRepository.saveSearch(query)
    }
    
    /**
     * Delete a specific search query from history.
     * @param query The search query to delete
     */
    suspend fun deleteSearch(query: String) {
        searchHistoryRepository.deleteSearch(query)
    }
    
    /**
     * Clear all search history.
     */
    suspend fun clearHistory() {
        searchHistoryRepository.clearHistory()
    }
}
