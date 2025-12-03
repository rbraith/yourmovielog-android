package com.rbraithwaite.untitledmovietracker.test_utils.fakes.database

import com.rbraithwaite.untitledmovieapp.data.database.entities.ReviewEntity
import com.rbraithwaite.untitledmovieapp.data.database.dao.ReviewDao
import com.rbraithwaite.untitledmovieapp.data.database.entities.MediaReviewEntity
import org.mockito.kotlin.mock

class FakeReviewDao(
    private val database: FakeDatabase
): ReviewDao() {
    val mock: ReviewDao = mock()

    override suspend fun insertReview(review: MediaReviewEntity) {
        mock.insertReview(review)

        database.insert(review, MediaReviewEntityIdSelector())
    }

    override suspend fun getAllReviews(): List<ReviewEntity> {
        mock.getAllReviews()

        return database.find()
    }

    override suspend fun upsertReviews(vararg entities: ReviewEntity): List<Long> {
        mock.upsertReviews(*entities)

        return database.upsertMultiple(
            entities.toList(),
            ReviewEntityIdSelector()
        )
    }
}

class MediaReviewEntityIdSelector: IdSelector<MediaReviewEntity, String> {
    override fun getId(entity: MediaReviewEntity): String = entity.uuid

    override fun updateId(
        entity: MediaReviewEntity,
        newId: String
    ): MediaReviewEntity {
        return entity.copy(uuid = newId)
    }

    override fun isNewEntity(entity: MediaReviewEntity): Boolean = false

    override fun incrementId(id: String): String = id
}

class ReviewEntityIdSelector: LongIdSelector<ReviewEntity>() {
    override fun getId(entity: ReviewEntity): Long {
        return entity.id
    }

    override fun updateId(entity: ReviewEntity, newId: Long): ReviewEntity {
        return entity.copy(id = newId)
    }
}