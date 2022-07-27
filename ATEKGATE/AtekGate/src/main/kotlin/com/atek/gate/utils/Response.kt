package com.atek.gate.utils

sealed class Response<T>(val data: T?, val message: String?) {
    class Success<T>(data: T?): Response<T>(data, null)
    class Error<T>(message: String): Response<T>(null, message)
}
