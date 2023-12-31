package com.rbraithwaite.untitledmovietracker.test_utils.data_builders

import com.rbraithwaite.test_data_utils.TestDataBuilder
import com.rbraithwaite.test_data_utils.builder_base.BaseBuilder
import com.rbraithwaite.untitledmovieapp.core.data.SearchResult
import kotlin.reflect.KClass

@BaseBuilder
abstract class AbstractTmdbMovieSearchResultBuilder: TestDataBuilder<SearchResult.TmdbMovie>()

fun tmdbMovieSearchResult(block: (TmdbMovieSearchResultBuilder.() -> Unit)? = null): TmdbMovieSearchResultBuilder {
    return TmdbMovieSearchResultBuilder().also { builder -> block?.let { builder.apply(it) } }
}

class TmdbMovieSearchResultBuilder: BaseAbstractTmdbMovieSearchResultBuilder<TmdbMovieSearchResultBuilder>() {
    override var data = SearchResult.TmdbMovie(
        id = 123,
        title = "",
        overview = "",
        posterPath = null,
        genreIds = emptyList(),
        popularity = 1.23f,
        releaseDate = null,
        voteAverage = 3.21f,
        voteCount = 456
    )
}