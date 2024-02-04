package com.rbraithwaite.untitledmovietracker.test_utils.data_builders.database_entities

import com.rbraithwaite.test_data_utils.TestDataBuilder
import com.rbraithwaite.test_data_utils.builder_base.BaseBuilder
import com.rbraithwaite.untitledmovieapp.data.database.entities.CustomMovieEntity

@BaseBuilder
abstract class AbstractCustomMovieEntityBuilder: TestDataBuilder<CustomMovieEntity>()

class CustomMovieEntityBuilder: BaseAbstractCustomMovieEntityBuilder<CustomMovieEntityBuilder>() {
    override var data = CustomMovieEntity(
        title = "default test title"
    )
}

fun aCustomMovieEntity(buildBlock: CustomMovieEntityBuilder.() -> Unit = {}): CustomMovieEntityBuilder {
    return CustomMovieEntityBuilder().apply(buildBlock)
}