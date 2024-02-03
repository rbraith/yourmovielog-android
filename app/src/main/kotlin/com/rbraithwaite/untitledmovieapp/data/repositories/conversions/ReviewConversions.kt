package com.rbraithwaite.untitledmovieapp.data.repositories.conversions

import com.rbraithwaite.untitledmovieapp.core.data.Review
import com.rbraithwaite.untitledmovieapp.data.database.entities.ReviewEntity
import java.lang.IllegalStateException

fun Review.toEntity(): ReviewEntity {
    val (mediaId: Long, mediaType: String) = when (mediaType) {
        is Review.MediaType.TmdbMovie -> {
            mediaType.id to ReviewEntity.Type.TMDB_MOVIE.value
        }
        is Review.MediaType.CustomMovie -> {
            mediaType.id to ReviewEntity.Type.CUSTOM.value
        }
    }

    return ReviewEntity(
        id = id,
        mediaId = mediaId,
        mediaType = mediaType,
        rating = rating,
        review = review,
        reviewDate = reviewDate,
        watchContext = watchContext
    )
}

fun ReviewEntity.toReview(): Review {
    val reviewMediaType = when (mediaType) {
        ReviewEntity.Type.CUSTOM.value -> {
            Review.MediaType.CustomMovie(mediaId)
        }
        ReviewEntity.Type.TMDB_MOVIE.value -> {
            Review.MediaType.TmdbMovie(mediaId)
        }
        else -> throw IllegalStateException("Unknown media type: $mediaType")
    }

    return Review(
        id = id,
        rating = rating,
        review = review,
        reviewDate = reviewDate,
        watchContext = watchContext,
        mediaType = reviewMediaType
    )
}