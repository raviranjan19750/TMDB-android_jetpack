package com.example.tmdb_atlys.util

import com.example.tmdb_atlys.BuildConfig

object Constants {
    const val TMDB_API_KEY = BuildConfig.TMDB_API_KEY
    const val TMDB_BASE_URL = BuildConfig.TMDB_BASE_URL
    const val TMDB_IMAGE_BASE_URL = BuildConfig.TMDB_IMAGE_BASE_URL
    
    // Image sizes
    const val IMAGE_SIZE_W200 = "w200"
    const val IMAGE_SIZE_W500 = "w500"
    const val IMAGE_SIZE_ORIGINAL = "original"
    
    // Database
    const val DATABASE_NAME = "tmdb_database"
    
    // Search
    const val SEARCH_DEBOUNCE_MILLIS = 300L
    const val MAX_SEARCH_HISTORY_ITEMS = 10
}
