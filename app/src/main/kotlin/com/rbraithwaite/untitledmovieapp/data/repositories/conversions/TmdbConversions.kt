package com.rbraithwaite.untitledmovieapp.data.repositories.conversions

import com.rbraithwaite.untitledmovieapp.core.data.TmdbLite
import com.rbraithwaite.untitledmovieapp.data.database.entities.TmdbLiteMovieEntity
import com.rbraithwaite.untitledmovieapp.data.database.entities.combined.TmdbLiteMovieWithGenres
import com.rbraithwaite.untitledmovieapp.data.network.models.SearchMultiResult
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun TmdbLiteMovieWithGenres.toTmdbLiteMovie(): TmdbLite.Movie {
    val liteGenreIds = genreIds.map { it.genreId }

    return tmdbMovie.toTmdbLiteMovie(genreIds = liteGenreIds)
}

fun TmdbLiteMovieEntity.toTmdbLiteMovie(genreIds: List<Int> = emptyList()): TmdbLite.Movie {
    return TmdbLite.Movie(
        id,
        isAdult,
        backdropPath,
        title,
        originalLanguage,
        originalTitle,
        overview,
        posterPath,
        popularity,
        genreIds,
        releaseDate,
        video,
        voteAverage,
        voteCount
    )
}

fun TmdbLite.Movie.toEntity(): TmdbLiteMovieEntity {
    return TmdbLiteMovieEntity(
        id,
        isAdult,
        backdropPath,
        title,
        originalLanguage,
        originalTitle,
        overview,
        posterPath,
        popularity,
        releaseDate,
        video,
        voteAverage,
        voteCount
    )
}

fun SearchMultiResult.Movie.toTmdbLiteMovie(): TmdbLite.Movie {
    return TmdbLite.Movie(
        id = id,
        isAdult = adult,
        backdropPath = backdropPath,
        title = title,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        posterPath = posterPath,
        popularity = popularity,
        genreIds = genreIds,
        releaseDate = parseTmdbDateString(releaseDate),
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount
    )
}

private fun parseTmdbDateString(dateString: String): LocalDate? {
    if (dateString.isEmpty()) {
        return null
    }
    return LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE)
}