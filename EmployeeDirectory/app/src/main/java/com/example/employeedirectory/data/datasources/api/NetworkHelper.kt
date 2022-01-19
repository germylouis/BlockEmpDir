package com.example.employeedirectory.data.datasources.api

import android.util.Log
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException

/**
 * Source code from: https://medium.com/@douglas.iacovelli/how-to-handle-errors-with-retrofit-and-coroutines-33e7492a912
 */
object NetworkHelper {

    suspend fun <T : Any> safeApiCall(
        dispatcher: CoroutineDispatcher,
        apiCall: () -> StateFlow<T>
    ): ResultWrapper.Success<T> {
        var api: StateFlow<T>? = null
        withContext(dispatcher) {
            try {
                api = apiCall.invoke()
            } catch (throwable: Throwable) {
                when (throwable) {
                    is IOException -> ResultWrapper.NetworkError
                    is HttpException -> {
                        val code = throwable.code()
                        val errorResponse = convertErrorBody(throwable)
                        return@withContext ResultWrapper.GenericError(code, errorResponse)
                    }
                    else -> {}
                }
            }
        }
        return ResultWrapper.Success(api?.value)
    }

    private fun convertErrorBody(throwable: HttpException): ApiStatus? {
        return try {
            throwable.response()?.errorBody()?.source()?.let {
                val moshiAdapter = Moshi.Builder().build().adapter(ApiStatus::class.java)
                moshiAdapter.fromJson(it)
            }
        } catch (exception: Exception) {
            null
        }
    }

    enum class ApiStatus {
        SUCCESS,
        ERROR,
        LOADING
    }
}
