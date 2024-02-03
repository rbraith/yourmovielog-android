package com.rbraithwaite.untitledmovieapp.core.data

sealed interface CustomMedia {
    data class Movie(
        val id: Long = 0L,
        val title: String = ""
    ): CustomMedia
}
