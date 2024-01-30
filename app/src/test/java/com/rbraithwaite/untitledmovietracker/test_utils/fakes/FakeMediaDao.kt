package com.rbraithwaite.untitledmovietracker.test_utils.fakes

import com.rbraithwaite.untitledmovieapp.data.database.entities.CustomMediaEntity
import com.rbraithwaite.untitledmovieapp.data.database.dao.MediaDao
import com.rbraithwaite.untitledmovieapp.data.database.entities.MediaReviewEntity
import com.rbraithwaite.untitledmovieapp.data.database.entities.TmdbLiteMovieEntity
import com.rbraithwaite.untitledmovieapp.data.database.entities.TmdbLiteMovieGenreJunction
import com.rbraithwaite.untitledmovieapp.data.database.entities.combined.TmdbLiteMovieWithGenres

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

    override suspend fun findTmdbLiteMoviesById(movieIds: List<Long>): List<TmdbLiteMovieWithGenres> {
        val movies = database.find<TmdbLiteMovieEntity> { movieIds.contains(id) }

        val foundMovieIds = movies.map { it.id }

        val genreJunctions = database.find<TmdbLiteMovieGenreJunction> { foundMovieIds.contains(movieId) }
        val groupedGenreJunctions = genreJunctions.groupBy { it.movieId }

        return movies.map { movie ->
            TmdbLiteMovieWithGenres(
                movie,
                groupedGenreJunctions.getOrElse(movie.id) { emptyList() }
            )
        }
    }

    override suspend fun addOrUpdateTmdbLiteMovie(movie: TmdbLiteMovieEntity) {
        val found = database.find<TmdbLiteMovieEntity> { id == movie.id }

        if (found.isEmpty()) {
            database.insert(movie)
        } else {
            database.update(movie) { id == movie.id }
        }
    }

    override suspend fun clearGenreIdsForMovie(movieId: Long) {
        database.delete<TmdbLiteMovieGenreJunction> {
            this.movieId == movieId
        }
    }

    override suspend fun addMovieGenreJunctions(vararg junctions: TmdbLiteMovieGenreJunction) {
        for (junction in junctions) {
            database.insert(junction)
        }
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