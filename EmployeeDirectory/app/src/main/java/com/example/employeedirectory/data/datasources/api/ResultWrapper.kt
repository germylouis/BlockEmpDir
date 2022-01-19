package com.example.employeedirectory.data.datasources.api

/**
 * Source code from: https://medium.com/@douglas.iacovelli/how-to-handle-errors-with-retrofit-and-coroutines-33e7492a912
 */
sealed class ResultWrapper<out T : Any> {
    data class Success<out T : Any>(val value: T?) : ResultWrapper<T>()
    data class GenericError(val code: Int? = null, val error: NetworkHelper.ApiStatus? = null) :
        ResultWrapper<Nothing>()

    object NetworkError : ResultWrapper<Nothing>()
}
