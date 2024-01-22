package com.rbraithwaite.untitledmovieapp.core.repositories

import com.rbraithwaite.untitledmovieapp.core.data.MediaReview
import kotlin.reflect.KClass

interface ReviewRepository {
    suspend fun getAllReviews(extras: Set<KClass<out MediaReview.Extras>> = emptySet()): List<MediaReview>

    suspend fun addReviewForCustomMedia(review: MediaReview, customMediaId: Long)
}