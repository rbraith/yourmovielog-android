package com.rbraithwaite.yourmovielog.data.repositories.conversions

import com.rbraithwaite.yourmovielog.core.data.TmdbData
import com.rbraithwaite.yourmovielog.data.network.models.Movie
import com.rbraithwaite.yourmovielog.data.network.models.TvShow
import java.time.LocalDate
import java.time.format.DateTimeFormatter


fun Movie.toTmdbDataMovie(): TmdbData.Movie {
    return TmdbData.Movie(
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

fun TvShow.toTmdbDataTvShow(): TmdbData.TvShow {
    return TmdbData.TvShow(
        id = id,
        isAdult = adult,
        backdropPath = backdropPath,
        name = name,
        originalLanguage = originalLanguage,
        originalName = originalName,
        overview = overview,
        posterPath = posterPath,
        genreIds = genreIds,
        popularity = popularity,
        firstAirDate = parseTmdbDateString(firstAirDate),
        voteAverage = voteAverage,
        voteCount = voteCount,
        originCountry = originCountry
    )
}

private fun parseTmdbDateString(dateString: String): LocalDate? {
    if (dateString.isEmpty()) {
        return null
    }
    return LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE)
}