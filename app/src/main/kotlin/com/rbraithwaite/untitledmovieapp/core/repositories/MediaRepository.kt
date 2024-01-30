package com.rbraithwaite.untitledmovieapp.core.repositories

import com.rbraithwaite.untitledmovieapp.core.data.CustomMedia
import com.rbraithwaite.untitledmovieapp.core.data.Media
import com.rbraithwaite.untitledmovieapp.core.data.MediaReview
import com.rbraithwaite.untitledmovieapp.core.data.SearchResult
import com.rbraithwaite.untitledmovieapp.core.data.TmdbLite

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

    suspend fun addOrUpdateTmdbLite(tmdblite: TmdbLite)

    // REFACTOR [24-01-27 12:27p.m.] -- this should be moved to ReviewRepository.
    // REFACTOR [23-12-20 3:41p.m.] -- this is very lazy lol - I need to rethink how I'm adding reviews,
    //  and core data in general, like have one common addMediaReview(<media-specific-id>, review)
    //  ---
    //  re: core data, I need to think about how to handle tmdb data.
    // SMELL [23-12-20 3:43p.m.] -- should this be saying 'tmdb'? is that too much info here?
    suspend fun addTmdbMovieReview(
        tmdbMovieId: Long,
        review: MediaReview
    )

    suspend fun findMedia(searchCriteria: String): List<SearchResult>
}