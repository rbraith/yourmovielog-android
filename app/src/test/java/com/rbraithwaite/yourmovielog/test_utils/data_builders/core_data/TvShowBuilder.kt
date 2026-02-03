package com.rbraithwaite.yourmovielog.test_utils.data_builders.core_data

import com.rbraithwaite.test_data_utils.TestDataBuilder
import com.rbraithwaite.test_data_utils.builder_base.BaseBuilder
import com.rbraithwaite.yourmovielog.core.data.TvShow
import java.time.LocalDateTime
import java.util.UUID

fun aTvShow(buildBlock: TvShowBuilder.() -> Unit = {}): TvShowBuilder {
    return TvShowBuilder().apply(buildBlock)
}

@BaseBuilder
abstract class AbstractTvShowBuilder : TestDataBuilder<TvShow>()

class TvShowBuilder : BaseAbstractTvShowBuilder<TmdbTvShowBuilder>() {
    override var data = TvShow(
        title = "test tv show",
        uuid = UUID(12L, 34L),
        createdAt = LocalDateTime.of(2023, 1, 1, 0, 0),
        modifiedAt = LocalDateTime.of(2023, 1, 1, 0, 0),
        tmdbId = null
    )
}
