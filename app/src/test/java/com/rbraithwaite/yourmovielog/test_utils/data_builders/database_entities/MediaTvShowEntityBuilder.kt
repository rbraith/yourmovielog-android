package com.rbraithwaite.yourmovielog.test_utils.data_builders.database_entities

import com.rbraithwaite.test_data_utils.TestDataBuilder
import com.rbraithwaite.test_data_utils.builder_base.BaseBuilder
import com.rbraithwaite.yourmovielog.data.database.entities.MediaTvShowEntity
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.UUID

@BaseBuilder
abstract class AbstractMediaTvShowEntityBuilder : TestDataBuilder<MediaTvShowEntity>()

class MediaTvShowEntityBuilder : BaseAbstractMediaTvShowEntityBuilder<MediaTvShowEntityBuilder>() {
    override var data: MediaTvShowEntity = MediaTvShowEntity(
        uuid = UUID(12L, 34L).toString(),
        title = "a tv show title",
        createdAt = LocalDateTime.of(2023, 1, 1, 0, 0).toEpochSecond(ZoneOffset.UTC),
        modifiedAt = LocalDateTime.of(2023, 1, 1, 0, 0).toEpochSecond(ZoneOffset.UTC),
        tmdbId = null
    )

    fun withUuid(uuid: UUID): MediaTvShowEntityBuilder = this.apply { data = data.copy(uuid = uuid.toString()) }
}

fun aMediaTvShowEntity(buildBlock: MediaTvShowEntityBuilder.() -> Unit = {}): MediaTvShowEntityBuilder {
    return MediaTvShowEntityBuilder().apply(buildBlock)
}
