package com.rbraithwaite.yourmovielog.test_utils.data_builders.network_models

import com.rbraithwaite.test_data_utils.TestDataBuilder
import com.rbraithwaite.test_data_utils.builder_base.BaseBuilder
import com.rbraithwaite.yourmovielog.data.network.models.Movie

fun aMovie(
    buildBlock: MovieBuilder.() -> Unit = {}
): MovieBuilder {
    return MovieBuilder().apply(buildBlock)
}

@BaseBuilder
abstract class AbstractMovieBuilder: TestDataBuilder<Movie>()

class MovieBuilder: BaseAbstractMovieBuilder<MovieBuilder>() {
    override var data = Movie(
        adult = false,
        backdropPath = null,
        id = 0,
        title = "some movie",
        originalLanguage = "en-US",
        originalTitle = "original movie title",
        overview = "this is the overview",
        posterPath = null,
        genreIds = listOf(1),
        popularity = 7.5f,
        releaseDate = "2023-11-17",
        video = false,
        voteAverage = 6.7f,
        voteCount = 1234
    )
}