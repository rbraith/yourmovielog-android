package com.rbraithwaite.untitledmovietracker.test_utils.data_builders.network_models

import com.rbraithwaite.untitledmovieapp.data.network.models.SearchMultiResult
import com.rbraithwaite.untitledmovietracker.test_utils.data_builders.Builder

fun aSearchMultiResultTvShow(
    buildBlock: SearchMultiResultTvBuilder.() -> Unit = {}
): SearchMultiResultTvBuilder {
    return SearchMultiResultTvBuilder().apply(buildBlock)
}

class SearchMultiResultTvBuilder: Builder<SearchMultiResult.TvShow> {
    private var data = SearchMultiResult.TvShow(
        adult = false,
        backdropPath = null,
        id = 0,
        name = "some tv show",
        originalLanguage = "en-US",
        originalName = "original tv show name",
        overview = "this is the overview",
        posterPath = null,
        mediaType = "tv",
        genreIds = listOf(1),
        popularity = 7.5f,
        firstAirDate = "2023-11-17",
        voteAverage = 6.7f,
        voteCount = 1234,
        originCountry = listOf("Canada")
    )

    override fun build(): SearchMultiResult.TvShow {
        return data.copy()
    }

    fun withName(name: String) = apply { data = data.copy(name = name) }
}