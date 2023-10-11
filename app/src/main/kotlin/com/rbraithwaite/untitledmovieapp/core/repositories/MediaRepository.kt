package com.rbraithwaite.untitledmovieapp.core.repositories

import com.rbraithwaite.untitledmovieapp.core.data.CustomMedia
import com.rbraithwaite.untitledmovieapp.core.data.MediaReview

interface MediaRepository {
    suspend fun addNewCustomMediaWithReview(
        customMedia: CustomMedia,
        review: MediaReview
    )
}