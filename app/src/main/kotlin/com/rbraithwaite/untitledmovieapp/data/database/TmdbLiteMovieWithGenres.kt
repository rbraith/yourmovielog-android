package com.rbraithwaite.untitledmovieapp.data.database

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class TmdbLiteMovieWithGenres(
    @Embedded
    val tmdbMovie: TmdbLiteMovieEntity,
    // REFACTOR [24-01-28 12:20a.m.] -- not exactly how you're supposed to use junction tables
    //  really, as the joined table itself. the movie genre ids shouldn't be a junction I guess?
    //  or I return the actual genre entities idk.
    @Relation(
        parentColumn = TmdbLiteMovieEntity.Contract.Columns.ID,
        entityColumn = "movie_id",
    )
    val genreIds: List<TmdbLiteMovieGenreJunction>
)