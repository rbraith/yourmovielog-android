package com.rbraithwaite.untitledmovieapp.core.repositories

import com.rbraithwaite.untitledmovieapp.core.data.MediaReview

interface MediaRepository {
    suspend fun addNewCustomMediaWithReview(
        customMediaTitle: String,
        review: MediaReview
    ): Result<Unit>
}