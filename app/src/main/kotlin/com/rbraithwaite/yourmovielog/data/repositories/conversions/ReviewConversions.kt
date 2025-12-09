package com.rbraithwaite.yourmovielog.data.repositories.conversions

import com.rbraithwaite.yourmovielog.core.data.MediaReview
import com.rbraithwaite.yourmovielog.data.database.entities.MediaReviewEntity
import java.time.ZoneOffset
import java.util.UUID

fun MediaReview.toEntity(mediaId: UUID): MediaReviewEntity {
    return MediaReviewEntity(
        uuid = uuid.toString(),
        mediaId = mediaId.toString(),
        rating = rating,
        review = review,
        reviewDate = reviewDate,
        watchContext = watchContext,
        createdAt = createdAt.toEpochSecond(ZoneOffset.UTC),
        modifiedAt = modifiedAt.toEpochSecond(ZoneOffset.UTC),
    )
}