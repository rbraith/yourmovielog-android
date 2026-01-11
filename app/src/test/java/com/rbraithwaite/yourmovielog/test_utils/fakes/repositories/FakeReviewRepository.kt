package com.rbraithwaite.yourmovielog.test_utils.fakes.repositories

import com.rbraithwaite.yourmovielog.core.data.MediaReview
import com.rbraithwaite.yourmovielog.core.repositories.ReviewRepository
import java.util.UUID

class FakeReviewRepository : ReviewRepository {

    private val reviews: MutableList<MediaReview> = mutableListOf()

    // *********************************************************
    // ReviewRepository
    // *********************************************************
    //region ReviewRepository

    override suspend fun addReview(
        review: MediaReview,
        mediaId: UUID
    ) {
        // SMELL [26-01-7 11:30p.m.] mediaId here reveals a problem
        //  in the real code, the db review entity uses the media id
        //  I'm thinking mediaId should be on all MediaReview objs already?
        //  there's a similar thing for media tv episodes/seasons.
        reviews.add(review)
    }

    //endregion ReviewRepository

    fun getReviews(): List<MediaReview> {
        return reviews
    }
}
