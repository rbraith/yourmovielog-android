package com.rbraithwaite.untitledmovietracker.test_utils.fakes

import com.rbraithwaite.untitledmovieapp.data.database.entities.ReviewEntity
import com.rbraithwaite.untitledmovieapp.data.database.dao.ReviewDao

class FakeReviewDao(
    private val database: FakeDatabase
): ReviewDao() {
    override suspend fun getAllReviews(): List<ReviewEntity> {
        return database.find()
    }
}