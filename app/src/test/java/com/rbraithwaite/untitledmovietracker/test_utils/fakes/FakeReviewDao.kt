package com.rbraithwaite.untitledmovietracker.test_utils.fakes

import com.rbraithwaite.untitledmovieapp.data.database.MediaReviewEntity
import com.rbraithwaite.untitledmovieapp.data.database.ReviewDao

class FakeReviewDao(
    private val database: FakeDatabase
): ReviewDao() {
    override suspend fun getAllReviews(): List<MediaReviewEntity> {
        return database.find()
    }
}