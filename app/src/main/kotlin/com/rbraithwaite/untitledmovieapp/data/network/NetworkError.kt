package com.rbraithwaite.untitledmovieapp.data.network

sealed class NetworkError: Throwable() {
    class Unknown(val throwable: Throwable? = null): NetworkError()

    data class Http(val code: Int): NetworkError()

    data class Network(val throwable: Throwable? = null): NetworkError()
}