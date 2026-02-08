package com.anilpaudel.domain

/**
 * Created by Anil Paudel on 25/08/2025.
 */
sealed class DataState <out T>{
    data object Loading : DataState<Nothing>()
    data class Success <out T>(val result: T) : DataState<T>()
    data class Error (val error: Throwable) : DataState<Nothing>()
}