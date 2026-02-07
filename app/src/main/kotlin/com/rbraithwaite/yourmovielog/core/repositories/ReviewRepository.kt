package com.rbraithwaite.yourmovielog.core.repositories

import com.rbraithwaite.yourmovielog.core.data.MediaReview
import java.util.UUID

interface ReviewRepository {
    suspend fun addReview(review: MediaReview, mediaId: UUID)

    // TODO [26-02-6 10:15p.m.] consider pagination instead?
    suspend fun getAllReviews(): List<MediaReview>
}
