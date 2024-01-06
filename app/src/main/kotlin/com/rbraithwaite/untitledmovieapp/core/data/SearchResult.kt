package com.rbraithwaite.untitledmovieapp.core.data

import java.time.LocalDate

sealed interface SearchResult {
    data class CustomMedia(
        val data: com.rbraithwaite.untitledmovieapp.core.data.CustomMedia
    ): SearchResult

    data class TmdbMovie(
        val data: TmdbLite.Movie
    ): SearchResult

    data class TmdbTvShow(
        val data: TmdbLite.TvShow
    ): SearchResult

    data class TmdbPerson(
        val data: TmdbLite.Person
    ): SearchResult
}