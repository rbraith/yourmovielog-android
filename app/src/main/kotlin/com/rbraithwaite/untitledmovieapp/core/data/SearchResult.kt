package com.rbraithwaite.untitledmovieapp.core.data

import java.time.LocalDate

sealed interface SearchResult {
    data class CustomMedia(
        val id: Long,
        val title: String
    ): SearchResult

    data class TmdbMovie(
        val id: Int,
        val title: String,
        val overview: String,
        val posterPath: String?,
        val genreIds: List<Int>,
        val popularity: Float,
        val releaseDate: LocalDate,
        val voteAverage: Float,
        val voteCount: Int
    ): SearchResult

    data class TmdbTvShow(
        val id: Int,
        val name: String,
        val overview: String,
        val posterPath: String?,
        val genreIds: List<Int>,
        val popularity: Float,
        val firstAirDate: LocalDate,
        val voteAverage: Float,
        val voteCount: Int
    ): SearchResult

    data class TmdbPerson(
        val id: Int,
        val name: String,
        val popularity: Float,
        // TODO [23-11-19 3:58p.m.] -- gender should be an enum.
        val gender: Int,
        val knownForDepartment: String,
        val profilePath: String?
    ): SearchResult
}