package com.rbraithwaite.untitledmovieapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.rbraithwaite.untitledmovieapp.core.data.ReviewDate

@Entity(
    tableName = MediaReviewEntity.Contract.TABLE_NAME,
)
data class MediaReviewEntity(
    @PrimaryKey
    @ColumnInfo(name = Contract.Columns.UUID)
    val uuid: String,

    @ColumnInfo(name = Contract.Columns.MEDIA_ID)
    val mediaId: String,

    @ColumnInfo(name = Contract.Columns.RATING)
    val rating: Int?,

    @ColumnInfo(name = Contract.Columns.REVIEW)
    val review: String?,

    @ColumnInfo(name = Contract.Columns.REVIEW_DATE)
    val reviewDate: ReviewDate?,

    @ColumnInfo(name = Contract.Columns.WATCH_CONTEXT)
    val watchContext: String?,

    @ColumnInfo(name = Contract.Columns.CREATED_AT)
    val createdAt: Long,

    @ColumnInfo(name = Contract.Columns.MODIFIED_AT)
    val modifiedAt: Long,
) {
    object Contract {
        const val TABLE_NAME = "media_review"

        object Columns {
            const val UUID = "uuid"
            const val MEDIA_ID = "media_id"
            const val RATING = "rating"
            const val REVIEW = "review"
            const val REVIEW_DATE = "review_date"
            const val WATCH_CONTEXT = "watch_context"
            const val CREATED_AT = "created_at"
            const val MODIFIED_AT = "modified_at"
        }
    }
}
