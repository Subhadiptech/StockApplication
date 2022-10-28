package com.ersubhadip.stockapplication

sealed class Resource<T>(data: T? = null, message: String? = null) {
    class Success<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T>(val isLoading: Boolean = true) : Resource<T>(null)
}