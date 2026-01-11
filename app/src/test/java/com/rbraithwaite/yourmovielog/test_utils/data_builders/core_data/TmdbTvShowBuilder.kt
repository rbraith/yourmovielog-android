package com.rbraithwaite.yourmovielog.test_utils.data_builders.core_data

import com.rbraithwaite.test_data_utils.TestDataBuilder
import com.rbraithwaite.test_data_utils.builder_base.BaseBuilder
import com.rbraithwaite.yourmovielog.core.data.TmdbData

fun aTmdbTvShow(buildBlock: TmdbTvShowBuilder.() -> Unit = {}): TmdbTvShowBuilder {
    return TmdbTvShowBuilder().apply(buildBlock)
}

@BaseBuilder
abstract class AbstractTmdbTvShowBuilder : TestDataBuilder<TmdbData.TvShow>()

class TmdbTvShowBuilder : BaseAbstractTmdbTvShowBuilder<TmdbTvShowBuilder>() {
    override var data = TmdbData.TvShow(
        id = 0L,
        isAdult = false,
        backdropPath = null,
        name = "test tv show",
        originalLanguage = "en-US",
        originalName = "original tv show name",
        overview = "this is the overview",
        posterPath = null,
        genreIds = listOf(1),
        popularity = 7.5f,
        firstAirDate = null,
        voteAverage = 1.2f,
        voteCount = 10,
        originCountry = listOf("US")
    )
}
