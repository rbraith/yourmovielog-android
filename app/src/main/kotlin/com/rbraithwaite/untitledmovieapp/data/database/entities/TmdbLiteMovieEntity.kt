package com.rbraithwaite.untitledmovieapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = TmdbLiteMovieEntity.Contract.TABLE_NAME)
data class TmdbLiteMovieEntity(
    @PrimaryKey
    @ColumnInfo(name = Contract.Columns.ID)
    val id: Long,

    @ColumnInfo(name = Contract.Columns.IS_ADULT)
    val isAdult: Boolean,

    @ColumnInfo(name = Contract.Columns.BACKDROP_PATH)
    val backdropPath: String?,

    @ColumnInfo(name = Contract.Columns.TITLE)
    val title: String,

    @ColumnInfo(name = Contract.Columns.ORIGINAL_LANGUAGE)
    val originalLanguage: String,

    @ColumnInfo(name = Contract.Columns.ORIGINAL_TITLE)
    val originalTitle: String,

    @ColumnInfo(name = Contract.Columns.OVERVIEW)
    val overview: String,

    @ColumnInfo(name = Contract.Columns.POSTER_PATH)
    val posterPath: String?,

    @ColumnInfo(name = Contract.Columns.POPULARITY)
    val popularity: Float,

    @ColumnInfo(name = Contract.Columns.RELEASE_DATE)
    val releaseDate: LocalDate?,

    @ColumnInfo(name = Contract.Columns.VIDEO)
    val video: Boolean,

    @ColumnInfo(name = Contract.Columns.VOTE_AVERAGE)
    val voteAverage: Float,

    @ColumnInfo(name = Contract.Columns.VOTE_COUNT)
    val voteCount: Int
) {
    object Contract {
        const val TABLE_NAME = "tmdb_lite_movie"

        object Columns {
            const val ID = "id"
            const val IS_ADULT = "is_adult"
            const val BACKDROP_PATH = "backdrop_path"
            const val TITLE = "title"
            const val ORIGINAL_LANGUAGE = "orig_lang"
            const val ORIGINAL_TITLE = "orig_title"
            const val OVERVIEW = "overview"
            const val POSTER_PATH = "poster_path"
            const val POPULARITY = "popularity"
            const val RELEASE_DATE = "release_date"
            const val VIDEO = "video"
            const val VOTE_AVERAGE = "vote_average"
            const val VOTE_COUNT = "vote_count"
        }
    }
}