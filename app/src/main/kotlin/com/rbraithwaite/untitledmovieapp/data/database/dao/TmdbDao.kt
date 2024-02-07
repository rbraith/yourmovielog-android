package com.rbraithwaite.untitledmovieapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.rbraithwaite.untitledmovieapp.data.database.entities.combined.TmdbLiteMovieWithGenres
import com.rbraithwaite.untitledmovieapp.data.database.entities.TmdbLiteMovieEntity
import com.rbraithwaite.untitledmovieapp.data.database.entities.TmdbLiteMovieGenreJunction

@Dao
abstract class TmdbDao {
    // TEST NEEDED [24-01-28 12:02a.m.] --
    @Query(
        "SELECT * FROM ${TmdbLiteMovieEntity.Contract.TABLE_NAME} " +
                "WHERE ${TmdbLiteMovieEntity.Contract.Columns.ID} IN (:movieIds)"
    )
    abstract suspend fun findTmdbLiteMoviesById(movieIds: List<Long>): List<TmdbLiteMovieWithGenres>

    // TEST NEEDED [24-01-27 12:42p.m.] -- .
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun upsertTmdbLiteMovies(vararg movies: TmdbLiteMovieEntity): List<Long>

    // TEST NEEDED [24-01-22 12:40a.m.] -- .
    @Transaction
    open suspend fun upsertGenreIdsForMovie(
        movieId: Long,
        genreIds: List<Int>
    ) {
        clearGenreIdsForMovie(movieId)

        val junctions = genreIds.map { genreId ->
            TmdbLiteMovieGenreJunction(movieId, genreId)
        }.toTypedArray()

        addMovieGenreJunctions(*junctions)
    }

    @Query(
        "DELETE FROM ${TmdbLiteMovieGenreJunction.Contract.TABLE_NAME} " +
                "WHERE ${TmdbLiteMovieGenreJunction.Contract.Columns.MOVIE_ID} = :movieId")

    protected abstract suspend fun clearGenreIdsForMovie(movieId: Long)

    @Insert
    protected abstract suspend fun addMovieGenreJunctions(vararg junctions: TmdbLiteMovieGenreJunction)
}