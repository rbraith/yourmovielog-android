package com.rbraithwaite.untitledmovieapp.core.repositories

import com.rbraithwaite.untitledmovieapp.core.data.MediaReview
import com.rbraithwaite.untitledmovieapp.core.data.Review
import java.util.UUID
import kotlin.reflect.KClass

interface ReviewRepository {
    suspend fun addReview(review: MediaReview, mediaId: UUID)

    suspend fun getAllReviews(extras: Set<KClass<out Review.Extras>> = emptySet()): List<Review>

    suspend fun upsertReviews(vararg reviews: Review)
}