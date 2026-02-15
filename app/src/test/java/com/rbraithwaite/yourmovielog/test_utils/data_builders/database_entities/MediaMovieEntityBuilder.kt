package com.rbraithwaite.yourmovielog.test_utils.data_builders.database_entities

import com.rbraithwaite.test_data_utils.TestDataBuilder
import com.rbraithwaite.test_data_utils.builder_base.BaseBuilder
import com.rbraithwaite.yourmovielog.data.database.entities.MediaMovieEntity
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.UUID

@BaseBuilder
abstract class AbstractMediaMovieEntityBuilder : TestDataBuilder<MediaMovieEntity>()

class MediaMovieEntityBuilder : BaseAbstractMediaMovieEntityBuilder<MediaMovieEntityBuilder>() {
    override var data: MediaMovieEntity = MediaMovieEntity(
        uuid = UUID(12L, 34L).toString(),
        title = "a movie title",
        createdAt = LocalDateTime.of(2023, 1, 1, 0, 0).toEpochSecond(ZoneOffset.UTC),
        modifiedAt = LocalDateTime.of(2023, 1, 1, 0, 0).toEpochSecond(ZoneOffset.UTC),
        tmdbId = null
    )

    fun withUuid(uuid: UUID): MediaMovieEntityBuilder = this.apply { data = data.copy(uuid = uuid.toString()) }
}

fun aMediaMovieEntity(buildBlock: MediaMovieEntityBuilder.() -> Unit = {}): MediaMovieEntityBuilder {
    return MediaMovieEntityBuilder().apply(buildBlock)
}
