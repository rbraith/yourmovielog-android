package com.rbraithwaite.untitledmovietracker.test_utils

import com.rbraithwaite.untitledmovieapp.core.data.Review
import com.rbraithwaite.untitledmovieapp.data.database.entities.ReviewEntity

fun ReviewEntity.equalsReview(review: Review): Boolean {
    if (id != review.id) {
        return false
    }

    when (review.mediaType) {
        is Review.MediaType.CustomMovie -> {
            if (mediaType != ReviewEntity.Type.CUSTOM.value) {
                return false
            }

            if (mediaId != (review.mediaType as Review.MediaType.CustomMovie).id) {
                return false
            }
        }
        is Review.MediaType.TmdbMovie -> {
            if (mediaType != ReviewEntity.Type.TMDB_MOVIE.value) {
                return false
            }

            if (mediaId != (review.mediaType as Review.MediaType.TmdbMovie).id) {
                return false
            }
        }
    }

    if (this.review != review.review) {
        return false
    }

    if (reviewDate != review.reviewDate) {
        return false
    }

    if (rating != review.rating) {
        return false
    }

    if (watchContext != review.watchContext) {
        return false
    }

    return true
}