package com.rbraithwaite.untitledmovietracker.test_utils.data_builders.network_models

import com.rbraithwaite.untitledmovieapp.data.network.models.SearchMultiResultMovie
import com.rbraithwaite.untitledmovietracker.test_utils.data_builders.Builder

fun aSearchMultiResultMovie(
    buildBlock: SearchMultiResultMovieBuilder.() -> Unit = {}
): SearchMultiResultMovieBuilder {
    return SearchMultiResultMovieBuilder().apply(buildBlock)
}

// REFACTOR [23-11-19 11:46p.m.] -- It would be much nicer to create these builders via some
//  kind of annotation processing / code generation.
class SearchMultiResultMovieBuilder: Builder<SearchMultiResultMovie> {
    private var data = SearchMultiResultMovie(
        adult = false,
        backdropPath = null,
        id = 0,
        title = "some movie",
        originalLanguage = "en-US",
        originalTitle = "original movie title",
        overview = "this is the overview",
        posterPath = null,
        mediaType = "movie",
        genreIds = listOf(1),
        popularity = 7.5f,
        releaseDate = "2023-11-17",
        video = false,
        voteAverage = 6.7f,
        voteCount = 1234
    )

    override fun build(): SearchMultiResultMovie {
        return data.copy()
    }

    fun withTitle(title: String) = apply { data = data.copy(title = title) }
}