package com.rbraithwaite.untitledmovieapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
abstract class MediaDao {
    @Insert
    abstract suspend fun addCustomMedia(
        customMedia: CustomMediaEntity
    ): Long

    // REFACTOR [24-01-21 1:26a.m.] -- move to ReviewDao.
    @Insert
    abstract suspend fun addReview(
        mediaReview: MediaReviewEntity
    ): Long

    // TEST NEEDED [24-01-19 11:09p.m.] -- .
    // REFACTOR [24-01-19 10:54p.m.] -- hardcoded id col name.
    @Query("SELECT * FROM ${CustomMediaEntity.Contract.TABLE_NAME} WHERE id IN (:customMediaIds)")
    abstract suspend fun findCustomMediaWithIds(customMediaIds: List<Long>): List<CustomMediaEntity>

    // TEST NEEDED [24-01-28 12:02a.m.] --
    @Query(
        "SELECT * FROM ${TmdbLiteMovieEntity.Contract.TABLE_NAME} " +
                "WHERE ${TmdbLiteMovieEntity.Contract.Columns.ID} IN (:movieIds)"
    )
    abstract suspend fun findTmdbLiteMoviesById(movieIds: List<Long>): List<TmdbLiteMovieWithGenres>

    // TODO [23-11-3 10:04p.m.] -- I need a strategy for testing the real & fake dao in parallel to
    //  keep their behaviour aligned.
    // This syntax "'%' || :searchCriteria || '%'" concatenates the %'s with the search criteria
    // string, so that the search doesn't need to be an exact match
    @Query("SELECT * FROM ${CustomMediaEntity.Contract.TABLE_NAME} " +
            "WHERE ${CustomMediaEntity.Contract.Columns.TITLE} LIKE '%' || :searchCriteria || '%'")
    abstract suspend fun searchCustomMedia(searchCriteria: String): List<CustomMediaEntity>

    @Transaction
    open suspend fun addNewCustomMediaWithReview(
        customMedia: CustomMediaEntity,
        mediaReview: MediaReviewEntity
    ) {
        val mediaId = addCustomMedia(customMedia)

        val mediaReview2 = mediaReview.copy(mediaId = mediaId)
        addReview(mediaReview2)
    }

    // TEST NEEDED [24-01-27 12:42p.m.] -- .
    // TODO [24-01-27 12:33p.m.] -- this should return the id.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addOrUpdateTmdbLiteMovie(movie: TmdbLiteMovieEntity)

    // TEST NEEDED [24-01-22 12:40a.m.] -- .
    @Transaction
    open suspend fun addOrUpdateGenreIdsForMovie(
        movieId: Long,
        genreIds: List<Int>
    ) {
        clearGenreIdsForMovie(movieId)

        val junctions = genreIds.map { genreId ->
            TmdbLiteMovieGenreJunction(movieId, genreId)
        }.toTypedArray()

        addMovieGenreJunctions(*junctions)
    }

    @Query("DELETE FROM movie_x_genre WHERE movie_id = :movieId")
    protected abstract suspend fun clearGenreIdsForMovie(movieId: Long)

    @Insert
    protected abstract suspend fun addMovieGenreJunctions(vararg junctions: TmdbLiteMovieGenreJunction)
}