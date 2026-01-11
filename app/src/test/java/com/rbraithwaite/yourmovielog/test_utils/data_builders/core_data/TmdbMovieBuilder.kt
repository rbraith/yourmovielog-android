package com.rbraithwaite.yourmovielog.test_utils.data_builders.core_data

import com.rbraithwaite.test_data_utils.TestDataBuilder
import com.rbraithwaite.test_data_utils.builder_base.BaseBuilder
import com.rbraithwaite.yourmovielog.core.data.TmdbData

fun aTmdbMovie(buildBlock: TmdbMovieBuilder.() -> Unit = {}): TmdbMovieBuilder {
    return TmdbMovieBuilder().apply(buildBlock)
}

@BaseBuilder
abstract class AbstractTmdbMovieBuilder : TestDataBuilder<TmdbData.Movie>()

class TmdbMovieBuilder : BaseAbstractTmdbMovieBuilder<TmdbMovieBuilder>() {
    override var data = TmdbData.Movie(
        id = 0L,
        isAdult = false,
        backdropPath = null,
        title = "test movie",
        originalLanguage = "en-US",
        originalTitle = "original movie title",
        overview = "this is the overview",
        posterPath = null,
        popularity = 7.5f,
        genreIds = listOf(1),
        releaseDate = null,
        video = false,
        voteAverage = 1.2f,
        voteCount = 10
    )
}
