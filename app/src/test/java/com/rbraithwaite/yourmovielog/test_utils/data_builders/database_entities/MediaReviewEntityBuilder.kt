package com.rbraithwaite.yourmovielog.test_utils.data_builders.database_entities

import com.rbraithwaite.test_data_utils.TestDataBuilder
import com.rbraithwaite.test_data_utils.builder_base.BaseBuilder
import com.rbraithwaite.yourmovielog.core.data.ReviewDate
import com.rbraithwaite.yourmovielog.data.database.entities.MediaReviewEntity
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.UUID

@BaseBuilder
abstract class AbstractMediaReviewEntityBuilder : TestDataBuilder<MediaReviewEntity>()

class MediaReviewEntityBuilder : BaseAbstractMediaReviewEntityBuilder<MediaReviewEntityBuilder>() {
    override var data: MediaReviewEntity = MediaReviewEntity(
        uuid = UUID(12L, 34L).toString(),
        mediaId = UUID(56L, 78L).toString(),
        rating = 5,
        review = "a review",
        reviewDate = ReviewDate(2023, 0, 1),
        watchContext = "a watch context",
        createdAt = LocalDateTime.of(2023, 1, 1, 0, 0).toEpochSecond(ZoneOffset.UTC),
        modifiedAt = LocalDateTime.of(2023, 1, 1, 0, 0).toEpochSecond(ZoneOffset.UTC)
    )

    fun withUuid(uuid: UUID): MediaReviewEntityBuilder = this.apply { data = data.copy(uuid = uuid.toString()) }

    fun withMediaUuid(mediaId: UUID): MediaReviewEntityBuilder = this.apply {
        data = data.copy(mediaId = mediaId.toString())
    }
}

fun aMediaReviewEntity(buildBlock: MediaReviewEntityBuilder.() -> Unit = {}): MediaReviewEntityBuilder {
    return MediaReviewEntityBuilder().apply(buildBlock)
}
