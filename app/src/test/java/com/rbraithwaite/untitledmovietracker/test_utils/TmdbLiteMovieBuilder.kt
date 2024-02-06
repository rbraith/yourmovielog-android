package com.rbraithwaite.untitledmovietracker.test_utils

import com.rbraithwaite.test_data_utils.TestDataBuilder
import com.rbraithwaite.test_data_utils.builder_base.BaseBuilder
import com.rbraithwaite.untitledmovieapp.core.data.TmdbLite

@BaseBuilder
abstract class AbstractTmdbLiteMovieBuilder: TestDataBuilder<TmdbLite.Movie>()

class TmdbLiteMovieBuilder: BaseAbstractTmdbLiteMovieBuilder<TmdbLiteMovieBuilder>() {
    override var data = TmdbLite.Movie(
        id = 123L,
        isAdult = false,
        backdropPath = null,
        title = "default TmdbLite.Movie",
        originalLanguage = "en-US",
        originalTitle = "original title",
        overview = "this is the overview",
        posterPath = null,
        popularity = 23.45f,
        genreIds = listOf(1, 2, 3),
        releaseDate = null,
        video = false,
        voteAverage = 3.4f,
        voteCount = 4321
    )
}

fun aTmdbLiteMovie(buildBlock: TmdbLiteMovieBuilder.() -> Unit = {}): TmdbLiteMovieBuilder {
    return TmdbLiteMovieBuilder().apply(buildBlock)
}