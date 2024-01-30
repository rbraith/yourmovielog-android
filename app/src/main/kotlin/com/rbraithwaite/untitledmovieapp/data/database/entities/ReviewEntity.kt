package com.rbraithwaite.untitledmovieapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rbraithwaite.untitledmovieapp.core.data.ReviewDate

@Entity(tableName = ReviewEntity.Contract.TABLE_NAME)
data class ReviewEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = Contract.Columns.MEDIA_ID)
    val mediaId: Long = -1,

    // used to distinguish between ids for custom media or Tmdb media
    @ColumnInfo(name = Contract.Columns.MEDIA_TYPE)
    val mediaType: String,

    @ColumnInfo(name = Contract.Columns.RATING)
    val rating: Int?,

    @ColumnInfo(name = Contract.Columns.REVIEW)
    val review: String?,

    @ColumnInfo(name = Contract.Columns.REVIEW_DATE)
    val reviewDate: ReviewDate?,

    @ColumnInfo(name = Contract.Columns.WATCH_CONTEXT)
    val watchContext: String?
) {
    enum class Type(val value: String) {
        CUSTOM("custom"),
        TMDB_MOVIE("tmdb_movie")
    }

    object Contract {
        const val TABLE_NAME = "reviews"

        object Columns {
            const val MEDIA_ID = "media_id"
            const val MEDIA_TYPE = "media_type"
            const val RATING = "rating"
            const val REVIEW = "review"
            const val REVIEW_DATE = "review_date"
            const val WATCH_CONTEXT = "watch_context"
        }
    }
}
