package com.rbraithwaite.untitledmovieapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rbraithwaite.untitledmovieapp.core.data.ReviewDate

@Entity(tableName = "reviews")
data class MediaReviewEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "media_id") val mediaId: Long = -1,
    // used to distinguish between ids for custom media or Tmdb media
    @ColumnInfo(name = "media_type") val mediaType: String,
    @ColumnInfo(name = "rating") val rating: Int?,
    @ColumnInfo(name = "review") val review: String?,
    @ColumnInfo(name = "review_date") val reviewDate: ReviewDate?,
    @ColumnInfo(name = "watch_context") val watchContext: String?
) {
    enum class Type(val value: String) {
        CUSTOM("custom"),
        TMDB_MOVIE("tmdb_movie")
    }
}
