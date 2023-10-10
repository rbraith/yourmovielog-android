package com.rbraithwaite.untitledmovieapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Transaction

@Dao
abstract class MediaDao {
    @Insert
    abstract suspend fun addCustomMedia(
        customMedia: CustomMediaEntity
    ): Long

    @Insert
    abstract suspend fun addReview(
        mediaReview: MediaReviewEntity
    ): Long

    @Transaction
    open suspend fun addCustomMediaWithReview(
        customMedia: CustomMediaEntity,
        mediaReview: MediaReviewEntity
    ) {
        val mediaId = addCustomMedia(customMedia)

        val mediaReview2 = mediaReview.copy(mediaId = mediaId)
        addReview(mediaReview2)
    }
}