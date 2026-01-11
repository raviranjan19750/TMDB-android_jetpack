package com.example.tmdb_atlys.domain.repository

import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for search history operations.
 * Defines the contract for managing recent search queries.
 */
interface SearchHistoryRepository {
    
    /**
     * Get recent search queries.
     * @param limit Maximum number of items to return
     * @return Flow of list of recent search query strings
     */
    fun getRecentSearches(limit: Int): Flow<List<String>>
    
    /**
     * Save a search query to history.
     * @param query The search query to save
     */
    suspend fun saveSearch(query: String)
    
    /**
     * Delete a specific search query from history.
     * @param query The search query to delete
     */
    suspend fun deleteSearch(query: String)
    
    /**
     * Clear all search history.
     */
    suspend fun clearHistory()
}
