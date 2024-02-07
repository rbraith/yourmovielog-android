package com.rbraithwaite.untitledmovieapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rbraithwaite.untitledmovieapp.data.database.entities.CustomMovieEntity

@Dao
abstract class CustomMediaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun upsertCustomMovies(vararg customMovies: CustomMovieEntity): List<Long>

    // TEST NEEDED [24-01-19 11:09p.m.] -- .
    @Query(
        "SELECT * FROM ${CustomMovieEntity.Contract.TABLE_NAME} " +
                "WHERE ${CustomMovieEntity.Contract.Columns.ID} IN (:customMovieIds)"
    )
    abstract suspend fun findCustomMoviesWithIds(customMovieIds: List<Long>): List<CustomMovieEntity>

    // TODO [23-11-3 10:04p.m.] -- I need a strategy for testing the real & fake dao in parallel to
    //  keep their behaviour aligned.
    // This syntax "'%' || :searchCriteria || '%'" concatenates the %'s with the search criteria
    // string, so that the search doesn't need to be an exact match
    @Query("SELECT * FROM ${CustomMovieEntity.Contract.TABLE_NAME} " +
            "WHERE ${CustomMovieEntity.Contract.Columns.TITLE} LIKE '%' || :searchCriteria || '%'")
    abstract suspend fun searchCustomMoviesByTitle(searchCriteria: String): List<CustomMovieEntity>
}