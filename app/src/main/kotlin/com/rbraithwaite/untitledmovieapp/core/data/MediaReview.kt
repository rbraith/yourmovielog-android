package com.rbraithwaite.untitledmovieapp.core.data

import java.time.LocalDateTime
import java.util.UUID

data class MediaReview(
    val uuid: UUID,
    val rating: Int?,
    val review: String?,
    val reviewDate: ReviewDate?,
    val watchContext: String?,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime,
)
