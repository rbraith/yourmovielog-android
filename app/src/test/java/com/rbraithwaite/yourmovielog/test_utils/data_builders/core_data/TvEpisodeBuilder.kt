package com.rbraithwaite.yourmovielog.test_utils.data_builders.core_data

import com.rbraithwaite.test_data_utils.TestDataBuilder
import com.rbraithwaite.test_data_utils.builder_base.BaseBuilder
import com.rbraithwaite.yourmovielog.core.data.TvShow
import java.time.LocalDateTime
import java.util.UUID

@BaseBuilder
abstract class AbstractTvEpisodeBuilder : TestDataBuilder<TvShow.Episode>()

fun aTvEpisode(buildBlock: TvEpisodeBuilder.() -> Unit = {}): TvEpisodeBuilder {
    return TvEpisodeBuilder().apply(buildBlock)
}

class TvEpisodeBuilder : BaseAbstractTvEpisodeBuilder<TvEpisodeBuilder>() {
    override var data = TvShow.Episode(
        uuid = UUID(12L, 34L),
        title = "a tv episode title",
        episodeNumber = 1,
        createdAt = LocalDateTime.of(2023, 1, 1, 0, 0),
        modifiedAt = LocalDateTime.of(2023, 1, 1, 0, 0),
        tmdbId = null
    )
}
