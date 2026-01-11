package com.example.tmdb_atlys.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room entity for caching movies locally.
 * Stores essential movie information for offline access.
 */
@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String?,
    val voteAverage: Double,
    val voteCount: Int,
    val popularity: Double,
    val originalLanguage: String?,
    val originalTitle: String?,
    val isTrending: Boolean = false,
    val cachedAt: Long = System.currentTimeMillis()
)
