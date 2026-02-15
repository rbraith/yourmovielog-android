package com.rbraithwaite.yourmovielog.test_utils.fakes.repositories

import com.rbraithwaite.yourmovielog.core.data.MediaReview
import com.rbraithwaite.yourmovielog.core.data.MediaReviewWithMedia
import com.rbraithwaite.yourmovielog.core.repositories.ReviewRepository
import com.rbraithwaite.yourmovielog.test_utils.data_builders.core_data.MediaReviewBuilder
import com.rbraithwaite.yourmovielog.test_utils.data_builders.core_data.aTvShow

class FakeReviewRepository : ReviewRepository {

    private val reviews: MutableList<MediaReview> = mutableListOf()

    /**
     * If this is null, functionality that requires media (like getAllReviewsWithMedia()), will use
     * a dummy media object shared between all reviews regardless of their mediaId.
     */
    var fakeMediaRepository: FakeMediaRepository? = null

    // *********************************************************
    // ReviewRepository
    // *********************************************************
    //region ReviewRepository

    override suspend fun addReview(review: MediaReview) {
        // SMELL [26-01-7 11:30p.m.] mediaId here reveals a problem
        //  in the real code, the db review entity uses the media id
        //  I'm thinking mediaId should be on all MediaReview objs already?
        //  there's a similar thing for media tv episodes/seasons.
        reviews.add(review)
    }

    override suspend fun getAllReviews(): List<MediaReview> {
        return reviews
    }

    override suspend fun getAllReviewsWithMedia(): List<MediaReviewWithMedia> {
        if (fakeMediaRepository == null) {
            val dummyMedia = aTvShow().build()
            return reviews.map {
                MediaReviewWithMedia(it, dummyMedia)
            }
        } else {
            val mediaIds = reviews.map { it.mediaUuid }.toSet()
            val media = fakeMediaRepository!!.getMediaMatchingIds(mediaIds)
            return reviews.map { review ->
                MediaReviewWithMedia(
                    review,
                    media.first { it.uuid == review.mediaUuid }
                )
            }
        }
    }

    //endregion ReviewRepository

    fun getReviews(): List<MediaReview> {
        return reviews
    }

    fun setReviews(vararg reviews: MediaReviewBuilder) {
        this.reviews.clear()
        this.reviews.addAll(reviews.map { it.build() })
    }
}
