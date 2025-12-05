package com.rbraithwaite.untitledmovietracker.test_utils.fakes.database

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