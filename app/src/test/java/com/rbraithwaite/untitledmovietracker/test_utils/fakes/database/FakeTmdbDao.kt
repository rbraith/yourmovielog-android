package com.rbraithwaite.untitledmovietracker.test_utils.fakes.database

import com.rbraithwaite.untitledmovieapp.data.database.dao.TmdbDao
import com.rbraithwaite.untitledmovieapp.data.database.entities.TmdbLiteMovieEntity
import com.rbraithwaite.untitledmovieapp.data.database.entities.TmdbLiteMovieGenreJunction
import com.rbraithwaite.untitledmovieapp.data.database.entities.combined.TmdbLiteMovieWithGenres
import org.mockito.kotlin.mock

class FakeTmdbDao(
    private val database: FakeDatabase
): TmdbDao() {
    val mock: TmdbDao = mock()

    override suspend fun upsertTmdbLiteMovies(vararg movies: TmdbLiteMovieEntity): List<Long> {
        mock.upsertTmdbLiteMovies(*movies)

        return database.upsertMultiple(
            movies.toList(),
            TmdbLiteMovieIdSelector()
        )
    }

    override suspend fun upsertGenreIdsForMovie(movieId: Long, genreIds: List<Int>) {
        mock.upsertGenreIdsForMovie(movieId, genreIds)
        super.upsertGenreIdsForMovie(movieId, genreIds)
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

private class TmdbLiteMovieIdSelector: LongIdSelector<TmdbLiteMovieEntity>() {
    override fun getId(entity: TmdbLiteMovieEntity): Long {
        return entity.id
    }

    override fun updateId(entity: TmdbLiteMovieEntity, newId: Long): TmdbLiteMovieEntity {
        return entity.copy(id = newId)
    }
}