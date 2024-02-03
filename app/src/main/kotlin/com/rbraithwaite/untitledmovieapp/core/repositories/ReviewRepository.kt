package com.rbraithwaite.untitledmovieapp.core.repositories

import com.rbraithwaite.untitledmovieapp.core.data.Review
import kotlin.reflect.KClass

interface ReviewRepository {
    suspend fun getAllReviews(extras: Set<KClass<out Review.Extras>> = emptySet()): List<Review>

    suspend fun addOrUpdateReviews(vararg reviews: Review)
}