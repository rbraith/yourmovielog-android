package com.rbraithwaite.untitledmovieapp.core.repositories

import com.rbraithwaite.untitledmovieapp.core.data.MediaReview
import java.util.UUID
import kotlin.reflect.KClass

interface ReviewRepository {
    suspend fun addReview(review: MediaReview, mediaId: UUID)
}