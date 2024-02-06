package com.rbraithwaite.untitledmovietracker.test_utils.data_builders.network_models

import com.rbraithwaite.test_data_utils.Builder
import com.rbraithwaite.test_data_utils.TestDataBuilder
import com.rbraithwaite.test_data_utils.builder_base.BaseBuilder
import com.rbraithwaite.untitledmovieapp.data.network.models.SearchMultiResult

fun aSearchMultiResultMovie(
    buildBlock: SearchMultiResultMovieBuilder.() -> Unit = {}
): SearchMultiResultMovieBuilder {
    return SearchMultiResultMovieBuilder().apply(buildBlock)
}

@BaseBuilder
abstract class AbstractSearchMultiResultMovieBuilder: TestDataBuilder<SearchMultiResult.Movie>()

class SearchMultiResultMovieBuilder: BaseAbstractSearchMultiResultMovieBuilder<SearchMultiResultMovieBuilder>() {
    override var data = SearchMultiResult.Movie(
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
}