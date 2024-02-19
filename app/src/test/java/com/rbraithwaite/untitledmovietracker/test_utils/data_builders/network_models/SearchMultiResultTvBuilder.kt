package com.rbraithwaite.untitledmovietracker.test_utils.data_builders.network_models

import com.rbraithwaite.test_data_utils.Builder
import com.rbraithwaite.untitledmovieapp.data.network.models.TvShow

fun aTvShow(
    buildBlock: TvShowBuilder.() -> Unit = {}
): TvShowBuilder {
    return TvShowBuilder().apply(buildBlock)
}

class TvShowBuilder: Builder<TvShow> {
    private var data = TvShow(
        adult = false,
        backdropPath = null,
        id = 0,
        name = "some tv show",
        originalLanguage = "en-US",
        originalName = "original tv show name",
        overview = "this is the overview",
        posterPath = null,
        genreIds = listOf(1),
        popularity = 7.5f,
        firstAirDate = "2023-11-17",
        voteAverage = 6.7f,
        voteCount = 1234,
        originCountry = listOf("Canada")
    )

    override fun build(): TvShow {
        return data.copy()
    }

    fun withName(name: String) = apply { data = data.copy(name = name) }
}