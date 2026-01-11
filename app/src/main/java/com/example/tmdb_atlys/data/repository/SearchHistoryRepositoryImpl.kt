package com.example.tmdb_atlys.data.repository

import com.example.tmdb_atlys.data.local.dao.SearchHistoryDao
import com.example.tmdb_atlys.data.local.entity.SearchHistoryEntity
import com.example.tmdb_atlys.domain.repository.SearchHistoryRepository
import com.example.tmdb_atlys.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of SearchHistoryRepository.
 * Manages recent search queries in local database.
 */
@Singleton
class SearchHistoryRepositoryImpl @Inject constructor(
    private val searchHistoryDao: SearchHistoryDao
) : SearchHistoryRepository {
    
    override fun getRecentSearches(limit: Int): Flow<List<String>> {
        return searchHistoryDao.getRecentSearches(limit).map { entities ->
            entities.map { it.query }
        }
    }
    
    override suspend fun saveSearch(query: String) {
        if (query.isBlank()) return
        
        val trimmedQuery = query.trim()
        searchHistoryDao.insertSearch(
            SearchHistoryEntity(
                query = trimmedQuery,
                searchedAt = System.currentTimeMillis()
            )
        )
        
        // Keep only the most recent searches
        searchHistoryDao.trimHistory(Constants.MAX_SEARCH_HISTORY_ITEMS)
    }
    
    override suspend fun deleteSearch(query: String) {
        searchHistoryDao.deleteSearch(query)
    }
    
    override suspend fun clearHistory() {
        searchHistoryDao.clearAllSearchHistory()
    }
}
