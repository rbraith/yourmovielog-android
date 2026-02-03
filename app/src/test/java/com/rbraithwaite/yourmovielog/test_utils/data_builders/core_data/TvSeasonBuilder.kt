package com.rbraithwaite.yourmovielog.test_utils.data_builders.core_data

import com.rbraithwaite.test_data_utils.TestDataBuilder
import com.rbraithwaite.test_data_utils.builder_base.BaseBuilder
import com.rbraithwaite.yourmovielog.core.data.TvShow
import java.time.LocalDateTime
import java.util.UUID

fun aTvSeason(buildBlock: TvSeasonBuilder.() -> Unit = {}): TvSeasonBuilder {
    return TvSeasonBuilder().apply(buildBlock)
}

@BaseBuilder
abstract class AbstractTvSeasonBuilder : TestDataBuilder<TvShow.Season>()

class TvSeasonBuilder : BaseAbstractTvSeasonBuilder<TvSeasonBuilder>() {
    override var data = TvShow.Season(
        seasonNumber = 1,
        uuid = UUID(12L, 34L),
        createdAt = LocalDateTime.of(2023, 1, 1, 0, 0),
        modifiedAt = LocalDateTime.of(2023, 1, 1, 0, 0),
        tmdbId = null
    )
}
