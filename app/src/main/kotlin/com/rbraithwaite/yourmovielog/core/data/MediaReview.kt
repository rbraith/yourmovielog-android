package com.rbraithwaite.yourmovielog.core.data

import java.time.LocalDateTime
import java.util.UUID

data class MediaReview(
    val uuid: UUID,
    val mediaUuid: UUID,
    val rating: Int?,
    val review: String?,
    val reviewDate: ReviewDate?,
    val watchContext: String?,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime,
)

data class MediaReviewWithMedia(
    val value: MediaReview,
    val media: Media,
)
