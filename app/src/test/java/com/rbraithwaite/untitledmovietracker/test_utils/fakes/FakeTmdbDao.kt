package com.rbraithwaite.untitledmovietracker.test_utils.fakes

import com.rbraithwaite.untitledmovieapp.data.database.entities.CustomMovieEntity
import com.rbraithwaite.untitledmovieapp.data.database.dao.TmdbDao
import com.rbraithwaite.untitledmovieapp.data.database.entities.ReviewEntity
import com.rbraithwaite.untitledmovieapp.data.database.entities.TmdbLiteMovieEntity
import com.rbraithwaite.untitledmovieapp.data.database.entities.TmdbLiteMovieGenreJunction
import com.rbraithwaite.untitledmovieapp.data.database.entities.combined.TmdbLiteMovieWithGenres

class FakeTmdbDao(
    private val database: FakeDatabase
): TmdbDao() {
    override suspend fun insertOrUpdateTmdbLiteMovies(vararg movies: TmdbLiteMovieEntity): List<Long> {
        TODO("Not yet implemented")
    }

    // TODO [24-02-2 12:07a.m.] broken.
//    override suspend fun addCustomMedia(customMedia: CustomMovieEntity): Long {
//        return database.insert(customMedia, updateCustomMediaId)
//    }
//
//    override suspend fun addReview(mediaReview: ReviewEntity): Long {
//        return database.insert(mediaReview, updateMediaReviewId)
//    }
//
//    override suspend fun searchCustomMedia(searchCriteria: String): List<CustomMovieEntity> {
//        val allCustomMedia = database.find<CustomMovieEntity>()
//        return allCustomMedia.filter { it.title.contains(searchCriteria) }
//    }
//
//    override suspend fun findCustomMediaWithIds(customMediaIds: List<Long>): List<CustomMovieEntity> {
//        return database.find {
//            customMediaIds.contains(this.id)
//        }
//    }

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

    // TODO [24-02-2 12:07a.m.] broken.
//    override suspend fun addOrUpdateTmdbLiteMovie(movie: TmdbLiteMovieEntity) {
//        val found = database.find<TmdbLiteMovieEntity> { id == movie.id }
//
//        if (found.isEmpty()) {
//            database.insert(movie)
//        } else {
//            database.update(movie) { id == movie.id }
//        }
//    }

    override suspend fun clearGenreIdsForMovie(movieId: Long) {
        database.delete<TmdbLiteMovieGenreJunction> {
            this.movieId == movieId
        }
    }

    override suspend fun addMovieGenreJunctions(vararg junctions: TmdbLiteMovieGenreJunction) {
        // TODO [24-02-2 10:04p.m.]
//        for (junction in junctions) {
//            database.insert(junction)
//        }
    }
}

// REFACTOR [23-10-29 3:55p.m.] -- move this
private val updateCustomMediaId: (CustomMovieEntity.(Long) -> CustomMovieEntity) = {
    if (this.id == 0L) {
        this.copy(id = it)
    } else {
        this
    }
}

// REFACTOR [23-10-29 4:03p.m.] -- might be good to refactor entities with some common data class
//  holding a primary key long id?
private val updateMediaReviewId: (ReviewEntity.(Long) -> ReviewEntity) = {
    if (this.id == 0L) {
        this.copy(id = it)
    } else {
        this
    }
}