package com.example.tmdb_atlys.domain.model

import com.example.tmdb_atlys.util.Constants

/**
 * Domain model representing a Movie.
 * This is the clean model used in the presentation layer.
 */
data class Movie(
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
    val originalTitle: String?
) {
    /**
     * Get the full URL for the poster image.
     * @param size Image size (w200, w500, original)
     * @return Full image URL or null if no poster
     */
    fun getPosterUrl(size: String = Constants.IMAGE_SIZE_W500): String? {
        return posterPath?.let { "${Constants.TMDB_IMAGE_BASE_URL}$size$it" }
    }
    
    /**
     * Get the full URL for the backdrop image.
     * @param size Image size (w200, w500, original)
     * @return Full image URL or null if no backdrop
     */
    fun getBackdropUrl(size: String = Constants.IMAGE_SIZE_ORIGINAL): String? {
        return backdropPath?.let { "${Constants.TMDB_IMAGE_BASE_URL}$size$it" }
    }
    
    /**
     * Get formatted vote average (e.g., "8.5")
     */
    val formattedRating: String
        get() = String.format("%.1f", voteAverage)
    
    /**
     * Get release year from release date.
     */
    val releaseYear: String?
        get() = releaseDate?.take(4)
}
