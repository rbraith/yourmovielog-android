package com.rbraithwaite.yourmovielog.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.rbraithwaite.yourmovielog.data.database.entities.MediaReviewEntity

@Dao
abstract class ReviewDao {
    @Insert
    abstract suspend fun insertReview(review: MediaReviewEntity)
}
