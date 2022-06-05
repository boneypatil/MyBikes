package com.digikrafi.mybikes.network
import android.os.Bundle

/**
 * Created by rahul.p
 *
 */
sealed class Result<T> {
    open fun get(): T? = null
}

data class Success<T>(val data: T) : Result<T>() {
    override fun get(): T? = data
}

data class ErrorResult<T>(
    val errorCode: Int,
    var errorMessage: String,
    var bundle: Bundle? = null
) : Result<T>()

object ErrorCodes {
    const val ERROR_UNAUTHORIZED = 401
    const val ERROR_TIMEOUT = 408
    const val ERROR_NO_CONNECTION = 603
    const val ERROR_UNKNOWN = 604
}
