package com.rbraithwaite.yourmovielog.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rbraithwaite.yourmovielog.data.database.entities.MediaReviewEntity

@Dao
abstract class ReviewDao {
    @Insert
    abstract suspend fun insertReview(review: MediaReviewEntity)

    @Query("SELECT * FROM ${MediaReviewEntity.Contract.TABLE_NAME}")
    abstract suspend fun getAllReviews(): List<MediaReviewEntity>
}
