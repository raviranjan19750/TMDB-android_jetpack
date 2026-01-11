package com.example.tmdb_atlys.data.mapper

import com.example.tmdb_atlys.data.local.entity.MovieEntity
import com.example.tmdb_atlys.data.remote.dto.MovieDto
import com.example.tmdb_atlys.domain.model.Movie

/**
 * Mapper functions to convert between DTOs, Entities, and Domain models.
 */

/**
 * Convert MovieDto to MovieEntity for caching.
 */
fun MovieDto.toEntity(isTrending: Boolean = false): MovieEntity {
    return MovieEntity(
        id = id,
        title = title,
        overview = overview ?: "",
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage ?: 0.0,
        voteCount = voteCount ?: 0,
        popularity = popularity ?: 0.0,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        isTrending = isTrending,
        cachedAt = System.currentTimeMillis()
    )
}

/**
 * Convert MovieEntity to domain Movie model.
 */
fun MovieEntity.toDomain(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        voteCount = voteCount,
        popularity = popularity,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle
    )
}

/**
 * Convert MovieDto directly to domain Movie model.
 */
fun MovieDto.toDomain(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview ?: "",
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage ?: 0.0,
        voteCount = voteCount ?: 0,
        popularity = popularity ?: 0.0,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle
    )
}

/**
 * Convert list of MovieDto to list of MovieEntity.
 */
fun List<MovieDto>.toEntityList(isTrending: Boolean = false): List<MovieEntity> {
    return map { it.toEntity(isTrending) }
}

/**
 * Convert list of MovieEntity to list of domain Movie.
 */
fun List<MovieEntity>.toDomainList(): List<Movie> {
    return map { it.toDomain() }
}

/**
 * Convert list of MovieDto directly to list of domain Movie.
 */
fun List<MovieDto>.toDomainListFromDto(): List<Movie> {
    return map { it.toDomain() }
}
