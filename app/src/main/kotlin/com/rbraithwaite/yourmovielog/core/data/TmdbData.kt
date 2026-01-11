package com.rbraithwaite.yourmovielog.core.data

import java.time.LocalDate

/**
 * Media or person data from TMDB
 */
sealed interface TmdbData {
    data class Movie(
        val id: Long,
        val isAdult: Boolean,
        val backdropPath: String?,
        val title: String,
        val originalLanguage: String,
        val originalTitle: String,
        val overview: String,
        val posterPath: String?,
        val popularity: Float,
        val genreIds: List<Int>,
        val releaseDate: LocalDate?,
        val video: Boolean,
        val voteAverage: Float,
        val voteCount: Int
    ) : TmdbData

    data class TvShow(
        val id: Long,
        val isAdult: Boolean,
        val backdropPath: String?,
        val name: String,
        val originalLanguage: String,
        val originalName: String,
        val overview: String,
        val posterPath: String?,
        val genreIds: List<Int>,
        val popularity: Float,
        val firstAirDate: LocalDate?,
        val voteAverage: Float,
        val voteCount: Int,
        val originCountry: List<String>
    ) : TmdbData

    data class Person(
        val id: Long,
        val isAdult: Boolean,
        val name: String,
        val originalName: String,
        val popularity: Float,
        // TODO [23-11-19 3:58p.m.] -- gender should be an enum.
        val gender: Int,
        val knownForDepartment: String,
        val profilePath: String?
    ) : TmdbData
}
