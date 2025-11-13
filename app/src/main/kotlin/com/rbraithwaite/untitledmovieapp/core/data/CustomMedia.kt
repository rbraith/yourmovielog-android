package com.rbraithwaite.untitledmovieapp.core.data

// TODO [25-10-26 4:26p.m.] deprecated, delete this.
sealed interface CustomMedia {
    data class Movie(
        val id: Long = 0L,
        val title: String = ""
    ): CustomMedia
}
