package com.rbraithwaite.untitledmovieapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = TmdbLiteMovieGenreJunction.Contract.TABLE_NAME,
    primaryKeys = [
        TmdbLiteMovieGenreJunction.Contract.Columns.MOVIE_ID,
        TmdbLiteMovieGenreJunction.Contract.Columns.GENRE_ID
    ]
)
data class TmdbLiteMovieGenreJunction(
    @ColumnInfo(name = Contract.Columns.MOVIE_ID)
    val movieId: Long,

    @ColumnInfo(name = Contract.Columns.GENRE_ID)
    val genreId: Int
) {
    object Contract {
        const val TABLE_NAME = "movie_x_genre"

        object Columns {
            const val MOVIE_ID = "movie_id"
            const val GENRE_ID = "genre_id"
        }
    }
}
