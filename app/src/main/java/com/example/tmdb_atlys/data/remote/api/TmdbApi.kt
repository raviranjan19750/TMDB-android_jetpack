package com.example.tmdb_atlys.data.remote.api

import com.example.tmdb_atlys.data.remote.dto.SearchResponse
import com.example.tmdb_atlys.data.remote.dto.TrendingResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * TMDB API interface for Retrofit.
 * All endpoints require API key which is added via interceptor.
 */
interface TmdbApi {
    
    /**
     * Get trending movies for the week.
     * @param language Language for the response (default: en-US)
     * @return TrendingResponse containing list of trending movies
     */
    @GET("trending/movie/week")
    suspend fun getTrendingMovies(
        @Query("language") language: String = "en-US"
    ): TrendingResponse
    
    /**
     * Search for movies by query string.
     * @param query Search query
     * @param language Language for the response (default: en-US)
     * @param page Page number for pagination (default: 1)
     * @param includeAdult Include adult content (default: false)
     * @return SearchResponse containing list of matching movies
     */
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("include_adult") includeAdult: Boolean = false
    ): SearchResponse
}
