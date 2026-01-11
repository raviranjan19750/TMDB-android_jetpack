package com.example.tmdb_atlys.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tmdb_atlys.data.local.entity.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for search history operations.
 * Provides methods for managing recent search queries.
 */
@Dao
interface SearchHistoryDao {
    
    /**
     * Get recent search history ordered by most recent first.
     * @param limit Maximum number of items to return
     * @return Flow of list of search history items
     */
    @Query("SELECT * FROM search_history ORDER BY searchedAt DESC LIMIT :limit")
    fun getRecentSearches(limit: Int): Flow<List<SearchHistoryEntity>>
    
    /**
     * Get recent search history synchronously.
     * @param limit Maximum number of items to return
     * @return List of search history items
     */
    @Query("SELECT * FROM search_history ORDER BY searchedAt DESC LIMIT :limit")
    suspend fun getRecentSearchesSync(limit: Int): List<SearchHistoryEntity>
    
    /**
     * Insert or update a search query.
     * @param searchHistory The search history entity to insert/update
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearch(searchHistory: SearchHistoryEntity)
    
    /**
     * Delete a specific search query from history.
     * @param query The search query to delete
     */
    @Query("DELETE FROM search_history WHERE query = :query")
    suspend fun deleteSearch(query: String)
    
    /**
     * Clear all search history.
     */
    @Query("DELETE FROM search_history")
    suspend fun clearAllSearchHistory()
    
    /**
     * Keep only the most recent searches, delete older ones.
     * @param keepCount Number of recent searches to keep
     */
    @Query("""
        DELETE FROM search_history 
        WHERE query NOT IN (
            SELECT query FROM search_history 
            ORDER BY searchedAt DESC 
            LIMIT :keepCount
        )
    """)
    suspend fun trimHistory(keepCount: Int)
}
