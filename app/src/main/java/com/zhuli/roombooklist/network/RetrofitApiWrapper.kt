package com.zhuli.roombooklist.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

/**
 * Generic function for safely calling Retrofit API
 * Returns Result<T> object, containing success result or exception
 */
suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> {
    return withContext(Dispatchers.IO) {
        try {
            Result.success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> Result.failure(NetworkException("Network connection failed", throwable))
                is HttpException -> {
                    val code = throwable.code()
                    val errorResponse = throwable.response()?.errorBody()?.string()
                    Result.failure(ApiException(code, errorResponse ?: "Unknown error", throwable))
                }
                else -> Result.failure(UnknownException("Unknown error", throwable))
            }
        }
    }
}

// Custom exception classes
open class ApiError(message: String, cause: Throwable? = null) : Exception(message, cause)
class NetworkException(message: String, cause: Throwable? = null) : ApiError(message, cause)
class ApiException(val code: Int, message: String, cause: Throwable? = null) : ApiError(message, cause)
class UnknownException(message: String, cause: Throwable? = null) : ApiError(message, cause)