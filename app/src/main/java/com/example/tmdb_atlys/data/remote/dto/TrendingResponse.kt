package com.example.tmdb_atlys.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TrendingResponse(
    @Json(name = "page")
    val page: Int,
    
    @Json(name = "results")
    val results: List<MovieDto>,
    
    @Json(name = "total_pages")
    val totalPages: Int,
    
    @Json(name = "total_results")
    val totalResults: Int
)

@JsonClass(generateAdapter = true)
data class SearchResponse(
    @Json(name = "page")
    val page: Int,
    
    @Json(name = "results")
    val results: List<MovieDto>,
    
    @Json(name = "total_pages")
    val totalPages: Int,
    
    @Json(name = "total_results")
    val totalResults: Int
)
