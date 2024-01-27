package com.rbraithwaite.untitledmovieapp.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "movie_x_genre",
    primaryKeys = ["movie_id", "genre_id"]
)
data class TmdbLiteMovieGenreJunction(
    @ColumnInfo(name = "movie_id") val movieId: Int,
    @ColumnInfo(name = "genre_id") val genreId: Int
)
