package com.rbraithwaite.untitledmovietracker.test_utils.fakes

import com.rbraithwaite.untitledmovieapp.data.database.CustomMediaEntity
import com.rbraithwaite.untitledmovieapp.data.database.MediaDao
import com.rbraithwaite.untitledmovieapp.data.database.MediaReviewEntity
import com.rbraithwaite.untitledmovieapp.data.database.TmdbLiteMovieEntity
import com.rbraithwaite.untitledmovieapp.data.database.TmdbLiteMovieGenreJunction

class FakeMediaDao(
    private val database: FakeDatabase
): MediaDao() {

    override suspend fun addCustomMedia(customMedia: CustomMediaEntity): Long {
        return database.insert(customMedia, updateCustomMediaId)
    }

    override suspend fun addReview(mediaReview: MediaReviewEntity): Long {
        return database.insert(mediaReview, updateMediaReviewId)
    }

    override suspend fun searchCustomMedia(searchCriteria: String): List<CustomMediaEntity> {
        val allCustomMedia = database.find<CustomMediaEntity>()
        return allCustomMedia.filter { it.title.contains(searchCriteria) }
    }

    override suspend fun findCustomMediaWithIds(customMediaIds: List<Long>): List<CustomMediaEntity> {
        return database.find {
            customMediaIds.contains(this.id)
        }
    }

    override suspend fun addOrUpdateTmdbLiteMovie(movie: TmdbLiteMovieEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun clearGenreIdsForMovie(movieId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun addMovieGenreJunctions(vararg junctions: TmdbLiteMovieGenreJunction) {
        TODO("Not yet implemented")
    }
}

// REFACTOR [23-10-29 3:55p.m.] -- move this
private val updateCustomMediaId: (CustomMediaEntity.(Long) -> CustomMediaEntity) = {
    if (this.id == 0L) {
        this.copy(id = it)
    } else {
        this
    }
}

// REFACTOR [23-10-29 4:03p.m.] -- might be good to refactor entities with some common data class
//  holding a primary key long id?
private val updateMediaReviewId: (MediaReviewEntity.(Long) -> MediaReviewEntity) = {
    if (this.id == 0L) {
        this.copy(id = it)
    } else {
        this
    }
}