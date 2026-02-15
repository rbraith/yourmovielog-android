package com.rbraithwaite.yourmovielog.test_utils.data_builders.core_data

import com.rbraithwaite.test_data_utils.TestDataBuilder
import com.rbraithwaite.test_data_utils.builder_base.BaseBuilder
import com.rbraithwaite.yourmovielog.core.data.MediaReview
import java.time.LocalDateTime
import java.util.UUID

@BaseBuilder
abstract class AbstractMediaReviewBuilder : TestDataBuilder<MediaReview>()

class MediaReviewBuilder : BaseAbstractMediaReviewBuilder<MediaReviewBuilder>() {
    override var data = MediaReview(
        uuid = UUID(1L, 2L),
        mediaUuid = UUID(1L, 1L),
        rating = 78,
        review = "this is a review",
        reviewDate = null,
        watchContext = null,
        createdAt = LocalDateTime.of(2023, 1, 1, 1, 1, 1),
        modifiedAt = LocalDateTime.of(2023, 1, 1, 1, 1, 1)
    )
}

fun aReview(buildBlock: MediaReviewBuilder.() -> Unit = {}): MediaReviewBuilder {
    return MediaReviewBuilder().apply(buildBlock)
}
