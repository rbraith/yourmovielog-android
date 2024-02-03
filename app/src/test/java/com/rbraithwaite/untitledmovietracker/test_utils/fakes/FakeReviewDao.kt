package com.rbraithwaite.untitledmovietracker.test_utils.fakes

import com.rbraithwaite.untitledmovieapp.data.database.entities.ReviewEntity
import com.rbraithwaite.untitledmovieapp.data.database.dao.ReviewDao

class FakeReviewDao(
    private val database: FakeDatabase
): ReviewDao() {
    override suspend fun getAllReviews(): List<ReviewEntity> {
        return database.find()
    }

    override suspend fun insertOrUpdateReviews(vararg entities: ReviewEntity): List<Long> {
        val ids = mutableListOf<Long>()

        for (entity in entities) {
            // REFACTOR [24-02-2 12:31a.m.] -- hardcoded.
            if (entity.id == 0L) {
                TODO("fix")
//                val id = database.insert(entity) { copy(id = it) }
//                ids.add(id)
            } else {
                database.update(entity) { id == entity.id }
                ids.add(entity.id)
            }
        }

        return ids
    }
}