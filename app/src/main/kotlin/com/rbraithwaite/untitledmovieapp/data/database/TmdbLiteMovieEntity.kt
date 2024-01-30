package com.rbraithwaite.untitledmovieapp.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = TmdbLiteMovieEntity.Contract.TABLE_NAME)
data class TmdbLiteMovieEntity(
    @PrimaryKey @ColumnInfo(name = Contract.Columns.ID) val id: Long,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "poster_path") val posterPath: String?,
    @ColumnInfo(name = "popularity") val popularity: Float,
    @ColumnInfo(name = "release_date") val releaseDate: LocalDate?,
    @ColumnInfo(name = "vote_average") val voteAverage: Float,
    @ColumnInfo(name = "vote_count") val voteCount: Int
) {
    object Contract {
        const val TABLE_NAME = "tmdb_lite_movie"

        object Columns {
            const val ID = "id"
        }
    }
}