package com.rbraithwaite.untitledmovietracker.test_utils.data_builders.core_data

import com.rbraithwaite.test_data_utils.TestDataBuilder
import com.rbraithwaite.test_data_utils.builder_base.BaseBuilder
import com.rbraithwaite.untitledmovieapp.core.data.CustomMedia

@BaseBuilder
abstract class AbstractCustomMovieBuilder: TestDataBuilder<CustomMedia.Movie>()

fun aCustomMovie(buildBlock: CustomMovieBuilder.() -> Unit = {}): CustomMovieBuilder {
    return CustomMovieBuilder().apply(buildBlock)
}

class CustomMovieBuilder: BaseAbstractCustomMovieBuilder<CustomMovieBuilder>() {
    override var data = CustomMedia.Movie(
        title = "custom movie title"
    )

    // REFACTOR [24-02-2 12:45a.m.] -- hardcoded 0L.
    fun asNewMovie(): CustomMovieBuilder = apply { data = data.copy(id = 0L) }
}