package com.rbraithwaite.untitledmovieapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rbraithwaite.untitledmovieapp.data.database.entities.ReviewEntity

@Dao
abstract class ReviewDao {
    // TEST NEEDED [24-01-19 10:35p.m.] -- .
    @Query("SELECT * FROM ${ReviewEntity.Contract.TABLE_NAME}")
    abstract suspend fun getAllReviews(): List<ReviewEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertOrUpdateReviews(vararg entities: ReviewEntity): List<Long>
}