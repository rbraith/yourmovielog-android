package com.rbraithwaite.untitledmovieapp.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.rbraithwaite.untitledmovieapp.data.database.entities.MediaReviewEntity

@Dao
abstract class ReviewDao {
    // TEST NEEDED [24-01-19 10:35p.m.] -- .
    // REFACTOR [24-01-19 10:24p.m.] -- hardcoded table name, should be in a Contract or maybe in the
    //  entity.
    @Query("SELECT * FROM reviews")
    abstract suspend fun getAllReviews(): List<MediaReviewEntity>
}