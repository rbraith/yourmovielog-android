package com.rbraithwaite.untitledmovieapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
abstract class MediaDao {
    @Insert
    abstract suspend fun addCustomMedia(
        customMedia: CustomMediaEntity
    ): Long

    // REFACTOR [24-01-21 1:26a.m.] -- move to ReviewDao.
    @Insert
    abstract suspend fun addReview(
        mediaReview: MediaReviewEntity
    ): Long

    // TEST NEEDED [24-01-19 11:09p.m.] -- .
    // REFACTOR [24-01-19 10:54p.m.] -- hardcoded id col name.
    @Query("SELECT * FROM ${CustomMediaEntity.Contract.TABLE_NAME} WHERE id IN (:customMediaIds)")
    abstract suspend fun findCustomMediaWithIds(customMediaIds: List<Long>): List<CustomMediaEntity>

    // TODO [23-11-3 10:04p.m.] -- I need a strategy for testing the real & fake dao in parallel to
    //  keep their behaviour aligned.
    // This syntax "'%' || :searchCriteria || '%'" concatenates the %'s with the search criteria
    // string, so that the search doesn't need to be an exact match
    @Query("SELECT * FROM ${CustomMediaEntity.Contract.TABLE_NAME} " +
            "WHERE ${CustomMediaEntity.Contract.Columns.TITLE} LIKE '%' || :searchCriteria || '%'")
    abstract suspend fun searchCustomMedia(searchCriteria: String): List<CustomMediaEntity>

    @Transaction
    open suspend fun addNewCustomMediaWithReview(
        customMedia: CustomMediaEntity,
        mediaReview: MediaReviewEntity
    ) {
        val mediaId = addCustomMedia(customMedia)

        val mediaReview2 = mediaReview.copy(mediaId = mediaId)
        addReview(mediaReview2)
    }
}