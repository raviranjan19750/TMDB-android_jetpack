package com.example.tmdb_atlys.util

/**
 * A generic class that holds a value with its loading status.
 * Used to represent the result of network/database operations.
 */
sealed class Resource<out T> {
    
    data class Success<out T>(val data: T) : Resource<T>()
    
    data class Error(
        val message: String,
        val throwable: Throwable? = null
    ) : Resource<Nothing>()
    
    data object Loading : Resource<Nothing>()
    
    val isLoading: Boolean get() = this is Loading
    val isSuccess: Boolean get() = this is Success
    val isError: Boolean get() = this is Error
    
    fun getOrNull(): T? = (this as? Success)?.data
    
    fun errorMessageOrNull(): String? = (this as? Error)?.message
    
    inline fun <R> map(transform: (T) -> R): Resource<R> {
        return when (this) {
            is Success -> Success(transform(data))
            is Error -> this
            is Loading -> Loading
        }
    }
    
    inline fun onSuccess(action: (T) -> Unit): Resource<T> {
        if (this is Success) action(data)
        return this
    }
    
    inline fun onError(action: (String, Throwable?) -> Unit): Resource<T> {
        if (this is Error) action(message, throwable)
        return this
    }
    
    inline fun onLoading(action: () -> Unit): Resource<T> {
        if (this is Loading) action()
        return this
    }
}
