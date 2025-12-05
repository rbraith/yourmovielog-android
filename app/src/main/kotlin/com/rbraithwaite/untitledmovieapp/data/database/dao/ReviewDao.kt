package com.rbraithwaite.untitledmovieapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rbraithwaite.untitledmovieapp.data.database.entities.MediaReviewEntity

@Dao
abstract class ReviewDao {
    @Insert
    abstract suspend fun insertReview(review: MediaReviewEntity)
}