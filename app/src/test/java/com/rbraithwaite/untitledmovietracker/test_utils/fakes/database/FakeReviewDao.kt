package com.rbraithwaite.untitledmovietracker.test_utils.fakes.database

import com.rbraithwaite.untitledmovieapp.data.database.entities.ReviewEntity
import com.rbraithwaite.untitledmovieapp.data.database.dao.ReviewDao
import org.mockito.kotlin.mock

class FakeReviewDao(
    private val database: FakeDatabase
): ReviewDao() {
    val mock: ReviewDao = mock()

    override suspend fun getAllReviews(): List<ReviewEntity> {
        mock.getAllReviews()

        return database.find()
    }

    override suspend fun insertOrUpdateReviews(vararg entities: ReviewEntity): List<Long> {
        mock.insertOrUpdateReviews(*entities)

        return database.insertOrUpdateMultiple(
            entities.toList(),
            ReviewEntityIdSelector()
        )
    }
}

class ReviewEntityIdSelector: LongIdSelector<ReviewEntity>() {
    override fun getId(entity: ReviewEntity): Long {
        return entity.id
    }

    override fun updateId(entity: ReviewEntity, newId: Long): ReviewEntity {
        return entity.copy(id = newId)
    }
}