package com.rbraithwaite.untitledmovietracker.test_utils.data_builders

import com.rbraithwaite.test_data_utils.TestDataBuilder
import com.rbraithwaite.test_data_utils.builder_base.BaseBuilder
import com.rbraithwaite.untitledmovieapp.core.data.TmdbLite
import kotlin.reflect.KClass

@BaseBuilder
abstract class AbstractTmdbLiteMovieBuilder: TestDataBuilder<TmdbLite.Movie>()

// TODO [24-02-2 12:05a.m.] broken.
//fun tmdbLiteMovie(block: (TmdbLiteMovieBuilder.() -> Unit)? = null): TmdbLiteMovieBuilder {
//    return TmdbLiteMovieBuilder().also { builder -> block?.let { builder.apply(it) } }
//}

//class TmdbLiteMovieBuilder: BaseAbstractTmdbLiteMovieBuilder<TmdbLiteMovieBuilder>() {
//    override var data = TmdbLite.Movie(
//        id = 123,
//        title = "",
//        overview = "",
//        posterPath = null,
//        genreIds = emptyList(),
//        popularity = 1.23f,
//        releaseDate = null,
//        voteAverage = 3.21f,
//        voteCount = 456
//    )
//}