package com.rbraithwaite.untitledmovieapp.core.repositories

import com.rbraithwaite.untitledmovieapp.core.data.CustomMedia
import com.rbraithwaite.untitledmovieapp.core.data.Media
import com.rbraithwaite.untitledmovieapp.core.data.MediaReview
import com.rbraithwaite.untitledmovieapp.core.data.SearchResult

interface MediaRepository {
    /**
     * Add a new custom media to the repository. Adding new, as in the id of the arg is ignored and
     * 0L is used.
     */
    suspend fun addNewCustomMedia(customMedia: CustomMedia)

    /**
     * Add a new custom media, with a related media review.
     */
    suspend fun addNewCustomMediaWithReview(
        customMedia: CustomMedia,
        review: MediaReview
    )

    suspend fun findMedia(searchCriteria: String): List<SearchResult>
}