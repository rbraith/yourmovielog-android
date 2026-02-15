package com.rbraithwaite.yourmovielog.core.repositories

import com.rbraithwaite.yourmovielog.core.data.MediaReview
import com.rbraithwaite.yourmovielog.core.data.MediaReviewWithMedia

interface ReviewRepository {
    suspend fun addReview(review: MediaReview)

    // TODO [26-02-12 4:40p.m.] delete.
    suspend fun getAllReviews(): List<MediaReview>

    suspend fun getAllReviewsWithMedia(): List<MediaReviewWithMedia>
}
