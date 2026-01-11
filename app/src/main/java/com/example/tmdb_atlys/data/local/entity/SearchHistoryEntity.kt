package com.example.tmdb_atlys.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room entity for storing search history.
 * Keeps track of recent search queries for quick access.
 */
@Entity(tableName = "search_history")
data class SearchHistoryEntity(
    @PrimaryKey
    val query: String,
    val searchedAt: Long = System.currentTimeMillis()
)
